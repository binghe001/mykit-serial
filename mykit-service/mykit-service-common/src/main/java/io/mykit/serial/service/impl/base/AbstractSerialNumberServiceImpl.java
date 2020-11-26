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
package io.mykit.serial.service.impl.base;

import io.mykit.serial.bean.SerialNumber;
import io.mykit.serial.bean.meta.SerialNumberMeta;
import io.mykit.serial.bean.meta.SerialNumberMetaFactory;
import io.mykit.serial.common.TimeUtils;
import io.mykit.serial.constants.Constants;
import io.mykit.serial.converter.SerialNumberConverter;
import io.mykit.serial.converter.impl.SerialNumberConverterImpl;
import io.mykit.serial.enums.SerialNumberType;
import io.mykit.serial.provider.MachineSerialNumberProvider;
import io.mykit.serial.service.SerialNumberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author binghe
 * @version 1.0.0
 * @description 抽象序列号服务
 */
public abstract class AbstractSerialNumberServiceImpl implements SerialNumberService {
    private final Logger logger = LoggerFactory.getLogger(AbstractSerialNumberServiceImpl.class);

    protected long machineSerialNumber = -1;
    protected long genMethod = 0;
    protected long type = 0;
    protected long version = 0;

    protected SerialNumberType serialNumberType;
    protected SerialNumberMeta serialNumberMeta;

    protected SerialNumberConverter serialNumberConverter;

    protected MachineSerialNumberProvider machineSerialNumberProvider;

    public AbstractSerialNumberServiceImpl() {
        serialNumberType = SerialNumberType.MAX_PEAK;
    }

    public AbstractSerialNumberServiceImpl(String type) {
        serialNumberType = SerialNumberType.parse(type);
    }

    public AbstractSerialNumberServiceImpl(SerialNumberType serialNumberType) {
        this.serialNumberType = serialNumberType;
    }

    public void init() {
        this.machineSerialNumber = machineSerialNumberProvider.getMachineSerialNumber();

        if (machineSerialNumber < 0) {
            logger.error("The machine serialNumber is not configured properly so that mykit-serial Service refuses to start.");
            throw new IllegalStateException("The machine serialNumber is not configured properly so that mykit-serial Service refuses to start.");

        }
        if(this.serialNumberMeta == null){
            setSerialNumberMeta(SerialNumberMetaFactory.getSerialNumberMeta(serialNumberType));
            setType(serialNumberType.value());
        } else {
            if(this.serialNumberMeta.getTimeBits() == Constants.MAX_PEAK_TIME_BITS){
                setType(Constants.MAX_PEAK_INT);
            } else if(this.serialNumberMeta.getTimeBits() == Constants.MIN_GRANULARITY_TIME_BITS){
                setType(Constants.MIN_GRANULARITY_INT);
            } else {
                throw new RuntimeException("Init Error. The time bits in IdMeta should be set to 30 or 40!");
            }
        }
        setSerialNumberConverter(new SerialNumberConverterImpl(this.serialNumberMeta));
    }

    @Override
    public long genSerialNumber() {
        SerialNumber serialNumber = new SerialNumber();
        serialNumber.setMachine(machineSerialNumber);
        serialNumber.setGenMethod(genMethod);
        serialNumber.setType(type);
        serialNumber.setVersion(version);
        //调用抽象方法
        populateSerialNumber(serialNumber);

        long ret = serialNumberConverter.convert(serialNumber);

        // Use trace because it cause low performance
        if (logger.isTraceEnabled())
            logger.trace(String.format("serialNumber: %s => %d", serialNumber, ret));

        return ret;
    }

    protected abstract void populateSerialNumber(SerialNumber serialNumber);

    @Override
    public Date transTime(final long time) {
        if (serialNumberType == SerialNumberType.MAX_PEAK) {
            return new Date(time * 1000 + TimeUtils.EPOCH);
        } else if (serialNumberType == SerialNumberType.MIN_GRANULARITY) {
            return new Date(time + TimeUtils.EPOCH);
        }
        return null;
    }

    @Override
    public SerialNumber expSerialNumber(long serialNumber) {
        return serialNumberConverter.convert(serialNumber);
    }

    @Override
    public long makeSerialNumber(long time, long seq) {
        return makeSerialNumber(time, seq, machineSerialNumber);
    }

    @Override
    public long makeSerialNumber(long time, long seq, long machine) {
        return makeSerialNumber(genMethod, time, seq, machine);
    }

    @Override
    public long makeSerialNumber(long genMethod, long time, long seq, long machine) {
        return makeSerialNumber(type, genMethod, time, seq, machine);
    }

    @Override
    public long makeSerialNumber(long type, long genMethod, long time, long seq, long machine) {
        return makeSerialNumber(version, type, genMethod, time, seq, machine);
    }

    @Override
    public long makeSerialNumber(long version, long type, long genMethod,long time, long seq, long machine) {
        SerialNumberType serialNumberType = SerialNumberType.parse(type);
        SerialNumber serialNumber = new SerialNumber(machine, seq, time, genMethod, type, version);
        SerialNumberConverter serialNumberConverter = new SerialNumberConverterImpl(serialNumberType);
        return serialNumberConverter.convert(serialNumber);
    }

    public void setMachineSerialNumber(long machineSerialNumber) {
        this.machineSerialNumber = machineSerialNumber;
    }

    public void setGenMethod(long genMethod) {
        this.genMethod = genMethod;
    }

    public void setType(long type) {
        this.type = type;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public void setSerialNumberType(SerialNumberType serialNumberType) {
        this.serialNumberType = serialNumberType;
    }

    public void setSerialNumberMeta(SerialNumberMeta serialNumberMeta) {
        this.serialNumberMeta = serialNumberMeta;
    }

    public void setSerialNumberConverter(SerialNumberConverter serialNumberConverter) {
        this.serialNumberConverter = serialNumberConverter;
    }

    public void setMachineSerialNumberProvider(MachineSerialNumberProvider machineSerialNumberProvider) {
        this.machineSerialNumberProvider = machineSerialNumberProvider;
    }
}
