package com.alipay.sofa.rpc.strategy;

import com.alipay.sofa.rpc.event.Event;
import com.alipay.sofa.rpc.event.rest.RestServerReceiveEvent;
import com.alipay.sofa.rpc.tracer.sofatracer.RestTracerAdapter;

/**
 * @author <a href="mailto:a413650185@gmail.com">luozhiyun</a>
 */
public class RestServerReceiveTracerStrategy extends AbstractStrategy {
    /**
     * Rest服务端收到请求策略
     * @param originEvent 事件
     */
    @Override
    public void run(Event originEvent) {
        if (originEvent.getClass() == RestServerReceiveEvent.class) {
            RestServerReceiveEvent event = (RestServerReceiveEvent) originEvent;
            RestTracerAdapter.serverReceived(event.getRequest());
        }
    }
}
