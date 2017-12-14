package ma.hiddenfounders.codingchallenge.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = { "ma.hiddenfounders.codingchallenge.security.entity" })
@EnableJpaRepositories(basePackages = { "ma.hiddenfounders.codingchallenge.security.repository" })
public class JPAConfig {

   private static final String ENTITYMANAGER_PACKAGES_TO_SCAN = "ma.hiddenfounders.codingchallenge.security";

   @Resource
   private Environment environment;

   @Bean
   @Primary
   @ConfigurationProperties(prefix = "spring.datasource")
   public DataSourceProperties dataSourceProperties() {
      return new DataSourceProperties();
   }

   @Bean(name = "dataSource")
   public DataSource dataSource() {
      DataSourceProperties dataSourceProperties = dataSourceProperties();
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
      dataSource.setUrl(dataSourceProperties.getUrl());
      dataSource.setUsername(dataSourceProperties.getUsername());
      dataSource.setPassword(dataSourceProperties.getPassword());

      return dataSource;
   }

   @Bean(name = "entityManagerFactory")
   public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
      LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
      entityManagerFactoryBean.setDataSource(dataSource());
      entityManagerFactoryBean.setPackagesToScan(new String[] { ENTITYMANAGER_PACKAGES_TO_SCAN });
      entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
      entityManagerFactoryBean.setJpaProperties(hibernateProperties());

      return entityManagerFactoryBean;
   }

   @Bean
   public JpaVendorAdapter jpaVendorAdapter() {
      HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
      return hibernateJpaVendorAdapter;
   }

   private Properties hibernateProperties() {
      Properties properties = new Properties();
      properties.put("hibernate.dialect", environment.getRequiredProperty("spring.datasource.hibernate.dialect"));
      properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("spring.datasource.hibernate.hbm2ddl.method"));
      properties.put("hibernate.show_sql", environment.getRequiredProperty("spring.datasource.hibernate.show_sql"));
      properties.put("hibernate.format_sql", environment.getRequiredProperty("spring.datasource.hibernate.format_sql"));
      if (StringUtils.isNotEmpty(environment.getRequiredProperty("spring.datasource.defaultSchema"))) {
         properties.put("hibernate.default_schema", environment.getRequiredProperty("spring.datasource.defaultSchema"));
      }

      return properties;
   }

   @Bean
   public PlatformTransactionManager transactionManager() {
      JpaTransactionManager transactionManager = new JpaTransactionManager();
      transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());

      return transactionManager;
   }

   @Bean
   public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
      return new PersistenceExceptionTranslationPostProcessor();
   }
}