package com.alipay.sofa.rpc.codec.luoluo;

import com.alibaba.fastjson.JSON;
import com.alipay.sofa.rpc.codec.AbstractSerializer;
import com.alipay.sofa.rpc.common.RemotingConstants;
import com.alipay.sofa.rpc.common.utils.ClassUtils;
import com.alipay.sofa.rpc.config.ConfigUniqueNameGenerator;
import com.alipay.sofa.rpc.core.exception.SofaRpcException;
import com.alipay.sofa.rpc.core.request.SofaRequest;
import com.alipay.sofa.rpc.core.response.SofaResponse;
import com.alipay.sofa.rpc.ext.Extension;
import com.alipay.sofa.rpc.transport.AbstractByteBuf;
import com.alipay.sofa.rpc.transport.ByteArrayWrapperByteBuf;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author <a href="mailto:a413650185@gmail.com">luozhiyun</a>
 */
@Extension(value = "luoluo", code = 99)
public class LuoluoSerializer extends AbstractSerializer {

    @Override
    public AbstractByteBuf encode(Object object, Map<String, String> context) throws SofaRpcException {
        if (object == null) {
            throw buildSerializeError("Unsupported null message!");
        } else if (object instanceof SofaRequest) {
            return encodeSofaRequest((SofaRequest) object, context);
        } else if (object instanceof SofaResponse) {
            return encodeSofaResponse((SofaResponse) object, context);
        } else {
            throw buildSerializeError("Unsupported null message!");
        }
    }

    private AbstractByteBuf encodeSofaResponse(SofaResponse sofaResponse, Map<String, String> context) {
        Object appResponse = sofaResponse.getAppResponse();

        return new ByteArrayWrapperByteBuf(JSON.toJSONBytes(appResponse));
    }

    private AbstractByteBuf encodeSofaRequest(SofaRequest sofaRequest, Map<String, String> context) {

        Object[] args = sofaRequest.getMethodArgs();
        if (args.length > 1) {
            throw buildSerializeError("luoluo only support one parameter!");
        }
        return new ByteArrayWrapperByteBuf(JSON.toJSONBytes(args[0]));

    }


    @Override
    public void decode(AbstractByteBuf data, Object template, Map<String, String> context) throws SofaRpcException {
        if (template == null) {
            throw buildDeserializeError("template is null!");
        } else if (template instanceof SofaRequest) {
            decodeSofaRequest(data, (SofaRequest) template, context);
        } else if (template instanceof SofaResponse) {
            decodeSofaResponse(data, (SofaResponse) template, context);
        } else {
            throw buildDeserializeError("Only support decode from SofaRequest and SofaResponse template");
        }
    }

    private void decodeSofaResponse(AbstractByteBuf data, SofaResponse sofaResponse, Map<String, String> head) {
        if (!head.isEmpty()) {
            sofaResponse.setResponseProps(head);
        }
        //获取接口名
        String targetService = head.remove(RemotingConstants.HEAD_TARGET_SERVICE);
        //获取接口被调用的方法名
        String methodName = head.remove(RemotingConstants.HEAD_METHOD_NAME);
        // 读取接口里的方法参数和返回值
        String interfaceClass = ConfigUniqueNameGenerator.getInterfaceName(targetService);
        Class clazz = ClassUtils.forName(interfaceClass, true);

        //找到我们要调用的接口的方法
        Method pbMethod = null;
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (methodName.equals(method.getName())) {
                pbMethod = method;
                break;
            }
        }
        //获取到对应方法的返回类型
        Class returnType = pbMethod.getReturnType();
        //根据参数类型转成对象；
        Object pbReq = JSON.parseObject(data.array(), returnType);
        sofaResponse.setAppResponse(pbReq);
    }

    private void decodeSofaRequest(AbstractByteBuf data, SofaRequest sofaRequest, Map<String, String> head) {
        if (head == null) {
            throw buildDeserializeError("head is null!");
        }
        // 解析request信息
        String targetService = head.remove(RemotingConstants.HEAD_TARGET_SERVICE);
        if (targetService != null) {
            sofaRequest.setTargetServiceUniqueName(targetService);
            String interfaceName = ConfigUniqueNameGenerator.getInterfaceName(targetService);
            //设置接口信息
            sofaRequest.setInterfaceName(interfaceName);
        } else {
            throw buildDeserializeError("HEAD_TARGET_SERVICE is null");
        }

        String methodName = head.remove(RemotingConstants.HEAD_METHOD_NAME);
        if (methodName != null) {
            //设置方法
            sofaRequest.setMethodName(methodName);
        } else {
            throw buildDeserializeError("HEAD_METHOD_NAME is null");
        }
        String targetApp = head.remove(RemotingConstants.HEAD_TARGET_APP);
        if (targetApp != null) {
            //设置appName
            sofaRequest.setTargetAppName(targetApp);
        }

        for (Map.Entry<String, String> entry : head.entrySet()) {
            sofaRequest.addRequestProp(entry.getKey(), entry.getValue());
        }

        // 这里还需要把需要解码的对象类型获取到
        String interfaceClass = ConfigUniqueNameGenerator.getInterfaceName(targetService);
        Class clazz = ClassUtils.forName(interfaceClass, true);

        //找到我们要调用的接口的方法
        Method pbMethod = null;
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (methodName.equals(method.getName())) {
                pbMethod = method;
                break;
            }
        }
        //获取到对应方法的参数类型
        Class[] parameterTypes = pbMethod.getParameterTypes();
        Class parameterClazz = parameterTypes[0];
        //根据参数类型转成对象；
        Object pbReq = JSON.parseObject(data.array(), parameterClazz);

        sofaRequest.setMethodArgs(new Object[] { pbReq });
        sofaRequest.setMethodArgSigs(new String[] { parameterClazz.getName() });
    }

    @Override
    public Object decode(AbstractByteBuf data, Class clazz, Map<String, String> context) throws SofaRpcException {
        return null;
    }


}
