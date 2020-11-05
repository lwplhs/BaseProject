package com.lwp.website.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/09/10/11:38
 * @Description:
 */
//@Configuration
@MapperScan(basePackages = "com.lwp.baseproject.dao",sqlSessionFactoryRef = "mssqlSqlSessionFactory")
public class MyBatisDbMssqlConfig {
    private Logger logger = LoggerFactory.getLogger(MybatisDbMasterConfig.class);

    //@Primary
    //@Bean(name = "mssqlDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.mssql")
    public DataSource dataSource(){
        logger.info("使用MSSQL数据库.....");
        return DataSourceBuilder.create().build();
    }

    //@Primary
    //@Bean(name = "mssqlSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("mssqlDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.lwp.blog.entity");
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/*Mapper.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    //@Primary
    //@Bean("mssqlTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("mssqlDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    //@Primary
    //@Bean("mssqlSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("mssqlSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
