package com.justfor.seckill.service.Impl;

import com.justfor.seckill.constant.RedisConstant;
import com.justfor.seckill.dao.UserMapper;
import com.justfor.seckill.domain.MiaoshaUser;
import com.justfor.seckill.domain.vo.LoginVo;
import com.justfor.seckill.exception.GlobalException;
import com.justfor.seckill.redis.MiaoShaUserKey;
import com.justfor.seckill.redis.RedisManager;
import com.justfor.seckill.result.CodeMsg;
import com.justfor.seckill.service.UserService;
import com.justfor.seckill.util.MD5Util;
import com.justfor.seckill.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author lth
 * @version 1.0.0
 * @date
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {



    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisManager redisManager;


    @Override
    public String login(HttpServletResponse response, LoginVo loginVo) {
        if(loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断手机号是否存在
        MiaoshaUser user = getById(Long.parseLong(mobile));
        if(user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if(!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成cookie
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);

        return token;
    }

    @Override
    public Object getByToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)) {
            return null;
        }
        MiaoshaUser user = redisManager.get(MiaoShaUserKey.token, token, MiaoshaUser.class);
        //延长有效期
        if(user != null) {
            addCookie(response, token, user);
        }
        return user;
    }

    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
        redisManager.set(MiaoShaUserKey.token,token,user);
        Cookie cookie = new Cookie(RedisConstant.COOKI_NAME_TOKEN,token);
        cookie.setMaxAge(MiaoShaUserKey.token.exprireSeonds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    private MiaoshaUser getById(long id) {
        return userMapper.getById(id);
    }
}
