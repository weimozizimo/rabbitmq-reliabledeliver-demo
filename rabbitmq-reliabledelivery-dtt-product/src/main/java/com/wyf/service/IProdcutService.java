package com.wyf.service;

import com.wyf.bo.MsgTxtBo;

/**
*@Description 货物业务层接口
*@Author weiyifei
*@date 2021/6/16
*/
public interface IProdcutService {

    /**
     * 修改货物数量
     * @param msgTxtBo
     * @return
     */
    boolean updateProductStore(MsgTxtBo msgTxtBo);


}
