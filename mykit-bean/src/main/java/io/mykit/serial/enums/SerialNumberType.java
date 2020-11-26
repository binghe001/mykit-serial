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
package io.mykit.serial.enums;

import io.mykit.serial.constants.Constants;

/**
 * @author binghe
 * @version 1.0.0
 * @description 序列号类型
 */
public enum SerialNumberType {

    MAX_PEAK(Constants.MAX_PEAK), MIN_GRANULARITY(Constants.MIN_GRANULARITY);

    private String name;

    private SerialNumberType(String name) {
        this.name = name;
    }

    public long value() {
        switch (this) {
            case MAX_PEAK:
                return Constants.MAX_PEAK_INT;
            case MIN_GRANULARITY:
                return Constants.MIN_GRANULARITY_INT;
            default:
                return Constants.MAX_PEAK_INT;
        }
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static SerialNumberType parse(String name) {
        return Constants.MIN_GRANULARITY.equals(name) ? MIN_GRANULARITY : Constants.MAX_PEAK.equals(name) ? MAX_PEAK : null;
    }

    public static SerialNumberType parse(long type) {
        return (type == Constants.MIN_GRANULARITY_INT) ? MIN_GRANULARITY : (type == Constants.MAX_PEAK_INT) ? MAX_PEAK : null;
    }
}
