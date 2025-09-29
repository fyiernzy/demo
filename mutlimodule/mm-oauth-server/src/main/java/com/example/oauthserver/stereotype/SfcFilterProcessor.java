package com.example.oauthserver.stereotype;

import jakarta.servlet.Filter;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.MethodMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Arrays;
import java.util.Objects;

/*
 * A class that auto register the FilterRegistrationBean to disable filter registration in the servlet container.
 * It will register for either GenericBeanFilter or OncePerRequestFilter annotated with SfcBean.
 * This class targets to reduce the boilerplate code.
 */
@Configuration(proxyBeanMethods = false)
public class SfcFilterProcessor implements BeanDefinitionRegistryPostProcessor, BeanFactoryAware {

    private static final String REGISTRATION_BEAN = "RegistrationBean";
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) {
        this.beanFactory = Objects.requireNonNull((ConfigurableListableBeanFactory) beanFactory);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry registry) {
        Arrays.stream(registry.getBeanDefinitionNames())
            .filter(beanName -> isSfcFilter(beanName, registry))
            .filter(beanName -> !isFilterRegistrationBeanRegistered(beanName, registry))
            .forEach(beanName -> registerFilterRegistrationBean(beanName, registry));
    }

    private boolean isSfcFilter(String beanName, BeanDefinitionRegistry registry) {
        return isSfcBean(beanName, registry) && isDeclaredFilterType(beanName, registry);
    }

    private boolean isSfcBean(String beanName, BeanDefinitionRegistry registry) {
        BeanDefinition beanDefinition = registry.getBeanDefinition(beanName);
        Object source = beanDefinition.getSource();
        return source instanceof MethodMetadata methodMetadata
               && methodMetadata.isAnnotated(SfcComponentBean.class.getName());
    }

    private boolean isDeclaredFilterType(String beanName, BeanDefinitionRegistry registry) {
        BeanDefinition beanDefinition = registry.getBeanDefinition(beanName);
        Object source = beanDefinition.getSource();
        if (source instanceof MethodMetadata methodMetadata) {
            try {
                ClassLoader classLoader = beanFactory.getBeanClassLoader();
                Class<?> type = ClassUtils.forName(methodMetadata.getReturnTypeName(), classLoader);
                return isDeclaredFilterType(type);
            } catch (ClassNotFoundException ignored) {
                return false;
            }
        }
        return false;
    }

    private boolean isDeclaredFilterType(@Nullable Class<?> type) {
        return type != null && (OncePerRequestFilter.class.isAssignableFrom(type)
                                || GenericFilterBean.class.isAssignableFrom(type));
    }

    private boolean isFilterRegistrationBeanRegistered(String beanName,
                                                       BeanDefinitionRegistry registry) {
        return registry.containsBeanDefinition(filterRegistrationBeanName(beanName));
    }

    private void registerFilterRegistrationBean(String beanName, BeanDefinitionRegistry registry) {
        String frbName = filterRegistrationBeanName(beanName);
        BeanDefinition frbDefinition = BeanDefinitionBuilder
            .genericBeanDefinition(FilterRegistrationBean.class,
                () -> {
                    Filter filter = (Filter) beanFactory.getBean(beanName);
                    var filterRegistrationBean = new FilterRegistrationBean<>(filter);
                    filterRegistrationBean.setEnabled(false);
                    return filterRegistrationBean;
                })
            .getBeanDefinition();
        registry.registerBeanDefinition(frbName, frbDefinition);
    }

    private String filterRegistrationBeanName(String beanName) {
        return beanName + REGISTRATION_BEAN;
    }
}
