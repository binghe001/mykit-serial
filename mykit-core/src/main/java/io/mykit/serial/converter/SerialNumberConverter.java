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
package io.mykit.serial.converter;

import io.mykit.serial.bean.SerialNumber;

/**
 * @author binghe
 * @version 1.0.0
 * @description 数据转换接口
 */
public interface SerialNumberConverter {

    /**
     * 将SerialNumber转化成long型数字
     */
    long convert(SerialNumber serialNumber);

    /**
     * 将long类型的serialNumber转换成SerialNumber对象
     */
    SerialNumber convert(long serialNumber);
}
