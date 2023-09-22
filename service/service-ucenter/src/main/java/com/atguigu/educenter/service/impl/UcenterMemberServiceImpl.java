package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.config.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    /**
     * 登录逻辑实现
     * @param ucenterMember
     * @return
     */
    @Override
    public String login(UcenterMember ucenterMember) {
        //1.获取手机号和密码
        String mobile = ucenterMember.getMobile();
        String password = ucenterMember.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001,"登录失败");
        }
        //2.判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper=new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        if (mobileMember==null){
            throw new GuliException(20001,"用户不存在");
        }
        //3.判断密码
        //3.1 输入的密码MD5加密
        String md5password = MD5.encrypt(password);
        //3.2 与数据库判断
        if (!md5password.equals(mobileMember.getPassword())){
            throw new GuliException(20001,"账户名或密码错误");
        }
        //4.判断是否禁用
        if (mobileMember.getIsDisabled()){
            throw new GuliException(20001,"该用户已禁用,请联系管理员");
        }
        //5.生成token，使用jwt
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());

        return jwtToken;
    }

    /**
     * 注册逻辑
     * @param registerVo
     */
    @Override
    public void register(RegisterVo registerVo) {
        //1.获取注册的数据
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        //2.非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
            || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)){
            throw new GuliException(20001,"必填项不能为空");
        }
        //3.验证码判断
        String rediscode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(rediscode)){
            throw new GuliException(20001,"验证码错误");
        }

        //4.手机号不能重复
        QueryWrapper<UcenterMember> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(queryWrapper);
        if (count>0){
            throw new GuliException(20001,"手机号已存在");
        }

        //5.添加到数据库
        UcenterMember ucenterMember=new UcenterMember();
        ucenterMember.setMobile(mobile);
        ucenterMember.setNickname(nickname);
        //密码加密
        ucenterMember.setPassword(MD5.encrypt(password));
        //是否禁用
        ucenterMember.setIsDisabled(false);
        //默认头像
        ucenterMember.setAvatar("https://gitee.com/geng_kun_yuan/myimg/raw/master/img/20210919115746.jpg");

        baseMapper.insert(ucenterMember);

    }

    /**
     * 根据 微信openID 查询
     * @param openid
     * @return
     */
    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper=new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }


    /**
     * 插叙某一天的登录人数
     * @return
     */
    @Override
    public Integer countRegister(String day) {
        return baseMapper.countRegister(day);
    }
}
