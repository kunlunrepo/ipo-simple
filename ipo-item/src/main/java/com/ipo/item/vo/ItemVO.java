package com.ipo.item.vo;

import lombok.Data;
import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-07-21 11:11
 */
@Data
public class ItemVO {

    private Integer id;

    // 商品名称
    private String title;

    // 商品价格
    private BigDecimal price;

    // 商品库存
    private Integer stock;

    // 商品描述
    private String description;

    // 商品销量
    private Integer sales;

    // 商品描述图片的url
    private String imgUrl;

    // 秒杀活动状态：0-没有秒杀；1-活动待开始；2-活动进行中
    private Integer promoStatus;

    // 秒杀活动价格
    private BigDecimal promoPrice;

    // 秒杀活动ID
    private Integer promoId;

    // 秒杀活动开始时间
    private String startDate;
}
