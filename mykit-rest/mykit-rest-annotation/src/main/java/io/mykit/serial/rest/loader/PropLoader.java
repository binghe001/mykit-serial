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
package io.mykit.serial.rest.loader;

import io.mykit.serial.common.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author binghe
 * @version 1.0.0
 * @description 加载配置文件数据
 */
public class PropLoader extends BaseLoader {

    private volatile static Properties instance;

    static {
        InputStream in = PropLoader.class.getClassLoader().getResourceAsStream(DB_CONFIG_FILE_NAME);
        instance = new Properties();
        try {
            instance.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getStringValue(String key){
        if(instance == null) return "";
        return instance.getProperty(key, "");
    }

    public static Integer getIntegerValue(String key){
        String v = getStringValue(key);
        return StringUtils.isEmptyWithTrim(v) ? 0 : Integer.parseInt(v);
    }
    public static Long getLongValue(String key){
        String v = getStringValue(key);
        return StringUtils.isEmptyWithTrim(v) ? 0 : Long.parseLong(v);
    }

    public static Boolean getBooleanValue(String key){
        String v = getStringValue(key);
        return StringUtils.isEmptyWithTrim(v) ? false : Boolean.parseBoolean(v);
    }

    public static Float getFloatValue(String key) {
        String v = getStringValue(key);
        return StringUtils.isEmptyWithTrim(v) ? 0.0f : Float.parseFloat(v);
    }

}
