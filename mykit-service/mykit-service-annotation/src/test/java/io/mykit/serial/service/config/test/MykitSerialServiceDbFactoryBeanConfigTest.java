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
package io.mykit.serial.service.config.test;

import io.mykit.serial.bean.SerialNumber;
import io.mykit.serial.service.SerialNumberService;
import io.mykit.serial.service.config.MykitSerialServiceDbFactoryBeanConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.AssertJUnit;

/**
 * @author binghe
 * @version 1.0.0
 * @description 测试
 */
public class MykitSerialServiceDbFactoryBeanConfigTest extends BaseTest{

    @Before
    public void init(){
        applicationContext = new AnnotationConfigApplicationContext(MykitSerialServiceDbFactoryBeanConfig.class);
    }

    @Test
    public void testSimple() {
        SerialNumberService serialNumberService = applicationContext.getBean(SerialNumberService.class);
        for (int i = 0; i < 2; i++) {
            long serialNumber = serialNumberService.genSerialNumber();
            SerialNumber serialNumbero = serialNumberService.expSerialNumber(serialNumber);
            long serialNumber1 = serialNumberService.makeSerialNumber(serialNumbero.getVersion(), serialNumbero.getType(),
                    serialNumbero.getGenMethod(), serialNumbero.getTime(), serialNumbero.getSeq(),
                    serialNumbero.getMachine());
            System.out.println(serialNumber + ":" + serialNumbero);

            AssertJUnit.assertEquals(serialNumber, serialNumber1);

            try {
                System.out.println("You can change the system time in 10 seconds");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
            }
        }

    }
}
