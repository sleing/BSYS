package com.mt.tms.dto.tsdepartmentEntity;


import com.mt.tms.entity.tsdepartmentEntity.TsDepartment;

import java.util.List;
                        import com.mt.tms.entity.tsteacherEntity.TsTeacherInfo;
        import com.mt.tms.entity.tsstudentEntity.TsStudentInfo;
            import com.mt.tms.entity.tsstudentEntity.TsStudentInfo;
                                        public class TsDepartmentEditDto{

    private TsDepartment tsDepartment;


                                                                            //外键实体是：TsTeacherInfo
    private List<TsTeacherInfo> supervisorTeaTsTeacherInfos;
                        //外键实体是：TsStudentInfo
    private List<TsStudentInfo> supervisorStuTsStudentInfos;
                                    //外键实体是：TsStudentInfo
    private List<TsStudentInfo> leaderTsStudentInfos;
                                                                                                                        

    public  TsDepartment getTsDepartment()
    {
        return this.tsDepartment;
    }
    public  void setTsDepartment(TsDepartment tsDepartment)
    {
        this.tsDepartment = tsDepartment;
    }


                                                                            public List<TsTeacherInfo> getSupervisorTeaTsTeacherInfos()
    {
        return this.supervisorTeaTsTeacherInfos;
    }
    public void setSupervisorTeaTsTeacherInfos(List<TsTeacherInfo> supervisorTeaTsTeacherInfos)
    {
        this.supervisorTeaTsTeacherInfos = supervisorTeaTsTeacherInfos;
    }
                        public List<TsStudentInfo> getSupervisorStuTsStudentInfos()
    {
        return this.supervisorStuTsStudentInfos;
    }
    public void setSupervisorStuTsStudentInfos(List<TsStudentInfo> supervisorStuTsStudentInfos)
    {
        this.supervisorStuTsStudentInfos = supervisorStuTsStudentInfos;
    }
                                    public List<TsStudentInfo> getLeaderTsStudentInfos()
    {
        return this.leaderTsStudentInfos;
    }
    public void setLeaderTsStudentInfos(List<TsStudentInfo> leaderTsStudentInfos)
    {
        this.leaderTsStudentInfos = leaderTsStudentInfos;
    }
                                                                                                                        }
