package com.neefull.fsp.web.common.authentication;

import com.neefull.fsp.web.common.annotation.Helper;
import org.apache.shiro.authz.AuthorizationInfo;

/**
 * @author pei.wang
 */
@Helper
public class ShiroHelper extends ShiroRealm {

    /**
     * 获取当前用户的角色和权限集合
     *
     * @return AuthorizationInfo
     */
    public AuthorizationInfo getCurrentuserAuthorizationInfo() {
        return super.doGetAuthorizationInfo(null);
    }
}
