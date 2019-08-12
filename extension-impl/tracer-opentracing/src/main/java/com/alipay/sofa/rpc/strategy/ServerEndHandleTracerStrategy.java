package com.alipay.sofa.rpc.strategy;

import com.alipay.sofa.rpc.event.Event;
import com.alipay.sofa.rpc.event.ServerEndHandleEvent;
import com.alipay.sofa.rpc.tracer.Tracers;

/**
 * @author <a href="mailto:a413650185@gmail.com">luozhiyun</a>
 */
public class ServerEndHandleTracerStrategy extends AbstractStrategy {

    /**
     * 服务端发送完策略
     * @param originEvent 事件
     */
    @Override
    public void run(Event originEvent) {
        if (originEvent.getClass() == ServerEndHandleEvent.class) {
            // 检查下状态
            Tracers.checkState();
        }
    }
}
