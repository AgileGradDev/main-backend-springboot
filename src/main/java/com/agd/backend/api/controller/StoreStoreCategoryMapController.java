package com.agd.backend.api.controller;


import com.agd.backend.api.entity.StoreStoreCategoryMap;
import com.agd.backend.api.service.store.StoreStoreCategoryMapService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api(tags = "{StoreStoreCategoryMap}")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/store")
public class StoreStoreCategoryMapController {

    private final StoreStoreCategoryMapService storeStoreCategoryMapService;

    @PostMapping(value = "/{storeId}/map-category")
    public StoreStoreCategoryMap create(@PathVariable UUID storeId, @RequestParam String storeCategoryName) {
        return storeStoreCategoryMapService.createStoreStoreCategory(storeId, storeCategoryName);
    }

}
