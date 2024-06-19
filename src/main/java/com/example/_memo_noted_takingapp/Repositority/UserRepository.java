package com.example._memo_noted_takingapp.Repositority;

import com.example._memo_noted_takingapp.Model.User;
import com.example._memo_noted_takingapp.Model.dto.Request.ForgetRequest;
import com.example._memo_noted_takingapp.Model.dto.Response.UserResponse;
import jakarta.validation.Valid;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserRepository {
     @Results(id = "authMapper", value = {
             @Result(property = "userId",column = "user_id"),
             @Result(property = "name",column = "userName"),
             @Result(property = "email", column = "email"),
             @Result(property = "password", column = "Password")

     })
     @Select("INSERT INTO user_tb (userName,email, Password) VALUES (#{user.name},#{user.email}, #{user.password}) RETURNING *")
     User save(@Param("user") User newUser);

     @Select("SELECT * FROM user_tb WHERE email = #{email}")
     @ResultMap("authMapper")
     User getUserByEmail(@Param("email") String email);


     @Select("UPDATE user_tb SET Password = #{pass.password} WHERE email = #{email} RETURNING *")
     @ResultMap("authMapper")
     User updatePassword(@Param("pass") ForgetRequest forgetRequest , @Param("email") String email);

     @Select("SELECT user_id, userName AS username, email FROM user_tb WHERE user_id = #{userId}")
     @Result(property = "userId", column = "user_id")
     @Result(property = "username", column = "userName")
     @Result(property = "email", column = "email")
     UserResponse getUserById(@Param("userId") Long userId);

     @Select("SELECT * FROM user_tb WHERE email = #{email} AND Password = #{password}")
     @ResultMap("authMapper")
     User getUserByEmailandPassword(String email, @Valid String password);

}

