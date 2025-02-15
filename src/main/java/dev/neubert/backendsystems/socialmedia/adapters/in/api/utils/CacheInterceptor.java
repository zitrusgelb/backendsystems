package dev.neubert.backendsystems.socialmedia.adapters.in.api.utils;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.core.CacheControl;
import jakarta.ws.rs.core.Response;

@Interceptor
@Priority(0)
@Cached
public class CacheInterceptor {

    @AroundInvoke
    public Object cacheResponse(InvocationContext context) throws Exception {
        Object result = context.proceed();

        if (result instanceof Response response) {
            CacheControl cacheControl = new CacheControl();
            cacheControl.setMaxAge(300);
            cacheControl.setPrivate(false);

            return Response.fromResponse(response).cacheControl(cacheControl).build();
        }
        return result;
    }
}

