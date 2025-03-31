package com.room.controller;

import com.room.enums.RoleType;
import com.room.tools.dto.PagedResult;
import com.room.tools.dto.SelectResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/Select")
public class SelectController {

    @RequestMapping(value = "/RoleType",method = RequestMethod.POST)
    public PagedResult<SelectResult> RoleType(){

        List<SelectResult> rs = Arrays.stream(RoleType.values()).map(n -> new SelectResult(n.toString(), n.name(), n.index() + "", "")).toList();
        return PagedResult.GetInstance(rs,rs.stream().count());
    }
}