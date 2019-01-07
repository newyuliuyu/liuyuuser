package com.liuyu.user.web.dao;

import com.liuyu.user.web.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: UserDao <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-26 下午2:18 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Repository
public interface UserDao {
    int add(@Param("user") User user);

    int addUsers(@Param("users") List<User> users);

    int updateUser(@Param("user") User user);

    int updatePassword(@Param("user") User user);

    int delUser(@Param("user") User user);

    User get(@Param("userId") long userId);

    User queryWithUserName(@Param("userName") String userName);

    User queryWithPhone(@Param("phone") String userName);

    User queryWithEmail(@Param("email") String userName);

    List<User> queryUsers(@Param("searchText") String searchText);

    List<User> queryUsersWithUserNames(@Param("usernames") String[] usernames);
}
