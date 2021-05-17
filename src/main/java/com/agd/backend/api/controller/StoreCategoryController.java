package com.agd.backend.api.controller;

import com.agd.backend.api.entity.StoreCategory;
import com.agd.backend.api.service.store.StoreCategoryService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"StoreCategory"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/store/category")
public class StoreCategoryController {
    private final StoreCategoryService storeCategoryService;

    @PostMapping(value = "/")
    public StoreCategory create(@RequestParam String name) {
        return storeCategoryService.createStoreCategory(name);
    }
}
