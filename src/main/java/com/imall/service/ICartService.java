package com.imall.service;

import com.imall.common.ServerResponse;
import com.imall.vo.CartVo;
import org.springframework.stereotype.Service;

/**
 *
 * author:  ywy
 * date:  2018-07-27
 * desc:
 *
 */
public interface ICartService {
    ServerResponse<CartVo> add(Integer userId, Integer count, Integer productId);

    ServerResponse<CartVo> update(Integer userId, Integer count, Integer productId);

    ServerResponse<CartVo> deleteProduct(Integer userId,String productIds);

    ServerResponse<CartVo> getListByUserId(Integer userId);

    ServerResponse<CartVo> selectOrUnSelect(Integer userId,Integer productId, Integer checked);

    ServerResponse<Integer> getCartProductCount(Integer userId);
}