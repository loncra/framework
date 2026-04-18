package io.github.loncra.framework.citic.domain.body.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.loncra.framework.citic.domain.metadata.BankCardMetadata;
import io.github.loncra.framework.citic.domain.metadata.BasicResponseMetadata;

import java.io.Serial;
import java.util.List;

/**
 * @author maurice.chen
 */
public class QueryBankCardResponseBody extends BasicResponseMetadata {

    @Serial
    private static final long serialVersionUID = -5976146210497277386L;

    @JacksonXmlProperty(localName = "LIST_COUNT")
    private long count;

    @JacksonXmlElementWrapper(localName = "LIST")
    @JacksonXmlProperty(localName = "ROWS")
    private List<BankCardMetadata> result;

    public QueryBankCardResponseBody() {
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<BankCardMetadata> getResult() {
        return result;
    }

    public void setResult(List<BankCardMetadata> result) {
        this.result = result;
    }
}
