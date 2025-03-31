package com.room.dto;

import com.room.entity.AppUser;
import com.room.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

@Data
public class AppUserDto extends BaseDto
{

    /**
     * 账号
     */
    @JsonProperty("UserName")
    private String UserName;
    /**
     * 密码
     */
    @JsonProperty("Password")
    private String Password;
    /**
     * 原始密码
     */
    @JsonProperty("OrginPassword")
    private String OrginPassword;


    /**
     * 邮箱
     */
    @JsonProperty("Email")
    private String Email;
    /**
     * 头像
     */
    @JsonProperty("ImageUrls")
    private String ImageUrls;
    /**
     * 名称
     */
    @JsonProperty("Name")
    private String Name;
    /**
     * 手机号码
     */
    @JsonProperty("PhoneNumber")
    private String PhoneNumber;
    /**
     * 出生年月
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("Birth")
    private LocalDateTime Birth;


    /**
     * 用户角色
     */
    @JsonProperty("RoleType")
    private Integer RoleType;

    @JsonProperty("RoleTypeFormat")
    private String RoleTypeFormat(){
        return com.room.enums.RoleType.GetEnum(RoleType).toString();
    }

    /**
     * 把用户传输模型转换成用户实体
     */
    public AppUser MapToEntity() throws InvocationTargetException, IllegalAccessException {
        AppUser AppUser= new AppUser();
        BeanUtils.copyProperties(this,AppUser);
        return AppUser;
    }
}