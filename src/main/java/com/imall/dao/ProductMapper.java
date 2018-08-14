package com.imall.dao;

import com.imall.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectList();

    List<Product> selectByNameAndProductId(@Param("productName") String productName,@Param("productId") Integer productId);

    List<Product> selectByNameAndCategoryId(@Param("productName") String productName,@Param("categoryIdList") List<Integer> categoryIdList);

    // 一定要用Integer int无法为null 考虑商品删除
    Integer selectStockByProductId(Integer id);

    int getProductCount();
}