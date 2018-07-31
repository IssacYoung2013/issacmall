package com.imall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.imall.common.Const;
import com.imall.common.ResponseCode;
import com.imall.common.ServerResponse;
import com.imall.pojo.User;
import com.imall.service.IOrderService;
import com.imall.service.IUserService;
import com.imall.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * author:  ywy
 * date:  2018-07-30
 * desc:
 */
@Controller
@RequestMapping("/manage/order")
public class OrderMangerController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IOrderService iOrderService;

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderList(HttpSession session,
                                              @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        //检验下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()) {
            return iOrderService.mangeList(pageNum,pageSize);
        }
        else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<OrderVo> orderList(HttpSession session, Long orderNo) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        //检验下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()) {
            return iOrderService.mangeDetail(orderNo);
        }
        else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderSearch(HttpSession session, Long orderNo,
                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        //检验下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()) {
            return iOrderService.mangeSearch(orderNo,pageNum,pageSize);
        }
        else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    @RequestMapping("send_goods.do")
    @ResponseBody
    public ServerResponse<String> orderSendGoods(HttpSession session, Long orderNo) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        //检验下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()) {
            return iOrderService.manageSendGoods(orderNo);
        }
        else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }
}