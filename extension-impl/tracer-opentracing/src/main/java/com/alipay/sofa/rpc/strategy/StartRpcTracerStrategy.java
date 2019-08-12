package com.alipay.sofa.rpc.strategy;

import com.alipay.sofa.rpc.event.ClientStartInvokeEvent;
import com.alipay.sofa.rpc.event.Event;
import com.alipay.sofa.rpc.tracer.Tracers;

/**
 * @author <a href="mailto:a413650185@gmail.com">luozhiyun</a>
 */
public class StartRpcTracerStrategy extends AbstractStrategy {

    @Override
    public void run(Event originEvent) {
        if (originEvent.getClass() == ClientStartInvokeEvent.class) {
            ClientStartInvokeEvent event = (ClientStartInvokeEvent) originEvent;
            Tracers.startRpc(event.getRequest());
        }
    }
}
