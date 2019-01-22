package com.ex.seckill.common;

public abstract class BasePrefix implements KeyPrefix{

    public static final int TOKEN_EXPIRE=3600;

    private int expireSeconds;

    private String prefix;

    public BasePrefix(String prefix) {
        this(-1, prefix);
        System.out.println("BasePrefix");
    }
    public BasePrefix(int expireSeconds,String prefix){
        this.expireSeconds=expireSeconds;
        this.prefix=prefix;
    }

    /**
     * 默认-1永不过期
     * @return
     */
    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className=getClass().getSimpleName();
        return className+":"+prefix;
    }
}
