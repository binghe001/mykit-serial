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
package io.mykit.serial.service;


import io.mykit.serial.bean.SerialNumber;

import java.util.Date;

/**
 * @author binghe
 * @version 1.0.0
 * @description 全局序列号接口
 */
public interface SerialNumberService {

    /**
     * 生成默认的long型序列号
     */
    long genSerialNumber();

    /**
     * 将long类型的序列号解析为SerialNumber对象
     */
    SerialNumber expSerialNumber(long serialNumber);

    /**
     * 生成long型序列号
     */
    long makeSerialNumber(long time, long seq);

    /**
     * 生成long型序列号
     */
    long makeSerialNumber(long time, long seq, long machine);

    /**
     * 生成long型序列号
     */
    long makeSerialNumber(long genMethod, long time, long seq, long machine);

    /**
     * 生成long型序列号
     */
    long makeSerialNumber(long type, long genMethod, long time, long seq, long machine);

    /**
     * 生成long型序列号
     */
    long makeSerialNumber(long version, long type, long genMethod, long time, long seq, long machine);

    /**
     * 将long型时间转化为Date对象
     */
    Date transTime(long time);
}
