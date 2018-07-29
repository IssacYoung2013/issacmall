package com.imall.service;

import com.imall.common.ServerResponse;

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
}
