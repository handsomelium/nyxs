package com.liu.nyxs.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;


/**
 * 描述: TDengine配置类

 */
/*@Configuration
@MapperScan(basePackages = {"com.liu.nyxs.mapper.tdengine"}, sqlSessionTemplateRef = "tdengineSqlSessionTemplate")*/
public class TDengineConfig {

    /*private static final String MAPPER_LOCATION = "classpath:mapper/tdengine/*.xml";

    @Bean(name = "tDengineDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.tdengine-service")
    public DataSource tdengineDataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "tDengineSqlSessionFactory")
    public SqlSessionFactory tDengineSqlSessionFactory(@Qualifier("tDengineDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "tdengineSqlSessionTemplate")
    public SqlSessionTemplate tdengineSqlSessionTemplate(@Qualifier("tDengineSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
*/
}
