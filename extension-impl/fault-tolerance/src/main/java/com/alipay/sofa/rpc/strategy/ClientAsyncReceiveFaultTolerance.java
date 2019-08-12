package com.alipay.sofa.rpc.strategy;

import com.alipay.sofa.rpc.client.ProviderInfo;
import com.alipay.sofa.rpc.client.aft.FaultToleranceConfigManager;
import com.alipay.sofa.rpc.client.aft.InvocationStat;
import com.alipay.sofa.rpc.client.aft.InvocationStatFactory;
import com.alipay.sofa.rpc.config.ConsumerConfig;
import com.alipay.sofa.rpc.event.ClientAsyncReceiveEvent;
import com.alipay.sofa.rpc.event.Event;

/**
 * @author <a href="mailto:a413650185@gmail.com">luozhiyun</a>
 */
public class ClientAsyncReceiveFaultTolerance extends AbstractStrategy {
    /**
     * 客户端异步容错策略
     * @param originEvent 事件
     */
    @Override
    public void run(Event originEvent) {
        if (originEvent.getClass() == ClientAsyncReceiveEvent.class) {
            if (!FaultToleranceConfigManager.isEnable()) {
                return;
            }
            // 异步结果
            ClientAsyncReceiveEvent event = (ClientAsyncReceiveEvent) originEvent;
            ConsumerConfig consumerConfig = event.getConsumerConfig();
            ProviderInfo providerInfo = event.getProviderInfo();
            InvocationStat result = InvocationStatFactory.getInvocationStat(consumerConfig, providerInfo);
            if (result != null) {
                result.invoke();
                Throwable t = event.getThrowable();
                if (t != null) {
                    result.catchException(t);
                }
            }
        }
    }
}
