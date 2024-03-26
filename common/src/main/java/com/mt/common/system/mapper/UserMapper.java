package com.mt.common.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mt.common.core.web.PageParam;
import com.mt.common.system.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户Mapper接口
 * Created by wangfan on 2018-12-24 16:10
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 分页查询
     */
    List<User> listPage(@Param("page") PageParam<User> page);

    /**
     * 查询全部
     */
    List<User> listAll(@Param("page") Map<String, Object> page);

    /**
     * 查询所有User集合
     *
     */
    public List<User> findAllUsersWithIdName();

    public void doSubmit(@Param("email") String email,@Param("password") String password);

    public Long userRegister(@Param("eid") Long eid, @Param("deleted") Integer deleted, @Param("email") String email, @Param("email_verified") Integer email_verified, @Param("nickname") String nickname, @Param("password") String password, @Param("state") Integer state, @Param("username") String username);

    public Long updatePassword(String email, String password);

    public Long getUserEidById(@Param("username") String username);

    public Long getUserEid(@Param("username") String username);

    public Long getUserEidByEmail(@Param("email") String email);

    public List<User> findAllUsersWithIdNameEmail();

    /**
     * 批量添加用户
     * @param users
     */
    void mutiImportUser(List<User> users);
}
