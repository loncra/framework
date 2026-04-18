package io.github.loncra.framework.fasc.res.user;


import java.util.List;

public class GetUserTransactionDetailRes {

    private String transactionId;
    private List<Long> recordIds;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public List<Long> getRecordIds() {
        return recordIds;
    }

    public void setRecordIds(List<Long> recordIds) {
        this.recordIds = recordIds;
    }
}
