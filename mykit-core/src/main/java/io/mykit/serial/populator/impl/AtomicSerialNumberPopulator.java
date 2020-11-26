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

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author binghe
 * @version 1.0.0
 * @description 使用CAS生成全局序列号
 */
public class AtomicSerialNumberPopulator implements SerialNumberPopulator, ResetPopulator {

    /**
     * 保证序列号的修改和时间戳修改的原子性
     */
    class Variant {
        private long sequence = 0;
        private long lastTimestamp = -1;
    }

    /**
     * 使用AtomicReference类保证修改的原子性
     */
    private AtomicReference<Variant> variant = new AtomicReference<Variant>(new Variant());

    public AtomicSerialNumberPopulator(){
        super();
    }
    @Override
    public void reset() {
        variant = new AtomicReference<Variant>(new Variant());
    }

    @Override
    public void populateSerialNumber(SerialNumber serialNumber, SerialNumberMeta serialNumberMeta) {
        Variant varOld, varNew;
        long timestamp, sequence;
        while (true) {
            // Save the old variant
            varOld = variant.get();

            // populate the current variant
            timestamp = TimeUtils.genTime(SerialNumberType.parse(serialNumber.getType()));
            TimeUtils.validateTimestamp(varOld.lastTimestamp, timestamp);

            sequence = varOld.sequence;

            if (timestamp == varOld.lastTimestamp) {
                sequence++;
                sequence &= serialNumberMeta.getSeqBitsMask();
                if (sequence == 0) {
                    timestamp = TimeUtils.tillNextTimeUnit(varOld.lastTimestamp, SerialNumberType.parse(serialNumber.getType()));
                }
            } else {
                sequence = 0;
            }

            // Assign the current variant by the atomic tools
            varNew = new Variant();
            varNew.sequence = sequence;
            varNew.lastTimestamp = timestamp;

            if (variant.compareAndSet(varOld, varNew)) {
                serialNumber.setSeq(sequence);
                serialNumber.setTime(timestamp);
                break;
            }
        }
    }
}
