package com.mt.ems.service.student;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.ems.entity.student.Student;

import java.util.List;

public interface StudentService {
    /**
     * 根据分页参数查询学生集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findStudents(PageDTO pageDTO);

    /**
     * 查询全部学生集合
     *
     */
    public List<Student> findAllStudents();

    /**
     * 根据名称查询学生集合(只提取ID 和 Name)
     *
     * @param studentName 名称
     */
    public List<Student> findStudentsWithIdNameByName(String studentName);

    /**
     * 查询所有学生集合(只提取ID 和 Name)
     *
     */
    public List<Student> findAllStudentsWithIdName();

    /**
     * 根据ID查询指定的学生(只提取ID 和 Name)
     *
     * @param studentId Id
     */
    public Student findStudentsWithIdNameById(Long studentId);

    /**
     * 根据ID查询指定的学生
     *
     * @param studentId Id
     */
    public Student findStudent(Long studentId);

    /**
     * 根据ID查询指定的学生(包含外键)
     *
     * @param studentId Id
     */
    public Student findStudentWithForeignName(Long studentId);

    /**
     * 新增学生
     *
     * @param student 实体对象
     */
    public Student saveStudent(Student student);

    /**
     * 更新学生
     *
     * @param student 实体对象
     */
    public Student updateStudent(Student student);

    /**
     * 根据ID删除学生
     *
     * @param studentId ID
     */
    public void deleteStudent(Long studentId);
    /**
     * 根据名字查询学生信息
     */
    public List<Student>findStudentsByName(String name);
    /**
     * 修改学生信息
     */
    public Student myUpdateStudent(Student student);
}
