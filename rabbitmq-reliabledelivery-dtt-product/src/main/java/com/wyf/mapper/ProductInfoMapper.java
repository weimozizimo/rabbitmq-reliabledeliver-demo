package com.wyf.mapper;

import com.wyf.entity.ProductInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
*@Description 商品信息mapper类
*@Author weiyifei
*@date 2021/6/16
*/
@Mapper
public interface ProductInfoMapper {

    /**
     * 修改货物库存（加一）
     * @param productNo
     * @return
     */
    int updateProductStoreById(@Param("productNo") Integer productNo);

    /**
     * 根据id获取货物信息
     * @param productNo
     * @return
     */
    ProductInfo getById(@Param("productNo") Integer productNo);

}
