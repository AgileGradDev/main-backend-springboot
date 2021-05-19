package com.agd.backend.api.controller;


import com.agd.backend.api.entity.VisitLog;
import com.agd.backend.api.service.visit_log.VisitLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(tags = {"VisitLog"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/visit-log")
public class VisitLogController {

    private final VisitLogService visitLogService;

    @PostMapping(value = "/")
    public VisitLog create(
            @RequestParam UUID store_id,
            @RequestParam float rating,
            @RequestParam String text
    ) {
        return visitLogService.createVisitLog(store_id, rating, text);
    }

//    @GetMapping(value = "/{storeId}")
//    public List<VisitLog> list(@PathVariable UUID storeId) {
//
//    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(
            @ApiParam(value = "UUID", required = true) @PathVariable UUID id
    ){
        visitLogService.deleteVisitLog(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
