package com.leenow.security.context;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author LeeNow WangLi
 * @date 2021/1/24 19:37
 * @description
 */
public class SecurityContextHolder {
    private static final ThreadLocal<SecurityContext> CONTEXT_HOLDER = new ThreadLocal<>();

    private SecurityContextHolder() {
    }

    @NonNull
    public static SecurityContext getContext() {
        SecurityContext securityContext = CONTEXT_HOLDER.get();
        if (securityContext == null) {
            securityContext = createEmptyContext();
            CONTEXT_HOLDER.set(securityContext);
        }
        return securityContext;
    }

    /**
     * set context.
     */
    public static void setContext(@Nullable SecurityContext context) {
        CONTEXT_HOLDER.set(context);
    }

    /**
     * remove context.
     */
    public static void clearContext() {
        CONTEXT_HOLDER.remove();
    }

    /**
     * create empty SecurityContext.
     */
    @NonNull
    private static SecurityContext createEmptyContext() {
        return new SecurityContextImpl(null);
    }
}
