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

import io.mykit.serial.provider.MachineSerialNumberProvider;

/**
 * @author binghe
 * @version 1.0.0
 * @description 基于配置文件的序列号提供者
 */
public class PropertyMachineSerialNumberProvider implements MachineSerialNumberProvider {
    private long machineSerialNumber;
    @Override
    public long getMachineSerialNumber() {
        return this.machineSerialNumber;
    }

    public void setMachineSerialNumber(long machineSerialNumber) {
        this.machineSerialNumber = machineSerialNumber;
    }
}
