package com.imall.controller.backend;

import com.imall.common.Const;
import com.imall.common.ServerResponse;
import com.imall.pojo.User;
import com.imall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 *
 * author:  ywy
 * date:  2018-07-25
 * desc:
 *
 */
@Controller
@RequestMapping("/manage/user")
public class UserManagerController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response = iUserService.login(username,password);
        if(response.isSuccess()) {
            User user = response.getData();
            if(user.getRole() == Const.Role.ROLE_ADMIN) {
                // 说明登录是管理员
                session.setAttribute(Const.CURRENT_USER,user);
                return response;
            }
            else {
                return ServerResponse.createByErrorMessage("不是管理员，无法登录");
            }
        }

        return response;
    }
}