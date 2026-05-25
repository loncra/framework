package io.github.loncra.framework.spring.security.core.test.controller;

import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.security.audit.Auditable;
import io.github.loncra.framework.security.plugin.Plugin;
import io.github.loncra.framework.spring.security.core.audit.OperationDataTrace;
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

    @Auditable
    @OperationDataTrace
    @Plugin(name = "save")
    @PostMapping("save")
    public RestResult<Integer> save(
            @RequestBody
            OperationDataEntity operationDataEntity
    ) {
        operationDataService.save(operationDataEntity);
        return RestResult.ofSuccess(operationDataEntity.getId());
    }

    @Auditable
    @OperationDataTrace
    @Plugin(name = "delete")
    @PostMapping("delete")
    public RestResult<?> delete(
            @RequestParam
            List<Integer> ids
    ) {
        operationDataService.deleteById(ids);
        return RestResult.of("删除 " + ids.size() + " 记录成功");
    }

    @GetMapping("query")
    public List<OperationDataEntity> query() {
        return operationDataService.lambdaQuery().eq(OperationDataEntity::getName, "test").list();
    }
}
