package com.alipay.sofa.rpc.strategy;

import com.alipay.sofa.rpc.event.Event;
import com.alipay.sofa.rpc.event.rest.RestServerSendEvent;
import com.alipay.sofa.rpc.tracer.sofatracer.RestTracerAdapter;

/**
 * @author <a href="mailto:a413650185@gmail.com">luozhiyun</a>
 */
public class RestServerSendTracerStrategy extends AbstractStrategy {
    /**
     * Rest服务端发送完响应策略
     * @param originEvent 事件
     */
    @Override
    public void run(Event originEvent) {
        if (originEvent.getClass() == RestServerSendEvent.class) {
            RestServerSendEvent event = (RestServerSendEvent) originEvent;
            RestTracerAdapter.serverSend(event.getResponse(), event.getThrowable());
        }
    }
}
