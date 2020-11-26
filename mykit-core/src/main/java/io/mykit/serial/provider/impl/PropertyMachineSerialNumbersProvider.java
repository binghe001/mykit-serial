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
package io.mykit.serial.provider.impl;

import io.mykit.serial.provider.MachineSerialNumbersProvider;

/**
 * @author binghe
 * @version 1.0.0
 * @description 基于配置文件的多序列号提供者
 */
public class PropertyMachineSerialNumbersProvider implements MachineSerialNumbersProvider {
    private long[] machineSerialNumbers;
    private int currentIndex;
    @Override
    public long getNextMachineSerialNumber() {
        return getMachineSerialNumber();
    }

    @Override
    public long getMachineSerialNumber() {
        return machineSerialNumbers[currentIndex++ % machineSerialNumbers.length];
    }

    public void setMachineSerialNumbers(long[] machineSerialNumbers) {
        this.machineSerialNumbers = machineSerialNumbers;
    }
}
