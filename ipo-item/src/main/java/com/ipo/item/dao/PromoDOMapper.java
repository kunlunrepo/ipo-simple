package com.ipo.item.dao;

import com.ipo.item.dao.data.PromoDO;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-07-21 14:39
 */
public interface PromoDOMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(PromoDO record);

    int insertSelective(PromoDO record);

    PromoDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PromoDO record);

    int updateByPrimaryKey(PromoDO record);
    
}
