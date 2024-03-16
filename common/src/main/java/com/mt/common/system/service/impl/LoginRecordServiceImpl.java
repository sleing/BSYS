package com.mt.common.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mt.common.core.web.PageParam;
import com.mt.common.core.utils.UserAgentGetter;
import com.mt.common.system.entity.LoginRecord;
import com.mt.common.system.mapper.LoginRecordMapper;
import com.mt.common.system.service.LoginRecordService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 登录日志服务实现类
 * Created by wangfan on 2018-12-24 16:10
 */
@Service
public class LoginRecordServiceImpl extends ServiceImpl<LoginRecordMapper, LoginRecord> implements LoginRecordService {

    @Override
    public List<LoginRecord> listPage(PageParam<LoginRecord> page) {
        return baseMapper.listPage(page);
    }

    @Override
    public List<LoginRecord> listAll(Map<String, Object> page) {
        return baseMapper.listAll(page);
    }

    @Async
    @Override
    public void saveAsync(String username, Long type, String comments, HttpServletRequest request) {
        if (username == null) return;
        LoginRecord loginRecord = new LoginRecord();
        loginRecord.setUsername(username);
        loginRecord.setOperType(type);
        loginRecord.setComments(comments);
        UserAgentGetter agentGetter = new UserAgentGetter(request);
        loginRecord.setOs(agentGetter.getOS());
        loginRecord.setDevice(agentGetter.getDevice());
        loginRecord.setBrowser(agentGetter.getBrowser());
        loginRecord.setIp(agentGetter.getIp());
        baseMapper.insert(loginRecord);
    }

}
