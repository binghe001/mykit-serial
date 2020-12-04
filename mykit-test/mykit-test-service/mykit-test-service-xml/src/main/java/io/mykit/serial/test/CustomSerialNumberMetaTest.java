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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

/**
 * @author binghe
 * @version 1.0.0
 * @description 测试序列号
 */
@ContextConfiguration(locations = "/spring/mykit-serial-service-serialnumber-meta-test.xml")
public class CustomSerialNumberMetaTest extends AbstractTestNGSpringContextTests {

    @Test(groups = {"serialNumberService"})
    public void testSimple() {
        SerialNumberService serialNumberService = (SerialNumberService) applicationContext.getBean("serialNumberService");
        long id = serialNumberService.genSerialNumber();
        SerialNumber serialNumber = serialNumberService.expSerialNumber(id);
        long id1 = serialNumberService.makeSerialNumber(serialNumber.getVersion(), serialNumber.getType(),
                serialNumber.getGenMethod(), serialNumber.getTime(), serialNumber.getSeq(),
                serialNumber.getMachine());
        System.out.println(id + ":" + serialNumber);
        AssertJUnit.assertEquals(id, id1);
    }

}
