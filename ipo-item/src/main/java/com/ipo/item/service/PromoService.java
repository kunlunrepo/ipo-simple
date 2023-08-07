package com.ipo.item.service;

import com.ipo.item.model.PromoModel;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-07-21 14:29
 */
public interface PromoService {

    // 根据itemId获取即将进行的或正在进行的秒杀活动
    PromoModel getPromoByItemId(Integer itemId);
}
