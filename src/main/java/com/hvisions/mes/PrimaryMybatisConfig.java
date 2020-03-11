package com.hvisions.mes;

import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.enums.DBType;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author dpeng
 * @description primary 主数据源配置  跟分布式事务无关
 * @date 2019-08-15 13:47
 */
@Configuration
@MapperScan(basePackages = {"com.hvisions.mes.mapper"},sqlSessionTemplateRef = "primarySessionTemplate")
public class PrimaryMybatisConfig {



    private static final String DATA_SOURCE = "primaryDataSource";

    private static final String TRANSACTION_MANAGER = "primaryTransaction";

    private static final String SESSION_FACTORY = "primarySessionFactory";

    private static final String XML_PATH = "classpath*:mapper/**/*.xml";

    private static final String PACKAGE_PATH = "com.hvisions.mes.domain";

    private static final String SESSION_TEMPLATE = "primarySessionTemplate";

    @Bean(name = DATA_SOURCE)
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = SESSION_FACTORY)
    @Primary
    public SqlSessionFactory primarySessionFactory(@Qualifier(DATA_SOURCE) DataSource dataSource) throws  Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        MybatisConfiguration configuration = new MybatisConfiguration();

        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDialectType(DBType.POSTGRE.getDb());

        configuration.addInterceptor(paginationInterceptor);

        bean.setDataSource(dataSource);
        // 添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setTypeAliasesPackage(PACKAGE_PATH);
        bean.setMapperLocations(resolver.getResources(XML_PATH));

        configuration.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(configuration);
        return bean.getObject();
    }

    @Bean(name = SESSION_TEMPLATE)
    @Primary
    public SqlSessionTemplate primarySessionTemplate(@Qualifier(SESSION_FACTORY) SqlSessionFactory factory) throws Exception {
        return new SqlSessionTemplate(factory);
    }

    @Bean(name = TRANSACTION_MANAGER)
    @Primary
    public DataSourceTransactionManager primaryTransactionManager(@Qualifier(DATA_SOURCE) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
