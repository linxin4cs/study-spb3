package com.example.studyspb3backend.mapper;

import com.example.studyspb3backend.entity.auth.Account;
import com.example.studyspb3backend.entity.user.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("select * from account where username = #{text} or email = #{text}")
    Account findAccountByNameOrEmail(String text);

    @Select("select * from account where username = #{text} or email = #{text}")
    UserInfo findUserInfoByNameOrEmail(String text);

    @Insert("insert into account (username, password, email) values (#{username}, #{password}, #{email})")
    int createAccount(Account account);

    @Update("update account set password = #{password} where email = #{email}")
    int resetPasswordByEmail(String email, String password);
}
