package com.vic.myspring.service.impl;


import com.vic.myspring.dao.mapper.bo.User;
import com.vic.myspring.dao.mapper.bo.UserExample;
import com.vic.myspring.dao.mapper.factory.MapperFactory;
import com.vic.myspring.service.interfaces.IUserBusiSV;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wang on 2018/6/22.
 */
@Component
public class UserBusiSVImpl implements IUserBusiSV {

    @Override
    public User selectUserInfoById() {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(1);
        List<User> userList = MapperFactory.getSysUserMapper().selectByExample(example);
        if(userList!=null&&userList.size()>0){
            return userList.get(0);
        }
        return null;
    }

    /**  @Transactional
    BusinessException被抛出是不会回滚的，因为它不是继承RunTimeException。
     我在@Transactional做了一点小手脚，使其变成@Transactional(rollbackFor=Exception.class)
     这个的意思是指定回滚的异常，我直接就指定了Exception。也就是只要是Exception或者是Exception的子类，
     那么在此方法中，都会进行回滚。当然，还有notRollbackFor，这个是指定异常不回滚。
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public boolean insertUserInfo(User user) throws Exception {
        MapperFactory.getSysUserMapper().insertSelective(user);
        throw new Exception("手动报错");
//        return true;
    }

}
