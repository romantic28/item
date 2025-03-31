package com.room.tools;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.room.SysConst;
import com.room.enums.RoleType;
import com.room.tools.dto.CurrentUserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 解析当前token的用户信息 存到线程中,把CurrentUserDto保存到上下文线程对象中
 * 通过拦截 HTTP 请求，获取请求头中的 JWT token，解析其中的用户 ID 和角色类型信息，并将这些信息存储到 BaseContext 中。
 * 这使得后续的业务逻辑可以通过 BaseContext 获取当前用户的信息，从而进行权限控制或其他操作。
 * 如果请求没有 token，代码默认设置用户 ID 为 0，表示没有身份信息。
 */
public class CurrentUserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // jwt不建议作为请求参数，而应该在请求的请求头中
        // 获取请求头中的令牌（token）
        String token = request.getHeader("Authorization");
        if(token!=null) {

            token=token.replace("Bearer ","");
            DecodedJWT jwt=JWTUtils.getTokenInfo(token);
            String userId =jwt.getClaims().get(SysConst.UserIdClaim).asString();
            String roleType =jwt.getClaims().get(SysConst.RoleTypeClaim).asString();
            CurrentUserDto currentUserDto =new CurrentUserDto();
            currentUserDto.setUserId(Integer.valueOf(userId));
            currentUserDto.setRoleType(RoleType.GetEnum(Integer.valueOf(roleType)));
            BaseContext.setCurrentUserDto(currentUserDto);
        }
        else{
            CurrentUserDto currentUserDto =new CurrentUserDto();
            currentUserDto.setUserId(0);

            BaseContext.setCurrentUserDto(currentUserDto);
        }
        return true;

    }
}