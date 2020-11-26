/**
 * Copyright 2020-9999 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.mykit.serial.service.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.mykit.serial.provider.impl.DbMachineSerialNumberProvider;
import io.mykit.serial.service.config.PropLoader;
import io.mykit.serial.service.impl.SerialNumberServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

/**
 * @author binghe
 * @version 1.0.0
 * @description 基于数据库的配置
 */
@Configuration
public class MykitSerialServiceDbConfig {


    @Bean(name = {"datasource"}, initMethod = "init", destroyMethod = "close")
    @Primary
    public DruidDataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(PropLoader.getStringValue(PropLoader.MYKIT_SERIAL_URL));
        dataSource.setUsername(PropLoader.getStringValue(PropLoader.MYKIT_SERIAL_USER));
        dataSource.setPassword(PropLoader.getStringValue(PropLoader.MYKIT_SERIAL_PASSWORD));
        dataSource.setDriverClassName(PropLoader.getStringValue(PropLoader.MYKIT_SERIAL_DRIVER));
        dataSource.setInitialSize(PropLoader.getIntegerValue(PropLoader.MYKIT_SERIAL_INITIALSIZE));
        dataSource.setMinIdle(PropLoader.getIntegerValue(PropLoader.MYKIT_SERIAL_MINIDLE));
        dataSource.setMaxActive(PropLoader.getIntegerValue(PropLoader.MYKIT_SERIAL_MAXACTIVE));
        dataSource.setMaxWait(PropLoader.getIntegerValue(PropLoader.MYKIT_SERIAL_MAXWAIT));
        dataSource.setTimeBetweenEvictionRunsMillis(PropLoader.getIntegerValue(PropLoader.MYKIT_SERIAL_TIMEBETWEENEVICTIONRUNSMILLIS));
        dataSource.setMinEvictableIdleTimeMillis(PropLoader.getIntegerValue(PropLoader.MYKIT_SERIAL_MINEVICTABLEIDLETIMEMILLIS));
        dataSource.setValidationQuery(PropLoader.getStringValue(PropLoader.MYKIT_SERIAL_VALIDATIONQUERY));
        dataSource.setTestWhileIdle(PropLoader.getBooleanValue(PropLoader.MYKIT_SERIAL_TESTWHILEIDLE));
        dataSource.setTestOnBorrow(PropLoader.getBooleanValue(PropLoader.MYKIT_SERIAL_TESTONBORROW));
        dataSource.setTestOnReturn(PropLoader.getBooleanValue(PropLoader.MYKIT_SERIAL_TESTONRETURN));
        dataSource.setPoolPreparedStatements(PropLoader.getBooleanValue(PropLoader.MYKIT_SERIAL_POOLPREPAREDSTATEMENTS));
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(PropLoader.getIntegerValue(PropLoader.MYKIT_SERIAL_MAXPOOLPREPAREDSTATEMENTPERCONNECTIONSIZE));
        try {
            dataSource.setFilters(PropLoader.getStringValue(PropLoader.MYKIT_SERIAL_FILTERS));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    @Bean(name = {"jdbcTemplate"})
    public JdbcTemplate jdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }

    @Bean(name = {"dbMachineSerialNumberProvider"}, initMethod = "init")
    public DbMachineSerialNumberProvider dbMachineSerialNumberProvider(){
        DbMachineSerialNumberProvider dbMachineSerialNumberProvider = new DbMachineSerialNumberProvider();
        dbMachineSerialNumberProvider.setJdbcTemplate(jdbcTemplate());
        return dbMachineSerialNumberProvider;
    }

    @Bean(name = {"serialNumberService"}, initMethod = "init")
    public SerialNumberServiceImpl serialNumberService(){
        SerialNumberServiceImpl serialNumberService = new SerialNumberServiceImpl();
        serialNumberService.setMachineSerialNumberProvider(dbMachineSerialNumberProvider());
        return serialNumberService;
    }

}
