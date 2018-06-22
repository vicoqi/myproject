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
    BusinessException���׳��ǲ���ع��ģ���Ϊ�����Ǽ̳�RunTimeException��
     ����@Transactional����һ��С�ֽţ�ʹ����@Transactional(rollbackFor=Exception.class)
     �������˼��ָ���ع����쳣����ֱ�Ӿ�ָ����Exception��Ҳ����ֻҪ��Exception������Exception�����࣬
     ��ô�ڴ˷����У�������лع�����Ȼ������notRollbackFor�������ָ���쳣���ع���
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public boolean insertUserInfo(User user) throws Exception {
        MapperFactory.getSysUserMapper().insertSelective(user);
        throw new Exception("�ֶ�����");
//        return true;
    }

}
