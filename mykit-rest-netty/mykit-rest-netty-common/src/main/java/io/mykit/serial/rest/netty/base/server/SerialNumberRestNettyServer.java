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
package io.mykit.serial.rest.netty.base.server;

import io.mykit.serial.rest.netty.base.handler.BaseSerialNumberRestNettyServerHandler;
import io.mykit.serial.rest.netty.base.init.SerialNumberRestNettyServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author binghe
 * @version 1.0.0
 * @description 启动类
 */
public class SerialNumberRestNettyServer implements NettyServer {
    private final Logger logger = LoggerFactory.getLogger(SerialNumberRestNettyServer.class);

    private final int port;
    private BaseSerialNumberRestNettyServerHandler handler;

    public SerialNumberRestNettyServer(int port, BaseSerialNumberRestNettyServerHandler handler) {
        this.port = port;
        this.handler = handler;
    }

    @Override
    public void startup() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 1024);
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new SerialNumberRestNettyServerInitializer(this.handler));
            Channel ch = b.bind(port).sync().channel();
            logger.info("mykit-serial RestNettyServer is started.");

            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
