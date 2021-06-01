package com.agd.backend.api.controller;

import com.agd.backend.api.entity.StoreCategory;
import com.agd.backend.api.entity.StoreStoreCategoryMap;
import com.agd.backend.api.service.store.StoreCategoryService;
import com.agd.backend.api.service.store.StoreStoreCategoryMapService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(tags = {"StoreCategory"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/store/category")
public class StoreCategoryController {
    private final StoreCategoryService storeCategoryService;

    @GetMapping(value = "/{storeId}/category")
    public List<StoreCategory> list(@PathVariable UUID storeId) {
        return storeCategoryService.ListStoreCategory(storeId);
    }

    @PostMapping(value = "/")
    public StoreCategory create(@RequestParam String name) {
        return storeCategoryService.createStoreCategory(name);
    }
}
