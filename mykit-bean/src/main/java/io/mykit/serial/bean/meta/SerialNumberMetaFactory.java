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
package io.mykit.serial.bean.meta;

import io.mykit.serial.enums.SerialNumberType;

/**
 * @author binghe
 * @version 1.0.0
 * @description 序列号元数据工厂类
 */
public class SerialNumberMetaFactory {

    /**
     * 最大峰值型
     */
    private static final SerialNumberMeta MAX_PEAK = new SerialNumberMeta((byte) 10, (byte) 20, (byte) 30, (byte) 2, (byte) 1, (byte) 1);

    /**
     * 最小粒度型
     */
    private static final SerialNumberMeta MIN_GRANULARITY = new SerialNumberMeta((byte) 10, (byte) 10, (byte) 40, (byte) 2, (byte) 1, (byte) 1);


    /**
     * 根据类型获取序列号元数据
     */
    public static SerialNumberMeta getSerialNumberMeta(SerialNumberType type){
        return SerialNumberType.MAX_PEAK.equals(type) ? MAX_PEAK : SerialNumberType.MIN_GRANULARITY.equals(type) ? MIN_GRANULARITY : null;
    }
}
