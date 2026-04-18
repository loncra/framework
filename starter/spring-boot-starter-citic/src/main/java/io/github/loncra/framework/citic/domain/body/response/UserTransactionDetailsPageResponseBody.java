package io.github.loncra.framework.citic.domain.body.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.loncra.framework.citic.domain.metadata.BasicResponseMetadata;
import io.github.loncra.framework.citic.domain.metadata.UserTransactionDetailsMetadata;

import java.io.Serial;
import java.util.List;

/**
 * @author maurice.chen
 */
public class UserTransactionDetailsPageResponseBody extends BasicResponseMetadata {

    @Serial
    private static final long serialVersionUID = -9194003362079433582L;

    /**
     * 商户编号
     */
    @JacksonXmlProperty(localName = "MCHNT_ID")
    private String merchantId;

    @JacksonXmlProperty(localName = "USER_ID")
    private String userId;

    @JacksonXmlProperty(localName = "TOTAL_PAGE")
    private String totalPage;

    @JacksonXmlProperty(localName = "TOTAL_NUMBER")
    private String totalCount;

    @JacksonXmlElementWrapper(localName = "LIST")
    @JacksonXmlProperty(localName = "ROWS")
    private List<UserTransactionDetailsMetadata> elements;

    public UserTransactionDetailsPageResponseBody() {
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public List<UserTransactionDetailsMetadata> getElements() {
        return elements;
    }

    public void setElements(List<UserTransactionDetailsMetadata> elements) {
        this.elements = elements;
    }
}
