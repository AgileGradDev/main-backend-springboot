package com.agd.backend.api.controller;

import com.agd.backend.api.entity.StoreImage;
import com.agd.backend.api.service.store.StoreImageService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(tags = {"StoreImage"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/store/image")
public class StoreImageController {

    private final StoreImageService storeImageService;

    @GetMapping(value = "/{storeId}")
    public List<StoreImage> list(@PathVariable UUID storeId) {
        return storeImageService.listStoreImages(storeId);
    }

    @PostMapping(value = "/{storeId}")
    public StoreImage create(@PathVariable UUID storeId, @RequestParam String url) {
        return storeImageService.createStoreImage(storeId, url);
    }
}
