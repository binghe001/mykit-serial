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
package io.mykit.serial.test;

import io.mykit.serial.common.IpUtils;

/**
 * @author binghe
 * @version 1.0.0
 * @description 测试IP地址
 */
public class IpTest {

    public static void main(String[] args){
        System.out.println(IpUtils.getHostIp());
        System.out.println(IpUtils.getHostName());
        System.out.println(IpUtils.getHostIpAndHostName());
    }
}
