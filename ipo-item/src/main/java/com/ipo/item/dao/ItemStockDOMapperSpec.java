package com.ipo.item.dao;

import com.ipo.item.dao.data.ItemStockDO;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-07-21 14:22
 */
public interface ItemStockDOMapperSpec {

    ItemStockDO selectByItemId(Integer itemId);

    void updateStockByItemId(ItemStockDO itemStockDO);
}
