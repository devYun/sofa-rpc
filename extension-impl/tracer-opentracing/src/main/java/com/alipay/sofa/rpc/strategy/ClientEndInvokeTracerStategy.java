package com.alipay.sofa.rpc.strategy;

import com.alipay.sofa.rpc.event.ClientEndInvokeEvent;
import com.alipay.sofa.rpc.event.Event;
import com.alipay.sofa.rpc.tracer.Tracers;

/**
 * @author <a href="mailto:a413650185@gmail.com">luozhiyun</a>
 */
public class ClientEndInvokeTracerStategy extends AbstractStrategy {

    /**
     * 客户端结束调用策略
     * @param originEvent 事件
     */
    @Override
    public void run(Event originEvent) {
        if (originEvent.getClass() == ClientEndInvokeEvent.class) {
            ClientEndInvokeEvent event = (ClientEndInvokeEvent) originEvent;
            if (!event.getRequest().isAsync()) {
                // 因为同步调用重试行为，需要放到最后才能算 received
                Tracers.clientReceived(event.getRequest(), event.getResponse(), event.getThrowable());
            }
            // 检查下状态
            Tracers.checkState();
        }
    }
}
