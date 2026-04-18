package io.github.loncra.framework.mybatis.plus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import io.github.loncra.framework.commons.query.QueryGenerator;
import io.github.loncra.framework.crypto.algorithm.ByteSource;
import io.github.loncra.framework.crypto.algorithm.cipher.AesCipherService;
import io.github.loncra.framework.crypto.algorithm.cipher.OperationMode;
import io.github.loncra.framework.crypto.algorithm.cipher.RsaCipherService;
import io.github.loncra.framework.mybatis.MybatisAutoConfiguration;
import io.github.loncra.framework.mybatis.config.OperationDataTraceProperties;
import io.github.loncra.framework.mybatis.interceptor.audit.OperationDataTraceResolver;
import io.github.loncra.framework.mybatis.plus.audit.MybatisPlusOperationDataTraceResolver;
import io.github.loncra.framework.mybatis.plus.crypto.DataAesCryptoService;
import io.github.loncra.framework.mybatis.plus.crypto.DataRsaCryptoService;
import io.github.loncra.framework.mybatis.plus.interceptor.DecryptInterceptor;
import io.github.loncra.framework.mybatis.plus.interceptor.EncryptInnerInterceptor;
import io.github.loncra.framework.mybatis.plus.interceptor.LastModifiedDateInnerInterceptor;
import io.github.loncra.framework.mybatis.plus.interceptor.tenant.TenantEntityHandler;
import io.github.loncra.framework.mybatis.plus.tenant.TenantLinePolicy;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus 自动配置实现
 *
 * @author maurice.chen
 */
@Configuration
@ConditionalOnClass(MybatisPlusInterceptor.class)
@AutoConfigureBefore(MybatisAutoConfiguration.class)
@EnableConfigurationProperties({CryptoProperties.class, OperationDataTraceProperties.class})
@ConditionalOnProperty(prefix = "loncra.framework.mybatis.plus", value = "enabled", matchIfMissing = true)
public class MybatisPlusAutoConfiguration {

    /**
     * 创建 Mybatis-Plus 查询生成器 Bean
     *
     * @return Mybatis-Plus 查询生成器实例
     */
    @Bean
    @ConditionalOnMissingBean(QueryGenerator.class)
    public MybatisPlusQueryGenerator<?> mybatisPlusQueryGenerator() {
        return new MybatisPlusQueryGenerator<>();
    }

    /**
     * 租户行条件策略：默认始终追加租户条件；接入方可注册同名 Bean 覆盖（如运营后台跨租户查询）。
     *
     * @return 租户行策略实例
     */
    @Bean
    @ConditionalOnMissingBean(TenantLinePolicy.class)
    public TenantLinePolicy tenantLinePolicy() {
        return TenantLinePolicy.ALWAYS;
    }

    /**
     * 创建 Mybatis-Plus 操作数据追踪解析器 Bean
     *
     * @param operationDataTraceProperties 操作数据追踪配置属性
     *
     * @return Mybatis-Plus 操作数据追踪解析器实例
     */
    @Bean
    @ConditionalOnMissingBean(OperationDataTraceResolver.class)
    @ConditionalOnProperty(prefix = "loncra.framework.mybatis.operation-data-trace", value = "enabled", matchIfMissing = true)
    public MybatisPlusOperationDataTraceResolver mybatisPlusOperationDataTraceRepository(OperationDataTraceProperties operationDataTraceProperties) {
        return new MybatisPlusOperationDataTraceResolver(operationDataTraceProperties);
    }

    /**
     * 创建 Mybatis-Plus 拦截器 Bean
     * <p>配置了乐观锁、分页、防止全表更新删除、最后修改时间、加密等内部拦截器</p>
     *
     * @param applicationContext Spring 应用上下文
     *
     * @return Mybatis-Plus 拦截器实例
     */
    @Bean
    @ConditionalOnMissingBean(MybatisPlusInterceptor.class)
    public MybatisPlusInterceptor mybatisPlusInterceptor(
            ApplicationContext applicationContext,
            TenantLinePolicy tenantLinePolicy
    ) {

        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor(true));
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        interceptor.addInnerInterceptor(new LastModifiedDateInnerInterceptor(true));
        interceptor.addInnerInterceptor(new EncryptInnerInterceptor(true, applicationContext));
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantEntityHandler(applicationContext, tenantLinePolicy)));

        return interceptor;
    }

    /**
     * 创建解密拦截器 Bean
     *
     * @param applicationContext Spring 应用上下文
     *
     * @return 解密拦截器实例
     */
    @Bean
    @ConditionalOnMissingBean(DecryptInterceptor.class)
    public DecryptInterceptor decryptInterceptor(ApplicationContext applicationContext) {
        return new DecryptInterceptor(applicationContext);
    }

    /**
     * 创建 AES 数据加解密服务 Bean
     * <p>使用 ECB 模式进行 AES 加密，不使用初始化向量</p>
     *
     * @param cryptoProperties 加解密配置属性
     *
     * @return AES 数据加解密服务实例
     */
    @ConditionalOnMissingBean(DataAesCryptoService.class)
    @Bean(CryptoProperties.MYBATIS_PLUS_DATA_AES_CRYPTO_SERVICE_NAME)
    @ConditionalOnProperty(prefix = "loncra.framework.mybatis.plus.crypto", value = "enabled", matchIfMissing = true)
    public DataAesCryptoService dataAesCryptoService(CryptoProperties cryptoProperties) {

        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setInitializationVectorSize(0);
        aesCipherService.setMode(OperationMode.ECB);
        aesCipherService.setRandomNumberGenerator(null);

        ByteSource source = DataAesCryptoService.generate16ByteKey(cryptoProperties.getDataAesCryptoKey());

        return new DataAesCryptoService(aesCipherService, source.getBase64());
    }

    /**
     * 创建 RSA 数据加解密服务 Bean
     *
     * @param cryptoProperties 加解密配置属性
     *
     * @return RSA 数据加解密服务实例
     */
    @ConditionalOnMissingBean(DataRsaCryptoService.class)
    @Bean(CryptoProperties.MYBATIS_PLUS_DATA_RSA_CRYPTO_SERVICE_NAME)
    @ConditionalOnProperty(prefix = "loncra.framework.mybatis.plus.crypto", value = "enabled", matchIfMissing = true)
    public DataRsaCryptoService dataRsaCryptoService(CryptoProperties cryptoProperties) {
        return new DataRsaCryptoService(new RsaCipherService(), cryptoProperties.getDataRasCryptoPublicKey(), cryptoProperties.getDataRasCryptoPrivateKey());
    }

}
