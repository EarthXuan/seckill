package com.ex.seckill.common.impl;

import com.ex.seckill.common.BasePrefix;

public class UserKey extends BasePrefix{
    public UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getById=new UserKey(60,"id");
    public static UserKey getByName=new UserKey(60,"name");
}
