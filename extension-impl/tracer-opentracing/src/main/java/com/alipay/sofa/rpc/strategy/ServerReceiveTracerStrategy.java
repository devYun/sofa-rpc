package com.alipay.sofa.rpc.strategy;

import com.alipay.sofa.rpc.event.Event;
import com.alipay.sofa.rpc.event.ServerReceiveEvent;
import com.alipay.sofa.rpc.tracer.Tracers;

/**
 * @author <a href="mailto:a413650185@gmail.com">luozhiyun</a>
 */
public class ServerReceiveTracerStrategy extends AbstractStrategy {
    /**
     * 服务端接收请求策略
     * @param originEvent 事件
     */
    @Override
    public void run(Event originEvent) {
        if (originEvent.getClass() == ServerReceiveEvent.class) {
            ServerReceiveEvent event = (ServerReceiveEvent) originEvent;
            // 接到请求
            Tracers.serverReceived(event.getRequest());
        }
    }
}
