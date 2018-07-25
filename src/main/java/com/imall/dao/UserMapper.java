package com.imall.dao;

import com.imall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUsername(String username);

    int checkEmail(String email);

    int checkAnswer(@Param("username") String username,@Param("question") String question,@Param("answer") String answer);

    User selectLogin(@Param("username") String username, @Param("password") String password);

    String selectionQuestionByUsername(String username);

    int updatePasswordByUserName(@Param("username") String username,@Param("passwordNew") String passwordNew);

    int checkPassword(@Param("password") String password,@Param("userId") Integer userId);

    int checkEmailByUserId(@Param("email")String email, @Param("userId") Integer userId);
}