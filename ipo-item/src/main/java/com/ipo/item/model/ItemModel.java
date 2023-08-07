package com.ipo.item.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-07-21 10:36
 */
@Data
public class ItemModel implements Serializable {


    private Integer id;

    @NotBlank(message = "商品名称不能为空")
    private String title;

    @NotNull(message = "商品价格不能为空")
    @Min(value = 0, message = "商品价格必须大于0")
    private BigDecimal price;

    @NotNull(message = "库存不能不填")
    private Integer stock;

    @NotBlank(message = "商品描述信息不能为空")
    private String description;

    // 商品销量
    private Integer sales;

    // 商品描述图片的url
    private String imgUrl;

    // 秒杀活动
    private PromoModel promoModel;


}
