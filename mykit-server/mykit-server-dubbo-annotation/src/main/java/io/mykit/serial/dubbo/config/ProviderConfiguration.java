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
package io.mykit.serial.dubbo.config;

import io.mykit.serial.dubbo.loader.PropLoader;
import io.mykit.serial.enums.StoreType;
import io.mykit.serial.service.factory.SerialNumberServiceFactoryBean;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author binghe
 * @version 1.0.0
 * @description 服务提供者配置
 */
@Configuration
@EnableDubbo(scanBasePackages = {"io.mykit.serial.dubbo"})
@PropertySource("classpath:spring/mykit-serial-server.properties")
@ComponentScan(basePackages = {"io.mykit.serial"})
public class ProviderConfiguration {

    @Bean(name = {"serialNumberService"}, initMethod = "init")
    public SerialNumberServiceFactoryBean serialNumberService(){
        SerialNumberServiceFactoryBean serialNumberServiceFactoryBean = new SerialNumberServiceFactoryBean();
        serialNumberServiceFactoryBean.setProviderType(StoreType.PROPERTY);
        serialNumberServiceFactoryBean.setGenMethod(PropLoader.getIntegerValue(PropLoader.MYKIT_SERIAL_GENMETHOD));
        serialNumberServiceFactoryBean.setMachineSerialNumber(PropLoader.getIntegerValue(PropLoader.MYKIT_SERIAL_MACHINE));
        return serialNumberServiceFactoryBean;
    }
}
