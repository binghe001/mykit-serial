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
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author binghe
 * @version 1.0.0
 * @description 基于IP配置的序列号
 */
public class IpConfigurableMachineSerialNumberProvider implements MachineSerialNumberProvider {
    private final Logger logger = LoggerFactory.getLogger(IpConfigurableMachineSerialNumberProvider.class);
    private long machineSerialNumber;
    private Map<String, Long> ipsMap = new HashMap<String, Long>();

    public IpConfigurableMachineSerialNumberProvider(){

    }

    public IpConfigurableMachineSerialNumberProvider(String ips){
        this.setIps(ips);
        this.init();
    }

    public void init() {
        String ip = IpUtils.getHostIp();

        if (StringUtils.isEmpty(ip)) {
            String msg = "Fail to get host IP address. Stop to initialize the IpConfigurableMachineSerialNumberProvider provider.";

            logger.error(msg);
            throw new IllegalStateException(msg);
        }

        if (!ipsMap.containsKey(ip)) {
            String msg = String.format("Fail to configure SERIAL_NUMBER for host IP address %s. Stop to initialize the IpConfigurableMachineSerialNumberProvider provider.",ip);

            logger.error(msg);
            throw new IllegalStateException(msg);
        }
        machineSerialNumber = ipsMap.get(ip);
        logger.info("IpConfigurableMachineSerialNumberProvider.init ip {} id {}", ip, machineSerialNumber);
    }

    public void setIps(String ips) {
        logger.debug("IpConfigurableMachineSerialNumberProvider ips {}", ips);
        if (!StringUtils.isEmpty(ips)) {
            String[] ipArray = ips.split(",");

            for (int i = 0; i < ipArray.length; i++) {
                ipsMap.put(ipArray[i], (long) i);
            }
        }
    }

    public long getMachineId() {
        return machineSerialNumber;
    }

    public void setMachineId(long machineId) {
        this.machineSerialNumber = machineId;
    }

    @Override
    public long getMachineSerialNumber() {
        return this.machineSerialNumber;
    }

    public void setMachineSerialNumber(long machineSerialNumber) {
        this.machineSerialNumber = machineSerialNumber;
    }
}
