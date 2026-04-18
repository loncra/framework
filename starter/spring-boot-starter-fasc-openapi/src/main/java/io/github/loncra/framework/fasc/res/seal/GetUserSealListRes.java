package io.github.loncra.framework.fasc.res.seal;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.List;

/**
 * @Author： Fadada
 * @Date: 2022/10/8
 */
public class GetUserSealListRes extends BaseBean {

    private List<GetUserListSealInfo> sealInfos;

    public List<GetUserListSealInfo> getSealInfos() {
        return sealInfos;
    }

    public void setSealInfos(List<GetUserListSealInfo> sealInfos) {
        this.sealInfos = sealInfos;
    }
}
