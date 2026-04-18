package io.github.loncra.framework.citic;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import io.github.loncra.framework.citic.service.CiticService;
import io.github.loncra.framework.commons.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

@Configuration
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
@ConditionalOnProperty(prefix = "loncra.framework.citic", value = "enabled", matchIfMissing = true)
@EnableConfigurationProperties(CiticProperties.class)
public class CiticAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(CiticAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    public MappingJackson2XmlHttpMessageConverter mappingJackson2XmlHttpMessageConverter(Jackson2ObjectMapperBuilder builder) {
        XmlMapper xmlMapper = builder.createXmlMapper(true).build();
        xmlMapper.enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION);
        return new MappingJackson2XmlHttpMessageConverter(xmlMapper);
    }

    @Bean
    public CiticService citicService(MappingJackson2XmlHttpMessageConverter converter,CiticProperties citicProperties) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("[中信银行]:基础配置信息为: {}", SystemException.convertSupplier(() -> converter.getObjectMapper().writeValueAsString(citicProperties)));
        }
        SimpleClientHttpRequestFactory client = new SimpleClientHttpRequestFactory() {

            @Override
            protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
                if (connection instanceof HttpsURLConnection httpsURLConnection) {
                    this.prepareHttpsConnection(httpsURLConnection);
                }

                super.prepareConnection(connection, httpMethod);
            }

            private void prepareHttpsConnection(HttpsURLConnection connection) {
                connection.setHostnameVerifier(new SkipHostnameVerifier());

                try {
                    connection.setSSLSocketFactory(this.createSslSocketFactory());
                } catch (Exception e) {

                }

            }

            private SSLSocketFactory createSslSocketFactory() throws Exception {
                SSLContext context = SSLContext.getInstance("TLS");
                context.init(null, new TrustManager[]{new SkipX509TrustManager()}, new SecureRandom());
                return context.getSocketFactory();
            }

        };
        return new CiticService(new RestTemplate(client), citicProperties, converter);
    }

    private static final class SkipHostnameVerifier implements HostnameVerifier {
        private SkipHostnameVerifier() {
        }

        @Override
        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }
    }

    private static final class SkipX509TrustManager implements X509TrustManager {
        private SkipX509TrustManager() {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }
    }
}
