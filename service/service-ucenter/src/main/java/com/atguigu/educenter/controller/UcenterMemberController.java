package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(description = "登录注册")
@RestController
@RequestMapping("/educenter/member")
//@CrossOrigin
public class UcenterMemberController {

    @Autowired
    UcenterMemberService memberService;

    @PostMapping("login")
    public R longUser(@RequestBody UcenterMember ucenterMember){
        String token=memberService.login(ucenterMember);
        System.out.println("============生成一个token==========");
        System.out.println(token);
        return R.ok().data("token",token);
    }


    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        String id = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(id);
        return R.ok().data("userInfo",member);
    }


    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id){

        UcenterMember member=memberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder=new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;

    }


    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day){
        Integer count=memberService.countRegister(day);
        return R.ok().data("countRegister",count);
    }

}

