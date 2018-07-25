package com.imall.controller.backend;

import com.imall.common.Const;
import com.imall.common.ResponseCode;
import com.imall.common.ServerResponse;
import com.imall.pojo.Product;
import com.imall.pojo.User;
import com.imall.service.IProductService;
import com.imall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/manage/product")
public class ProductMangerController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @RequestMapping("product_save.do")
    @ResponseBody
    public ServerResponse productSave(HttpSession session, Product product) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录管理员");
        }
        if(iUserService.checkAdminRole(user).isSuccess()) {
            // 填充增加产品的逻辑
            return iProductService.saveOrUpdateProduct(product);
        }
        else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }
}