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
package io.mykit.serial.test;

import io.mykit.serial.bean.SerialNumber;
import io.mykit.serial.service.SerialNumberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author binghe
 * @version 1.0.0
 * @description 测试客户端
 */
public class MykitSerialClient {

    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/mykit-serial-server-client.xml");
        SerialNumberService serialNumberService = (SerialNumberService)context.getBean("serialNumberService");
        for (int i = 0; i < 1000; i++){
            long serialNumber = serialNumberService.genSerialNumber();
            SerialNumber expSerialNumber = serialNumberService.expSerialNumber(serialNumber);
            System.out.println(serialNumber + " ===>>> " +expSerialNumber);
        }
    }
}
