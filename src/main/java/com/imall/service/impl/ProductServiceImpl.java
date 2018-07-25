package com.imall.service.impl;

import com.imall.common.ServerResponse;
import com.imall.dao.ProductMapper;
import com.imall.pojo.Product;
import com.imall.service.IProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * author:  ywy
 * date:  2018-07-25
 * desc:
 *
 */
@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    ProductMapper productMapper;

    public ServerResponse saveOrUpdateProduct(Product product){
        if(product != null) {

            if(StringUtils.isNotBlank(product.getSubImages())) {
                String[] subImageArray = product.getSubImages().split(",");
                if(subImageArray.length > 0) {
                    product.setMainImage(subImageArray[0]);
                }
            }

            if(product.getId() != null) {
                int rowCount = productMapper.updateByPrimaryKey(product);
                if(rowCount > 0) {
                    return ServerResponse.createBySuccess("更新产品成功");
                }
                else  {
                    return ServerResponse.createByErrorMessage("更新产品失败");
                }
            } else {
                int rowCount = productMapper.insert(product);
                if(rowCount > 0) {
                    return ServerResponse.createBySuccess("新增产品成功");
                }
                else {
                    return ServerResponse.createByErrorMessage("新增产品失败");
                }
            }

        }

        return ServerResponse.createByErrorMessage("新增或更新产品参数不正确");
    }
}