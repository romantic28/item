package com.room.enums;

import java.util.HashMap;

public enum RoleType
{
    /**
     * 管理员
     */
    管理员(1),

    /**
     * 用户
     */
    用户(2);


    private final int index;

    RoleType(int index)
    {
        this.index = index;
    }

    public int index() {
        return index;
    }


    private static final HashMap<Integer,RoleType> MY_MAP = new HashMap<Integer,RoleType>();
    static {
        for (RoleType myEnum : values()) {
            MY_MAP.put(myEnum.index(), myEnum);
        }
    }

    public String toString() {

        return name();
    }
    public static RoleType GetEnum(Integer v)
    {
        if(v==null){
            return MY_MAP.values().stream().findFirst().get();
        }
        return MY_MAP.get(v);
    }
}
