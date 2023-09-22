package com.atguigu.servicebase.config;

import com.atguigu.commonutils.R;
import com.atguigu.servicebase.config.exception.GuliException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * 统一异常处理类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	/**
	 * 自定义异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(GuliException.class)
	@ResponseBody
	public R error(GuliException e){
		log.error(e.getMessage());
		e.printStackTrace();
		return R.error().message(e.getMsg()).code(e.getCode());
	}

	/**
	 * 全局异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public R error(Exception e){
		e.printStackTrace();
		return R.error().message("执行了全局异常处理...");
	}


	@ExceptionHandler(IOException.class)
	@ResponseBody
	public R error(IOException e){
		e.printStackTrace();
		return R.error().message("执行了IO异常处理...");
	}

	@ExceptionHandler(ArithmeticException.class)
	@ResponseBody
	public R error(ArithmeticException e){
		e.printStackTrace();
		return R.error().message("执行了ArithmeticException异常处理...");
	}
}
