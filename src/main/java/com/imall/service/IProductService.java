package com.imall.service;

import com.imall.common.ServerResponse;
import com.imall.pojo.Product;

/**
 *
 * author:  ywy
 * date:  2018-07-25
 * desc:
 *
 */
public interface IProductService {

    ServerResponse saveOrUpdateProduct(Product product);
}