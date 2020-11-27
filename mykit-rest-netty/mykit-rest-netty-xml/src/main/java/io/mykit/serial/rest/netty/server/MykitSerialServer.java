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
package io.mykit.serial.rest.netty.server;

import io.mykit.serial.rest.netty.base.server.SerialNumberRestNettyServer;
import io.mykit.serial.rest.netty.handler.SerialNumberRestNettyServerHandler;
import io.mykit.serial.service.SerialNumberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author binghe
 * @version 1.0.0
 * @description 启动Netty服务
 */
public class MykitSerialServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MykitSerialServer.class);
    public static void main(String[] args){
        int port = 10002;
        if(args != null && args.length > 0){
            port = Integer.parseInt(args[0]);
        }
        try{
            ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/mykit-serial-rest-main.xml");
            SerialNumberService serialNumberService = (SerialNumberService)context.getBean("serialNumberService");
            new SerialNumberRestNettyServer(port, new SerialNumberRestNettyServerHandler(serialNumberService)).startup();
        }catch (Exception e){
            LOGGER.error("启动Netty服务异常: {}", e);
        }
    }
}
