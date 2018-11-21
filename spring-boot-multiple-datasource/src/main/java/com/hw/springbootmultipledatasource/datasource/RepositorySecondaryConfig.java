package com.hw.springbootmultipledatasource.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
 * @Description 第二个myslq 数据源
 * @Author hw
 * @Date 2018/11/21 11:26
 * @Version 1.0
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactorySecondary",
        transactionManagerRef = "transactionManagerSecondary",
        basePackages = {"com.hw.springbootmultipledatasource.repository.secondary"})
public class RepositorySecondaryConfig {


    @Autowired
    @Qualifier("secondaryDS")
    private DataSource secondaryDS;


    /**
     * 该方法仅在需要使用JdbcTemplate对象时选用
     *
     * @param dataSource 注入名为secondDataSource的bean
     * @return 数据源JdbcTemplate对象
     */
    @Bean(name = "secondJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("secondaryDS") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


    @Bean(name = "entityManagerFactorySecondary")
    public LocalContainerEntityManagerFactoryBean entityManagerFactorySecondary(@Qualifier("secondJpaProperties") JpaProperties jpaProperties, EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(secondaryDS)
                .packages("com.hw.springbootmultipledatasource.entity.secondary")
                .persistenceUnit("secondaryPersistenceUnit")
                .properties(jpaProperties.getProperties())
//                .properties(jpaProperties.getHibernateProperties(new HibernateSettings()))
                .build();
    }

    @Bean(name = "entityManagerSecondary")
    public EntityManager entityManager(@Qualifier("entityManagerFactorySecondary") EntityManagerFactory factory) {
        return factory.createEntityManager();
    }

    @Bean(name = "transactionManagerSecondary")
    PlatformTransactionManager transactionManagerSecondary(@Qualifier("entityManagerFactorySecondary") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }

}
