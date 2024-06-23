package com.example._memo_noted_takingapp.Repositority;

import com.example._memo_noted_takingapp.Model.dto.Response.UserResponse;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserProfileRepository {
    @Results(id = "UserMapper", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "name", column = "userName"),
            @Result(property = "email", column = "email"),
    })
    @Select("UPDATE user_tb SET userName = #{user.name} WHERE user_id = #{user.userId} RETURNING *")
    UserResponse updateUser(@Param("user") String changeName, Long userId);

    @Update("UPDATE user_tb SET userName = #{changeUsername} WHERE user_id = #{userId}")
    void updateUsername(@Param("userId") Long userId, @Param("changeUsername") String changeUsername);
}
