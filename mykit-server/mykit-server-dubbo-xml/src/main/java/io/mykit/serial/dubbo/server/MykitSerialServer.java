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
package io.mykit.serial.dubbo.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author binghe
 * @version 1.0.0
 * @description 启动MykitSerial Dubbo Server
 */
public class MykitSerialServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MykitSerialServer.class);
    public static void main(String[] args){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/mykit-serial-server-main.xml");
        context.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            while (!"exit".equals(br.readLine())){
                Thread.sleep(60000);
            }
        }catch (Exception e){
            LOGGER.error("启动myit-serial-dubbo-xml服务失败 {}", e);
        }
    }
}
