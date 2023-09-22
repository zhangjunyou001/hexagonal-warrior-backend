package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/eduorder/order")
//@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Create order
     * @return
     */
    @PostMapping("createOrder/{courseId}")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request){
        //创建订单，返回订单号
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        String orderNo=orderService.createOrders(courseId,userId);
        return R.ok().data("orderId",orderNo);
    }

    /**
     * Get order info by id
     * @param orderNo
     * @return
     */
    @GetMapping("getOrderInfo/{orderNo}")
    public R getOrderInfo(@PathVariable String orderNo){

        System.out.println("Query order info by order ID");

        QueryWrapper<Order> wrapper=new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item",order);
    }


    /**
     * Check if user already buy the course
     */
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isByCourse(@PathVariable String courseId,@PathVariable String memberId){
        QueryWrapper<Order> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);
        int count = orderService.count(wrapper);
        if (count>0){
            return true;
        }else {
            return false;
        }
    }

}

