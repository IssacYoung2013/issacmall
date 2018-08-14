package com.imall.service;

import com.imall.common.ServerResponse;
import com.imall.vo.BaseCountVo;

/**
 *
 * author:  ywy
 * date:  2018-08-14
 * desc:
 *
 */
public interface IStatisticService {
    ServerResponse<BaseCountVo> getBaseCount();
}