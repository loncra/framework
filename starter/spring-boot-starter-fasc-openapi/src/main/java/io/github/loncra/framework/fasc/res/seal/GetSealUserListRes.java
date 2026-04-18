package io.github.loncra.framework.fasc.res.seal;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.List;

/**
 * @author gongj
 * @date 2022/7/13
 */
public class GetSealUserListRes extends BaseBean {

    private List<GetUserListSealUser> sealUsers;

    public List<GetUserListSealUser> getSealUsers() {
        return sealUsers;
    }

    public void setSealUsers(List<GetUserListSealUser> sealUsers) {
        this.sealUsers = sealUsers;
    }
}
