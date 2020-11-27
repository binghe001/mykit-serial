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
package io.mykit.serial.rest.controller;

import io.mykit.serial.bean.SerialNumber;
import io.mykit.serial.service.SerialNumberService;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author binghe
 * @version 1.0.0
 * @description Rest接口
 */
@RestController
public class SerialNumberController {

    protected SerialNumberService serialNumberService;

    public SerialNumberController(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/mukit-serial-rest-main.xml");
        serialNumberService = (SerialNumberService)context.getBean("serialNumberService");
    }

    @RequestMapping("/genSerialNumber")
    public long genId() {
        return serialNumberService.genSerialNumber();
    }

    @RequestMapping("/expSerialNumber")
    public SerialNumber explainId(@RequestParam(value = "serialNumber", defaultValue = "0") long serialNumber) {
        return serialNumberService.expSerialNumber(serialNumber);
    }

    @RequestMapping("/transtime")
    public String transTime(
            @RequestParam(value = "time", defaultValue = "-1") long time) {
        return serialNumberService.transTime(time).toString();
    }

    @RequestMapping("/makeSerialNumber")
    public long makeId(
            @RequestParam(value = "version", defaultValue = "-1") long version,
            @RequestParam(value = "type", defaultValue = "-1") long type,
            @RequestParam(value = "genMethod", defaultValue = "-1") long genMethod,
            @RequestParam(value = "machine", defaultValue = "-1") long machine,
            @RequestParam(value = "time", defaultValue = "-1") long time,
            @RequestParam(value = "seq", defaultValue = "-1") long seq) {

        long madeSerialNumber = -1;
        if (time == -1 || seq == -1)
            throw new IllegalArgumentException("Both time and seq are required.");
        else if (version == -1) {
            if (type == -1) {
                if (genMethod == -1) {
                    if (machine == -1) {
                        madeSerialNumber = serialNumberService.makeSerialNumber(time, seq);
                    } else {
                        madeSerialNumber = serialNumberService.makeSerialNumber(machine, time, seq);
                    }
                } else {
                    madeSerialNumber = serialNumberService.makeSerialNumber(genMethod, machine, time, seq);
                }
            } else {
                madeSerialNumber = serialNumberService.makeSerialNumber(type, genMethod, machine, time, seq);
            }
        } else {
            madeSerialNumber = serialNumberService.makeSerialNumber(version, type, genMethod, time, seq, machine);
        }
        return madeSerialNumber;
    }
}
