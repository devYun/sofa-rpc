package com.alipay.sofa.rpc.strategy;

import com.alipay.sofa.rpc.event.Event;
import com.alipay.sofa.rpc.event.ServerSendEvent;
import com.alipay.sofa.rpc.tracer.Tracers;

/**
 * @author <a href="mailto:a413650185@gmail.com">luozhiyun</a>
 */
public class ServerSendTracerStrategy extends AbstractStrategy {

    /**
     * 服务端发送响应策略
     * @param originEvent 事件
     */
    @Override
    public void run(Event originEvent) {

        if (originEvent.getClass() == ServerSendEvent.class) {
            // 发送响应
            ServerSendEvent event = (ServerSendEvent) originEvent;
            Tracers.serverSend(event.getRequest(), event.getResponse(), event.getThrowable());
        }
    }
}
