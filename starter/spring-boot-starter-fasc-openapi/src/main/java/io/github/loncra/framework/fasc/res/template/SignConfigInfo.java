package io.github.loncra.framework.fasc.res.template;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.List;

/**
 * @author gongj
 * @date 2022/6/22
 */
public class SignConfigInfo extends BaseBean {
    private Integer orderNo;
    private String signerSignMethod;
    private List<String> verifyMethods;
    private Boolean readingToEnd;
    private Boolean requestMemberSign;
    private String readingTime;

    public Boolean getRequestMemberSign() {
        return requestMemberSign;
    }

    public void setRequestMemberSign(Boolean requestMemberSign) {
        this.requestMemberSign = requestMemberSign;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getSignerSignMethod() {
        return signerSignMethod;
    }

    public void setSignerSignMethod(String signerSignMethod) {
        this.signerSignMethod = signerSignMethod;
    }

    public List<String> getVerifyMethods() {
        return verifyMethods;
    }

    public void setVerifyMethods(List<String> verifyMethods) {
        this.verifyMethods = verifyMethods;
    }

    public Boolean getReadingToEnd() {
        return readingToEnd;
    }

    public void setReadingToEnd(Boolean readingToEnd) {
        this.readingToEnd = readingToEnd;
    }

    public String getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(String readingTime) {
        this.readingTime = readingTime;
    }
}
