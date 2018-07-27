package com.imall.service;

import com.imall.common.ServerResponse;

import java.util.List;

/**
 *
 * author:  ywy
 * date:  2018-07-25
 * desc:
 *
 */
public interface ICategoryService {

    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(Integer categoryId,String categoryName);

    ServerResponse getChildrenParallelCategory(Integer categoryId);

    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);
}