package com.ipo.item.controller;

import com.ipo.item.exception.BusinessException;
import com.ipo.item.model.ItemModel;
import com.ipo.item.response.CommonReturnType;
import com.ipo.item.service.ItemService;
import com.ipo.item.vo.ItemVO;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * description : 测试接口
 *
 * @author kunlunrepo
 * date :  2023-07-21 10:25
 */
@Slf4j
@Controller
@RequestMapping("/item")
public class ItemController {


    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/create", method = {RequestMethod.POST})
    @ResponseBody
    public CommonReturnType createItem(
            @RequestParam(name="title") String title,
            @RequestParam(name="description") String description,
            @RequestParam(name="price") BigDecimal price,
            @RequestParam(name="stock") Integer stock,
            @RequestParam(name="imgUrl") String imgUrl
    )  throws BusinessException {
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);

        ItemModel itemModelForReturn = itemService.createItem(itemModel);
        ItemVO itemVO = convertVOFromModel(itemModelForReturn);

        return CommonReturnType.create(itemVO);
    }

    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ResponseBody
    public CommonReturnType updateItem(@RequestBody ItemModel itemModel)  throws BusinessException {
        ItemModel itemModelForReturn = itemService.updateItem(itemModel);
        ItemVO itemVO = convertVOFromModel(itemModelForReturn);
        return CommonReturnType.create(itemVO);
    }

    @RequestMapping(value="/get", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getItem(@RequestParam(name="id") Integer id) {
        ItemModel itemModel = itemService.getItemById(id);
        ItemVO itemVO = convertVOFromModel(itemModel);
        return CommonReturnType.create(itemVO);
    }

    private ItemVO convertVOFromModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel, itemVO);
        if (null != itemModel.getPromoModel()) {
            itemVO.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemVO.setPromoId(itemModel.getPromoModel().getId());
            itemVO.setStartDate(itemModel.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVO.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());
        } else {
            itemVO.setPromoStatus(0);
        }
        return itemVO;
    }

}
