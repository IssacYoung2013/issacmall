package com.imall.service;

import com.github.pagehelper.PageInfo;
import com.imall.common.ServerResponse;
import com.imall.vo.OrderVo;

import java.util.Map;

/**
 *
 *
 * @author Issac
 *  *   @date    2018-07-29
 * @desc
 */
public interface IOrderService {

    ServerResponse pay(Long orderNo, Integer userId, String path);

    ServerResponse aliCallback(Map<String, String> params);

    ServerResponse queryOrderPayStatus(Integer userId,Long orderNo);

    ServerResponse createOrder(Integer userId, Integer shippingId);

    ServerResponse<String> cancell(Integer userId,Long orderNo);

    ServerResponse getOrderCartProduct(Integer userId);

    ServerResponse<OrderVo> getOrderDtail(Integer userId, Long orderNo);

    ServerResponse<PageInfo> getOrderList(Integer userId, Integer pageNum, Integer pageSize);

    ServerResponse<PageInfo> mangeList(int pageNum,int pageSize);

    ServerResponse<OrderVo> mangeDetail(Long orderNo);

    ServerResponse<PageInfo> mangeSearch(Long orderNo,int pageNum,int pageSize);

    ServerResponse<String> manageSendGoods(Long orderNo);
}
