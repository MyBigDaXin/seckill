package com.justfor.seckill.controller;

import com.justfor.seckill.domain.MiaoshaOrder;
import com.justfor.seckill.domain.MiaoshaUser;
import com.justfor.seckill.domain.OrderInfo;
import com.justfor.seckill.domain.vo.GoodsVo;
import com.justfor.seckill.result.CodeMsg;
import com.justfor.seckill.result.Result;
import com.justfor.seckill.service.GoodsService;
import com.justfor.seckill.service.MiaoshaService;
import com.justfor.seckill.service.OrderService;
import com.justfor.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Constraint;

/**
 * @author lth
 * @date 2019年10月10日 16:04
 */

@Controller
@RequestMapping("/miaosha")
public class MiaoShaController {

    @Autowired
    UserService userService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;


    @RequestMapping("/do_miaosha")
    public String list(Model model,MiaoshaUser user,
                       @RequestParam("goodsId")long goodsId) {
        if(user == null) {
            return "login";
        }
        model.addAttribute("user", user);
        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0) {
            model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER.getMsg());
            return "miaosha_fail";
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null) {
            model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
            return "miaosha_fail";
        }
        //减库存 下订单 写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goods);
        return "order_detail";
    }
}
