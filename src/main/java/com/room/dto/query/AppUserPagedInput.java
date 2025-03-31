package com.room.dto.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.room.tools.dto.PagedInput;
import lombok.Data;

@Data
public class AppUserPagedInput  extends PagedInput {
    private Integer Id;

    /**
     * 姓名
     */
    @JsonProperty("Name")
    private String Name;

    /**
     * 邮箱
     */
    @JsonProperty("Email")
    private String Email;

    /**
     * 手机号码
     */
    @JsonProperty("PhoneNumber")
    private String PhoneNumber;

    /**
     * 用户角色
     */
    @JsonProperty("RoleType")
    private Integer RoleType;

}