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
import io.mykit.serial.common.CommonUtils;
import io.mykit.serial.constants.Constants;
import io.mykit.serial.enums.SerialNumberType;
import io.mykit.serial.populator.SerialNumberPopulator;
import io.mykit.serial.populator.impl.AtomicSerialNumberPopulator;
import io.mykit.serial.populator.impl.LockSerialNumberPopulator;
import io.mykit.serial.populator.impl.SyncSerialNumberPopulator;
import io.mykit.serial.service.impl.base.AbstractSerialNumberServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author binghe
 * @version 1.0.0
 * @description 序列号服务
 */
@Service
public class SerialNumberServiceImpl extends AbstractSerialNumberServiceImpl {
    private final Logger logger = LoggerFactory.getLogger(SerialNumberServiceImpl.class);
    protected SerialNumberPopulator serialNumberPopulator;

    public SerialNumberServiceImpl(){
        super();
        initPopulator();
    }

    public SerialNumberServiceImpl(String type) {
        super(type);
        initPopulator();
    }

    public SerialNumberServiceImpl(SerialNumberType type) {
        super(type);
        initPopulator();
    }

    public void initPopulator(){
        if(serialNumberPopulator != null){
            logger.info("The " + serialNumberPopulator.getClass().getCanonicalName() + " is used.");
        } else if (CommonUtils.isPropKeyOn(Constants.SYNC_LOCK_IMPL_KEY)) {
            logger.info("The SyncSerialNumberPopulator is used.");
            serialNumberPopulator = new SyncSerialNumberPopulator();
        } else if (CommonUtils.isPropKeyOn(Constants.ATOMIC_IMPL_KEY)) {
            logger.info("The AtomicSerialNumberPopulator is used.");
            serialNumberPopulator = new AtomicSerialNumberPopulator();
        } else {
            logger.info("The default LockSerialNumberPopulator is used.");
            serialNumberPopulator = new LockSerialNumberPopulator();
        }
    }

    @Override
    protected void populateSerialNumber(SerialNumber serialNumber) {
        serialNumberPopulator.populateSerialNumber(serialNumber, this.serialNumberMeta);
    }

    public void setSerialNumberPopulator(SerialNumberPopulator serialNumberPopulator) {
        this.serialNumberPopulator = serialNumberPopulator;
    }
}
