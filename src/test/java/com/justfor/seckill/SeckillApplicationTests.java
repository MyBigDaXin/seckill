package com.justfor.seckill;

import com.justfor.seckill.dao.GoodsDao;
import com.justfor.seckill.domain.vo.GoodsVo;
import com.justfor.seckill.service.GoodsService;
import com.justfor.seckill.util.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillApplicationTests {

	@Autowired
	GoodsService goodsService;
	@Test
	public void contextLoads() {
//		String calcPass = MD5Util.formPassToDBPass("ca96df1c6cd528a3541f80a77900272b", "123456");
//		System.out.println(calcPass);
		GoodsVo goodsVoByGoodsId = goodsService.getGoodsVoByGoodsId(1);
		System.out.println(goodsVoByGoodsId);
	}

}
