package com.mt.common.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mt.common.core.security.LoginUser;
import com.mt.common.core.web.PageParam;
import com.mt.common.system.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 用户服务类
 * Created by wangfan on 2018-12-24 16:10
 */
public interface UserService extends IService<User> {

    /**
     * 根据账号查询用户
     */
    User getByUsername(String username);

    /**
     * 根据id查询用户(关联查询)
     */
    User getFullById(Long eid);

    /**
     * 查询用户角色和权限
     */
    User selectRoleAndAuth(User user);

    /**
     * 查询用户权限
     */
    User selectUserAuth(User user);

    /**
     * 关联分页查询用户
     */
    List<User> listPage(PageParam<User> page);

    /**
     * 关联查询全部用户
     */
    List<User> listAll(Map<String, Object> page);

    /**
     * 添加用户(包含角色)
     */
    boolean saveUser(User user);

    /**
     * 修改用户(包含角色)
     */
    boolean updateUser(User user);

    /**
     * 比较用户密码
     *
     * @param dbPsw    数据库存储的密码
     * @param inputPsw 用户输入的密码
     * @return boolean
     */
    boolean comparePsw(String dbPsw, String inputPsw);

    /**
     * md5加密用户密码
     */
    String encodePsw(String psw);

    /**
     * 查询所有User集合(只提取ID 和 Name)
     *
     */
    public List<User> findAllUsersWithIdName();

    public LoginUser loadFromCache(String username);

    public LoginUser loadFromCache(String username,boolean force) ;

    public void cache(LoginUser user);

    public void decache(String username);

    public void decache() ;

    public void resetCache(String username) ;

    public User getFullByUsername(String username) ;

    public void doSubmit(String email,String password);

    public void userRegister(Long eid,Integer deleted, String email, Integer email_verified,String nickname,String password,Integer state,String username);

    /**
     * 修改密码
     */
    public void updatePassword(String email, String password);

    public Long getUserEidById(String username);

    public Long getUserEid(String username);

    public Long getUserEidByEmail(String email);

    public List<User> findAllUsersWithIdNameEmail();

    /**
     * 批量添加用户
     * @param users
     */
    void mutiImportUser(List<User> users);
}
