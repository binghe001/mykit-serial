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
package io.mykit.serial.bean;

import java.io.Serializable;

/**
 * @author binghe
 * @version 1.0.0
 * @description 全局序列号
 */
public class SerialNumber implements Serializable {
    private static final long serialVersionUID = -3266047926837382908L;

    /**
     * 机器id
     */
    private long machine;
    /**
     * 序列号
     */
    private long seq;
    /**
     * 秒级时间或者毫秒级时间
     */
    private long time;
    /**
     * 生成方式，00：嵌入发布模式 01：中心服务器发布模式 02：REST发布模式 03：保留未用
     */
    private long genMethod;
    /**
     * 序列号类型，0：最大峰值型 1：最小粒度型
     */
    private long type;
    /**
     * 版本，0：默认值，以免转化为整型再转化回字符串被截断 1：表示扩展或者扩容中
     */
    private long version;

    public SerialNumber() {
    }

    public SerialNumber(long machine, long seq, long time, long genMethod, long type, long version) {
        this.machine = machine;
        this.seq = seq;
        this.time = time;
        this.genMethod = genMethod;
        this.type = type;
        this.version = version;
    }

    public long getMachine() {
        return machine;
    }

    public void setMachine(long machine) {
        this.machine = machine;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getGenMethod() {
        return genMethod;
    }

    public void setGenMethod(long genMethod) {
        this.genMethod = genMethod;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[");
        sb.append("machine=").append(machine).append(",");
        sb.append("seq=").append(seq).append(",");
        sb.append("time=").append(time).append(",");
        sb.append("genMethod=").append(genMethod).append(",");
        sb.append("type=").append(type).append(",");
        sb.append("version=").append(version).append("]");

        return sb.toString();
    }
}
