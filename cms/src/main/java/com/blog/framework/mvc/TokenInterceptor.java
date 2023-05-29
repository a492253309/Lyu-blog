package com.blog.framework.mvc;

import com.blog.entity.User;
import com.blog.framework.exception.MyException;
import com.blog.framework.jwt.JWTUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token拦截器
 */
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        String uri = request.getRequestURI();
        String token=request.getHeader(JWTUtils.token);
        User user = JWTUtils.getUser(token);
        if (user == null){
            throw new MyException("超时或者不合法");
        }
        //token续期
        String newToken = JWTUtils.sign(user);
        response.setHeader(JWTUtils.token,newToken);
        request.setAttribute("user",user);
        UserInfoShareHolder.setUserInfo(user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
