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
import io.mykit.serial.service.config.MykitSerialServicePropertyConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.AssertJUnit;

/**
 * @author binghe
 * @version 1.0.0
 * @description 测试
 */
public class MykitSerialServicePropertyConfigTest extends BaseTest{

    @Before
    public void init(){
        applicationContext = new AnnotationConfigApplicationContext(MykitSerialServicePropertyConfig.class);
    }

    @Test
    public void testSample(){
        SerialNumberService serialNumberService = (SerialNumberService) applicationContext.getBean(SerialNumberService.class);

        long serialNumber = serialNumberService.genSerialNumber();
        SerialNumber serialNumberdo = serialNumberService.expSerialNumber(serialNumber);
        long serialNumber1 = serialNumberService.makeSerialNumber(serialNumberdo.getVersion(), serialNumberdo.getType(),
                serialNumberdo.getGenMethod(), serialNumberdo.getTime(), serialNumberdo.getSeq(),
                serialNumberdo.getMachine());
        System.err.println(serialNumber + ":" + serialNumberdo);
        AssertJUnit.assertEquals(serialNumber, serialNumber1);
    }
}
