package io.github.loncra.framework.spring.security.core.authentication.token;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.spring.security.core.entity.AuditAuthenticationDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.MultiValueMap;

import java.io.Serial;
import java.util.Map;


/**
 * 请求认证 token
 *
 * @author maurice
 */
public class RequestAuthenticationToken extends TypeAuthenticationToken {

    @Serial
    private static final long serialVersionUID = 8070060147431763553L;

    private final Map<String, Object> metadata;

    public RequestAuthenticationToken(
            AuditAuthenticationDetails details,
            UsernamePasswordAuthenticationToken token,
            Map<String, Object> metadata
    ) {
        super(token.getPrincipal(), token.getCredentials(), details.getType());
        setDetails(details);
        this.metadata = metadata;
    }

    /**
     * 获取请求参数信息
     *
     * @return 请求参数信息
     */
    public MultiValueMap<String, String> getParameterMap() {
        return CastUtils.cast(getDetails(), AuditAuthenticationDetails.class).getRequestParameters();
    }

    /**
     * 获取请求头信息
     *
     * @return 请求头信息
     */
    public MultiValueMap<String, String> getHeaderMap() {
        return CastUtils.cast(getDetails(), AuditAuthenticationDetails.class).getRequestHeaders();
    }

    /**
     * 获取附加元数据信息
     *
     * @return 元数据信息
     */
    public Map<String, Object> getMetadata() {
        return metadata;
    }
}
