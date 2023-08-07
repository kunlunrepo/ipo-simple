package com.ipo.item.service.impl;

import com.ipo.item.dao.PromoDOMapper;
import com.ipo.item.dao.PromoDOMapperSpec;
import com.ipo.item.dao.data.PromoDO;
import com.ipo.item.model.PromoModel;
import com.ipo.item.service.PromoService;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-07-21 14:38
 */
@Service
public class PromoServiceImpl implements PromoService {

    @Autowired
    private PromoDOMapper promoDOMapper;

    @Autowired
    private PromoDOMapperSpec promoDOMapperSpec;

    private PromoModel convertFromDataObject(PromoDO promoDo) {
        if (promoDo == null) {
            return null;
        }
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDo, promoModel);
        promoModel.setPromoItemPrice(new BigDecimal(promoDo.getPromoItemPrice()));
        promoModel.setStartDate(new DateTime(promoDo.getStartDate()));
        promoModel.setEndDate(new DateTime(promoDo.getEndDate()));
        return promoModel;
    }

    @Override
    public PromoModel getPromoByItemId(Integer itemId) {

        PromoDO promoDo = promoDOMapperSpec.selectByItemId(itemId);

        PromoModel promoModel = convertFromDataObject(promoDo);
        if (promoModel == null) {
            return null;
        }

        if (promoModel.getStartDate().isAfterNow()) {
            promoModel.setStatus(1);
        } else if (promoModel.getEndDate().isBeforeNow()) {
            promoModel.setStatus(3);
        } else {
            promoModel.setStatus(2);
        }

        return promoModel;
    }
}
