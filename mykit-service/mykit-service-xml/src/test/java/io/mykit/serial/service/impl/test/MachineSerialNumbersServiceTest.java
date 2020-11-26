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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

/**
 * @author binghe
 * @version 1.0.0
 * @description 测试机器序列号
 */
@ContextConfiguration(locations = "/spring/mykit-serial-service-machine-serialnumbers-test.xml")
public class MachineSerialNumbersServiceTest extends AbstractTestNGSpringContextTests {

    @Test(groups = {"serialNumberService"})
    public void testSimple() {
        SerialNumberService serialNumberService = (SerialNumberService) applicationContext.getBean("serialNumbersService");
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
