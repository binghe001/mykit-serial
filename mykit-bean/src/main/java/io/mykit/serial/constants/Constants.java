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
package io.mykit.serial.constants;

/**
 * @author binghe
 * @version 1.0.0
 * @description 常量类
 */
public class Constants {

    /**
     * 最大峰值型
     */
    public static final String MAX_PEAK = "max-peak";
    /**
     * 最大峰值int
     */
    public static final int MAX_PEAK_INT = 0;
    /**
     * 最大峰值时间位数
     */
    public static final int MAX_PEAK_TIME_BITS = 30;

    /**
     * 最小粒度型
     */
    public static final String MIN_GRANULARITY = "min-granularity";
    /**
     * 最小粒度int
     */
    public static final int MIN_GRANULARITY_INT = 1;
    /**
     * 最小粒度时间位数
     */
    public static final int MIN_GRANULARITY_TIME_BITS = 40;

    /**
     * 使用synchronized保证线程同步
     */
    public static final String SYNC_LOCK_IMPL_KEY = "mykit.serial.sync.lock.impl.key";

    /**
     * 使用CAS保证线程同步
     */
    public static final String ATOMIC_IMPL_KEY = "mykit.serial.atomic.impl.key";
}
