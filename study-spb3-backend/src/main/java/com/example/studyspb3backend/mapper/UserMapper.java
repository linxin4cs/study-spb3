package com.example.studyspb3backend.mapper;

import com.example.studyspb3backend.entity.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("select * from account where username = #{text} or email = #{text}")
    Account findByNameOrEmail(String text);

    @Insert("insert into account (username, password, email) values (#{username}, #{password}, #{email})")
    int createAccount(Account account);

    @Update("update account set password = #{password} where email = #{email}")
    int resetPassword(String email, String password);
}
