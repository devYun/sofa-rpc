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
package com.alipay.sofa.rpc.quickstart;

import com.alipay.sofa.rpc.context.RpcInvokeContext;
import com.alipay.sofa.rpc.log.Logger;
import com.alipay.sofa.rpc.log.LoggerFactory;

/**
 * Quick Start demo implements
 */
public class HelloServiceImpl implements HelloService {

    private final static Logger LOGGER = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String sayHello(String string) {
        LOGGER.info("Server receive: " + string);

        // 获取请求透传数据并打印
        //System.out.println("service receive reqBag -> " + RpcInvokeContext.getContext().getRequestBaggage("req_bag"));
        // 设置响应透传数据到当前线程的上下文中
        //RpcInvokeContext.getContext().putResponseBaggage("req_bag", "s2c");

        return "hello " + string + " ！";
    }
}