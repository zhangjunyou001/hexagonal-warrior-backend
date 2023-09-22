package com.atguigu.serurity.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "User instance")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;

	private String password;

	private String nickName;

	private String salt;

	private String token;

}



