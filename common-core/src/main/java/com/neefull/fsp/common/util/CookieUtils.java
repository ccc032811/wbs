/**
 * @(#)CookieUtils.java, 2015-8-21.
 * <p>
 * Copyright 2015 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neefull.fsp.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class CookieUtils {

    /**
     * @param request
     * @return
     */
    public static Cookie[] getCredentials(HttpServletRequest request, String tokenName) {
        Cookie cookie = getCookieByName(request, tokenName);
        Cookie[] cookieArray = new Cookie[1];
        if (cookie != null)
            cookieArray[0] = cookie;
        return cookieArray;
    }

    public static Cookie getCookieByName(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public static String getCookieValueByName(HttpServletRequest request, String key) {
        Cookie cookie = getCookieByName(request, key);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    /**
     * @param request
     * @param cookieName
     * @return
     */
    public static String getNonSpecCookieValue(HttpServletRequest request, String cookieName) {
        if (cookieName == null)
            return "";

        String cookies = request.getHeader("Cookie");
        if (cookies != null) {
            cookieName = cookieName + "=";
            int fromIndex = cookies.indexOf(cookieName);
            if (fromIndex >= 0) {
                int endIndex = cookies.indexOf(";", fromIndex);
                if (endIndex >= 0) {
                    return cookies.substring(fromIndex + cookieName.length(), endIndex);
                } else {
                    return cookies.substring(fromIndex + cookieName.length());
                }
            }
        }
        return "";
    }

    /**
     * 清理cookie
     *
     * @param response
     * @param name
     * @param domain
     */
    public static void cleanCookieWithDomain(HttpServletResponse response, String name,
                                             String domain) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setDomain(domain);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static void setCookieValue(HttpServletResponse response, String name, String value,
                                      int seconds) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(seconds);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static void cleanCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
