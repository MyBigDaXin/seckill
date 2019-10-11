package com.justfor.seckill.domain.vo;

import com.justfor.seckill.domain.OrderInfo;
import lombok.Data;

@Data
public class OrderDetailVo {
	private GoodsVo goods;
	private OrderInfo order;

}
