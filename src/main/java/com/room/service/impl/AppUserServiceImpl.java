package com.room.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.room.SysConst;
import com.room.dto.AppUserDto;
import com.room.entity.AppUser;
import com.room.mapper.AppUserMapper;
import com.room.service.IAppUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.room.tools.Extension;
import com.room.tools.JWTUtils;
import com.room.tools.dto.AppUserPagedInput;
import com.room.tools.dto.IdInput;
import com.room.tools.dto.IdsInput;
import com.room.tools.dto.PagedResult;
import com.room.tools.exception.CustomException;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-11-10
 */
@Service
public class AppUserServiceImpl extends ServiceImpl<AppUserMapper, AppUser> implements IAppUserService {
    /*
     * 前端参数传入
     * 赋值给entity
     * 调用mybatis-plus的方法
     * 返回创建结果给前端*/
    /**
     * 操作数据库的用户表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;

    /**
     * 用户创建或者修改
     */
    @SneakyThrows
    @Override
    public AppUserDto CreateOrEdit(AppUserDto input) {
        AppUser appUser=input.MapToEntity();
        //调用数据库的增加或者修改方法
        saveOrUpdate(appUser);
        //把传输模型返回给前端
        return appUser.MapToDto();
    }
    /**
     * 用户删除
     * */
    @Override
    public void Delete(IdInput input) {
        AppUser entity=AppUserMapper.selectById(input.getId());
        AppUserMapper.deleteById(entity);
    }

    @Override
    public void BatchDelete(IdsInput input) {
        for(Integer id : input.getIds()){
            IdInput idInput = new IdInput();
            idInput.setId(id);
            Delete(idInput);
        }
    }

    @Override
    public PagedResult<AppUserDto> List(AppUserPagedInput input) {
        //声明一个支持用户查询的(拉姆达)表达式
        LambdaQueryWrapper<AppUser> queryWrapper = Wrappers.<AppUser>lambdaQuery()
                .eq(input.getId() != null, AppUser::getId, input.getId())
                .eq(input.getCreatorId() != null, AppUser::getCreatorId, input.getCreatorId());

        //如果前端搜索传入Name不为空,则进行精确查询
        if (Extension.isNotNullOrEmpty(input.getName())) {
            queryWrapper = queryWrapper.eq(AppUser::getName, input.getName());
        }

        //如果前端搜索传入Email不为空,则进行精确查询
        if (Extension.isNotNullOrEmpty(input.getEmail())) {
            queryWrapper = queryWrapper.eq(AppUser::getEmail, input.getEmail());
        }

        //如果前端搜索传入getPhoneNumber不为空,则进行精确查询
        if (Extension.isNotNullOrEmpty(input.getPhoneNumber())) {
            queryWrapper = queryWrapper.eq(AppUser::getPhoneNumber, input.getPhoneNumber());
        }

        //如果前端搜索传入getRoleType不为空,则进行精确查询
        if (input.getRoleType() != null) {
            queryWrapper = queryWrapper.eq(AppUser::getRoleType, input.getRoleType());
        }


        //按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(AppUser::getCreationTime);
        //构建一个分页查询的model
        Page<AppUser> page = new Page<>(input.getPage(), input.getLimit());

        //从数据库进行分页查询获取用户数据
        IPage<AppUser> pageRecords = AppUserMapper.selectPage(page, queryWrapper);

        //获取所有满足条件的数据行数
        Long totalCount = AppUserMapper.selectCount(queryWrapper);

        //把用户实体转换成用户传输模型
        List<AppUserDto> items = Extension.copyBeanList(pageRecords.getRecords(), AppUserDto.class);

        //返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);
    }

    /**
     * 登录实现
     * @param input
     */
    @Override
    public String SignIn(AppUserDto input) {
        AppUser user = AppUserMapper.selectOne(Wrappers.<AppUser>lambdaQuery()
                .eq(AppUser::getUserName,input.getUserName())
                .eq(AppUser::getPassword,input.getPassword())
                .eq(AppUser::getRoleType,input.getRoleType()
        ));
        if(user==null){
            throw new CustomException("请检查登录的账号或者密码,角色是否都正确!");
        }
        Map<String, String> map = new HashMap<>();
        map.put(SysConst.UserIdClaim,user.getId().toString());
        map.put(SysConst.RoleTypeClaim,input.getRoleType().toString());

        String token = JWTUtils.getToken(map);
        return token;
    }
    /**
     * 用户单个查询
     */
    @Override
    public AppUserDto Get(AppUserPagedInput input) {
        if(input.getId()==null){
            return new AppUserDto();
        }
        return List(input).getItems().stream().findFirst().orElse(new AppUserDto());
    }
}
