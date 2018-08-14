package com.imall.vo;

/**
 * author:  ywy
 * date:  2018-08-14
 * desc:
 */
public class BaseCountVo {
    private Integer productCount;

    private Integer orderCount;

    private Integer userCount;

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }
}