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

import io.mykit.serial.provider.impl.PropertyMachineSerialNumberProvider;
import io.mykit.serial.service.config.PropLoader;
import io.mykit.serial.service.impl.SerialNumberServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author binghe
 * @version 1.0.0
 * @description 基于属性配置
 */
@Configuration
public class MykitSerialServicePropertyConfig {

    @Bean(name = {"propertyMachineSerialNumberProvider"})
    public PropertyMachineSerialNumberProvider propertyMachineSerialNumberProvider(){
        PropertyMachineSerialNumberProvider propertyMachineSerialNumberProvider = new PropertyMachineSerialNumberProvider();
        propertyMachineSerialNumberProvider.setMachineSerialNumber(PropLoader.getIntegerValue(PropLoader.MYKIT_SERIAL_MACHINE));
        return propertyMachineSerialNumberProvider;
    }

    @Bean(name = {"serialNumberService"}, initMethod = "init")
    public SerialNumberServiceImpl serialNumberService(){
        SerialNumberServiceImpl serialNumberService = new SerialNumberServiceImpl();
        serialNumberService.setMachineSerialNumberProvider(propertyMachineSerialNumberProvider());
        return serialNumberService;
    }

}
