package com.example.studyspb3backend.mapper;

import com.example.studyspb3backend.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from account where username = #{text} or email = #{text}")
    Account findByNameOrEmail(String text);
}
