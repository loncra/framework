package io.github.loncra.framework.fasc.bean.common;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fadada
 * 2021/10/16 16:34:09
 */
public class Actor extends BaseBean {
    private String actorId;
    private String actorType;
    private String actorName;
    private List<String> permissions;
    private String actorOpenId;
    private String actorFDDId;
    private String actorEntityId;
    private List<ActorCorpMember> actorCorpMembers;
    private String identNameForMatch;
    private String certType;
    private String certNoForMatch;
    private String accountName;
    private Notification notification;
    private Boolean sendNotification;
    private List<String> notifyType;
    private String notifyAddress;
    private String clientUserId;
    private List<String> authScopes = new ArrayList();

    public String getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(String clientUserId) {
        this.clientUserId = clientUserId;
    }

    public List<String> getAuthScopes() {
        return authScopes;
    }

    public void setAuthScopes(List<String> authScopes) {
        this.authScopes = authScopes;
    }

    public String getActorEntityId() {
        return actorEntityId;
    }

    public void setActorEntityId(String actorEntityId) {
        this.actorEntityId = actorEntityId;
    }

    public String getActorType() {
        return actorType;
    }

    public void setActorType(String actorType) {
        this.actorType = actorType;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public String getActorOpenId() {
        return actorOpenId;
    }

    public void setActorOpenId(String actorOpenId) {
        this.actorOpenId = actorOpenId;
    }

    public String getActorFDDId() {
        return actorFDDId;
    }

    public void setActorFDDId(String actorFDDId) {
        this.actorFDDId = actorFDDId;
    }

    public List<ActorCorpMember> getActorCorpMembers() {
        return actorCorpMembers;
    }

    public void setActorCorpMembers(List<ActorCorpMember> actorCorpMembers) {
        this.actorCorpMembers = actorCorpMembers;
    }

    public String getIdentNameForMatch() {
        return identNameForMatch;
    }

    public void setIdentNameForMatch(String identNameForMatch) {
        this.identNameForMatch = identNameForMatch;
    }

    public String getCertNoForMatch() {
        return certNoForMatch;
    }

    public void setCertNoForMatch(String certNoForMatch) {
        this.certNoForMatch = certNoForMatch;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Boolean getSendNotification() {
        return sendNotification;
    }

    public void setSendNotification(Boolean sendNotification) {
        this.sendNotification = sendNotification;
    }

    public List<String> getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(List<String> notifyType) {
        this.notifyType = notifyType;
    }

    public String getNotifyAddress() {
        return notifyAddress;
    }

    public void setNotifyAddress(String notifyAddress) {
        this.notifyAddress = notifyAddress;
    }
}
