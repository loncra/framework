package io.github.loncra.framework.fasc.req.user;/**
 * @author xjf
 * @date 2023年09月22日 14:05
 */

import io.github.loncra.framework.fasc.bean.base.BaseReq;

/**
 *
 *
 * @author xjf
 * @date 2023年09月22日 14:05
 */
public class GetVerifyDetailReq extends BaseReq {

    private String transactionId;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
