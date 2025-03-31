package com.room.tools.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class IdInput {
    @JsonProperty("Id")
    private Integer Id;
}
