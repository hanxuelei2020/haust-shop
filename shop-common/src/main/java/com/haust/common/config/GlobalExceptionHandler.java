package com.haust.common.config;


import com.haust.common.util.ResponseUtil;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

/**
 * 这里是一个全局异常捕捉类，对异常进行捕捉和处理。
 */
@ControllerAdvice
@Order(value = Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler {

	/**
	 *
	 * @param e 异常对象
	 * @return 返回值是使用ResponseUtil返回一个异常状态码，（错误的请求参数）
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseBody
	public Object badArgumentHandler(IllegalArgumentException e) {
		e.printStackTrace();
		return ResponseUtil.badArgumentValue();
	}

	/**
	 * 请求参数方法不对的异常，返回值仍然是请求参数异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseBody
	public Object badArgumentHandler(MethodArgumentTypeMismatchException e) {
		e.printStackTrace();
		return ResponseUtil.badArgumentValue();
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseBody
	public Object badArgumentHandler(MissingServletRequestParameterException e) {
		e.printStackTrace();
		return ResponseUtil.badArgumentValue();
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody
	public Object badArgumentHandler(HttpMessageNotReadableException e) {
		e.printStackTrace();
		return ResponseUtil.badArgumentValue();
	}

	/**
	 * 验证没有通过异常，即穿过来的数据可能存在空值
	 * @param e
	 * @return
	 */
	@ExceptionHandler(ValidationException.class)
	@ResponseBody
	public Object badArgumentHandler(ValidationException e) {
		e.printStackTrace();
		if (e instanceof ConstraintViolationException exs) {
			Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
			for (ConstraintViolation<?> item : violations) {
				String message = ((PathImpl) item.getPropertyPath()).getLeafNode().getName() + item.getMessage();
				return ResponseUtil.fail(402, message);
			}
		}
		return ResponseUtil.badArgumentValue();
	}

	/**
	 * 全局异常处理器
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Object seriousHandler(Exception e) {
		e.printStackTrace();
		return ResponseUtil.serious();
	}
}
