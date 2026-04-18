package io.github.loncra.framework.fasc.req.seal;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

import java.util.List;

/**
 * @author gongj
 * @date 2022/7/13
 */
public class GetSealListReq extends BaseReq {

    private String ownerId;
    private String openCorpId;
    private String grantFreeSign;
    private SealListFilter listFilter;

    public static class SealListFilter {
        List<String> categoryType;

        List<String> sealTags;

        public List<String> getCategoryType() {
            return categoryType;
        }

        public void setCategoryType(List<String> categoryType) {
            this.categoryType = categoryType;
        }

        public List<String> getSealTags() {
            return sealTags;
        }

        public void setSealTags(List<String> sealTags) {
            this.sealTags = sealTags;
        }
    }

    @Deprecated
    public String getOwnerId() {
        return ownerId;
    }

    @Deprecated
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public String getGrantFreeSign() {
        return grantFreeSign;
    }

    public void setGrantFreeSign(String grantFreeSign) {
        this.grantFreeSign = grantFreeSign;
    }

    public SealListFilter getListFilter() {
        return listFilter;
    }

    public void setListFilter(SealListFilter listFilter) {
        this.listFilter = listFilter;
    }
}
