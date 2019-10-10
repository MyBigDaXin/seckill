package com.justfor.seckill.service;

import com.justfor.seckill.domain.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;


public interface UserService {
    String login(HttpServletResponse response, @Valid LoginVo loginVo);

    Object getByToken(HttpServletResponse response, String token);
}
