package com.blog.framework.mvc;

import com.blog.entity.User;

/**
 * 用户信息共享的ThreadLocal类
 */
public class UserInfoShareHolder {

    private static final ThreadLocal<User> USER_INFO_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 存储用户信息
     */
    public static void setUserInfo(User userInfo) {
        USER_INFO_THREAD_LOCAL.set(userInfo);
    }

    /**
     * 获取用户相关信息
     */
    public static User getUserInfo() {
        return USER_INFO_THREAD_LOCAL.get();
    }

    /**
     * 清除ThreadLocal信息
     */
    public static void remove() {
        USER_INFO_THREAD_LOCAL.remove();
    }
}