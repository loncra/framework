package io.github.loncra.framework.fasc.req.voucher;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

import java.util.List;

public class GetVoucherTaskListReq extends BaseReq {
    private OpenId initiator;
    private SignTaskListFilter listFilter;
    private Integer listPageNo;
    private Integer listPageSize;

    public OpenId getInitiator() {
        return initiator;
    }

    public void setInitiator(OpenId initiator) {
        this.initiator = initiator;
    }

    public SignTaskListFilter getListFilter() {
        return listFilter;
    }

    public void setListFilter(SignTaskListFilter listFilter) {
        this.listFilter = listFilter;
    }

    public Integer getListPageNo() {
        return listPageNo;
    }

    public void setListPageNo(Integer listPageNo) {
        this.listPageNo = listPageNo;
    }

    public Integer getListPageSize() {
        return listPageSize;
    }

    public void setListPageSize(Integer listPageSize) {
        this.listPageSize = listPageSize;
    }

    public static class SignTaskListFilter {
        private String signTaskSubject;
        private String actorId;
        private List<String> signTaskStatus;

        public String getSignTaskSubject() {
            return signTaskSubject;
        }

        public void setSignTaskSubject(String signTaskSubject) {
            this.signTaskSubject = signTaskSubject;
        }

        public String getActorId() {
            return actorId;
        }

        public void setActorId(String actorId) {
            this.actorId = actorId;
        }

        public List<String> getSignTaskStatus() {
            return signTaskStatus;
        }

        public void setSignTaskStatus(List<String> signTaskStatus) {
            this.signTaskStatus = signTaskStatus;
        }
    }

    public static class OpenId {
        private String idType;
        private String openId;

        public String getIdType() {
            return idType;
        }

        public void setIdType(String idType) {
            this.idType = idType;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }
    }
}
