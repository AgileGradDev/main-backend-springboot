package com.agd.backend.api.controller;

import com.agd.backend.api.model.response.ListResult;
import com.agd.backend.api.model.response.SingleResult;
import com.agd.backend.api.service.ResponseService;
import com.agd.backend.api.entity.Store;
import com.agd.backend.api.model.response.CommonResult;
import com.agd.backend.api.repository.StoreRepository;
import com.agd.backend.api.service.store.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(tags = {"Store"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/store")
public class StoreController {

    private final StoreRepository storeRepository;
    private final StoreService storeService;
    private final ResponseService responseService;

    @GetMapping(value = "/{id}")
    public Store retrieve(@PathVariable UUID id) {
        return storeService.retrieveStore(id);
    }

    @ApiOperation(value = "MVP 전용 API", notes = "식당 정보 전체 조회")
    @GetMapping(value = "/all")
    public List<Store> listStore() {
        return storeService.ListStore();
    }

    @ApiOperation(value = "상점 등록", notes = "상점 등록한다.")
    @PostMapping(value = "/create")
    public SingleResult<Store> make_store(@ApiParam(value = "식당 이름", required = true) @RequestParam String name,
                                          @ApiParam(value = "식당 설명", required = true) @RequestParam String detail,
                                          @ApiParam(value = "식당 영업 시간", required = true) @RequestParam String hours_operation,
                                          @ApiParam(value = "식당 평점", required = true) @RequestParam float rating,
                                          @ApiParam(value = "식당 위치", required = true) @RequestParam String location) {

        Store store = Store.builder()
                .name(name)
                .description(detail)
                .opening_hours(hours_operation)
                .rating(rating)
                .location(location)
                .build();
        return responseService.getSingleResult(storeRepository.save(store));
    }

    @ApiOperation(value = "상점 정보 갱신", notes = "이름으로 상점 찾아서 해당 상점 정보를 갱신한다.")
    @PutMapping(value = "/update")
    public SingleResult<Store> modify_store(
            @ApiParam(value = "기존 식당 이름", required = true) @RequestParam String name,
            @ApiParam(value = "새로운 식당 이름. 안바꿀거면 그대로.", required = true) @RequestParam String new_name,
            @ApiParam(value = "식당 설명", required = true) @RequestParam String detail,
            @ApiParam(value = "식당 영업 시간", required = true) @RequestParam String hours_operation,
            @ApiParam(value = "식당 평점", required = true) @RequestParam float rating,
            @ApiParam(value = "식당 위치", required = true) @RequestParam String location) {
        Store previous = storeRepository.findByName(name);
        storeRepository.deleteById(previous.getId());
        Store store = Store.builder()
                .name(new_name)
                .description(detail)
                .opening_hours(hours_operation)
                .rating(rating)
                .location(location)
                .build();
        return responseService.getSingleResult(storeRepository.save(store));
    }

    @ApiOperation(value = "상점 해지", notes = "상점 이름으로 삭제한다.")
    @DeleteMapping(value = "/delete/{name}")
    public CommonResult delete_store(
            @ApiParam(value = "식당 이름", required = true) @PathVariable String name) {
        storeRepository.deleteByName(name);
        return responseService.getSuccessResult();
    }
}