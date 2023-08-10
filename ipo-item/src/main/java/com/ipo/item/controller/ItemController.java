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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RedisTemplate redisTemplate;

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

    // 创建线程池
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ResponseBody
    public CommonReturnType updateItem(@RequestBody ItemModel itemModel)  throws BusinessException {

        String key = "item_" + itemModel.getId(); // key
        // 删除缓存
        redisTemplate.delete(key);

        // 更新数据库
        ItemModel itemModelForReturn = itemService.updateItem(itemModel);
        ItemVO itemVO = convertVOFromModel(itemModelForReturn);

        // 延迟删除缓存
//        delayDel(key);
        executorService.schedule(() -> {
            log.info("数据延迟删除");
            redisTemplate.delete(key);
        }, 5, TimeUnit.SECONDS); // 延时5秒执行

        return CommonReturnType.create(itemVO);
    }

    private void delayDel(String key) {
        try {
            Thread.sleep(1*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        redisTemplate.delete(key);
    }

    @RequestMapping(value="/get", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getItem(@RequestParam(name="id") Integer id) {

        // 首先从缓存中读取
        ItemModel itemModel = (ItemModel) redisTemplate.opsForValue().get("item_" + id);
        if (null == itemModel) {
            // 缓存中没有则从数据库中查询
            itemModel = itemService.getItemById(id);

            // 插入缓存中，方便下次查询
            redisTemplate.opsForValue().set("item_" + id, itemModel);
        }

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
