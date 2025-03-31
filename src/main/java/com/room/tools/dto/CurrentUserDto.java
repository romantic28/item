package com.room.tools.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.room.enums.RoleType;
import lombok.Data;

@Data
public class CurrentUserDto {
    @JsonProperty("UserId")
    private Integer UserId;
    @JsonProperty("RoleType")
    private RoleType RoleType;
}
