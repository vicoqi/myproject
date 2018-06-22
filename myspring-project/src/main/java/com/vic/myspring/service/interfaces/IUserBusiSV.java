package com.vic.myspring.service.interfaces;

import com.vic.myspring.dao.mapper.bo.User;

/**
 * Created by wang on 2018/6/22.
 */
public interface IUserBusiSV {
    User selectUserInfoById();
    boolean insertUserInfo(User user) throws Exception;
}

