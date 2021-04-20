package com.example.demo.api.controller;

import com.example.demo.api.entity.Store;
import com.example.demo.api.model.response.CommonResult;
import com.example.demo.api.model.response.SingleResult;
import com.example.demo.api.repo.StoreJpaRepo;
import com.example.demo.api.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"Store"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "store")
public class StoreController {

    private final StoreJpaRepo storeJpaRepo;
    private final ResponseService responseService;

    @ApiOperation(value = "식당 정보 조회", notes = "이름으로 식당 정보를 조회한다")
    @GetMapping(value = "/{r_name}")
    public SingleResult<Store> findStoreByName(@PathVariable String r_name){
        return responseService.getSingleResult(storeJpaRepo.findByRname(r_name));
    }

    @ApiOperation(value = "상점 등록", notes = "상점 등록한다.")
    @PostMapping(value = "/create")
    public SingleResult<Store> make_store(@ApiParam(value = "식당 이름", required = true)@RequestParam String r_name,
            @ApiParam(value = "식당 설명", required = true)@RequestParam String r_detail,
            @ApiParam(value = "식당 영업 시간", required = true)@RequestParam String r_hours_operation,
            @ApiParam(value = "식당 평점", required = true)@RequestParam float r_rating,
            @ApiParam(value = "식당 위치", required = true)@RequestParam String r_location){

        Store store = Store.builder()
                .rname(r_name)
                .rdetail(r_detail)
                .rhours_operation(r_hours_operation)
                .rrating(r_rating)
                .rlocation(r_location)
                .build();
        return responseService.getSingleResult(storeJpaRepo.save(store));
    }

    @ApiOperation(value = "상점 정보 갱신", notes = "이름으로 상점 찾아서 해당 상점 정보를 갱신한다.")
    @PutMapping(value = "/update")
    public SingleResult<Store> modify_store(
            @ApiParam(value = "기존 식당 이름", required = true)@RequestParam String r_name,
            @ApiParam(value = "새로운 식당 이름. 안바꿀거면 그대로.", required = true)@RequestParam String new_r_name,
            @ApiParam(value = "식당 설명", required = true)@RequestParam String r_detail,
            @ApiParam(value = "식당 영업 시간", required = true)@RequestParam String r_hours_operation,
            @ApiParam(value = "식당 평점", required = true)@RequestParam float r_rating,
            @ApiParam(value = "식당 위치", required = true)@RequestParam String r_location) {
        Store previous = storeJpaRepo.findByRname(r_name);
        storeJpaRepo.deleteById(previous.getRid());
        Store store = Store.builder()
                .rname(new_r_name)
                .rdetail(r_detail)
                .rhours_operation(r_hours_operation)
                .rrating(r_rating)
                .rlocation(r_location)
                .build();
        return responseService.getSingleResult(storeJpaRepo.save(store));
    }

    @ApiOperation(value = "상점 해지", notes = "상점 이름으로 삭제한다.")
    @DeleteMapping(value = "/delete/{r_name}")
    public CommonResult delete_store(
            @ApiParam(value = "식당 이름", required = true)@PathVariable String r_name) {
        storeJpaRepo.deleteByRname(r_name);
        return responseService.getSuccessResult();
    }
}