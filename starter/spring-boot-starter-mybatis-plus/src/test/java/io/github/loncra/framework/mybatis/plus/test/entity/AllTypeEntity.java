package io.github.loncra.framework.mybatis.plus.test.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.loncra.framework.commons.annotation.JsonCollectionGenericType;
import io.github.loncra.framework.commons.enumerate.basic.DisabledOrEnabled;
import io.github.loncra.framework.commons.enumerate.basic.ExecuteStatus;
import io.github.loncra.framework.commons.id.BasicIdentification;
import io.github.loncra.framework.commons.id.StringIdEntity;
import io.github.loncra.framework.mybatis.handler.JacksonJsonTypeHandler;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@TableName(value = "tb_all_type_entity", autoResultMap = true)
public class AllTypeEntity implements BasicIdentification<Integer> {

    @Serial
    private static final long serialVersionUID = 5548079224380108843L;

    private Integer id;

    private String name;

    private Integer age;

    private String title;

    private BigDecimal score;

    private BigDecimal price;

    private Instant creationTime;

    private Instant deletedTime;

    @NotEmpty
    @TableField(typeHandler = JacksonJsonTypeHandler.class)
    private Map<String, Object> device;

    @TableField(typeHandler = JacksonJsonTypeHandler.class)
    @JsonCollectionGenericType(StringIdEntity.class)
    private List<StringIdEntity> entities;

    private DisabledOrEnabled status = DisabledOrEnabled.Disabled;

    private CreativeModeEnum creativeMode = CreativeModeEnum.IMAGE_TO_VIDEO;

    @TableField(typeHandler = JacksonJsonTypeHandler.class)
    @JsonCollectionGenericType(ExecuteStatus.class)
    private List<ExecuteStatus> executes = Collections.singletonList(ExecuteStatus.Processing);

    public AllTypeEntity() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Map<String, Object> getDevice() {
        return device;
    }

    public void setDevice(Map<String, Object> device) {
        this.device = device;
    }

    public List<StringIdEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<StringIdEntity> entities) {
        this.entities = entities;
    }

    public DisabledOrEnabled getStatus() {
        return status;
    }

    public void setStatus(DisabledOrEnabled status) {
        this.status = status;
    }

    public List<ExecuteStatus> getExecutes() {
        return executes;
    }

    public void setExecutes(List<ExecuteStatus> executes) {
        this.executes = executes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Instant getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Instant creationTime) {
        this.creationTime = creationTime;
    }

    public Instant getDeletedTime() {
        return deletedTime;
    }

    public void setDeletedTime(Instant deletedTime) {
        this.deletedTime = deletedTime;
    }

    public CreativeModeEnum getCreativeMode() {
        return creativeMode;
    }

    public void setCreativeMode(CreativeModeEnum creativeMode) {
        this.creativeMode = creativeMode;
    }
}