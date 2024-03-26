package com.mt.common.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mt.common.core.config.Config;
import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.security.LoginUser;
import com.mt.common.core.web.PageParam;
import com.mt.common.system.entity.Menu;
import com.mt.common.system.entity.Role;
import com.mt.common.system.entity.User;
import com.mt.common.system.entity.UserRole;
import com.mt.common.system.mapper.MenuMapper;
import com.mt.common.system.mapper.UserMapper;
import com.mt.common.system.mapper.UserRoleMapper;
import com.mt.common.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 * Created by wangfan on 2018-12-24 16:10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private UserMapper userMapper;

    @Resource
    private RedisTemplate<String, LoginUser> redisTemplate;

    @Override
    public User getByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }

    @Override
    public User getFullById(Long userId) {
        List<User> userList = baseMapper.listAll(new PageParam<User>().put("eid", userId).getNoPageParam());
        if (userList == null || userList.size() == 0) return null;
        return selectRoleAndAuth(userList.get(0));
    }
    @Override
    public User getFullByUsername(String username) {
        List<User> userList = baseMapper.listAll(new PageParam<User>().put("username", username).getNoPageParam());
        if (userList == null || userList.size() == 0) return null;
        return selectRoleAndAuth(userList.get(0));
    }

    @Override
    public User selectRoleAndAuth(User user) {
        if (user == null) return user;
        user.setRoles(userRoleMapper.listByUserId(user.getEid()));
        return selectUserAuth(user);
    }

    @Override
    public User selectUserAuth(User user) {
        if (user == null) return user;
        List<Menu> menus = menuMapper.listAuthoritiesByUserId(user.getEid());

//        List<Menu> authList = new ArrayList<>();
//        for (Menu menu : menus) {
//            if (StrUtil.isNotBlank(menu.getAuthority())) {
//                authList.add(menu);
//            }
//        }
        user.setAuthorities(menus);
        return user;
    }

    @Override
    public List<User> listPage(PageParam<User> page) {
        List<User> users = baseMapper.listPage(page);
        // 查询用户的角色
        selectUserRoles(users);
        return users;
    }

    @Override
    public List<User> listAll(Map<String, Object> page) {
        List<User> users = baseMapper.listAll(page);
        // 查询用户的角色
        selectUserRoles(users);
        return users;
    }

    @Transactional
    @Override
    public boolean saveUser(User user) {
        if (user.getUsername() != null && baseMapper.selectCount(new QueryWrapper<User>()
                .eq("username", user.getUsername())) > 0) {
            throw new BusinessException("账号已存在");
        }
        if (user.getPhone() != null && baseMapper.selectCount(new QueryWrapper<User>()
                .eq("phone", user.getPhone())) > 0) {
            throw new BusinessException("手机号已存在");
        }
        if (user.getEmail() != null && baseMapper.selectCount(new QueryWrapper<User>()
                .eq("email", user.getEmail())) > 0) {
            throw new BusinessException("邮箱已存在");
        }
        boolean result = baseMapper.insert(user) > 0;
        List<Long> ids = new ArrayList();

        if(user.getUserType().equals("teacher")){
            ids.add(Long.valueOf(11));  //教师角色的eid
        }

        if(user.getUserType().equals("student")){
            ids.add(Long.valueOf(10));
        }

        if (result) {
            addUserRoles(user.getEid(), ids, false);
        }
        return result;
    }

    @Transactional
    @Override
    public boolean updateUser(User user) {
        if (user.getUsername() != null && baseMapper.selectCount(new QueryWrapper<User>()
                .eq("username", user.getUsername()).ne("eid", user.getEid())) > 0) {
            throw new BusinessException("账号已存在");
        }
        if (user.getPhone() != null && baseMapper.selectCount(new QueryWrapper<User>()
                .eq("phone", user.getPhone()).ne("eid", user.getEid())) > 0) {
            throw new BusinessException("手机号已存在");
        }
        if (user.getEmail() != null && baseMapper.selectCount(new QueryWrapper<User>()
                .eq("email", user.getEmail()).ne("eid", user.getEid())) > 0) {
            throw new BusinessException("邮箱已存在");
        }
        boolean result = baseMapper.updateById(user) > 0;
        if (result) {
            addUserRoles(user.getEid(), user.getRoleIds(), true);
        }
        return result;
    }

    @Override
    public boolean comparePsw(String dbPsw, String inputPsw) {
        return dbPsw != null && new BCryptPasswordEncoder().matches(inputPsw, dbPsw);
    }

    @Override
    public String encodePsw(String psw) {
        if (psw == null) return null;
        return new BCryptPasswordEncoder().encode(psw);
    }

    /**
     * 查询所有User集合
     * @return
     */
    @Override
    public List<User> findAllUsersWithIdName() {
        return userMapper.findAllUsersWithIdName();
    }

    @Override
    public LoginUser loadFromCache(String username) {
        return this.loadFromCache(username,false);
    }
    @Override
    public LoginUser loadFromCache(String username,boolean force) {
        LoginUser loginUser  = null;
        if(!force) {
            loginUser = (LoginUser) redisTemplate.opsForValue().get("user:" + username);
            if (loginUser != null){
                loginUser.setCached(true);
                return loginUser;
            }
        }

        User user = this.getFullByUsername(username);

        if (user == null) throw new UsernameNotFoundException("没有找到名为 "+username+" 的用户");
        //user = this.selectUserAuth(user);

        loginUser = LoginUser.create(user);
        loginUser.setCached(false);

        return  loginUser;

    }
    @Override
    public void cache(LoginUser user)
    {
        //TODO: 写入redis
        redisTemplate.opsForValue().set("user:"+user.getUsername(), user, Config.tokenExpireTime, TimeUnit.MILLISECONDS);
    }
    @Override
    public void resetCache(String username) {
        redisTemplate.boundValueOps("user:"+username).expire(Config.tokenExpireTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public void decache() {
        Set<String> keys = redisTemplate.keys("user:*");
        redisTemplate.delete(keys);
    }

    @Override
    public void decache(String username) {
        redisTemplate.delete("user:"+username);
    }

    /**
     * 批量查询用户的角色
     */
    private void selectUserRoles(List<User> users) {
        if (users != null && users.size() > 0) {
            List<Long> userIds = users.stream().map(User::getEid).collect(Collectors.toList());
            List<Role> userRoles = userRoleMapper.listByUserIds(userIds);
            for (User user : users) {
                List<Role> roles = userRoles.stream().filter(d -> user.getEid().equals(d.getEid())).collect(Collectors.toList());
                user.setRoles(roles);
            }
        }
    }

    /**
     * 添加用户角色
     */
    private void addUserRoles(Long userId, List<Long> roleIds, boolean deleteOld) {
        if (deleteOld) {
            userRoleMapper.delete(new UpdateWrapper<UserRole>().eq("user_id", userId));
        }
        if (userRoleMapper.insertBatch(userId, roleIds) < roleIds.size()) {
            throw new BusinessException("操作失败");
        }
    }

    @Override
    public void doSubmit(String email,String password) {
        userMapper.doSubmit(email,password);
    }

    //zheng
    /**
     * 新增用户
     *
     * @param eid,deleted,email,email_verified,nickname,password,state,username  实体对象
     * @return
     */
    @Override
    public void userRegister(Long eid,Integer deleted, String email, Integer email_verified,String nickname,String password,Integer state,String username) {
        Long rows = this.userMapper.userRegister(eid,deleted,email,email_verified,nickname,password,state,username);
        if (rows != 1) {
            String error = "新增保存学生出错，数据库应该返回1,但返回了 " + rows;
            throw new BusinessException(error);
        }
    }

    /**
     * 修改密码
     */
    @Override
    public void updatePassword(String email, String password) {
        //TODO:请在此校验参数的合法性
//        this.validateUpdateStudent(student);
        password = new BCryptPasswordEncoder().encode(password);
        Long rows = this.userMapper.updatePassword(email, password);
        if (rows != 1) {
            String error = "修改保存密码出错，数据库应该返回1,但返回了 " + rows + ",数据可能被删除";
            throw new BusinessException(error);
        }
    }

    @Override
    public Long getUserEidById(String username) {
        List<User> allUsers = userMapper.findAllUsersWithIdName();
        List<String> allUsername = new ArrayList<>();
        for (User allUser : allUsers) {
            String i = allUser.getUsername();
            allUsername.add(i);
        }
        int i = 0;
        for (String aLong : allUsername) {
            if (aLong.equals(username)){
                i+=1;
            }
        }
        if(i==0)
            return userMapper.getUserEidById(username);
        else
            return -1L;  //要转换为Long型
    }

    @Override
    public Long getUserEid(String username) {
        return userMapper.getUserEid(username);
    }

    @Override
    public List<User> findAllUsersWithIdNameEmail() {
        return userMapper.findAllUsersWithIdNameEmail();
    }

    @Override
    public void mutiImportUser(List<User> users) {
        userMapper.mutiImportUser(users);
    }

    @Override
    public Long getUserEidByEmail(String email) {
        List<User> allUsers = userMapper.findAllUsersWithIdNameEmail();

        List<String> allUserEmail = new ArrayList<>();
        for (User allUser : allUsers) {
            String userEmail = allUser.getEmail();
            Long eid = allUser.getEid();
            if(eid!=1L && eid!=16L && eid!=17L && eid!=18L && eid!=19L && eid!=20L && eid!=22L && eid!=23L && eid!=31L) {
                allUserEmail.add(userEmail);
            }
        }
        int i = 0;
        for (String aLong : allUserEmail) {
            if (aLong.equals(email)){
                i+=1;
            }
        }
        //不等于0是user表里有这个
        if(i != 0)
            return userMapper.getUserEidByEmail(email);
        else
            return -1L;  //要转换为Long型
    }


}
