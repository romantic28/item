package com.room.service;

import com.room.dto.AppUserDto;
import com.room.entity.AppUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.room.tools.dto.AppUserPagedInput;
import com.room.tools.dto.IdInput;
import com.room.tools.dto.IdsInput;
import com.room.tools.dto.PagedResult;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.InvocationTargetException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-11-10
 */
public interface IAppUserService extends IService<AppUser> {
    public AppUserDto CreateOrEdit(AppUserDto input);
    /**
     * 用户模块删除
     * */
    public void Delete(IdInput input);
    /**
     * 用户模块批量删除
     */
    public void BatchDelete(IdsInput input);

    /**
     * 用户的分页查询方法接口定义
     */
    public PagedResult<AppUserDto> List(AppUserPagedInput input);

    /**
     * 登录
     */
    public String SignIn(AppUserDto input);
    /**
     * 查询单个对象
     */
    public AppUserDto Get(AppUserPagedInput input);
}
