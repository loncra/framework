package io.github.loncra.framework.spring.security.core.test.controller;

import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.security.plugin.Plugin;
import io.github.loncra.framework.spring.security.core.test.entity.OperationDataEntity;
import io.github.loncra.framework.spring.security.core.test.service.OperationDataService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Plugin(
        name = "OperateDataController",
        id = "operateData",
        type = "menu",
        sources = "test"
)
@RequestMapping("operateData")
public class OperationDataController {

    private final OperationDataService operationDataService;

    public OperationDataController(OperationDataService operationDataService) {
        this.operationDataService = operationDataService;
    }

    @PostMapping("save")
    @Plugin(name = "save", audit = true, operationDataTrace = true)
    public RestResult<Integer> save(
            @RequestBody
            OperationDataEntity operationDataEntity
    ) {
        operationDataService.save(operationDataEntity);
        return RestResult.ofSuccess(operationDataEntity.getId());
    }

    @PostMapping("delete")
    @Plugin(name = "delete", audit = true, operationDataTrace = true)
    public RestResult<?> delete(
            @RequestParam
            List<Integer> ids
    ) {
        return RestResult.of("删除 " + ids.size() + " 记录成功");
    }

    @GetMapping("query")
    public List<OperationDataEntity> query() {
        return operationDataService.lambdaQuery().eq(OperationDataEntity::getName, "test").list();
    }
}
