package com.justfor.seckill.dao;

import com.justfor.seckill.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM miaosha_user WHERE id = #{id}")
    MiaoshaUser getById(long id);
}
