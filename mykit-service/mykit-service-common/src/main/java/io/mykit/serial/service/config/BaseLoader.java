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
package io.mykit.serial.service.config;

/**
 * @author binghe
 * @version 1.0.0
 * @description 加载数据的基础类
 */
public class BaseLoader {

    /**
     * 数据库文件
     */
    protected static final String DB_CONFIG_FILE_NAME = "mykit/serial/mykit-serial-service.properties";

    /**
     * jdbc驱动
     */
    public static final String MYKIT_SERIAL_DRIVER = "mykit.serial.driver";
    /**
     * 初始化大小
     */
    public static final String MYKIT_SERIAL_INITIALSIZE = "mykit.serial.initialSize";
    /**
     * 最小连接数
     */
    public static final String MYKIT_SERIAL_MINIDLE = "mykit.serial.minIdle";
    /**
     * 最大活跃连接数
     */
    public static final String MYKIT_SERIAL_MAXACTIVE = "mykit.serial.maxActive";
    /**
     * 最大等待时间
     */
    public static final String MYKIT_SERIAL_MAXWAIT = "mykit.serial.maxWait";
    /**
     *
     */
    public static final String MYKIT_SERIAL_TIMEBETWEENEVICTIONRUNSMILLIS = "mykit.serial.timeBetweenEvictionRunsMillis";
    /**
     *
     */
    public static final String MYKIT_SERIAL_MINEVICTABLEIDLETIMEMILLIS = "mykit.serial.minEvictableIdleTimeMillis";
    /**
     * 验证的SQL语句
     */
    public static final String MYKIT_SERIAL_VALIDATIONQUERY = "mykit.serial.validationQuery";
    /**
     *
     */
    public static final String MYKIT_SERIAL_TESTWHILEIDLE = "mykit.serial.testWhileIdle";
    /**
     *
     */
    public static final String MYKIT_SERIAL_TESTONBORROW = "mykit.serial.testOnBorrow";
    /**
     *
     */
    public static final String MYKIT_SERIAL_TESTONRETURN = "mykit.serial.testOnReturn";
    /**
     *
     */
    public static final String MYKIT_SERIAL_POOLPREPAREDSTATEMENTS = "mykit.serial.poolPreparedStatements";
    /**
     *
     */
    public static final String MYKIT_SERIAL_MAXPOOLPREPAREDSTATEMENTPERCONNECTIONSIZE = "mykit.serial.maxPoolPreparedStatementPerConnectionSize";
    /**
     *
     */
    public static final String MYKIT_SERIAL_FILTERS = "mykit.serial.filters";
    /**
     * 配置的机器id
     */
    public static final String MYKIT_SERIAL_MACHINE = "mykit.serial.machine";
    /**
     * 以逗号分隔的ip列表
     */
    public static final String MYKIT_SERIAL_IPS = "mykit.serial.ips";
    /**
     * 数据库连接
     */
    public static final String MYKIT_SERIAL_URL = "mykit.serial.url";
    /**
     * 数据库用户名
     */
    public static final String MYKIT_SERIAL_USER = "mykit.serial.user";
    /**
     * 数据库密码
     */
    public static final String MYKIT_SERIAL_PASSWORD = "mykit.serial.password";
}
