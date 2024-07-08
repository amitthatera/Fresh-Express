package com.store.grocery.fresh_express.security;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;
import org.bouncycastle.operator.InputDecryptorProvider;
import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo;
import org.bouncycastle.pkcs.PKCSException;
import org.bouncycastle.pkcs.jcajce.JcePKCSPBEInputDecryptorProviderBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

@Component
public class RSAKeyReader {

    static PrivateKey readPrivateKey(String privateKeyPath, String password) throws IOException, PKCSException {
        PrivateKeyInfo pki;
        Security.addProvider(new BouncyCastleProvider());

        try (InputStream inputStream = new ClassPathResource(privateKeyPath).getInputStream();
             PEMParser pemParser = new PEMParser(new InputStreamReader(new ByteArrayInputStream(inputStream.readAllBytes())))) {

            Object object = pemParser.readObject();
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");

            if (object instanceof PKCS8EncryptedPrivateKeyInfo epki) {
                JcePKCSPBEInputDecryptorProviderBuilder builder = new JcePKCSPBEInputDecryptorProviderBuilder().setProvider("BC");
                InputDecryptorProvider idp = builder.build(password.toCharArray());
                pki = epki.decryptPrivateKeyInfo(idp);

            } else if (object instanceof PEMEncryptedKeyPair epki) {
                PEMKeyPair pkp = epki.decryptKeyPair(new JcePEMDecryptorProviderBuilder().setProvider("BC").build(password.toCharArray()));
                pki = pkp.getPrivateKeyInfo();

            } else {
                throw new PKCSException("Invalid encrypted private key class: " + object.getClass().getName());
            }

            return converter.getPrivateKey(pki);
        }
    }

    static PublicKey readPublicKey(String publicKeyPath) throws IOException {
        Security.addProvider(new BouncyCastleProvider());

        try (InputStream inputStream = new ClassPathResource(publicKeyPath).getInputStream();
             PEMParser pemParser = new PEMParser(new InputStreamReader(inputStream))) {

            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
            Object object = pemParser.readObject();

            if (object instanceof SubjectPublicKeyInfo publicKeyInfo) {
                return converter.getPublicKey(publicKeyInfo);
            } else {
                throw new IOException("Invalid public key file format");
            }

        } catch (IOException e) {
            throw new IOException("Error reading public key file", e);
        } catch (Exception e) {
            throw new RuntimeException("Error loading public key", e);
        }
    }


}
