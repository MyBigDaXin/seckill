package com.justfor.seckill.redis;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author lth
 * @date 2019年10月10日 14:17
 */

@Data
@AllArgsConstructor
public class BasePrefix implements KeyPrefix{

    private int expireSeconds;;

    private String prefix;

    public BasePrefix(String prefix) {
        this(0,prefix);
    }

    @Override
    public int exprireSeonds() {
        return this.expireSeconds;
    }

    @Override
    public String getPrefix() {
        String simpleName = getClass().getSimpleName();
        return simpleName + ":" + prefix;
    }
}
