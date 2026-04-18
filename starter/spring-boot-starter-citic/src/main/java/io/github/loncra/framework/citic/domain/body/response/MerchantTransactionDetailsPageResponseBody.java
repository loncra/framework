package io.github.loncra.framework.citic.domain.body.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.loncra.framework.citic.domain.metadata.BasicResponseMetadata;
import io.github.loncra.framework.citic.domain.metadata.MerchantTransactionDetailsMetadata;

import java.io.Serial;
import java.util.List;

/**
 * @author maurice.chen
 */
public class MerchantTransactionDetailsPageResponseBody extends BasicResponseMetadata {

    @Serial
    private static final long serialVersionUID = -9194003362079433582L;

    @JacksonXmlProperty(localName = "TOTAL_PAGE")
    private String totalPage;

    @JacksonXmlProperty(localName = "TOTAL_NUMBER")
    private String totalCount;

    @JacksonXmlElementWrapper(localName = "LIST")
    @JacksonXmlProperty(localName = "ROWS")
    private List<MerchantTransactionDetailsMetadata> elements;

    public MerchantTransactionDetailsPageResponseBody() {
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

    public List<MerchantTransactionDetailsMetadata> getElements() {
        return elements;
    }

    public void setElements(List<MerchantTransactionDetailsMetadata> elements) {
        this.elements = elements;
    }
}
