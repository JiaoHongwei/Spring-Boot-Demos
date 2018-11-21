package com.hw.springbootmultipledatasource.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @Description 主数据源
 * @Author hw
 * @Date 2018/11/21 11:22
 * @Version 1.0
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryPrimary",
        transactionManagerRef = "transactionManagerPrimary",
        basePackages = {"com.hw.springbootmultipledatasource.repository.primary"})//设置dao（repo）所在位置
public class RepositoryPrimaryConfig {
    @Autowired
    @Qualifier("primaryDS")
    private DataSource primaryDS;


    /**
     * 该方法仅在需要使用JdbcTemplate对象时选用
     *
     * @param dataSource 注入名为secondDataSource的bean
     * @return 数据源JdbcTemplate对象
     */
    @Bean(name = "primaryJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("primaryDS") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "entityManagerPrimary")
    @Primary
    public EntityManager entityManager(@Qualifier("entityManagerFactoryPrimary") EntityManagerFactory factory) {
        return factory.createEntityManager();
    }


    @Bean(name = "entityManagerFactoryPrimary")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary(@Qualifier("primaryJpaProperties") JpaProperties jpaProperties, EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(primaryDS)
                .packages("com.hw.springbootmultipledatasource.entity.primary")
                .persistenceUnit("primaryPersistenceUnit")
                .properties(jpaProperties.getProperties())
//                .properties(jpaProperties.getHibernateProperties(new HibernateSettings()))
                .build();
    }


    @Bean(name = "transactionManagerPrimary")
    @Primary
    PlatformTransactionManager transactionManagerPrimary(@Qualifier("entityManagerFactoryPrimary") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }

}
