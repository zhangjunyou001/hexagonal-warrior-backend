package com.atguigu.serviceedu.controller;

import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eduservice/user")
//@CrossOrigin
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
