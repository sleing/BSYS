package com.mt.tms.dto.tsmeetingEntity;


import com.mt.tms.entity.tsmeetingEntity.TsMeeting;

import java.util.List;
                    import com.mt.tms.entity.tsdepartmentEntity.TsDepartment;
            import com.mt.tms.entity.tsdepartmentEntity.TsDepartment;
                                            public class TsMeetingEditDto{

    private TsMeeting tsMeeting;


                                                                //外键实体是：TsDepartment
    private List<TsDepartment> organizationTsDepartments;
                                    //外键实体是：TsDepartment
    private List<TsDepartment> viceOrganizationTsDepartments;
                                                                                                                                    

    public  TsMeeting getTsMeeting()
    {
        return this.tsMeeting;
    }
    public  void setTsMeeting(TsMeeting tsMeeting)
    {
        this.tsMeeting = tsMeeting;
    }


                                                                public List<TsDepartment> getOrganizationTsDepartments()
    {
        return this.organizationTsDepartments;
    }
    public void setOrganizationTsDepartments(List<TsDepartment> organizationTsDepartments)
    {
        this.organizationTsDepartments = organizationTsDepartments;
    }
                                    public List<TsDepartment> getViceOrganizationTsDepartments()
    {
        return this.viceOrganizationTsDepartments;
    }
    public void setViceOrganizationTsDepartments(List<TsDepartment> viceOrganizationTsDepartments)
    {
        this.viceOrganizationTsDepartments = viceOrganizationTsDepartments;
    }
                                                                                                                                    }
