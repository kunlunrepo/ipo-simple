package com.ipo.item.model;

import lombok.Data;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-07-21 10:40
 */
@Data
public class PromoModel implements Serializable {

    private Integer id;

    // 秒杀活动状态：1-未开始；2-进行中；3-已结束
    private Integer status;

    // 秒杀活动名称
    private String promoName;

    // 秒杀活动的开始时间
    private DateTime startDate;

    // 秒杀活动的结束时间
    private DateTime endDate;

    // 秒杀活动的使用商品
    private Integer itemId;

    // 秒杀活动的商品价格
    private BigDecimal promoItemPrice;
}
