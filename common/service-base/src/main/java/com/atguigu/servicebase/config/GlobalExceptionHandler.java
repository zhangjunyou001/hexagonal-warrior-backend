package com.atguigu.servicebase.config;

import com.atguigu.commonutils.R;
import com.atguigu.servicebase.config.exception.GuliException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(GuliException.class)
	@ResponseBody
	public R error(GuliException e){
		log.error(e.getMessage());
		e.printStackTrace();
		return R.error().message(e.getMsg()).code(e.getCode());
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public R error(Exception e){
		e.printStackTrace();
		return R.error().message("Global exception handling performed...");
	}


	@ExceptionHandler(IOException.class)
	@ResponseBody
	public R error(IOException e){
		e.printStackTrace();
		return R.error().message("IOException handling performed...");
	}

	@ExceptionHandler(ArithmeticException.class)
	@ResponseBody
	public R error(ArithmeticException e){
		e.printStackTrace();
		return R.error().message("ArithmeticException handling performed...");
	}
}
