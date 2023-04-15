package com.haust.common.handler;

import com.haust.common.service.UserTokenManager;
import com.haust.common.validator.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
* @Description: 用户登录的处理类
* @Author: 韩雪磊
 * 使用mvc拦截器HandlerInterceptor+方法参数解析器HandlerMethodArgumentResolver最合适处理登录问题
*/
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
	public static final String LOGIN_TOKEN_KEY = "X-Dts-Token";
	private UserTokenManager userTokenManager;

	public UserTokenManager getUserTokenManager() {
		return userTokenManager;
	}

	public void setUserTokenManager(UserTokenManager userTokenManager) {
		this.userTokenManager = userTokenManager;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().isAssignableFrom(Integer.class)
				&& parameter.hasParameterAnnotation(LoginUser.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest request,
			WebDataBinderFactory factory) throws Exception {

		// return new Integer(1);
		String token = request.getHeader(LOGIN_TOKEN_KEY);
		if (token == null || token.isEmpty()) {
			return null;
		}

		return userTokenManager.getUserId(token);
	}
}
