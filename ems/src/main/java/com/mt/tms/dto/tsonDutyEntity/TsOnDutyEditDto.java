package com.mt.tms.dto.tsonDutyEntity;


import com.mt.tms.entity.tsonDutyEntity.TsOnDuty;

import java.util.List;
                    import com.mt.tms.entity.tsworklaceEntity.TsWorkPlace;
                import com.mt.tms.entity.tsstudentEntity.TsStudentInfo;
                    public class TsOnDutyEditDto{

    private TsOnDuty tsOnDuty;


                                                                //外键实体是：TsWorkPlace
    private List<TsWorkPlace> onDutyAddressTsWorkPlaces;
                                                //外键实体是：TsStudentInfo
    private List<TsStudentInfo> onDutyStuTsStudentInfos;
                                                            

    public  TsOnDuty getTsOnDuty()
    {
        return this.tsOnDuty;
    }
    public  void setTsOnDuty(TsOnDuty tsOnDuty)
    {
        this.tsOnDuty = tsOnDuty;
    }


                                                                public List<TsWorkPlace> getOnDutyAddressTsWorkPlaces()
    {
        return this.onDutyAddressTsWorkPlaces;
    }
    public void setOnDutyAddressTsWorkPlaces(List<TsWorkPlace> onDutyAddressTsWorkPlaces)
    {
        this.onDutyAddressTsWorkPlaces = onDutyAddressTsWorkPlaces;
    }
                                                public List<TsStudentInfo> getOnDutyStuTsStudentInfos()
    {
        return this.onDutyStuTsStudentInfos;
    }
    public void setOnDutyStuTsStudentInfos(List<TsStudentInfo> onDutyStuTsStudentInfos)
    {
        this.onDutyStuTsStudentInfos = onDutyStuTsStudentInfos;
    }
                                                            }
