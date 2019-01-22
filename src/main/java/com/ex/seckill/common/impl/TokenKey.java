package com.ex.seckill.common.impl;

import com.ex.seckill.common.BasePrefix;

public class TokenKey extends BasePrefix{
    public TokenKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public TokenKey(String prefix) {
        super(prefix);
    }

    public static TokenKey token=new TokenKey(BasePrefix.TOKEN_EXPIRE,"token");
    public static TokenKey getByName=new TokenKey("name");
}
