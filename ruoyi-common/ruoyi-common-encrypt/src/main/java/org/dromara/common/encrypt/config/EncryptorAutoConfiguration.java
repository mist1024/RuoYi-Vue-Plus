package org.dromara.common.encrypt.config;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.dromara.common.encrypt.annotation.EncryptField;
import org.dromara.common.encrypt.core.EncryptorManager;
import org.dromara.common.encrypt.interceptor.MybatisDecryptInterceptor;
import org.dromara.common.encrypt.interceptor.MybatisEncryptInterceptor;
import org.dromara.common.encrypt.properties.EncryptorProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.tokenizeToStringArray;

/**
 * 加解密配置
 *
 * @author 老马
 * @version 4.6.0
 */
@AutoConfiguration(after = MybatisPlusAutoConfiguration.class)
@EnableConfigurationProperties(EncryptorProperties.class)
@ConditionalOnProperty(value = "mybatis-encryptor.enable", havingValue = "true")
@Slf4j
public class EncryptorAutoConfiguration {

    @Resource
    private EncryptorProperties properties;
    @Resource
    private MybatisPlusProperties mybatisPlusProperties;

    @Bean
    public EncryptorManager encryptorManager() {
        Map<Class<?>, Set<Field>> fieldCache = scanEncryptClasses(mybatisPlusProperties.getTypeAliasesPackage());
        return new EncryptorManager(fieldCache);
    }

    // 通过typeAliasesPackage设置的扫描包,来确定哪些实体类进行缓存
    private Map<Class<?>, Set<Field>> scanEncryptClasses(String typeAliasesPackage) {
        Map<Class<?>, Set<Field>> fieldCache = new HashMap<>();
        try {
            String[] packagePatternArray = tokenizeToStringArray(typeAliasesPackage,
                ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
            for (String packagePattern : packagePatternArray) {
                org.springframework.core.io.Resource[] resources = new PathMatchingResourcePatternResolver().getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                    + ClassUtils.convertClassNameToResourcePath(packagePattern) + "/**/*.class");
                for (org.springframework.core.io.Resource resource : resources) {
                    ClassMetadata classMetadata = new CachingMetadataReaderFactory().getMetadataReader(resource).getClassMetadata();
                    Class<?> clazz = Resources.classForName(classMetadata.getClassName());
                    Set<Field> safeFieldSetFromClazz = getSafeFieldSetFromClazz(clazz);
                    if(CollectionUtil.isNotEmpty(safeFieldSetFromClazz)) {
                        fieldCache.put(clazz, safeFieldSetFromClazz);
                    }
                }
            }
        }catch (Exception e) {
            log.error("初始化数据安全缓存是出错:{}", e.getMessage());
        }
        return fieldCache;
    }

    // 获得一个类的加密字段集合
    private Set<Field> getSafeFieldSetFromClazz(Class<?> clazz) {
        Set<Field> fieldSet = new HashSet<>();
        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            fieldSet.addAll(Arrays.asList(fields));
            clazz = clazz.getSuperclass();
        }
        fieldSet = fieldSet.stream().filter(field ->
                field.isAnnotationPresent(EncryptField.class) && field.getType() == String.class)
            .collect(Collectors.toSet());
        for (Field field : fieldSet) {
            field.setAccessible(true);
        }
        return fieldSet;
    }

    @Bean
    public MybatisEncryptInterceptor mybatisEncryptInterceptor(EncryptorManager encryptorManager) {
        return new MybatisEncryptInterceptor(encryptorManager, properties);
    }

    @Bean
    public MybatisDecryptInterceptor mybatisDecryptInterceptor(EncryptorManager encryptorManager) {
        return new MybatisDecryptInterceptor(encryptorManager, properties);
    }

}



