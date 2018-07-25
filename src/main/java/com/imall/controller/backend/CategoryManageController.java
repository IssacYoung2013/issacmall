package com.imall.controller.backend;

import com.imall.common.Const;
import com.imall.common.ResponseCode;
import com.imall.common.ServerResponse;
import com.imall.pojo.User;
import com.imall.service.ICategoryService;
import com.imall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session,String categoryName,@RequestParam(value = "parentId",defaultValue = "0") int parentId) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        //检验下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.addCategory(categoryName,parentId);
        }
        else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session,Integer categoryId,String categoryName) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        //检验下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.updateCategoryName(categoryId,categoryName);
        }
        else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        //检验下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.getChildrenParallelCategory(categoryId);
        }
        else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        //检验下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()) {
            // 查询当前节点的id和当前分类的子分类
            return iCategoryService.selectCategoryAndChildrenById(categoryId);
        }
        else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }
}