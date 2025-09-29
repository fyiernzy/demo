package com.example.mmecommerce.common.infra;

import com.example.mmecommerce.common.RegisterErrorCode;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ErrorCodeRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware,
    EnvironmentAware {

    private ResourceLoader resourceLoader;
    private Environment environment;

    @Override
    public void setResourceLoader(@NonNull ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(@NonNull AnnotationMetadata importingClassMetadata,
                                        @NonNull BeanDefinitionRegistry registry) {
        // 1. Resolve base packages to be scanned.
        List<String> basePackages = resolveBasePackages(importingClassMetadata, registry);

        // 2. Build scanner to scan the base packages
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(
            false, this.environment);
        scanner.setResourceLoader(this.resourceLoader);
        scanner.addIncludeFilter(new AnnotationTypeFilter(RegisterErrorCode.class));

        // 3. Scan each base packages and register the error code enum
        for (String basePackage : basePackages) {
            scanner.findCandidateComponents(basePackage).stream()
                .map(this::resolveClassType)
                .filter(this::isErrorCodeEnum)
                .forEach(candidate -> registerEnumConstantsAsBeans(candidate, registry));
        }
    }

    private Class<?> resolveClassType(BeanDefinition beanDefinition) {
        try {
            return ClassUtils.forName(
                Objects.requireNonNull(beanDefinition.getBeanClassName()),
                this.resourceLoader.getClassLoader()
            );
        } catch (ClassNotFoundException ignored) {
            return null;
        }
    }

    private List<String> resolveBasePackages(AnnotationMetadata metadata,
                                             BeanDefinitionRegistry registry) {
        List<String> bases = new ArrayList<>();
        try {
            BeanFactory beanFactory = (BeanFactory) registry;
            if (AutoConfigurationPackages.has(beanFactory)) {
                bases.addAll(AutoConfigurationPackages.get(beanFactory));
            }
        } catch (BeansException ignored) {
        }
        if (bases.isEmpty()) {
            String className = metadata.getClassName();
            bases.add(ClassUtils.getPackageName(className));
        }
        return bases;
    }

    private boolean isErrorCodeEnum(Class<?> candidate) {
        return candidate != null && candidate.isEnum() && implementsErrorCode(candidate);
    }

    private boolean implementsErrorCode(Class<?> type) {
        return ErrorCode.class.isAssignableFrom(type);
    }

    private void registerEnumConstantsAsBeans(Class<?> enumClass, BeanDefinitionRegistry registry) {
        Object[] constants = enumClass.getEnumConstants();
        for (Object constant : constants) {
            ErrorCode errorCode = (ErrorCode) constant;
            String beanName = buildBeanName(errorCode);
            if (!registry.containsBeanDefinition(beanName)) {
                BeanDefinitionBuilder b = BeanDefinitionBuilder.genericBeanDefinition(
                    ErrorCode.class, () -> errorCode);
                registry.registerBeanDefinition(beanName, b.getBeanDefinition());
            }
        }
    }

    private String buildBeanName(ErrorCode errorCode) {
        return errorCode.getName() + "#" + errorCode.getCode();
    }

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }
}
