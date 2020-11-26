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
package io.mykit.serial.service.impl.test;

import io.mykit.serial.bean.SerialNumber;
import io.mykit.serial.service.SerialNumberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

/**
 * @author binghe
 * @version 1.0.0
 * @description 测试使用FactoryBean生成序列号
 */
public class SerialNumberServiceFactoryBeanTest {

    @Test(groups = {"serialNumberService"})
    public void testSimple() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/mykit-serial-service-factory-bean-property-test.xml");
        SerialNumberService serialNumberService = (SerialNumberService) applicationContext.getBean("serialNumberService");

        long serialNumber = serialNumberService.genSerialNumber();
        SerialNumber serialNumberdo = serialNumberService.expSerialNumber(serialNumber);
        long serialNumber1 = serialNumberService.makeSerialNumber(serialNumberdo.getVersion(), serialNumberdo.getType(),
                serialNumberdo.getGenMethod(), serialNumberdo.getTime(), serialNumberdo.getSeq(),
                serialNumberdo.getMachine());
        System.err.println(serialNumber + ":" + serialNumberdo);
        AssertJUnit.assertEquals(serialNumber, serialNumber1);
    }

    @Test(groups = {"serialNumberService"})
    public void testIpConfigurable() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/mykit-serial-service-factory-bean-ip-configurable-test.xml");
        SerialNumberService serialNumberService = (SerialNumberService) applicationContext.getBean("serialNumberServiceIpConfigurable");

        long serialNumber = serialNumberService.genSerialNumber();
        SerialNumber serialNumbero = serialNumberService.expSerialNumber(serialNumber);
        long serialNumber1 = serialNumberService.makeSerialNumber(serialNumbero.getVersion(), serialNumbero.getType(),
                serialNumbero.getGenMethod(), serialNumbero.getTime(), serialNumbero.getSeq(),
                serialNumbero.getMachine());

        System.err.println(serialNumber + ":" + serialNumbero);

        AssertJUnit.assertEquals(serialNumber, serialNumber1);
    }

    @Test(groups = {"serialNumberService"})
    public void testDb() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/mykit-serial-service-factory-bean-db-test.xml");
        SerialNumberService serialNumberService = (SerialNumberService) applicationContext.getBean("serialNumberServiceDb");

        long serialNumber = serialNumberService.genSerialNumber();
        SerialNumber serialNumbero = serialNumberService.expSerialNumber(serialNumber);
        long serialNumbero1 = serialNumberService.makeSerialNumber(serialNumbero.getVersion(), serialNumbero.getType(),
                serialNumbero.getGenMethod(), serialNumbero.getTime(), serialNumbero.getSeq(),
                serialNumbero.getMachine());

        System.err.println(serialNumber + ":" + serialNumbero);

        AssertJUnit.assertEquals(serialNumber, serialNumbero1);
    }
}
