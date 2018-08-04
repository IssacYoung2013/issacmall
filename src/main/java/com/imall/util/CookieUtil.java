package com.imall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Issac
 *  *   @date    2018-08-04
 * @desc
 */
@Slf4j
public class CookieUtil {
    private final static String COOKIE_DOMAIN = "issacmall.com";
    private final static String COOKIE_NAME = "imall_login_token";

    public static String readLoginToken(HttpServletRequest request) {
        Cookie[] cks = request.getCookies();
        if(cks != null) {
            for (Cookie ck :
                    cks) {
                log.info("read cookieName:{},cookeValue:{}", ck.getName(), ck.getValue());
                if(StringUtils.equals(ck.getName(),COOKIE_NAME)) {
                    log.info("return cookieName{},cookieValue:{}",ck.getName(),ck.getValue());
                }
            }
        }

        return null;
    }

    // a: A.issacmall.com cookie.domain=A.issacmall.com path="/"
    // b: B.issacmall.com cookie.domain=B.issacmall.com path="/"
    // c: A.issacmall.com/test/cc cookie.domain=A.issacmall.com path="/test/cc"
    // d: A.issacmall.com/test/dd cookie.domain=A.issacmall.com path="/test/dd"
    // e: A.issacmall.com/test cookie.domain=A.issacmall.com path="/test"
    public static void writeLoginToken(HttpServletResponse response,String token) {
        Cookie ck = new Cookie(COOKIE_NAME,token);
        ck.setDomain(COOKIE_DOMAIN);
        ck.setHttpOnly(true); // 不允许脚本访问
        ck.setPath("/"); // 代表设置在根目录
        //单位是秒
        //如果 maxage不设置的话，cokkie就不会写入硬盘，而是写在内存。只在当前页面有效
        ck.setMaxAge(60 * 60 *24 *364); // 如果是-1 代表永久
        log.info("write cookieName:{},cookeValue:{}",ck.getName(),ck.getValue());
        response.addCookie(ck);
    }

    public static void delLoginToken(HttpServletRequest request,HttpServletResponse response) {
        Cookie[] cks = request.getCookies();
        if(cks != null) {
            for (Cookie ck :
                    cks) {
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)) {
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    ck.setMaxAge(0); // 设置0 表示 代表删除此cookie.
                    log.info("del cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());

                    response.addCookie(ck);
                    return;
                }
            }
        }
    }

}
