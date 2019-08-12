/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.sofa.rpc.event;

import com.alipay.sofa.rpc.strategy.AbstractStrategy;
import com.alipay.sofa.rpc.tracer.Tracers;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Subscriber of event.
 *
 * @author <a href="mailto:zhanggeng.zg@antfin.com">GengZhang</a>
 * @see EventBus
 */
public abstract class Subscriber {

    private static final ConcurrentMap<Class<? extends Event>, AbstractStrategy> STRATEGY_MAP = new ConcurrentHashMap<>();
    /**
     * 接到事件是否同步执行
     */
    protected boolean sync = true;

    /**
     * 事件订阅者
     */
    protected Subscriber() {
    }

    /**
     * 事件订阅者
     *
     * @param sync 是否同步
     */
    protected Subscriber(boolean sync) {
        this.sync = sync;
    }

    /**
     * 是否同步
     *
     * @return 是否同步
     */
    public boolean isSync() {
        return sync;
    }

    /**
     * 事件处理，请处理异常
     *
     * @param event 事件
     */
    public void onEvent(Event event){
        if (openEvent()) {
            return;
        }

        AbstractStrategy strategy = getStrategy(event.getClass());
        if (Objects.nonNull(strategy)) {
            strategy.run(event);
        }
    }

    /**
     * 是否开启事件处理
     * @return
     */
    protected abstract boolean openEvent();


    public void addStrategy(Class<? extends Event> clazz,  AbstractStrategy strategy) {
        STRATEGY_MAP.put(clazz, strategy);
    }

    public AbstractStrategy getStrategy(Class<? extends Event> clazz ) {
        return STRATEGY_MAP.get(clazz);
    }


}
