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
package io.mykit.serial.dubbo.loader;

/**
 * @author binghe
 * @version 1.0.0
 * @description 加载数据的基础类
 */
public class BaseLoader {

    /**
     * 数据库文件
     */
    protected static final String DB_CONFIG_FILE_NAME = "spring/mykit-serial-server.properties";

    /**
     * 机器号
     */
    public static final String MYKIT_SERIAL_MACHINE = "mykit.serial.machine";
    /**
     * 生成方式
     */
    public static final String MYKIT_SERIAL_GENMETHOD = "mykit.serial.genMethod";
}
