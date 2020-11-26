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
package io.mykit.serial.provider.impl;

import io.mykit.serial.common.IpUtils;
import io.mykit.serial.provider.MachineSerialNumberProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

/**
 * @author binghe
 * @version 1.0.0
 * @description 依赖数据库的全局序列号提供者的实现类
 */
public class DbMachineSerialNumberProvider implements MachineSerialNumberProvider {
    private final Logger logger = LoggerFactory.getLogger(DbMachineSerialNumberProvider.class);
    private long machineSerialNumber;
    private JdbcTemplate jdbcTemplate;

    public DbMachineSerialNumberProvider(){

    }

    public void init(){
        String ip = IpUtils.getHostIp();
        if(StringUtils.isEmpty(ip)){
            String msg = "Fail to get host IP address. Stop to initialize the DbMachineSerialNumberProvider provider.";
            logger.error(msg);
            throw new IllegalStateException(msg);
        }
        Long serialNumber = null;
        try {
            serialNumber = jdbcTemplate.queryForObject("select SERIAL_NUMBER from DB_MACHINE_SERIAL_NUMBER_PROVIDER where IP = ?", new Object[]{ip}, Long.class);
        } catch (EmptyResultDataAccessException e) {
            // Ignore the exception
            logger.error("No allocation before for ip {}.", ip);
        }

        if (serialNumber != null) {
            machineSerialNumber = serialNumber;
            return;
        }
        logger.info( "Fail to get SERIAL_NUMBER from DB for host IP address {}. Next step try to allocate one.", ip);

        int count = jdbcTemplate.update("update DB_MACHINE_SERIAL_NUMBER_PROVIDER set IP = ? where IP is null limit 1",  ip);

        if (count <= 0 || count > 1) {
            String msg = String.format("Fail to allocte SERIAL_NUMBER for host IP address {}. The {} records are updated. Stop to initialize the DbMachineSerialNumberProvider provider.", ip, count);
            logger.error(msg);
            throw new IllegalStateException(msg);
        }

        try {
            serialNumber = jdbcTemplate.queryForObject("select SERIAL_NUMBER from DB_MACHINE_SERIAL_NUMBER_PROVIDER where IP = ?", new Object[]{ip}, Long.class);

        } catch (EmptyResultDataAccessException e) {
            // Ignore the exception
            logger.error("Fail to do allocation for ip {}.", ip);
        }

        if (serialNumber == null) {
            String msg = String.format("Fail to get SERIAL_NUMBER from DB for host IP address {} after allocation. Stop to initialize the DbMachineSerialNumberProvider provider.", ip);
            logger.error(msg);
            throw new IllegalStateException(msg);
        }
        machineSerialNumber = serialNumber;
    }

    @Override
    public long getMachineSerialNumber() {
        return machineSerialNumber;
    }

    public void setMachineSerialNumber(long machineSerialNumber) {
        this.machineSerialNumber = machineSerialNumber;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
