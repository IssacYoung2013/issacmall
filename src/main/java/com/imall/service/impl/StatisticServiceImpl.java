package com.imall.service.impl;

import com.imall.common.ServerResponse;
import com.imall.dao.OrderMapper;
import com.imall.dao.ProductMapper;
import com.imall.dao.UserMapper;
import com.imall.pojo.Product;
import com.imall.service.IStatisticService;
import com.imall.vo.BaseCountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * author:  ywy
 * date:  2018-08-14
 * desc:
 *
 */
@Service("iStatisticService")
public class StatisticServiceImpl implements IStatisticService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    OrderMapper orderMapper;

    /**
     * 获取基本统计数据
     * @return
     */
    public ServerResponse<BaseCountVo> getBaseCount(){
        BaseCountVo baseCountVo = new BaseCountVo();
        baseCountVo.setUserCount(userMapper.getUserCount());
        baseCountVo.setOrderCount(orderMapper.getOrderCount());
        baseCountVo.setProductCount(productMapper.getProductCount());

        return ServerResponse.createBySuccess(baseCountVo);
    }

}