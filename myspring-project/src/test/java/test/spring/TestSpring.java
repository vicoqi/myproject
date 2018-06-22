package test.spring;

import com.vic.myspring.dao.mapper.bo.User;
import com.vic.myspring.service.interfaces.IUserBusiSV;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wang on 2018/6/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:context/core-context.xml"})
public class TestSpring {
    @Autowired //自动注入
    private IUserBusiSV userBusiSV;

    @Test
//    @Transactional   //标明此方法需使用事务
    @Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
    public void test(){
        System.out.println("测试Spring整合Junit4进行单元测试");
        User user = userBusiSV.selectUserInfoById();
        System.out.println(user);
    }
    @Test
//    @Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚,可以避免产生脏数据到数据库
    public void testInsert(){
        User user = new User();
        user.setUserId(6);
        user.setUserName("测试spring");
        user.setAddress("america");
        try {
            System.out.println(userBusiSV.insertUserInfo(user));
        }catch (Exception e){
            System.out.println("一捕获到报错");
        }
    }
}
