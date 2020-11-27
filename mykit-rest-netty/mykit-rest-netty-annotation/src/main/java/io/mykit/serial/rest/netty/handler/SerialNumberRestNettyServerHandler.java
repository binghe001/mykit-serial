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
package io.mykit.serial.rest.netty.handler;

import io.mykit.serial.rest.netty.base.handler.BaseSerialNumberRestNettyServerHandler;
import io.mykit.serial.rest.netty.config.SerialNumberRestConfig;
import io.mykit.serial.service.SerialNumberService;
import io.netty.channel.ChannelHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author binghe
 * @version 1.0.0
 * @description Netty 处理类
 */
@ChannelHandler.Sharable
public class SerialNumberRestNettyServerHandler extends BaseSerialNumberRestNettyServerHandler {
    private SerialNumberService serialNumberService;
    public SerialNumberRestNettyServerHandler(SerialNumberService serialNumberService){
        this.serialNumberService = serialNumberService;
    }
    @Override
    protected SerialNumberService getSerialNumber() {
        if(serialNumberService == null){
            ApplicationContext context = new AnnotationConfigApplicationContext(SerialNumberRestConfig.class);
            serialNumberService = (SerialNumberService)context.getBean("serialNumberService");
        }
        return serialNumberService;
    }
}
