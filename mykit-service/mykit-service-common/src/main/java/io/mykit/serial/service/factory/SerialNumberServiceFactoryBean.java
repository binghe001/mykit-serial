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
package io.mykit.serial.service.factory;

import com.alibaba.druid.pool.DruidDataSource;
import io.mykit.serial.enums.StoreType;
import io.mykit.serial.provider.impl.DbMachineSerialNumberProvider;
import io.mykit.serial.provider.impl.IpConfigurableMachineSerialNumberProvider;
import io.mykit.serial.provider.impl.PropertyMachineSerialNumberProvider;
import io.mykit.serial.service.SerialNumberService;
import io.mykit.serial.service.impl.SerialNumberServiceImpl;
import io.mykit.serial.service.config.PropLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

/**
 * @author binghe
 * @version 1.0.0
 * @description 生成bean实例的工厂类
 */
public class SerialNumberServiceFactoryBean implements FactoryBean<SerialNumberService> {
    protected final Logger log = LoggerFactory.getLogger(SerialNumberServiceFactoryBean.class);

    /**
     * 数据存储类型
     */
    private StoreType providerType;

    private long machineSerialNumber;

    private String ips;

    private String dbUrl;
    //private String dbName;
    private String dbUser;
    private String dbPassword;

    private long genMethod = -1;
    private long type = -1;
    private long version = -1;

    private SerialNumberService serialNumberService;

    public void init() {
        if (providerType == null) {
            log.error("The type of SerialNumber service is mandatory.");
            throw new IllegalArgumentException("The type of SerialNumber service is mandatory.");
        }
        switch (providerType) {
            case PROPERTY:
                serialNumberService = constructPropertySerialNumberService(machineSerialNumber);
                break;
            case IP_CONFIGURABLE:
                serialNumberService = constructIpConfigurableSerialNumberService(ips);
                break;
            case DB:
                serialNumberService = constructDbSerialNumberService(dbUrl, dbUser, dbPassword);
                break;
        }
    }

    private SerialNumberService constructPropertySerialNumberService(long machineSerialNumber) {
        log.info("Construct Property SerialNumberService machineId {}", machineSerialNumber);

        PropertyMachineSerialNumberProvider propertyMachineSerialNumberProvider = new PropertyMachineSerialNumberProvider();
        propertyMachineSerialNumberProvider.setMachineSerialNumber(machineSerialNumber);

        SerialNumberServiceImpl serialNumberService = new SerialNumberServiceImpl();
        serialNumberService.setMachineSerialNumberProvider(propertyMachineSerialNumberProvider);
        if (genMethod != -1)
            serialNumberService.setGenMethod(genMethod);
        if (type != -1)
            serialNumberService.setType(type);
        if (version != -1)
            serialNumberService.setVersion(version);
        serialNumberService.init();
        return serialNumberService;
    }

    private SerialNumberService constructIpConfigurableSerialNumberService(String ips) {
        log.info("Construct Ip Configurable SerialNumberService ips {}", ips);

        IpConfigurableMachineSerialNumberProvider ipConfigurableMachineSerialNumberProvider = new IpConfigurableMachineSerialNumberProvider(ips);

        SerialNumberServiceImpl serialNumberService = new SerialNumberServiceImpl();
        serialNumberService.setMachineSerialNumberProvider(ipConfigurableMachineSerialNumberProvider);
        if (genMethod != -1)
            serialNumberService.setGenMethod(genMethod);
        if (type != -1)
            serialNumberService.setType(type);
        if (version != -1)
            serialNumberService.setVersion(version);
        serialNumberService.init();
        return serialNumberService;
    }

    private SerialNumberService constructDbSerialNumberService(String dbUrl, String dbUser, String dbPassword) {
        log.info("Construct Db SerialNumberService dbUrl {} dbUser {} dbPassword {}", dbUrl, dbUser, dbPassword);

        DruidDataSource dataSource = new DruidDataSource();

        String jdbcDriver = PropLoader.getStringValue(PropLoader.MYKIT_SERIAL_DRIVER);
        try {
            dataSource.setDriverClassName(jdbcDriver);
        } catch (Exception e) {
            log.error("Wrong JDBC driver {}", jdbcDriver);
            log.error("Wrong JDBC driver error: ", e);
            throw new IllegalStateException("Wrong JDBC driver ", e);
        }

        dataSource.setInitialSize(PropLoader.getIntegerValue(PropLoader.MYKIT_SERIAL_INITIALSIZE));
        dataSource.setMinIdle(PropLoader.getIntegerValue(PropLoader.MYKIT_SERIAL_MINIDLE));
        dataSource.setMaxActive(PropLoader.getIntegerValue(PropLoader.MYKIT_SERIAL_MAXACTIVE));
        dataSource.setMaxWait(PropLoader.getLongValue(PropLoader.MYKIT_SERIAL_MAXWAIT));
        dataSource.setTimeBetweenEvictionRunsMillis(PropLoader.getIntegerValue(PropLoader.MYKIT_SERIAL_TIMEBETWEENEVICTIONRUNSMILLIS));
        dataSource.setMinEvictableIdleTimeMillis(PropLoader.getLongValue(PropLoader.MYKIT_SERIAL_MINEVICTABLEIDLETIMEMILLIS));
        dataSource.setValidationQuery(PropLoader.getStringValue(PropLoader.MYKIT_SERIAL_VALIDATIONQUERY));
        dataSource.setTestWhileIdle(PropLoader.getBooleanValue(PropLoader.MYKIT_SERIAL_TESTWHILEIDLE));
        dataSource.setTestOnBorrow(PropLoader.getBooleanValue(PropLoader.MYKIT_SERIAL_TESTONBORROW));
        dataSource.setTestOnReturn(PropLoader.getBooleanValue(PropLoader.MYKIT_SERIAL_TESTONRETURN));
        dataSource.setPoolPreparedStatements(PropLoader.getBooleanValue(PropLoader.MYKIT_SERIAL_POOLPREPAREDSTATEMENTS));
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(PropLoader.getIntegerValue(PropLoader.MYKIT_SERIAL_MAXPOOLPREPAREDSTATEMENTPERCONNECTIONSIZE));
        try {
            dataSource.setFilters(PropLoader.getStringValue(PropLoader.MYKIT_SERIAL_FILTERS));
        } catch (SQLException e) {
            log.error("Wrong init DruidDataSource  {}", dataSource);
            log.error("init JDBC DruidDataSource error: ", e);
            throw new IllegalStateException("Wrong JDBC driver ", e);
        }

        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUser);
        dataSource.setPassword(dbPassword);

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setLazyInit(false);
        jdbcTemplate.setDataSource(dataSource);

        DbMachineSerialNumberProvider dbMachineSerialNumberProvider = new DbMachineSerialNumberProvider();
        dbMachineSerialNumberProvider.setJdbcTemplate(jdbcTemplate);
        dbMachineSerialNumberProvider.init();

        SerialNumberServiceImpl serialNumberService = new SerialNumberServiceImpl();
        serialNumberService.setMachineSerialNumberProvider(dbMachineSerialNumberProvider);
        if (genMethod != -1)
            serialNumberService.setGenMethod(genMethod);
        if (type != -1)
            serialNumberService.setType(type);
        if (version != -1)
            serialNumberService.setVersion(version);
        serialNumberService.init();
        return serialNumberService;
    }

    @Override
    public SerialNumberService getObject() throws Exception {
        return serialNumberService;
    }

    @Override
    public Class<?> getObjectType() {
        return SerialNumberService.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public StoreType getProviderType() {
        return providerType;
    }

    public void setProviderType(StoreType providerType) {
        this.providerType = providerType;
    }

    public long getMachineSerialNumber() {
        return machineSerialNumber;
    }

    public void setMachineSerialNumber(long machineSerialNumber) {
        this.machineSerialNumber = machineSerialNumber;
    }

    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public long getGenMethod() {
        return genMethod;
    }

    public void setGenMethod(long genMethod) {
        this.genMethod = genMethod;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public SerialNumberService getSerialNumberService() {
        return serialNumberService;
    }

    public void setSerialNumberService(SerialNumberService serialNumberService) {
        this.serialNumberService = serialNumberService;
    }
}
