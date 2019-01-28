package com.ex.seckill.common.impl;

import com.ex.seckill.common.BasePrefix;

public class AccessKey extends BasePrefix{
    public AccessKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public AccessKey(String prefix) {
        super(prefix);
    }

    public static AccessKey access=new AccessKey(5,"access");

    public static AccessKey withExpire(int expireSeconds) {
        return new AccessKey(expireSeconds, "access");
    }
}
