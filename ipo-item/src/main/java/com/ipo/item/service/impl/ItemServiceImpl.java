package com.ipo.item.service.impl;

import com.ipo.item.dao.ItemDOMapper;
import com.ipo.item.dao.ItemStockDOMapper;
import com.ipo.item.dao.ItemStockDOMapperSpec;
import com.ipo.item.dao.data.ItemDO;
import com.ipo.item.dao.data.ItemStockDO;
import com.ipo.item.exception.BusinessException;
import com.ipo.item.exception.EmBusinessError;
import com.ipo.item.model.ItemModel;
import com.ipo.item.model.PromoModel;
import com.ipo.item.service.ItemService;
import com.ipo.item.service.PromoService;
import com.ipo.item.validato.ValidationResult;
import com.ipo.item.validato.ValidatorImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-07-21 11:29
 */
@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private ItemDOMapper itemDOMapper;

    @Autowired
    private ItemStockDOMapper itemStockDOMapper;

    @Autowired
    private ItemStockDOMapperSpec itemStockDOMapperSpec;

    @Autowired
    private PromoService promoService;

    private ItemDO convertItemDOFromItemModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel, itemDO);
        itemDO.setPrice(itemModel.getPrice().doubleValue());
        return itemDO;
    }

    private ItemStockDO convertItemStockDOFromItemModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setItemId(itemModel.getId());
        itemStockDO.setStock(itemModel.getStock());
        return itemStockDO;
    }

    private ItemModel converModelFromDataObject(ItemDO itemDO, ItemStockDO itemStockDO) {
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO, itemModel);
        itemModel.setPrice(new BigDecimal(itemDO.getPrice()));
        itemModel.setStock(itemStockDO.getStock());
        return itemModel;
    }

    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        // 校验入参
        ValidationResult result = validator.validate(itemModel);
        if (result.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        // model->obj
        ItemDO itemDo = this.convertItemDOFromItemModel(itemModel);

        // 新增数据
        itemDOMapper.insertSelective(itemDo);
        itemModel.setId(itemDo.getId());

        ItemStockDO itemStockDO = this.convertItemStockDOFromItemModel(itemModel);
        itemStockDOMapper.insertSelective(itemStockDO);

        // 返回创建完成的对象
        return this.getItemById(itemModel.getId());
    }

    @Override
    public ItemModel updateItem(ItemModel itemModel)  throws BusinessException {
        // 校验入参
        ValidationResult result = validator.validate(itemModel);
        if (result.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        // model->obj
        ItemDO itemDo = this.convertItemDOFromItemModel(itemModel);

        // 更新数据
        itemDo.setUpdateTime(new Date());
        itemDOMapper.updateByPrimaryKeySelective(itemDo);

        ItemStockDO itemStockDO = this.convertItemStockDOFromItemModel(itemModel);
        itemStockDO.setUpdateTime(new Date());
        itemStockDOMapperSpec.updateStockByItemId(itemStockDO);

        // 返回创建完成的对象
        return this.getItemById(itemModel.getId());
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
        log.info("通过数据库获取商品信息：{}", itemDO);
        if (itemDO == null) {
            return null;
        }
        // 操作获取库存数量
        ItemStockDO itemStockDO = itemStockDOMapperSpec.selectByItemId(id);
        log.info("通过数据库获取库存信息：{}", itemStockDO);

        // obj->model
        ItemModel itemModel = converModelFromDataObject(itemDO, itemStockDO);

        // 获取活动商品信息
        PromoModel promoModel = promoService.getPromoByItemId(itemModel.getId());
        log.info("获取商品促销信息：{}", promoModel);
        if (promoModel != null && promoModel.getStatus().intValue() != 3) {
            itemModel.setPromoModel(promoModel);
        }

        return itemModel;
    }
}
