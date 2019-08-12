package com.alipay.sofa.rpc.strategy;

import com.alipay.sofa.rpc.event.ClientBeforeSendEvent;
import com.alipay.sofa.rpc.event.Event;
import com.alipay.sofa.rpc.tracer.Tracers;

/**
 * @author <a href="mailto:a413650185@gmail.com">luozhiyun</a>
 */
public class ClientBeforeSendTracerStrategy extends AbstractStrategy {

    /**
     * 客户端发送前处理策略
     * @param originEvent 事件
     */
    @Override
    public void run(Event originEvent) {
        if (originEvent.getClass() == ClientBeforeSendEvent.class) {
            ClientBeforeSendEvent event = (ClientBeforeSendEvent) originEvent;
            Tracers.clientBeforeSend(event.getRequest());
        }
    }
}
