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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

@Component
public class RSAKeyReader {

    private static final Logger logger = LoggerFactory.getLogger(RSAKeyReader.class);

    public static PrivateKey readPrivateKey(String privateKeyPath, String password) throws IOException, PKCSException {
        Security.addProvider(new BouncyCastleProvider());

        ClassPathResource resource = new ClassPathResource(privateKeyPath);
        if (!resource.exists()) {
            logger.error("Private key resource does not exist: {}", privateKeyPath);
            throw new IOException("Private key resource does not exist: " + privateKeyPath);
        }

        try (InputStream inputStream = resource.getInputStream();
             PEMParser pemParser = new PEMParser(new InputStreamReader(inputStream))) {

            logger.info("Reading private key from path: {}", privateKeyPath);

            Object object = pemParser.readObject();
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");

            PrivateKeyInfo pki;
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

    public static PublicKey readPublicKey(String publicKeyPath) throws IOException {
        Security.addProvider(new BouncyCastleProvider());

        ClassPathResource resource = new ClassPathResource(publicKeyPath);
        if (!resource.exists()) {
            logger.error("Public key resource does not exist: {}", publicKeyPath);
            throw new IOException("Public key resource does not exist: " + publicKeyPath);
        }

        try (InputStream inputStream = resource.getInputStream();
             PEMParser pemParser = new PEMParser(new InputStreamReader(inputStream))) {

            logger.info("Reading public key from path: {}", publicKeyPath);

            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
            Object object = pemParser.readObject();

            if (object instanceof SubjectPublicKeyInfo publicKeyInfo) {
                return converter.getPublicKey(publicKeyInfo);
            } else {
                throw new IOException("Invalid public key file format");
            }
        }
    }

}
