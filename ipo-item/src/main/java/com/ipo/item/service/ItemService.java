package com.ipo.item.service;

import com.ipo.item.exception.BusinessException;
import com.ipo.item.model.ItemModel;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-07-21 10:27
 */
public interface ItemService {

    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    ItemModel updateItem(ItemModel itemModel) throws BusinessException;

    ItemModel getItemById(Integer id);

}
