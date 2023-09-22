package com.atguigu.serviceedu.controller;

import com.atguigu.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@Api(description="登录管理")
@RestController
@RequestMapping("/eduservice/user")
//@CrossOrigin //跨域
public class EduLoginController {


    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").
                data("name","admin").
                data("avatar","https://gitee.com/geng_kun_yuan/myimg/raw/master/img/20210918164842.png");
    }
}
