package com.mt.tms.dto.tsteacherEntity;


import com.mt.tms.entity.tsteacherEntity.TsTeacherInfo;

import java.util.List;
                        import com.mt.tms.entity.tsCollegeEntity.TsCollege;
                import com.mt.tms.entity.tspositionEntity.TsPosition;
                        public class TsTeacherInfoEditDto{

    private TsTeacherInfo tsTeacherInfo;


                                                                            //外键实体是：TsCollege
    private List<TsCollege> collegeTsColleges;
                                                //外键实体是：TsPosition
    private List<TsPosition> positionTsPositions;
                                                                        

    public  TsTeacherInfo getTsTeacherInfo()
    {
        return this.tsTeacherInfo;
    }
    public  void setTsTeacherInfo(TsTeacherInfo tsTeacherInfo)
    {
        this.tsTeacherInfo = tsTeacherInfo;
    }


                                                                            public List<TsCollege> getCollegeTsColleges()
    {
        return this.collegeTsColleges;
    }
    public void setCollegeTsColleges(List<TsCollege> collegeTsColleges)
    {
        this.collegeTsColleges = collegeTsColleges;
    }
                                                public List<TsPosition> getPositionTsPositions()
    {
        return this.positionTsPositions;
    }
    public void setPositionTsPositions(List<TsPosition> positionTsPositions)
    {
        this.positionTsPositions = positionTsPositions;
    }
                                                                        }
