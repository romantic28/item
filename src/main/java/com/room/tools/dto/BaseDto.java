package com.room.tools.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.room.dto.AppUserDto;
import lombok.Data;
import java.time.LocalDateTime;


/**
 * 前端和后端进行交互的公共字段 基类
 */
@Data
public class BaseDto {

    /**
     * 主表的ID
     */
    @JsonProperty("Id")
    private   Integer  Id;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("CreationTime")
    private LocalDateTime CreationTime;

    /**
     * 创建人
     */
    @JsonProperty("CreatorId")
    private Integer CreatorId;

    /**
     * 创建人的信息
     */
    @JsonProperty("CreatorAppUserDto")
    private AppUserDto CreatorAppUserDto;


    /**
     * 操作行为 比如增加 修改 中需要执行一些特定业务
     */
    @JsonProperty("Behavior")
    private String Behavior;
}
