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
package io.mykit.serial.service.impl.test;

import io.mykit.serial.service.SerialNumberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author binghe
 * @version 1.0.0
 * @description 性能测试
 */
public class SerialNumberServicePerfTest {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring/mykit-serial-service-test.xml");

        final SerialNumberService serialNumberService = (SerialNumberService) ac.getBean("serialNumberService");

        final long[][] times = new long[100][10000];

        Thread[] threads = new Thread[100];
        for (int i = 0; i < threads.length; i++) {
            final int ip = i;
            threads[i] = new Thread(()->{
                for (int j = 0; j < 10000; j++) {
                    long t1 = System.nanoTime();
                    serialNumberService.genSerialNumber();
                    long t = System.nanoTime() - t1;
                    times[ip][j] = t;
                }
            });
        }

        long lastMilis = System.currentTimeMillis();

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
        System.out.println("QPS: " + ((1000000 / (System.currentTimeMillis() - lastMilis)) * 1000));

        long sum = 0;
        long max = 0;
        for (int i = 0; i < times.length; i++) {
            for (int j = 0; j < times[i].length; j++) {
                sum += times[i][j];

                if (times[i][j] > max)
                    max = times[i][j];
            }
        }
        System.out.println("AVG(us): " + sum / 1000 / 1000000);
        System.out.println("MAX(ms): " + max / 1000000);
    }
}
