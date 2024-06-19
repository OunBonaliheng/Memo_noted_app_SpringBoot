package com.example._memo_noted_takingapp.Repositority;

import com.example._memo_noted_takingapp.Model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserProfileRepository {
    @Results(id = "UserMapper", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "name", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "Password"),
    })
    @Select("UPDATE user_tb SET username = #{user.name} WHERE email = #{email} RETURNING *")
    User updateUser( @Param("email") String email,@Param("user") User changeName);
}
