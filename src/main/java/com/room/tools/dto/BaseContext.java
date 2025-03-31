package com.room.tools.dto;

public class BaseContext {
    private static ThreadLocal<CurrentUserDto> threadLocal = new ThreadLocal<>();
    /**
     * 设置当前的用户信息
     */
    public static void setCurrentUserDto(CurrentUserDto dto){
        threadLocal.set(dto);
    }
    /**
     * 获取当前的用户信息
     */
    public static CurrentUserDto getCurrentUserDto(){
        return threadLocal.get();
    }
}
