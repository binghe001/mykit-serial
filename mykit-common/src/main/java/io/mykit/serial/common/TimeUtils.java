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
package io.mykit.serial.common;

import io.mykit.serial.enums.SerialNumberType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author binghe
 * @version 1.0.0
 * @description 通用时间工具类
 */
public class TimeUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeUtils.class);

    //TODO 优化点，可做成配置
    public static final long EPOCH = 1420041600000L;


    public static void validateTimestamp(long lastTimestamp, long timestamp) {
        if (timestamp < lastTimestamp) {
            if (LOGGER.isErrorEnabled())
                LOGGER.error(String.format("Clock moved backwards.  Refusing to generate id for %d second/milisecond.", lastTimestamp - timestamp));

            throw new IllegalStateException(String.format( "Clock moved backwards.  Refusing to generate id for %d second/milisecond.", lastTimestamp - timestamp));
        }
    }

    public static long tillNextTimeUnit(final long lastTimestamp, final SerialNumberType serialNumberType) {
        if (LOGGER.isInfoEnabled())
            LOGGER.info(String.format("Ids are used out during %d. Waiting till next second/milisencond.", lastTimestamp));

        long timestamp = TimeUtils.genTime(serialNumberType);
        while (timestamp <= lastTimestamp) {
            timestamp = TimeUtils.genTime(serialNumberType);
        }
        if (LOGGER.isInfoEnabled())
            LOGGER.info(String.format("Next second/milisencond %d is up.", timestamp));
        return timestamp;
    }

    public static long genTime(final SerialNumberType serialNumberType) {
        if (serialNumberType == SerialNumberType.MAX_PEAK)
            return (System.currentTimeMillis() - TimeUtils.EPOCH) / 1000;
        else if (serialNumberType == SerialNumberType.MIN_GRANULARITY)
            return (System.currentTimeMillis() - TimeUtils.EPOCH);
        return (System.currentTimeMillis() - TimeUtils.EPOCH) / 1000;
    }

    public static void main(String[] args){
        Date date = new Date(EPOCH);
        System.out.println(date);
    }
}
