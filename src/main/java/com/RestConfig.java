package com;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
 
    @Configuration
    public class RestConfig {
 
        @Bean
        public CrosConfig corsFilter() {
            CorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            CorsConfiguration config = new CorsConfiguration();
            config.addAllowedOrigin("*");
            config.addAllowedMethod(HttpMethod.GET);
            config.addAllowedMethod(HttpMethod.POST);
            config.setMaxAge((long)3600);
            ((UrlBasedCorsConfigurationSource) source).registerCorsConfiguration("/**", config);
            return new CrosConfig(source);
        }
    }