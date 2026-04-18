package io.github.loncra.framework.fasc.req.seal;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.List;

/**
 * @author gongj
 * @date 2022/7/13
 */
public class GetSealListFilter extends BaseBean {
    private List<String> categoryType;

    public List<String> getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(List<String> categoryType) {
        this.categoryType = categoryType;
    }
}
