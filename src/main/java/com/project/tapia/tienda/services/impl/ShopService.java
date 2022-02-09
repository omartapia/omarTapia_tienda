package com.project.tapia.tienda.services.impl;

import com.project.tapia.tienda.dao.IShopDao;
import com.project.tapia.tienda.exception.ApiWebClientException;
import com.project.tapia.tienda.models.Client;
import com.project.tapia.tienda.models.Shop;
import com.project.tapia.tienda.services.IShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService implements IShopService {
    private static final Logger logger = LoggerFactory.getLogger(ShopService.class);

    @Autowired
    private IShopDao dao;

    @Override
    public List<Shop> findAll() {
        return dao.findAll();
    }

    @Override
    public Shop findById(Long id) {
        return dao.findById(id)
                .orElseThrow(() -> new ApiWebClientException("shop no found."));
    }

    @Override
    public Shop save(Shop shop) {
        return dao.save(shop);
    }

}
