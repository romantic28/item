package com.room.tools.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class AppUserPagedInput  extends PagedInput {
    @JsonProperty("Id")
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