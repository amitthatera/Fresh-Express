package com.store.grocery.fresh_express.config;

import com.store.grocery.fresh_express.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class BeanConfiguration {

    private final UserRepository userRepository;

    public BeanConfiguration(UserRepository userRepository){
        super();
        this.userRepository = userRepository;
    }

    @Bean
    UserDetailsService getUserDetailService(){
        return username -> this.userRepository.findByEmailAddress(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Exist!!"));
    }
}
