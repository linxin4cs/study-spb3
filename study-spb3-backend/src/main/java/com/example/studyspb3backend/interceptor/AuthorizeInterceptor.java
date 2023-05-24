package com.example.studyspb3backend.interceptor;

import com.example.studyspb3backend.entity.user.UserInfo;
import com.example.studyspb3backend.mapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorizeInterceptor implements HandlerInterceptor {

    @Resource
    UserMapper userMapper;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request,
                             @NotNull HttpServletResponse response,
                             @NotNull Object handler) throws Exception {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        User user = (User) authentication.getPrincipal();
        String username = user.getUsername();

        UserInfo userInfo = userMapper.findUserInfoByNameOrEmail(username);
        request.getSession().setAttribute("userInfo", userInfo);

        return true;
    }
}
