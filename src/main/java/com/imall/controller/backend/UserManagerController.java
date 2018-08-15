package com.imall.controller.backend;

import com.imall.common.Const;
import com.imall.common.ServerResponse;
import com.imall.pojo.User;
import com.imall.service.IUserService;
import com.imall.util.CookieUtil;
import com.imall.util.JsonUtil;
import com.imall.util.RedisPoolUtil;
import com.imall.util.RedisShardedPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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

    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session, HttpServletResponse httpServletResponse){
        ServerResponse<User> response = iUserService.login(username,password);
        if(response.isSuccess()) {
            User user = response.getData();
            if(user.getRole() == Const.Role.ROLE_ADMIN) {
                // 说明登录是管理员
//                session.setAttribute(Const.CURRENT_USER,user);
                // 新增redis共享cookie, session的方式
                CookieUtil.writeLoginToken(httpServletResponse,session.getId());
                RedisShardedPoolUtil.setEx(session.getId(), JsonUtil.obj2String(response.getData()),Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
                return response;
            }
            else {
                return ServerResponse.createByErrorMessage("不是管理员，无法登录");
            }
        }

        return response;
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList( @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                   @RequestParam(value = "pageSize",defaultValue = "10") int pageSize) {
        // 填充业务
        return iUserService.getUserList(pageNum,pageSize);
    }
}