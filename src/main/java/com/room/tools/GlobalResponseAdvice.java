package com.room.tools;

import com.room.tools.dto.ResponseData;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 全局响应处理切面
 */
@ControllerAdvice
public class GlobalResponseAdvice  implements ResponseBodyAdvice<Object> {

    /**
     * 是否开启支持
     * @param returnType 返回的类型
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     *对写入body之前进行拦截拦截处理
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if(body==null){
            //如果没有返回值则说明直接成功,直接返回一个成功的包装Data
            return ResponseData.OfSuccess();
        }
        else if(body instanceof ResponseData<?>)
        {
            //如果返回本身就是ResponseData 咱们就不需要包装Data了
            return body;
        }
        else{
            //如果有返回值,咱们需要把body包装一下在返回
            return ResponseData.GetResponseDataInstance(body,"成功",true);
        }

    }
}
