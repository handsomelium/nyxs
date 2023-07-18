package com.liu.nyxs.config.datasource;

/**
 * mysql配置类
 */
/*@Configuration
@MapperScan(basePackages = {"com.liu.nyxs.mapper.mysql"}, sqlSessionTemplateRef = "mysqlSqlSessionTemplate")*/
public class MysqlConfig {


    /*private static final String MAPPER_LOCATION = "classpath:mapper/mysql/*.xml";

    @Bean(name = "mysqlDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.mysql-service")
    @Primary
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "mysqlSqlSessionFactory")
    @Primary
    public SqlSessionFactory mysqlSqlSessionFactory(@Qualifier("mysqlDataSource") DataSource dataSource) throws Exception {

        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        return bean.getObject();
    }

    @Bean(name = "mysqlTransactionManager")
    @Primary
    public DataSourceTransactionManager mysqlTransactionManager(@Qualifier("mysqlDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "mysqlSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate mysqlSqlSessionTemplate(@Qualifier("mysqlSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }*/
}

