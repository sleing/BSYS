package com.mt.ems.dao.student;
//操作数据库

import com.mt.common.core.web.base.PageDTO;
import com.mt.ems.entity.student.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "studentDao")
public interface StudentDao {

    /**
    * 根据分页参数查询学生集合
    *
    * @param pageDTO 分页条件
    */
    public List<Student> findStudents(PageDTO pageDTO);

    /**
    * 查询全部学生集合
    *
    */
    public List<Student> findAllStudents();

    /**
    * 查询所有学生集合(只提取ID 和 Name)
    *
    */
    public List<Student> findAllStudentsWithIdName();

    /**
    * 根据名称查询学生集合(只提取ID 和 Name)
    *
    * @param studentName 名称
    */
    public List<Student> findStudentsWithIdNameByName(@Param("studentName") String studentName);

    /**
    * 根据ID查询指定的学生(只提取ID 和 Name)
    *
    * @param studentId Id
    */
    public Student findStudentsWithIdNameById(@Param(" studentId") Long studentId);

    /**
    * 根据分页参数查询学生集合的数量
    *
    * @param pageDTO 分页条件
    */
    public Long findStudentTotalCount(PageDTO pageDTO);

    /**
    * 根据ID查询指定的学生
    *
    * @param studentId Id
    */
    public Student findStudent(@Param("studentId") Long studentId);

    /**
    * 根据ID查询指定的学生(包含外键)
    *
    * @param studentId Id
    */
    public Student findStudentWithForeignName(@Param("studentId") Long studentId);

    /**
    * 新增学生
    *
    * @param student 实体对象
    */
    public Long saveStudent(Student student);

    /**
    * 更新学生
    *
    * @param student 实体对象
    */
    public Long updateStudent(Student student);

    /**
    * 根据ID删除学生
    *
    * @param studentId ID
    */
    public Long deleteStudent(@Param("studentId") Long studentId);

    /**
     * 根据名字查询学生信息
     *
     * @param name 姓名
     */
    public List<Student>findStudentsByName(@Param("name")String name);

    /**
     * 修改学生信息
     *
     * @param student 学生信息实体对象
     */
    public Long myUpdateStudent(Student student);
}
