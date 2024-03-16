
//接受前端发送来的请求
package com.mt.ems.controller.student;

import com.mt.common.core.annotation.ApiPageParam;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.ems.dto.student.StudentEditDto;
import com.mt.ems.entity.student.Student;
import com.mt.ems.service.student.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "学生") //生成文档的
@RestController	//对外提供服务的 bean
@RequestMapping("/api/ems/student/Student")
@CrossOrigin(allowCredentials = "true")
public class StudentController {
	private static Logger logger = LoggerFactory.getLogger(StudentController.class);


	@Autowired
	private StudentService studentService;

	/**
	 * 根据分页参数查询学生集合
	 *
	 * @param pageDTO 分页条件
	 */
	@PreAuthorize("hasAuthority('ems:student:Student:view')")
	@ApiOperation("分页查询学生")
	@ApiPageParam
	@PostMapping("/findStudents") //映射一个post的请求
	public PageResultDTO findStudents(@RequestBody PageDTO pageDTO) {
		return this.studentService.findStudents(pageDTO);
	}

	/**
	 * 根据ID查询指定的学生
	 *
	 * @param studentId Id
	 */
	@PreAuthorize("hasAuthority('ems:student:Student:view')")
	@ApiOperation("根据id查询学生")
	@ApiPageParam
	@PostMapping("/findStudent")
	public Student findStudent(@RequestParam Long studentId) {
		return this.studentService.findStudent(studentId);
	}

	/**
	 * 根据ID查询指定的学生(包含外键名称)
	 *
	 * @param studentId Id
	 */
	@PreAuthorize("hasAuthority('ems:student:Student:view')")
	@ApiOperation("根据ID查询指定的学生(包含外键名称)")
	@PostMapping("/findStudentForView")
	public Student findStudentForView(@RequestParam Long studentId) {
		return this.studentService.findStudentWithForeignName(studentId);
	}

	/**
	 * 根据ID查询指定的学生(包含学生和外键名称)
	 *
	 * @param studentId Id
	 */
	@PreAuthorize("hasAuthority('ems:student:Student:view')")
	@ApiOperation("根据ID查询指定的学生(包含学生和外键名称)")
	@PostMapping("/findStudentForEdit")
	public StudentEditDto findStudentForEdit(@RequestParam Long studentId) {
		StudentEditDto studentEditDto = new StudentEditDto();
		studentEditDto.setStudent(this.studentService.findStudentWithForeignName(studentId));

		this.prepareStudentEditDto(studentEditDto);

		return studentEditDto;
	}

	/**
	 * 根据ID查询指定的学生(只提取ID 和 Name)
	 *
	 * @param studentId Id
	 */
	@PreAuthorize("hasAuthority('ems:student:Student:view')")
	@ApiOperation("根据ID查询指定的学生(只提取ID 和 Name)")
	@PostMapping("/findStudentsWithIdNameById")
	public Student findStudentsWithIdNameById(@RequestParam Long studentId) {
		return this.studentService.findStudentsWithIdNameById(studentId);
	}

	/**
	 * 根据名称查询学生集合(只提取ID 和 Name)
	 *
	 * @param studentName 名称
	 */
	@PreAuthorize("hasAuthority('ems:student:Student:view')")
	@ApiOperation("根据名称查询学生集合(只提取ID 和 Name)")
	@PostMapping("/findStudentsWithIdNameByName")
	public List<Student> findStudentsWithIdNameByName(@RequestParam String studentName) {
		return this.studentService.findStudentsWithIdNameByName(studentName);
	}


	/**
	 * 创建新的学生
	 */
	@PreAuthorize("hasAuthority('ems:student:Student:add')")
	@ApiOperation("创建新的学生")
	@PostMapping("/createStudent")
	public StudentEditDto createStudent() {
		StudentEditDto studentEditDto = new StudentEditDto();
		studentEditDto.setStudent(new Student());

		this.prepareStudentEditDto(studentEditDto);
		return studentEditDto;
	}

	private void prepareStudentEditDto(StudentEditDto studentEditDto) {
	}

	/**
	 * 新增保存学生
	 *
	 * @param student 实体对象
	 */
	@PreAuthorize("hasAuthority('ems:student:Student:add')")
	@ApiOperation("新增保存学生")
	@PostMapping("/saveStudent")
	public Student saveStudent(@RequestBody Student student) {
		return this.studentService.saveStudent(student);
	}

	/**
	 * 修改保存学生
	 *
	 * @param student 实体对象
	 */
	@PreAuthorize("hasAuthority('ems:student:Student:update')")
	@ApiOperation("修改保存学生")
	@PostMapping("/updateStudent")
	public Student updateStudent(@RequestBody Student student) {
		return this.studentService.updateStudent(student);
	}

	//dto 用于同时传输两个对象
	//dto用起来太麻烦
	//可以选择创建一个 Map<Student>


	/**
	 * 根据ID删除学生
	 *
	 * @param studentId ID
	 */
	@PreAuthorize("hasAuthority('ems:student:Student:remove')")
	@ApiOperation("根据ID删除学生")
	@PostMapping("/deleteStudent")
	public void deleteStudent(@RequestParam Long studentId) {
		this.studentService.deleteStudent(studentId);
	}

	/*
	自己写的学生查找的功能
	 */
	@PreAuthorize("hasAuthority('ems:student:Student:view')")//权限管理
	@GetMapping("/findStudentsByName")
	public List<Student>findStudentsByName(@RequestParam String name)
	{
		List<Student> students = this.studentService.findStudentsByName(name);
//		Student student = new Student();
//		student.setName("ght1");
//		students.add(student);
//		student.setName("ght2");
//		students.add(student);
		return students;
	}


	@PreAuthorize("hasAuthority('ems:student:Student:update')")//权限管理
	@ApiOperation("修改学生")
	@PostMapping("/myUpdateStudent")
		public Student myUpdateStudent(@RequestBody Student student)
	{
		  return this.studentService.myUpdateStudent(student);

	}
	//PostMapping与GetMapping的区别
}