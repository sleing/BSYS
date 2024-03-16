package com.mt.tms.dto.tsactivityEntity;


import com.mt.tms.entity.tsactivityEntity.TsActivity;

import java.util.List;
                                import com.mt.tms.entity.tsstudentEntity.TsStudentInfo;
                                            public class TsActivityEditDto{

    private TsActivity tsActivity;


                                                                                                    //外键实体是：TsStudentInfo
    private List<TsStudentInfo> activityHostTsStudentInfos;
                                                                                                                                    

    public  TsActivity getTsActivity()
    {
        return this.tsActivity;
    }
    public  void setTsActivity(TsActivity tsActivity)
    {
        this.tsActivity = tsActivity;
    }


                                                                                                    public List<TsStudentInfo> getActivityHostTsStudentInfos()
    {
        return this.activityHostTsStudentInfos;
    }
    public void setActivityHostTsStudentInfos(List<TsStudentInfo> activityHostTsStudentInfos)
    {
        this.activityHostTsStudentInfos = activityHostTsStudentInfos;
    }
                                                                                                                                    }
