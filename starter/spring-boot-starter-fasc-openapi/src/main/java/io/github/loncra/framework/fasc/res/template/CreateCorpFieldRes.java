package io.github.loncra.framework.fasc.res.template;

import java.util.List;

/**
 * @author Fadada
 * @date 2023/6/28 11:16
 */
public class CreateCorpFieldRes {

    private List<CreateCorpFieldFailInfo> failField;

    public List<CreateCorpFieldFailInfo> getFailField() {
        return failField;
    }

    public void setFailField(List<CreateCorpFieldFailInfo> failField) {
        this.failField = failField;
    }
}
