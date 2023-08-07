package com.ipo.item.dao;

import com.ipo.item.dao.data.ItemStockDO;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-07-21 14:15
 */
public interface ItemStockDOMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ItemStockDO record);

    int insertSelective(ItemStockDO record);

    ItemStockDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemStockDO record);

    int updateByPrimaryKey(ItemStockDO record);

}
