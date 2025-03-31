package com.room.controller;


import com.room.dto.AppUserDto;
import com.room.service.IAppUserService;
import com.room.tools.BaseContext;
import com.room.tools.Extension;
import com.room.tools.dto.*;
import com.room.tools.exception.CustomException;
import lombok.SneakyThrows;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2024-11-10
 */
@RestController
@RequestMapping("/user")
public class AppUserController {

    @Autowired
    private IAppUserService appUserService;


    /**
     * 创建或者修改
     * */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public AppUserDto CreateOrEdit(@RequestBody AppUserDto input) throws Exception {

        return appUserService.CreateOrEdit(input);

    }
    /**
     * 用户模块删除
     */
    @RequestMapping(value = "/Delete",method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input){
        appUserService.Delete(input);
    }
    /**
     * 用户模块批量删除
     */
    @RequestMapping(value = "/BatchDelete",method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput idsInput){
        appUserService.BatchDelete(idsInput);
    }
    /**
     * 登录
     */
    @RequestMapping(value = "/SignIn",method = RequestMethod.POST)
    public ResponseData<String> SignIn(@RequestBody AppUserDto Input ){
        String token = appUserService.SignIn(Input);
        return ResponseData.GetResponseDataInstance(token,"成功",true);
    }

    /**
     * 用户分页查询接口
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<AppUserDto> List(@RequestBody AppUserPagedInput input) {

        return appUserService.List(input);
    }
    /**
     * 查询单个对用户
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    public AppUserDto Get(@RequestBody AppUserPagedInput input)
    {
        return appUserService.Get(input);
    }
    /**
     * 获取用户信息
     */
    @SneakyThrows
    @RequestMapping(value = "/GetByToken", method = RequestMethod.POST)
    public AppUserDto GetByToken(@RequestHeader("Authorization") String token) {
        try {
            System.out.println("Received token: " + token);
            Integer userId = BaseContext.getCurrentUserDto().getUserId();
            System.out.println("User ID: " + userId);
            AppUserPagedInput queryInput = new AppUserPagedInput();
            queryInput.setId(userId);
            AppUserDto appUserDto = appUserService.Get(queryInput);
            System.out.println("AppUserDto: " + appUserDto);
            if (Extension.isNullOrZero(appUserDto.getId())) {
                throw new CustomException("该token不属于该系统");
            }
            return appUserDto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统异常,请联系管理员");
        }
    }
}
