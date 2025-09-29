package com.example.oauthserver.stereotype;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.util.Arrays;

@Configuration(proxyBeanMethods = false)
public class Checking implements BeanDefinitionRegistryPostProcessor, BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
        Arrays.stream(registry.getBeanDefinitionNames())
            .forEach(System.out::println);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
        RegisteredClientRepository registeredClientRepository
    ) {
        return (args) -> {
            System.out.println(
                registeredClientRepository.findByClientId("api-visa").getTokenSettings()
                    .getSettings().getClass());
        };
    }
}
