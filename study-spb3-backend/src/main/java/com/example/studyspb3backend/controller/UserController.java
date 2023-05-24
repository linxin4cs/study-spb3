package com.example.studyspb3backend.controller;

import com.example.studyspb3backend.entity.RestBean;
import com.example.studyspb3backend.entity.user.UserInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @GetMapping("/me")
    public RestBean<UserInfo> getMe(@SessionAttribute("userInfo") UserInfo userInfo) {
        return RestBean.success(userInfo);
    }

}
