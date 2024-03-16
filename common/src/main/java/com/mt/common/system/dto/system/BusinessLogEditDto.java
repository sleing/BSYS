package com.mt.common.system.dto.system;

import com.mt.common.system.entity.BusinessLog;
import com.mt.common.system.entity.User;

import java.util.List;

public class BusinessLogEditDto {

    private BusinessLog businessLog;


    //外键实体是：User
    private List<User> userUsers;


    public BusinessLog getBusinessLog() {
        return this.businessLog;
    }

    public void setBusinessLog(BusinessLog businessLog) {
        this.businessLog = businessLog;
    }


    public List<User> getUserUsers() {
        return this.userUsers;
    }

    public void setUserUsers(List<User> userUsers) {
        this.userUsers = userUsers;
    }
}
