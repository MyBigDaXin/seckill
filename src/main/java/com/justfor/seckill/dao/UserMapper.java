package com.justfor.seckill.dao;

import com.justfor.seckill.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> get();

    MiaoshaUser getById(long id);
}
