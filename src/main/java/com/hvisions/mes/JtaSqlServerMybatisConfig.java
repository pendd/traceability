package com.hvisions.mes;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.enums.DBType;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author dpeng
 * @description sqlServer 数据源配置
 * @date 2019-08-15 13:47
 */
@Configuration
@MapperScan(basePackages = {"com.hvisions.mes.xa.sqlServerMapper"},sqlSessionTemplateRef = "sqlServerSessionTemplate")
public class JtaSqlServerMybatisConfig {

    private static final String DATA_SOURCE = "sqlServerDataSource";

    private static final String SESSION_FACTORY = "sqlServerSession";

    private static final String XML_PATH = "classpath*:xa/sqlServerMapper/*Mapper.xml";

    private static final String PACKAGE_PATH = "com.hvisions.mes.domain";

    private static final String SESSION_TEMPLATE = "sqlServerSessionTemplate";

    @Bean(name = DATA_SOURCE)
    @ConfigurationProperties(prefix = "spring.jta.atomikos.datasource.jta-sqlserver")
    public DataSource sqlServerDataSource() {
        return new AtomikosDataSourceBean();
    }

    @Bean(name = SESSION_FACTORY)
    public SqlSessionFactory sqlServerSessionFactory(@Qualifier(DATA_SOURCE) DataSource dataSource) throws  Exception {

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();

        bean.setDataSource(dataSource);
        // 添加XML目录
        bean.setTypeAliasesPackage(PACKAGE_PATH);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(XML_PATH));

        return bean.getObject();
    }

    @Bean(name = SESSION_TEMPLATE)
    public SqlSessionTemplate sqlServerSessionTemplate(@Qualifier(SESSION_FACTORY) SqlSessionFactory factory) throws Exception {
        return new SqlSessionTemplate(factory);
    }

}
