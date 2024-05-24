package com.ruijie.rcos.rcdc.rco.module.def.api.dto.user;

import java.util.Date;
import java.util.UUID;

/**
 * Description: 登录记录实体
 * Copyright: Copyright (c) 2018
 * Company: Ruijie Co., Ltd.
 * Create Time: 2021/11/2 17:29
 *
 * @author zjy
 */
public class UserLoginRecordDTO {
    private UUID id;

    private String userId;

    private String userName;

    private String userGroupId;

    private String userGroupName;

    private String terminalId;

    private String terminalIp;

    private String terminalMac;

    private String terminalName;

    private String deskType;

    private String deskStrategyPattern;

    private String deskId;

    private String deskName;

    private String computerName;

    private String deskIp;

    private String deskMac;

    private String deskSystem;

    private String deskImage;

    private String deskStrategy;

    private String sessionState;

    private Date createTime;

    private Date loginTime;

    private Long authDuration;

    private String userType;

    private Date connectTime;

    private Long connectDuration;

    private Date logoutTime;

    private Long useDuration;

    private Long connectionId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }

    public String getTerminalIp() {
        return terminalIp;
    }

    public void setTerminalIp(String terminalIp) {
        this.terminalIp = terminalIp;
    }

    public String getTerminalMac() {
        return terminalMac;
    }

    public void setTerminalMac(String terminalMac) {
        this.terminalMac = terminalMac;
    }

    public String getDeskId() {
        return deskId;
    }

    public void setDeskId(String deskId) {
        this.deskId = deskId;
    }

    public String getDeskName() {
        return deskName;
    }

    public void setDeskName(String deskName) {
        this.deskName = deskName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Long getAuthDuration() {
        return authDuration;
    }

    public void setAuthDuration(Long authDuration) {
        this.authDuration = authDuration;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Date getConnectTime() {
        return connectTime;
    }

    public void setConnectTime(Date connectTime) {
        this.connectTime = connectTime;
    }

    public Long getConnectDuration() {
        return connectDuration;
    }

    public void setConnectDuration(Long connectDuration) {
        this.connectDuration = connectDuration;
    }

    public Date getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }

    public Long getUseDuration() {
        return useDuration;
    }

    public void setUseDuration(Long useDuration) {
        this.useDuration = useDuration;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    public String getDeskType() {
        return deskType;
    }

    public void setDeskType(String deskType) {
        this.deskType = deskType;
    }

    public String getDeskStrategyPattern() {
        return deskStrategyPattern;
    }

    public void setDeskStrategyPattern(String deskStrategyPattern) {
        this.deskStrategyPattern = deskStrategyPattern;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getDeskIp() {
        return deskIp;
    }

    public void setDeskIp(String deskIp) {
        this.deskIp = deskIp;
    }

    public String getDeskMac() {
        return deskMac;
    }

    public void setDeskMac(String deskMac) {
        this.deskMac = deskMac;
    }

    public String getDeskSystem() {
        return deskSystem;
    }

    public void setDeskSystem(String deskSystem) {
        this.deskSystem = deskSystem;
    }

    public String getDeskImage() {
        return deskImage;
    }

    public void setDeskImage(String deskImage) {
        this.deskImage = deskImage;
    }

    public String getDeskStrategy() {
        return deskStrategy;
    }

    public void setDeskStrategy(String deskStrategy) {
        this.deskStrategy = deskStrategy;
    }

    public String getSessionState() {
        return sessionState;
    }

    public void setSessionState(String sessionState) {
        this.sessionState = sessionState;
    }

    public Long getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(Long connectionId) {
        this.connectionId = connectionId;
    }
}
