package com.microservices.backend_client.mongoConf;

import com.microservices.backend_client.filter.ClientFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {
    @Bean
    public FilterRegistrationBean<ClientFilter> clientFilter() {
        FilterRegistrationBean<ClientFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ClientFilter());
        registrationBean.addUrlPatterns("/*"); // Adjust URL patterns as needed
        return registrationBean;
    }
}
