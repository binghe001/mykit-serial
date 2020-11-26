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
package io.mykit.serial.populator.impl;

import io.mykit.serial.bean.SerialNumber;
import io.mykit.serial.enums.SerialNumberType;
import io.mykit.serial.bean.meta.SerialNumberMeta;
import io.mykit.serial.common.TimeUtils;
import io.mykit.serial.populator.ResetPopulator;
import io.mykit.serial.populator.SerialNumberPopulator;

/**
 * @author binghe
 * @version 1.0.0
 * @description 基础实现类
 */
public abstract class BasePopulator implements SerialNumberPopulator, ResetPopulator {

    /**
     * 序列号
     */
    protected long sequence = 0;
    /**
     * 最新的时间戳
     */
    protected long lastTimestamp = -1;

    public void populateSerialNumber(SerialNumber serialNumber, SerialNumberMeta serialNumberMeta) {
        long timestamp = TimeUtils.genTime(SerialNumberType.parse(serialNumber.getType()));
        TimeUtils.validateTimestamp(lastTimestamp, timestamp);

        if (timestamp == lastTimestamp) {
            sequence++;
            sequence &= serialNumberMeta.getSeqBitsMask();
            if (sequence == 0) {
                timestamp = TimeUtils.tillNextTimeUnit(lastTimestamp, SerialNumberType.parse(serialNumber.getType()));
            }
        } else {
            lastTimestamp = timestamp;
            sequence = 0;
        }

        serialNumber.setSeq(sequence);
        serialNumber.setTime(timestamp);
    }

    public void reset() {
        this.sequence = 0;
        this.lastTimestamp = -1;
    }

}
