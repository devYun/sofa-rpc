package com.alipay.sofa.rpc.strategy;

import com.alipay.sofa.rpc.event.ClientAsyncReceiveEvent;
import com.alipay.sofa.rpc.event.Event;
import com.alipay.sofa.rpc.tracer.Tracers;

/**
 * @author <a href="mailto:a413650185@gmail.com">luozhiyun</a>
 */
public class ClientAsyncReceiveTracerStrategy extends AbstractStrategy {

    /**
     * 客户端异步接收到响应后处理的策略
     * @param originEvent 事件
     */
    @Override
    public void run(Event originEvent) {
        if (originEvent.getClass() == ClientAsyncReceiveEvent.class) {
            ClientAsyncReceiveEvent event = (ClientAsyncReceiveEvent) originEvent;
            // 拿出tracer信息 让入Tracer自己的上下文
            Tracers.clientAsyncReceivedPrepare();
            // 记录收到返回
            Tracers.clientReceived(event.getRequest(), event.getResponse(), event.getThrowable());
        }
    }
}
