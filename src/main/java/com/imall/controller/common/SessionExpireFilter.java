package com.imall.controller.common;

import com.imall.common.Const;
import com.imall.pojo.User;
import com.imall.util.CookieUtil;
import com.imall.util.JsonUtil;
import com.imall.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 *
 *
 * @author Issac
 *  *   @date    2018-08-04
 * @desc
 */
public class SessionExpireFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;

        String loginToken = CookieUtil.readLoginToken(httpServletRequest);

        if(StringUtils.isNotEmpty(loginToken)) {
            // 判断logintoken是否为空或者""
            // 如果不为空的话，符合条件，继续拿user信息
            String userJsonStr = RedisShardedPoolUtil.get(loginToken);
            User user = JsonUtil.string2Obj(userJsonStr,User.class);
            if(user!= null) {
                // 如果user不为空，则重置session时间，即调用expire命令
                RedisShardedPoolUtil.expire(loginToken, Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
            }
        }
        filterChain.doFilter(httpServletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
