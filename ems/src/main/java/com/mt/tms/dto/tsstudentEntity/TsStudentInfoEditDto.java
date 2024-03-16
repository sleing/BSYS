package com.mt.tms.dto.tsstudentEntity;


import com.mt.tms.entity.tsstudentEntity.TsStudentInfo;

import java.util.List;
                    import com.mt.tms.entity.tsCollegeEntity.TsCollege;
                                                public class TsStudentInfoEditDto{

    private TsStudentInfo tsStudentInfo;


                                                                //外键实体是：TsCollege
    private List<TsCollege> collegeTsColleges;
                                                                                                                                                

    public  TsStudentInfo getTsStudentInfo()
    {
        return this.tsStudentInfo;
    }
    public  void setTsStudentInfo(TsStudentInfo tsStudentInfo)
    {
        this.tsStudentInfo = tsStudentInfo;
    }


                                                                public List<TsCollege> getCollegeTsColleges()
    {
        return this.collegeTsColleges;
    }
    public void setCollegeTsColleges(List<TsCollege> collegeTsColleges)
    {
        this.collegeTsColleges = collegeTsColleges;
    }
                                                                                                                                                }
