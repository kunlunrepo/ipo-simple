package com.ipo.item.dao;

import com.ipo.item.dao.data.ItemDO;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-07-21 13:55
 */
public interface ItemDOMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ItemDO record);

    int insertSelective(ItemDO record);

    ItemDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemDO record);

    int updateByPrimaryKey(ItemDO record);
}
