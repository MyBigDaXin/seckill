package com.justfor.seckill.redis;

/**
 * @author lth
 * @date 2019年10月10日 14:37
 */


public class MiaoShaUserKey extends BasePrefix{
    public static final int TOKEN_EXPIRE = 3600*24 * 2;
    public static MiaoShaUserKey token = new MiaoShaUserKey(TOKEN_EXPIRE,"tk");

    public MiaoShaUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
