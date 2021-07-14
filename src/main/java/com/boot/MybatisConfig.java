package com.boot;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class MybatisConfig {

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources(mapperLocations));
        // 添加分页插件
        bean.setPlugins(new Interceptor[]{mybatisIntercepter()});
        return bean.getObject();
    }

    @Bean
    public Interceptor mybatisIntercepter() {
        MyInterceptor myInterceptor = new MyInterceptor();
        Properties properties = new Properties();
        properties.put("username", "ywj");
        properties.put("password", "yy");
        myInterceptor.setProperties(properties);
        return myInterceptor;
    }

}
