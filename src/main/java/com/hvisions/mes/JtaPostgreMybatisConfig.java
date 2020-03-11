package com.hvisions.mes;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
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
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import javax.transaction.UserTransaction;

/**
 * @author dpeng
 * @description primary 数据源配置
 * @date 2019-08-15 13:47
 */

@Configuration
@MapperScan(basePackages = {"com.hvisions.mes.xa.postgresqlMapper"},sqlSessionTemplateRef = "postgreSessionTemplate")
public class JtaPostgreMybatisConfig {

    private static final String DATA_SOURCE = "postgreDataSource";

    private static final String TRANSACTION_MANAGER = "jtaTransaction";

    private static final String SESSION_FACTORY = "postgreSessionFactory";

    private static final String XML_PATH = "classpath*:xa/postgresqlMapper/*Mapper.xml";

    private static final String PACKAGE_PATH = "com.hvisions.mes.domain";

    private static final String SESSION_TEMPLATE = "postgreSessionTemplate";

    @Bean(name = DATA_SOURCE)
    @ConfigurationProperties(prefix = "spring.jta.atomikos.datasource.jta-postgre")
    public DataSource primaryDataSource() {
        return new AtomikosDataSourceBean();
    }

    @Bean(name = SESSION_FACTORY)
    public SqlSessionFactory primarySessionFactory(@Qualifier(DATA_SOURCE) DataSource dataSource) throws  Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        MybatisConfiguration configuration = new MybatisConfiguration();

        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDialectType(DBType.POSTGRE.getDb());

        configuration.addInterceptor(paginationInterceptor);

        bean.setDataSource(dataSource);
        // 添加XML目录
        bean.setTypeAliasesPackage(PACKAGE_PATH);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(XML_PATH));

        configuration.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(configuration);
        return bean.getObject();
    }

    @Bean(name = SESSION_TEMPLATE)
    public SqlSessionTemplate primarySessionTemplate(@Qualifier(SESSION_FACTORY) SqlSessionFactory factory) throws Exception {
        return new SqlSessionTemplate(factory);
    }

    @Bean(name = TRANSACTION_MANAGER)
    public JtaTransactionManager regTransactionManager () {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        UserTransaction userTransaction = new UserTransactionImp();
        return new JtaTransactionManager(userTransaction, userTransactionManager);
    }
}
