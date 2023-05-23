package com.example.studyspb3backend.controller;

import com.example.studyspb3backend.entity.RestBean;
import com.example.studyspb3backend.service.AuthorizeService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthorizeController {
    private final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private final String USERNAME_REGEX = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$";

    @Resource
    AuthorizeService service;

    @PostMapping("/valid-register-email")
    public RestBean<String> validateRegisterEmail(@Pattern(regexp = EMAIL_REGEX) @RequestParam("email") String email,
                                                  HttpSession httpSession) {

        String result = service.sendValidateEmail(email, httpSession.getId(), false);
        if (result == null) {
            return RestBean.success("邮件已发送，请注意查收");
        } else {
            return RestBean.failure(400, result);
        }
    }


    @PostMapping("/valid-reset-email")
    public RestBean<String> validateResetEmail(@Pattern(regexp = EMAIL_REGEX) @RequestParam("email") String email,
                                               HttpSession httpSession) {

        String result = service.sendValidateEmail(email, httpSession.getId(), true);
        if (result == null) {
            return RestBean.success("邮件已发送，请注意查收");
        } else {
            return RestBean.failure(400, result);
        }
    }

    @PostMapping("/register")
    public RestBean<String> registerUser(@Pattern(regexp = USERNAME_REGEX) @Length(min = 3, max = 8) @RequestParam("username") String username,
                                         @Length(min = 6, max = 16) @RequestParam("password") String password,
                                         @Pattern(regexp = EMAIL_REGEX) @RequestParam("email") String email,
                                         @Length(min = 6, max = 6) @RequestParam("code") String code,
                                         HttpSession httpSession) {

        String result = service.validateAndRegister(username, password, email, code, httpSession.getId());
        if (result == null) {
            return RestBean.success("注册成功");
        } else {
            return RestBean.failure(400, result);
        }

    }

    /**
     * 1. 发验证邮件
     * 2. 验证验证码是否正确，正确则在 Session 中存一个标记
     * 3. 用户发起重置密码请求，如果存在标记，则成功重置
     */
    @PostMapping("/start-reset")
    public RestBean<String> startReset(@Pattern(regexp = EMAIL_REGEX) @RequestParam("email") String email,
                                       @Length(min = 6, max = 6) @RequestParam("code") String code,
                                       HttpSession httpSession) {

        String result = service.validateOnly(email, code, httpSession.getId());
        if (result == null) {
            httpSession.setAttribute("reset-password", email);
            return RestBean.success();
        } else {
            return RestBean.failure(400, result);
        }
    }

    @PostMapping("/do-reset")
    public RestBean<String> resetPassword(@Length(min = 6, max = 16) @RequestParam("password") String password,
                                          HttpSession httpSession) {

        String email = (String) httpSession.getAttribute("reset-password");
        if (email == null) {
            return RestBean.failure(401, "请先完成邮箱验证");
        } else if (service.resetPassword(email, password)) {
            httpSession.removeAttribute("reset-password");
            return RestBean.success("密码重置成功");
        } else {
            return RestBean.failure(500, "内部错误，请联系管理人员");
        }
    }
}
