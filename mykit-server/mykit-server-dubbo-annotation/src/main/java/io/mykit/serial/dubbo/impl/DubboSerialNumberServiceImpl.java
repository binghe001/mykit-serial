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
package io.mykit.serial.dubbo.impl;

import io.mykit.serial.bean.SerialNumber;
import io.mykit.serial.service.SerialNumberService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author binghe
 * @version 1.0.0
 * @description 发布Dubbo服务
 */
@DubboService
@Component
public class DubboSerialNumberServiceImpl implements SerialNumberService {
    @Autowired
    private SerialNumberService serialNumberService;

    @Override
    public long genSerialNumber() {
        return serialNumberService.genSerialNumber();
    }

    @Override
    public SerialNumber expSerialNumber(long serialNumber) {
        return serialNumberService.expSerialNumber(serialNumber);
    }

    @Override
    public long makeSerialNumber(long time, long seq) {
        return serialNumberService.makeSerialNumber(time, seq);
    }

    @Override
    public long makeSerialNumber(long time, long seq, long machine) {
        return serialNumberService.makeSerialNumber(time, seq, machine);
    }

    @Override
    public long makeSerialNumber(long genMethod, long time, long seq, long machine) {
        return serialNumberService.makeSerialNumber(genMethod, time, seq, machine);
    }

    @Override
    public long makeSerialNumber(long type, long genMethod, long time, long seq, long machine) {
        return serialNumberService.makeSerialNumber(type, genMethod, time, seq, machine);
    }

    @Override
    public long makeSerialNumber(long version, long type, long genMethod, long time, long seq, long machine) {
        return serialNumberService.makeSerialNumber(version, type, genMethod, time, seq, machine);
    }

    @Override
    public Date transTime(long time) {
        return serialNumberService.transTime(time);
    }
}
