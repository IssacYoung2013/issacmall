package com.imall.service.impl;

import com.imall.common.Const;
import com.imall.common.ServerResponse;
import com.imall.dao.UserMapper;
import com.imall.pojo.User;
import com.imall.service.IUserService;
import com.imall.util.MD5Util;
import com.imall.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 *
 * author:  ywy
 * date:  2018-07-24
 * desc:
 *
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        //todo 密码登录MD5
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username,md5Password);
        if(user == null) {
            return ServerResponse.createByErrorMessage("密码错误");
        }

        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功",user);
    }

    @Override
    public ServerResponse<String> register(User user) {

        ServerResponse vaildResponse = this.checkVaild(user.getUsername(),Const.USERNAME);
        if(!vaildResponse.isSuccess()) {
            return vaildResponse;
        }
        vaildResponse = this.checkVaild(user.getEmail(),Const.EMAIL);
        if(!vaildResponse.isSuccess()) {
            return vaildResponse;
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);
        //mD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));

        int resultCount = userMapper.insert(user);
        if(resultCount == 0) {
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccess("注册成功");
    }

    @Override
    public ServerResponse<String> checkVaild(String str,String type) {
        if(StringUtils.isNotBlank(type)) {
            // 开始校验
            if(Const.USERNAME.equals(type)) {
                int resultCount = userMapper.checkUsername(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("用户已存在");
                }
            }
            if(Const.EMAIL.equals(type)) {
                int  resultCount = userMapper.checkEmail(str);
                if (resultCount > 0 ) {
                    return ServerResponse.createByErrorMessage("邮箱已注册");
                }
            }
        }
        else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    @Override
    public ServerResponse selectQuestion(String username) {
        ServerResponse vaildResponse = this.checkVaild(username,Const.USERNAME);
        if(vaildResponse.isSuccess()) {
            // 用户不存在
            return ServerResponse.createByErrorMessage("用户不存在");
        }

        String question = userMapper.selectionQuestionByUsername(username);
        if(StringUtils.isNotBlank(question)) {
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMessage("找回密码的问题是空的");
    }

    @Override
    public ServerResponse<String> checkAnswer(String username,String question,String answer){
        int resultCount = userMapper.checkAnswer(username,question,answer);
        if(resultCount > 0 ) {
            // 说明问题及问题答案正确
            String forgetToken = UUID.randomUUID().toString();
            RedisShardedPoolUtil.setEx(Const.TOKEN_PREFIX+ username,forgetToken,60 * 60 * 12);

            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("问题的答案错误");
    }

    @Override
    public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken) {
        if(StringUtils.isBlank(forgetToken)) {
            return ServerResponse.createByErrorMessage("参数错误，token需要传递");
        }
        ServerResponse vaildResponse = this.checkVaild(username,Const.USERNAME);
        if(vaildResponse.isSuccess()) {
            // 用户不存在
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String token = RedisShardedPoolUtil.get(Const.TOKEN_PREFIX + username);
        if(StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMessage("token无效或者过期");
        }
        if(StringUtils.equals(forgetToken,token)) {
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount = userMapper.updatePasswordByUserName(username,md5Password);

            if(rowCount > 0) {
                return ServerResponse.createBySuccess("修改密码成功");
            }
        }
        else {
            return ServerResponse.createByErrorMessage("token错误，请重新获取重置密码的token");
        }

        return ServerResponse.createByErrorMessage("修改密码失败");
    }

    @Override
    public ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user) {
        // 防止横向越权，校验一下这个用户的旧密码，一定要是这个用户
        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld),user.getId());
        if(resultCount == 0) {
            return ServerResponse.createByErrorMessage("旧密码错误");
        }

        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if(updateCount > 0) {
            return ServerResponse.createBySuccess("密码更新成功");
        }

        return ServerResponse.createByErrorMessage("密码更新失败");
    }

    @Override
    public ServerResponse<User> updateInformation(User user) {
        // username是不能更新
        // email也要进行一个校验，校验email是不是已存在，并且存在的email如果相同
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(),user.getId());
        if(resultCount > 0 ) {
            return ServerResponse.createByErrorMessage("email已存在，请更换email再尝试更新");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setAnswer(user.getAnswer());

        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if(updateCount > 0) {
            return ServerResponse.createBySuccess("更新个人信息成功",updateUser);
        }
        return ServerResponse.createByErrorMessage("更新个人信息失败");
   }

   @Override
   public ServerResponse<User> getInformation(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if(user == null) {
            return ServerResponse.createByErrorMessage("找不到当前用户");
        }
        user.setPassword(StringUtils.EMPTY);

        return ServerResponse.createBySuccess(user);
   }

   //backend

    @Override
    public ServerResponse checkAdminRole(User user) {
        if(user != null && user.getRole().intValue() == Const.Role.ROLE_ADMIN) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }
}