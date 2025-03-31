package com.room.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.room.dto.AppUserDto;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2024-11-10
 */


@Data
@TableName("AppUser")
public class AppUser extends  BaseEntity {

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
    @JsonProperty("Birth")
    private LocalDateTime Birth;

    /**
     * 用户角色
     */
    @JsonProperty("RoleType")
    private Integer RoleType;

    /**
     * 把用户实体转换成用户传输模型
     */
    public AppUserDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        AppUserDto AppUserDto = new AppUserDto();
        BeanUtils.copyProperties(this,AppUserDto);
        return AppUserDto;
    }


}
