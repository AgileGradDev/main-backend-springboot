package com.agd.backend.api.controller;


import com.agd.backend.api.entity.VisitLog;
import com.agd.backend.api.model.response.ListResult;
import com.agd.backend.api.service.ResponseService;
import com.agd.backend.api.service.visit_log.VisitLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api(tags = {"StoreVisitLog"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/store/{id}/visit-log")
public class StoreVisitLogController {

    private final VisitLogService visitLogService;
    private final ResponseService responseService;

//    public ListResult<VisitLog> listVisitLog(
//            @ApiParam(value = "Restaurant name", required = true) @PathVariable UUID id
//    ) {
//
//    }
}
