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
package io.mykit.serial.converter.impl;

import io.mykit.serial.bean.SerialNumber;
import io.mykit.serial.bean.meta.SerialNumberMeta;
import io.mykit.serial.bean.meta.SerialNumberMetaFactory;
import io.mykit.serial.converter.SerialNumberConverter;
import io.mykit.serial.enums.SerialNumberType;

/**
 * @author binghe
 * @version 1.0.0
 * @description 数据转换的实现类
 */
public class SerialNumberConverterImpl implements SerialNumberConverter {

    private SerialNumberMeta serialNumberMeta;

    public SerialNumberConverterImpl(SerialNumberType serialNumberType){
        this(SerialNumberMetaFactory.getSerialNumberMeta(serialNumberType));
    }

    public SerialNumberConverterImpl(SerialNumberMeta serialNumberMeta){
        this.serialNumberMeta = serialNumberMeta;
    }


    @Override
    public long convert(SerialNumber serialNumber) {
        return doConvert(serialNumber, serialNumberMeta);
    }

    @Override
    public SerialNumber convert(long serialNumber) {
        return doConvert(serialNumber, serialNumberMeta);
    }

    /**
     * 将SerialNumber对象转换成long型数字
     */
    protected long doConvert(SerialNumber serialNumber, SerialNumberMeta serialNumberMeta){
        long ret = 0;

        ret |= serialNumber.getMachine();

        ret |= serialNumber.getSeq() << serialNumberMeta.getSeqBitsStartPos();

        ret |= serialNumber.getTime() << serialNumberMeta.getTimeBitsStartPos();

        ret |= serialNumber.getGenMethod() << serialNumberMeta.getGenMethodBitsStartPos();

        ret |= serialNumber.getType() << serialNumberMeta.getTypeBitsStartPos();

        ret |= serialNumber.getVersion() << serialNumberMeta.getVersionBitsStartPos();

        return ret;
    }

    /**
     * 将long型数据转换成SerialNumber对象
     */
    protected SerialNumber doConvert(long serialNumber, SerialNumberMeta serialNumberMeta) {
        SerialNumber ret = new SerialNumber();

        ret.setMachine(serialNumber & serialNumberMeta.getMachineBitsMask());

        ret.setSeq((serialNumber >>> serialNumberMeta.getSeqBitsStartPos()) & serialNumberMeta.getSeqBitsMask());

        ret.setTime((serialNumber >>> serialNumberMeta.getTimeBitsStartPos()) & serialNumberMeta.getTimeBitsMask());

        ret.setGenMethod((serialNumber >>> serialNumberMeta.getGenMethodBitsStartPos()) & serialNumberMeta.getGenMethodBitsMask());

        ret.setType((serialNumber >>> serialNumberMeta.getTypeBitsStartPos()) & serialNumberMeta.getTypeBitsMask());

        ret.setVersion((serialNumber >>> serialNumberMeta.getVersionBitsStartPos()) & serialNumberMeta.getVersionBitsMask());

        return ret;
    }

    public SerialNumberMeta getSerialNumberMeta() {
        return serialNumberMeta;
    }

    public void setSerialNumberMeta(SerialNumberMeta serialNumberMeta) {
        this.serialNumberMeta = serialNumberMeta;
    }
}
