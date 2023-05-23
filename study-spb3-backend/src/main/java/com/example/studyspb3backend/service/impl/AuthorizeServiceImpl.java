package com.example.studyspb3backend.service.impl;

import com.example.studyspb3backend.entity.Account;
import com.example.studyspb3backend.mapper.UserMapper;
import com.example.studyspb3backend.service.AuthorizeService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class AuthorizeServiceImpl implements AuthorizeService {

    @Value("${spring.mail.properties.from}")
    String from;

    @Resource
    UserMapper mapper;

    @Resource
    MailSender mailSender;

    @Autowired
    StringRedisTemplate redisTemplate;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) {
            throw new UsernameNotFoundException("用户名不能为空");
        }

        Account account = mapper.findByNameOrEmail(username);

        if (account == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        return User.withUsername(account.getUsername())
                .password(account.getPassword())
                .roles("user")
                .build();
    }

    /**
     * 1. 生成验证码
     * 2. 发送邮箱，如果发送成功则插入到 Redis 中，键为邮箱，值为验证码
     * 3. 验证码有效期为三分钟，如果在此时重新请求发送验证码，只有在剩余时间低于两分钟时才会重新发送，重复此流程。
     * 4. 在用户注册时，从 Redis 中取出相应的键值对进行验证。
     */
    @Override
    public String sendValidateEmail(String email, String sessionId) {
        String key = "email:" + sessionId + ":" + email;

        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            Long expire = Optional.ofNullable(redisTemplate.getExpire(key, TimeUnit.SECONDS)).orElse(0L);

            if (expire > 120) {
                return "请求频繁，请稍后再试";
            }
        }

        if (mapper.findByNameOrEmail(email) != null) {
            return "该邮箱已被注册";
        }

        Random random = new Random();
        int code = random.nextInt(900000) + 100000;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("Study-SPB3 验证码");
        message.setText("您的验证码为：" + code + "，有效期为三分钟。");

        try {
            mailSender.send(message);
            redisTemplate.opsForValue().set(key, String.valueOf(code), 3, TimeUnit.MINUTES);

            return null;
        } catch (MailException mailException) {
            mailException.printStackTrace();
            return "验证码发送失败，请联系管理员";
        }
    }

    @Override
    public String validateAndRegister(String username, String password, String email, String code, String sessionId) {
        String key = "email:" + sessionId + ":" + email;

        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            String val = redisTemplate.opsForValue().get(key);

            if (val == null) {
                return "验证码已过期，请重新请求";
            } else {
                if (val.equals(code)) {
                    password = passwordEncoder.encode(password);

                    Account account = new Account();
                    account.setUsername(username);
                    account.setPassword(password);
                    account.setEmail(email);

                    if (mapper.insert(account) > 0) {
                        return null;
                    } else {
                        return "内部错误，请联系管理员";
                    }
                } else {
                    return "验证码错误";
                }
            }
        } else {
            return "请先请求一封验证码邮件";
        }


    }
}
