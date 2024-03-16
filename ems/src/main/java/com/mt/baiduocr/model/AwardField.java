package com.mt.baiduocr.model;


import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AwardField {
    private static final long serialVersionUID = 1L;

    private String instructor;  //指导老师
    private List<String> students;    //所有获奖学生
    private String certificate_number;  //证书编号
    private Long ocr_award_id;

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public List<String> getStudents() {
        return students;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }

    public String getCertificate_number() {
        return certificate_number;
    }

    public void setCertificate_number(String certificate_number) {
        this.certificate_number = certificate_number;
    }

    public Long getOcr_award_id() {
        return ocr_award_id;
    }

    public void setOcr_award_id(Long ocr_award_id) {
        this.ocr_award_id = ocr_award_id;
    }
}
