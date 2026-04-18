package io.github.loncra.framework.captcha.tianai;

import cloud.tianai.captcha.common.AnyMap;
import cloud.tianai.captcha.common.constant.CommonConstant;
import cloud.tianai.captcha.common.response.ApiResponse;
import cloud.tianai.captcha.common.util.CollectionUtils;
import cloud.tianai.captcha.common.util.ObjectUtils;
import cloud.tianai.captcha.generator.ImageCaptchaGenerator;
import cloud.tianai.captcha.generator.ImageTransform;
import cloud.tianai.captcha.generator.common.model.dto.ImageCaptchaInfo;
import cloud.tianai.captcha.generator.impl.MultiImageCaptchaGenerator;
import cloud.tianai.captcha.generator.impl.StandardRotateImageCaptchaGenerator;
import cloud.tianai.captcha.generator.impl.transform.Base64ImageTransform;
import cloud.tianai.captcha.resource.CrudResourceStore;
import cloud.tianai.captcha.resource.ImageCaptchaResourceManager;
import cloud.tianai.captcha.resource.ResourceProviders;
import cloud.tianai.captcha.resource.common.model.dto.Resource;
import cloud.tianai.captcha.resource.common.model.dto.ResourceMap;
import cloud.tianai.captcha.resource.impl.DefaultImageCaptchaResourceManager;
import cloud.tianai.captcha.validator.ImageCaptchaValidator;
import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import cloud.tianai.captcha.validator.impl.BasicCaptchaTrackValidator;
import io.github.loncra.framework.captcha.AbstractCaptchaService;
import io.github.loncra.framework.captcha.CaptchaProperties;
import io.github.loncra.framework.captcha.GenerateCaptchaResult;
import io.github.loncra.framework.captcha.SimpleCaptcha;
import io.github.loncra.framework.captcha.intercept.Interceptor;
import io.github.loncra.framework.captcha.storage.CaptchaStorageManager;
import io.github.loncra.framework.captcha.tianai.body.TianaiRequestBody;
import io.github.loncra.framework.captcha.tianai.config.TemplateProperties;
import io.github.loncra.framework.captcha.tianai.config.TianaiCaptchaProperties;
import io.github.loncra.framework.captcha.token.InterceptToken;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.commons.TimeProperties;
import io.github.loncra.framework.commons.exception.ErrorCodeException;
import io.github.loncra.framework.commons.exception.SystemException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.env.RandomValuePropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;
import org.springframework.validation.Validator;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * tianai 验证码服务实现
 *
 * @author maurice.chen
 */
public class TianaiCaptchaService extends AbstractCaptchaService<TianaiRequestBody> implements CrudResourceStore {

    public static final Logger LOGGER = LoggerFactory.getLogger(TianaiCaptchaService.class);

    public static final String DEFAULT_TYPE = "tianai";

    private final ImageCaptchaGenerator imageCaptchaGenerator;

    private final TianaiCaptchaProperties tianaiCaptchaProperties;

    /**
     * 负责计算一些数据存到缓存中，用于校验使用
     * mageCaptchaValidator负责校验用户滑动滑块是否正确和生成滑块的一些校验数据; 比如滑块到凹槽的百分比值
     */
    private final ImageCaptchaValidator imageCaptchaValidator = new BasicCaptchaTrackValidator();

    private final Map<String, Map<String, List<ResourceMap>>> templateResourceTagMap = new HashMap<>(2);
    private final Map<String, Map<String, List<Resource>>> resourceTagMap = new HashMap<>(2);

    public TianaiCaptchaService(CaptchaProperties captchaProperties,
                                Validator validator,
                                Interceptor interceptor,
                                CaptchaStorageManager captchaStorageManager,
                                TianaiCaptchaProperties tianaiCaptchaProperties) {

        setCaptchaProperties(captchaProperties);
        setInterceptor(interceptor);
        setValidator(validator);
        setCaptchaStorageManager(captchaStorageManager);

        tianaiCaptchaProperties
                .getTemplateMap()
                .forEach((key, value) -> value.forEach(r -> this.addTemplate(key, r)));

        tianaiCaptchaProperties
                .getResourceMap()
                .forEach((key, value) -> value.forEach(r -> addResource(key, new Resource(r.getType(), r.getData(), r.getTag()))));

        this.tianaiCaptchaProperties = tianaiCaptchaProperties;
        ImageCaptchaResourceManager imageCaptchaResourceManager = new DefaultImageCaptchaResourceManager(this, new ResourceProviders());
        ImageTransform imageTransform = new Base64ImageTransform();

        this.imageCaptchaGenerator = new MultiImageCaptchaGenerator(imageCaptchaResourceManager,  imageTransform).init();
    }

    private void addTemplate(String key, TemplateProperties r) {
        ResourceMap resource = new ResourceMap(CommonConstant.DEFAULT_TAG, 4);
        if (Objects.nonNull(r.getActiveImage())) {
            Resource image = new Resource(r.getActiveImage().getType(), r.getActiveImage().getData(), r.getActiveImage().getTag());
            resource.put(StandardRotateImageCaptchaGenerator.TEMPLATE_ACTIVE_IMAGE_NAME, image);
        }

        if (Objects.nonNull(r.getFixedImage())) {
            Resource image = new Resource(r.getFixedImage().getType(), r.getFixedImage().getData(), r.getFixedImage().getTag());
            resource.put(StandardRotateImageCaptchaGenerator.TEMPLATE_FIXED_IMAGE_NAME, image);
        }

        if (Objects.nonNull(r.getMaskImage())) {
            Resource image = new Resource(r.getMaskImage().getType(), r.getMaskImage().getData(), r.getMaskImage().getTag());
            resource.put(StandardRotateImageCaptchaGenerator.TEMPLATE_MASK_IMAGE_NAME, image);
        }

        this.addTemplate(key, resource);
    }

    public TianaiCaptchaProperties getTianaiCaptchaProperties() {
        return tianaiCaptchaProperties;
    }

    @Override
    protected Map<String, Object> createGenerateArgs() {
        Map<String, Object> result = new LinkedHashMap<>();

        result.put(TianaiCaptchaProperties.JS_URL_KEY, tianaiCaptchaProperties.getJsPath());

        return result;
    }

    @Override
    protected boolean matchesCaptcha(HttpServletRequest request, SimpleCaptcha captcha) {
        String captchaValue = request.getParameter(getCaptchaParamName());
        String md5 = DigestUtils.md5DigestAsHex(captcha.getValue().getBytes());
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("[tianai 验证码] 匹配验证码信息:请求值为:{},对比值为:{}", captchaValue, md5);
        }
        if (!Strings.CS.equals(md5, captchaValue)) {
            return false;
        }

        ImageCaptchaTrack track = SystemException.convertSupplier(() -> CastUtils.getObjectMapper().readValue(captcha.getValue(), ImageCaptchaTrack.class));
        Assert.notNull(track, "[tianai 验证码] 读取 ImageCaptchaTrack 数据为 null");
        Duration duration = Duration.between(
                Instant.ofEpochMilli(track.getStartTime()),
                Instant.ofEpochMilli(track.getStopTime())
        );
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(
                    "[tianai 验证码] 调用滑动时间比对，当前滑动时间值为:{} 秒, 服务验证码超时值为:{} 秒",
                    duration.getSeconds(),
                    tianaiCaptchaProperties.getServerVerifyTimeout().toSeconds()
            );
        }
        return duration.getSeconds() < tianaiCaptchaProperties.getServerVerifyTimeout().toSeconds();
    }

    public RestResult<Object> clientVerify(ImageCaptchaTrack imageCaptchaTrack, String token) {
        InterceptToken interceptToken = createInterceptToken(token);
        SimpleCaptcha captcha = getCaptchaStorageManager().getCaptcha(interceptToken);
        getCaptchaStorageManager().deleteCaptcha(interceptToken);

        try {
            Assert.notNull(captcha, "验证内容已过期");

            AnyMap map = SystemException.convertSupplier(() -> CastUtils.getObjectMapper().readValue(captcha.getValue(), AnyMap.class));
            ApiResponse<?> response = imageCaptchaValidator.valid(imageCaptchaTrack, map);
            if (response.isSuccess()) {

                String value = SystemException.convertSupplier(() -> CastUtils.getObjectMapper().writeValueAsString(imageCaptchaTrack));
                captcha.setValue(value);
                getCaptchaStorageManager().saveCaptcha(captcha, interceptToken);

                long useTime = imageCaptchaTrack.getStopTime() - imageCaptchaTrack.getStartTime();
                return RestResult.ofSuccess("校验成功, 本次使用 " + useTime / 1000 + " 秒", (Object)DigestUtils.md5DigestAsHex(captcha.getValue().getBytes()));
            }
            return RestResult.of(response.getMsg(), HttpStatus.OK.value(), ErrorCodeException.DEFAULT_EXCEPTION_CODE);
        } catch (Exception e) {
            LOGGER.error("校验 tianai 数据错误", e);
            RestResult<Object> result = RestResult.ofException(e);
            result.setStatus(HttpStatus.OK.value());
            return result;
        }
    }

    @Override
    protected GenerateCaptchaResult doGenerateCaptcha(InterceptToken buildToken,
                                                      TianaiRequestBody requestBody,
                                                      HttpServletRequest request) {
        String type = requestBody.getGenerateImageType();
        if (RandomValuePropertySource.RANDOM_PROPERTY_SOURCE_NAME.equals(requestBody.getGenerateImageType())) {
            type = tianaiCaptchaProperties.getRandomCaptchaType().get(RandomUtils.secure().randomInt(0, tianaiCaptchaProperties.getRandomCaptchaType().size()));
        }

        // 生成滑块验证码图片, 可选项
        // SLIDER (滑块验证码)
        // ROTATE (旋转验证码)
        // CONCAT (滑动还原验证码)
        // WORD_IMAGE_CLICK (文字点选验证码)
        //
        // 更多验证码支持 详见 cloud.tianai.captcha.common.constant.CaptchaTypeConstant
        ImageCaptchaInfo imageCaptchaInfo = imageCaptchaGenerator.generateCaptchaImage(type);
        Float tolerant = tianaiCaptchaProperties.getTolerantMap()
                .getOrDefault(imageCaptchaInfo.getTemplateImageTag(), 0.0F);
        imageCaptchaInfo.setTolerant(tolerant);
        // 这个map数据应该存到缓存中，校验的时候需要用到该数据
        Map<String, Object> map = imageCaptchaValidator.generateImageCaptchaValidData(imageCaptchaInfo);
        // 兼容 js 动态函数名称使用
        imageCaptchaInfo.setType(imageCaptchaInfo.getType().toLowerCase());
        Map<String, Object> body = CastUtils.convertValue(imageCaptchaInfo, CastUtils.MAP_TYPE_REFERENCE);

        return GenerateCaptchaResult.of(body, SystemException.convertSupplier(() -> CastUtils.getObjectMapper().writeValueAsString(map)));
    }

    @Override
    protected TimeProperties getCaptchaExpireTime() {
        return tianaiCaptchaProperties.getCaptchaExpireTime();
    }

    @Override
    public String getType() {
        return DEFAULT_TYPE;
    }

    @Override
    public String getCaptchaParamName() {
        return tianaiCaptchaProperties.getCaptchaParamName();
    }

    @Override
    public void init(ImageCaptchaResourceManager resourceManager) {

    }

    @Override
    public List<Resource> randomGetResourceByTypeAndTag(String type, String tag, Integer quantity) {
        List<Resource> resources = listResourcesByTypeAndTag(type, tag);
        if (CollectionUtils.isEmpty(resources)) {
            throw new IllegalStateException("随机获取资源错误，store中资源为空, type:" + type + ",tag:" + tag);
        }
        int size = resources.size();
        if (quantity > size) {
            throw new IllegalArgumentException("请求的资源数量超过可用资源总数");
        }

        Set<Integer> indexes = new HashSet<>(quantity);
        while (indexes.size() < quantity) {
            indexes.add(ThreadLocalRandom.current().nextInt(size));
        }

        List<Resource> result = new ArrayList<>(quantity);
        for (int index : indexes) {
            result.add(resources.get(index));
        }
        return result;
    }

    @Override
    public List<ResourceMap> randomGetTemplateByTypeAndTag(String type, String tag, Integer quantity) {
        List<ResourceMap> templates = listTemplatesByTypeAndTag(type, tag);
        if (CollectionUtils.isEmpty(templates)) {
            throw new IllegalStateException("随机获取模板错误，store中模板为空, type:" + type + ",tag:" + tag);
        }
        int size = templates.size();
        if (quantity > size) {
            throw new IllegalArgumentException("请求的模板数量超过可用模板总数");
        }

        Set<Integer> indexes = new HashSet<>(quantity);
        while (indexes.size() < quantity) {
            indexes.add(ThreadLocalRandom.current().nextInt(size));
        }

        List<ResourceMap> result = new ArrayList<>(quantity);
        for (int index : indexes) {
            result.add(templates.get(index));
        }
        return result;
    }

    @Override
    public void addResource(String type, Resource resource) {
        if (ObjectUtils.isEmpty(resource.getTag())) {
            resource.setTag(CommonConstant.DEFAULT_TAG);
        }
        ensureTypeTagMapExists(resourceTagMap, type, resource.getTag());
        resourceTagMap.get(type).get(resource.getTag()).add(resource);
    }

    @Override
    public void addTemplate(String type, ResourceMap template) {
        if (ObjectUtils.isEmpty(template.getTag())) {
            template.setTag(CommonConstant.DEFAULT_TAG);
        }
        ensureTypeTagMapExistsForTemplate(templateResourceTagMap, type, template.getTag());
        templateResourceTagMap.get(type).get(template.getTag()).add(template);
    }

    @Override
    public Resource deleteResource(String type, String id) {
        Map<String, List<Resource>> tagMap = resourceTagMap.get(type);
        if (tagMap == null) {
            return null;
        }

        for (List<Resource> resources : tagMap.values()) {
            Iterator<Resource> iterator = resources.iterator();
            while (iterator.hasNext()) {
                Resource res = iterator.next();
                if (res.getId().equals(id)) {
                    iterator.remove();
                    return res;
                }
            }
        }
        return null;
    }

    @Override
    public ResourceMap deleteTemplate(String type, String id) {
        Map<String, List<ResourceMap>> tagMap = templateResourceTagMap.get(type);
        if (tagMap == null) {
            return null;
        }

        for (List<ResourceMap> templates : tagMap.values()) {
            Iterator<ResourceMap> iterator = templates.iterator();
            while (iterator.hasNext()) {
                ResourceMap temp = iterator.next();
                if (temp.getId().equals(id)) {
                    iterator.remove();
                    return temp;
                }
            }
        }
        return null;
    }

    @Override
    public List<Resource> listResourcesByTypeAndTag(String type, String tag) {
        if (!ObjectUtils.isEmpty(tag)) {
            Map<String, List<Resource>> tagMap = resourceTagMap.get(type);
            return tagMap == null ? Collections.emptyList() : tagMap.getOrDefault(tag, Collections.emptyList());
        }
        List<Resource> result = new ArrayList<>();
        Map<String, List<Resource>> tagMap = resourceTagMap.get(type);
        if (tagMap != null) {
            for (List<Resource> list : tagMap.values()) {
                result.addAll(list);
            }
        }
        return result;
    }

    @Override
    public List<ResourceMap> listTemplatesByTypeAndTag(String type, String tag) {
        if (!ObjectUtils.isEmpty(tag)) {
            Map<String, List<ResourceMap>> tagMap = templateResourceTagMap.get(type);
            return tagMap == null ? Collections.emptyList() : tagMap.getOrDefault(tag, Collections.emptyList());
        }
        List<ResourceMap> result = new ArrayList<>();
        Map<String, List<ResourceMap>> tagMap = templateResourceTagMap.get(type);
        if (tagMap != null) {
            for (List<ResourceMap> list : tagMap.values()) {
                result.addAll(list);
            }
        }
        return result;
    }

    @Override
    public void clearAllResources() {
        resourceTagMap.clear();
    }


    @Override
    public void clearAllTemplates() {
        templateResourceTagMap.clear();
    }

    private void ensureTypeTagMapExists(Map<String, Map<String, List<Resource>>> map, String type, String tag) {
        map.computeIfAbsent(type, k -> new HashMap<>())
                .computeIfAbsent(tag, k -> new ArrayList<>(20));
    }

    private void ensureTypeTagMapExistsForTemplate(Map<String, Map<String, List<ResourceMap>>> map, String type, String tag) {
        map.computeIfAbsent(type, k -> new HashMap<>())
                .computeIfAbsent(tag, k -> new ArrayList<>(2));
    }
}
