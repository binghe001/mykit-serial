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
package io.mykit.serial.service.impl;

import io.mykit.serial.bean.SerialNumber;
import io.mykit.serial.common.TimeUtils;
import io.mykit.serial.enums.SerialNumberType;
import io.mykit.serial.populator.ResetPopulator;
import io.mykit.serial.populator.SerialNumberPopulator;
import io.mykit.serial.provider.MachineSerialNumberProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author binghe
 * @version 1.0.0
 * @description 机器序列号服务
 */
@Service
public class MachineSerialNumbersServiceImpl extends SerialNumberServiceImpl{

    private final Logger logger = LoggerFactory.getLogger(MachineSerialNumberProvider.class);

    protected long lastTimestamp = -1;

    protected Map<Long, Long> machineIdMap = new ConcurrentHashMap<Long, Long>();
    public static final String STORE_FILE_NAME = "machineSerialNumberInfo.store";
    private String storeFilePath;
    private File storeFile;
    private Lock lock = new ReentrantLock();

    @Override
    public void init() {
        if (!(this.machineSerialNumberProvider instanceof MachineSerialNumberProvider)) {
            logger.error("The machineIdProvider is not a MachineIdsProvider instance so that Vesta Service refuses to start.");
            throw new RuntimeException("The machineIdProvider is not a MachineIdsProvider instance so that Vesta Service refuses to start.");
        }
        super.init();
        initStoreFile();
        initMachineSerialNumber();
    }

    @Override
    protected void populateSerialNumber(SerialNumber serialNumber) {
        supportChangeMachineSerialNumber(serialNumber);
    }

    private void supportChangeMachineSerialNumber(SerialNumber serialNumber) {
        try {
            serialNumber.setMachine(this.machineSerialNumber);
            serialNumberPopulator.populateSerialNumber(serialNumber, this.serialNumberMeta);
            this.lastTimestamp = serialNumber.getTime();
        } catch (IllegalStateException e) {
            logger.warn("Clock moved backwards, change MachineSerialNumberPopulator and reset MachineSerialNumberProvider");
            lock.lock();
            try {
                if (serialNumber.getMachine() == this.machineSerialNumber) {
                    changeMachineSerialNumber();
                    resetSerialNumberPopulator();
                }
            } finally {
                lock.unlock();
            }
            supportChangeMachineSerialNumber(serialNumber);
        }
    }

    protected void changeMachineSerialNumber() {
        this.machineIdMap.put(this.machineSerialNumber, this.lastTimestamp);
        storeInFile();
        initMachineSerialNumber();
    }

    protected void resetSerialNumberPopulator() {
        if (serialNumberPopulator instanceof ResetPopulator) {
            ((ResetPopulator) serialNumberPopulator).reset();
        } else {
            try {
                SerialNumberPopulator newSerialNumberPopulator = this.serialNumberPopulator.getClass().newInstance();
                this.serialNumberPopulator = newSerialNumberPopulator;
            } catch (InstantiationException e1) {
                throw new RuntimeException("Reset SerialNumberPopulator <[" + this.serialNumberPopulator.getClass().getCanonicalName() + "]> instance error", e1);
            } catch (IllegalAccessException e1) {
                throw new RuntimeException("Reset SerialNumberPopulator <[" + this.serialNumberPopulator.getClass().getCanonicalName() + "]> instance error", e1);
            }
        }
    }

    protected void initStoreFile() {
        if (storeFilePath == null || storeFilePath.length() == 0) {
            storeFilePath = System.getProperty("user.dir") + File.separator + STORE_FILE_NAME;
        }
        try {
            logger.info("machineSerialNumber info store in <[" + storeFilePath + "]>");
            storeFile = new File(storeFilePath);
            if (storeFile.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(storeFile));
                String line = reader.readLine();
                while (line != null && line.length() > 0) {
                    String[] kvs = line.split(":");
                    if (kvs.length == 2) {
                        this.machineIdMap.put(Long.parseLong(kvs[0]), Long.parseLong(kvs[1]));
                    } else {
                        throw new IllegalArgumentException(storeFile.getAbsolutePath() + " has illegal value <[" + line + "]>");
                    }
                    line = reader.readLine();
                }
                reader.close();
            }
        } catch (FileNotFoundException e) {
            logger.error("initStoreFile throws FileNotFoundException: {}", e);
        } catch (IOException e) {
            logger.error("initStoreFile throws IOException: {}", e);
        }
    }

    protected void initMachineSerialNumber() {
        long startSerialNumber = this.machineSerialNumber;
        long newMachineSerialNumber = this.machineSerialNumber;
        while(true) {
            if (this.machineIdMap.containsKey(newMachineSerialNumber)) {
                long timestamp = TimeUtils.genTime(SerialNumberType.parse(this.type));
                if (this.machineIdMap.get(newMachineSerialNumber) < timestamp) {
                    this.machineSerialNumber = newMachineSerialNumber;
                    break;
                } else {
                    newMachineSerialNumber = ((MachineSerialNumberProvider)this.machineSerialNumberProvider).getMachineSerialNumber();
                }
                if(newMachineSerialNumber == startSerialNumber){
                    throw new RuntimeException("No machineSerialNumber is available");
                }
            } else {
                this.machineSerialNumber = newMachineSerialNumber;
                break;
            }
        }
    }

    protected void storeInFile() {
        Writer writer = null;
        try {
            writer = new FileWriter(storeFile, false);
            for (Map.Entry<Long, Long> entry : this.machineIdMap.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            logger.error("Write machineSerialNumber info to File<[" + storeFile.getAbsolutePath() + "]> error");
            throw new RuntimeException("Write machineSerialNumber info to File<[" + storeFile.getAbsolutePath() + "]> error");
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public void setStoreFilePath(String storeFilePath) {
        this.storeFilePath = storeFilePath;
    }
}
