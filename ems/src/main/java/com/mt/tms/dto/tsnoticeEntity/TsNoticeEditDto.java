package com.mt.tms.dto.tsnoticeEntity;


import com.mt.tms.entity.tsnoticeEntity.TsNotice;

import java.util.List;
                            import com.mt.tms.entity.tsdepartmentEntity.TsDepartment;
                            public class TsNoticeEditDto{

    private TsNotice tsNotice;


                                                                                        //外键实体是：TsDepartment
    private List<TsDepartment> noticeDepartmentTsDepartments;
                                                                                    

    public  TsNotice getTsNotice()
    {
        return this.tsNotice;
    }
    public  void setTsNotice(TsNotice tsNotice)
    {
        this.tsNotice = tsNotice;
    }


                                                                                        public List<TsDepartment> getNoticeDepartmentTsDepartments()
    {
        return this.noticeDepartmentTsDepartments;
    }
    public void setNoticeDepartmentTsDepartments(List<TsDepartment> noticeDepartmentTsDepartments)
    {
        this.noticeDepartmentTsDepartments = noticeDepartmentTsDepartments;
    }
                                                                                    }
