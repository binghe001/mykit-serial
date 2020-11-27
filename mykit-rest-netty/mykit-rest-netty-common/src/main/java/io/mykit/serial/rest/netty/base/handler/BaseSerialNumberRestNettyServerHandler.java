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
package io.mykit.serial.rest.netty.base.handler;

import io.mykit.serial.bean.SerialNumber;
import io.mykit.serial.service.SerialNumberService;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpHeaders.is100ContinueExpected;
import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author binghe
 * @version 1.0.0
 * @description 序列号处理
 */
public abstract class BaseSerialNumberRestNettyServerHandler extends ChannelHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(BaseSerialNumberRestNettyServerHandler.class);

    private static final String SERIAL_NUMBER = "serialNumber";
    private static final String VERSION = "version";
    private static final String TYPE = "type";
    private static final String GENMETHOD = "genMethod";
    private static final String MACHINE = "machine";
    private static final String TIME = "time";
    private static final String SEQ = "seq";

    private static final String ACTION_GENID = "/genSerialNumber";

    private static final String ACTION_EXPID = "/expSerialNumber";

    private static final String ACTION_TRANSTIME = "/transtime";

    private static final String ACTION_MAKEID = "/makeSerialNumber";

    protected abstract SerialNumberService getSerialNumber();

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof HttpRequest))
            return;

        HttpRequest req = (HttpRequest) msg;

        if (is100ContinueExpected(req)) {
            ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
        }

        URI uri = new URI(req.getUri());

        if (log.isDebugEnabled())
            log.debug("request uri==" + uri.getPath());

        long serialNumber = -1;
        long time = -1;
        long version = -1;
        long type = -1;
        long genmethod = -1;
        long machine = -1;
        long seq = -1;

        QueryStringDecoder decoderQuery = new QueryStringDecoder(req.getUri());
        Map<String, List<String>> uriAttributes = decoderQuery.parameters();
        for (Map.Entry<String, List<String>> attr : uriAttributes.entrySet()) {
            for (String attrVal : attr.getValue()) {
                if (log.isDebugEnabled())
                    log.debug("Request Parameter: " + attr.getKey() + '=' + attrVal);

                if (SERIAL_NUMBER.equals(attr.getKey())) {
                    serialNumber = Long.parseLong(attrVal);
                } else if (TIME.equals(attr.getKey())) {
                    time = Long.parseLong(attrVal);
                } else if (VERSION.equals(attr.getKey())) {
                    version = Long.parseLong(attrVal);
                } else if (TYPE.equals(attr.getKey())) {
                    type = Long.parseLong(attrVal);
                } else if (GENMETHOD.equals(attr.getKey())) {
                    genmethod = Long.parseLong(attrVal);
                } else if (MACHINE.equals(attr.getKey())) {
                    machine = Long.parseLong(attrVal);
                } else if (SEQ.equals(attr.getKey())) {
                    seq = Long.parseLong(attrVal);
                }
            }
        }

        StringBuilder sbContent = new StringBuilder();

        if (ACTION_GENID.equals(uri.getPath())) {
            long serialNumber1l = getSerialNumber().genSerialNumber();

            if (log.isTraceEnabled())
                log.trace("Generated id: " + serialNumber1l);

            sbContent.append(serialNumber1l);
        } else if (ACTION_EXPID.equals(uri.getPath())) {
            SerialNumber serialNumbero = getSerialNumber().expSerialNumber(serialNumber);

            if (log.isTraceEnabled())
                log.trace("Explained id: " + serialNumbero);

            JSONObject jo = JSONObject.fromObject(serialNumbero);

            sbContent.append(jo);
        } else if (ACTION_TRANSTIME.equals(uri.getPath())) {
            Date date = getSerialNumber().transTime(time);

            if (log.isTraceEnabled())
                log.trace("Time: " + date);

            sbContent.append(date);
        } else if (ACTION_MAKEID.equals(uri.getPath())) {
            long madeSerialNumber = -1;

            if (time == -1 || seq == -1)
                sbContent.append("Both time and seq are required.");
            else if (version == -1) {
                if (type == -1) {
                    if (genmethod == -1) {
                        if (machine == -1) {
                            madeSerialNumber = getSerialNumber().makeSerialNumber(time, seq);
                        } else {
                            madeSerialNumber = getSerialNumber().makeSerialNumber(machine, time, seq);
                        }
                    } else {
                        madeSerialNumber = getSerialNumber().makeSerialNumber(genmethod, machine, time, seq);
                    }
                } else {
                    madeSerialNumber = getSerialNumber().makeSerialNumber(type, genmethod, machine, time, seq);
                }
            } else {
                madeSerialNumber = getSerialNumber().makeSerialNumber(version, type, genmethod, machine, time, seq);
            }

            if (log.isTraceEnabled())
                log.trace("SerialNumber: " + madeSerialNumber);

            sbContent.append(madeSerialNumber);

        } else {
            sbContent.append("\r\n");
            sbContent.append("Please input right URI:");
            sbContent.append("\r\n");
            sbContent.append("    Example 1: http://ip:port/genSerialNumber");
            sbContent.append("\r\n");
            sbContent.append("    Example 2: http://ip:port/expSerialNumber?serialNumber=?");
            sbContent.append("\r\n");
            sbContent.append("    Example 3: http://ip:port/transtime?time=?");
            sbContent.append("\r\n");
            sbContent.append("    Example 4: http://ip:port/makeSerialNumber?version=?&type=?&genmethod=?&machine=?&time=?&seq=?");

        }

        if (log.isTraceEnabled())
            log.trace("Message body: " + sbContent);

        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(sbContent.toString().getBytes(Charset.forName("UTF-8"))));

        response.headers().set(CONTENT_TYPE, "text/plain");
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());

        boolean keepAlive = isKeepAlive(req);

        if (log.isTraceEnabled())
            log.trace("Keep Alive: " + keepAlive);

        if (!keepAlive) {
            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
        } else {
            response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            ctx.write(response);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        if (log.isErrorEnabled())
            log.error("HTTP Server Error: ", cause);
        ctx.close();
    }
}
