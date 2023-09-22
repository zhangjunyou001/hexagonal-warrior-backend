package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/eduorder/paylog")
//@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        Map map=payLogService.createNative(orderNo);
        System.out.println("****Return QR Code:"+map);
        return R.ok().data(map);
    }

    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo) {

        System.out.println("##### Query order status");

        Map<String,String> map = payLogService.queryPayStatusSimulation(orderNo);

        System.out.println("*****Query Order status:"+map);
        if(map == null) {
            return R.error().message("Pay failed");
        }

        if(map.get("trade_state").equals("SUCCESS")) {// pay success

            System.out.println("##### Pay Success");

            // add history to order table, and update order status
            payLogService.updateOrdersStatus(map);
            return R.ok().message("Pay Success");
        }
        return R.ok().code(25000).message("Paying..");

    }
}

