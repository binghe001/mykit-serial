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

import java.util.Arrays;

/**
 * @author binghe
 * @version 1.0.0
 * @description 通用工具类
 */
public class CommonUtils {
    /**
     * 打开开关
     */
    public static String[] SWITCH_ON_EXP = new String[]{"ON", "TRUE", "on", "true"};
    /**
     * 关闭开关
     */
    public static String[] SWITCH_OFF_EXP = new String[]{"OFF", "FALSE", "off", "false"};

    public static boolean isOn(String swtch) {
        return Arrays.asList(SWITCH_ON_EXP).contains(swtch);
    }

    public static boolean isPropKeyOn(String key) {
        String prop = System.getProperty(key);
        return Arrays.asList(SWITCH_ON_EXP).contains(prop);
    }
}
