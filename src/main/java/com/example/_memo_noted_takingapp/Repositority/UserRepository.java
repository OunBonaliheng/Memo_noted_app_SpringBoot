package com.example._memo_noted_takingapp.Repositority;

import com.example._memo_noted_takingapp.Model.User;
import com.example._memo_noted_takingapp.Model.dto.Request.ForgetRequest;
import com.example._memo_noted_takingapp.Model.dto.Response.UserResponse;
import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper
public interface UserRepository {
     @Results(id = "authMapper", value = {
             @Result(property = "userId",column = "user_id"),
             @Result(property = "name",column = "userName"),
             @Result(property = "email", column = "email"),
             @Result(property = "password", column = "Password"),
             @Result(property = "gender", column = "gender"),
             @Result(property = "profileImage", column = "profile_image")
     })
     @Select("INSERT INTO user_tb (userName,email, Password, gender,profile_image) VALUES (#{user.name},#{user.email}, #{user.password},CAST(#{user.gender} AS gender_enum), #{user.profileImage}) RETURNING *")
     User save(@Param("user") User newUser);

     @Select("SELECT * FROM user_tb WHERE email = #{email}")
     @ResultMap("authMapper")
     User getUserByEmail(@Param("email") String email);

     @Select("UPDATE user_tb SET Password = #{pass.password} WHERE email = #{email} RETURNING *")
     @ResultMap("authMapper")
     User updatePassword(@Param("pass") ForgetRequest forgetRequest , @Param("email") String email);

     @Select("SELECT * FROM user_tb WHERE user_id = #{id}")
     @Result(property = "userId",column = "user_id")
     @Result(property = "name",column = "userName")
     @Result(property = "email", column = "email")
     @Result(property = "gender", column = "gender")
     @Result(property = "profileImage", column = "profile_image")
     UserResponse getUserById(@Param("userId") Integer id);
}

