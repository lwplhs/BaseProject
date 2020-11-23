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
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        sqlSessionFactoryBean.setMapperLocations(resolveMapperLocations());
        return sqlSessionFactoryBean.getObject();
    }

    public Resource[] resolveMapperLocations() {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        List<String> mapperLocations = new ArrayList<>();
        mapperLocations.add("classpath*:/mapper/*Mapper.xml");
        mapperLocations.add("classpath*:/mapper/business/registration/*Mapper.xml");
        List<Resource> resources = new ArrayList();
        if (mapperLocations != null) {
            for (String mapperLocation : mapperLocations) {
                try {
                    Resource[] mappers = resourceResolver.getResources(mapperLocation);
                    resources.addAll(Arrays.asList(mappers));
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return resources.toArray(new Resource[resources.size()]);
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
