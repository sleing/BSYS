package com.mt.ems.service.student.impl;

import com.mt.ems.dao.student.StudentDao;

import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.common.core.web.base.BaseEntity;
import com.mt.common.core.web.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.mt.ems.entity.student.Student;
import com.mt.ems.service.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class StudentServiceBean extends BaseService implements StudentService {

    private static Logger logger = LogManager.getLogger();

	@Autowired
	private StudentDao studentDao;

	@Resource
	private RedisTemplate<String, List<Student>> redisTemplate;

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	/**
	 * 根据分页参数查询学生集合
	 *
	 * @param pageDTO 分页条件
	 */
	@Override
	public PageResultDTO findStudents(PageDTO pageDTO){
        pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
		//TODO:请在此校验参数的合法性
		this.validateFindStudents(pageDTO);

		List<Student> studentDTOS = this.studentDao.findStudents(pageDTO);
		Long totalCount = this.studentDao.findStudentTotalCount(pageDTO);

		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount(totalCount);
		pageResultDTO.setDatas(studentDTOS);

		return pageResultDTO;
	}

	/**
	 * 查询全部学生集合
	 *
	 */
	@Override
	public List<Student> findAllStudents(){
		return this.studentDao.findAllStudents();
	}

	/**
	 * 查询所有学生集合(只提取ID 和 Name)
	 *
	 */
	@Override
	public List<Student> findAllStudentsWithIdName(){
		//TODO:请在此校验参数的合法性
		this.validateFindAllStudentsWithIdName();
		return this.studentDao.findAllStudentsWithIdName();
	}

	/**
	 * 根据名称查询学生集合(只提取ID 和 Name)
	 *
	 * @param studentName 名称
	 */
	@Override
	public List<Student> findStudentsWithIdNameByName(String studentName){
		//TODO:请在此校验参数的合法性
		this.validateFindStudentsWithIdNameByName(studentName);
		//TODO:缓存取对应参数
		Set<String> keys = stringRedisTemplate.keys("searchData:Student_where_studentName_" + studentName);
		List<Student> students = new ArrayList<>();
		if (keys.isEmpty()) {
		students = this.studentDao.findStudentsWithIdNameByName(studentName);
		redisTemplate.opsForValue().set("searchData:Student_where_studentName_" + studentName, students, 30, TimeUnit.DAYS);
		} else {
		students = redisTemplate.opsForValue().get("searchData:Student_where_studentName_" + studentName);
		}
		return students;
	}

	/**
	 * 根据ID查询指定的学生(只提取ID 和 Name)
	 *
	 * @param studentId Id
	 */
	@Override
	public Student findStudentsWithIdNameById(Long studentId){
		//TODO:请在此校验参数的合法性
		this.validateFindStudentsWithIdNameById(studentId);
		return this.studentDao.findStudentsWithIdNameById(studentId);
	}

	/**
	 * 根据ID查询指定的学生
	 *
	 * @param studentId Id
	 */
	@Override
	public Student findStudent(Long studentId){
		//TODO:请在此校验参数的合法性
		this.validateFindStudent(studentId);
		return this.studentDao.findStudent(studentId);
	}

	/**
	 * 根据ID查询指定的学生(包含外键)
	 *
	 * @param studentId Id
	 */
	@Override
	public Student findStudentWithForeignName(Long studentId){
		//TODO:请在此校验参数的合法性
		this.validateFindStudentWithForeignName(studentId);
		return this.studentDao.findStudentWithForeignName(studentId);
	}

	/**
	 * 新增学生
	 *
	 * @param student 实体对象
	 */
	@Override
	public Student saveStudent(Student student){
		//TODO:请在此校验参数的合法性
		this.validateSaveStudent(student);
		//TODO:填充公共参数
		this.setSavePulicColumns(student);
		Long rows = this.studentDao.saveStudent(student);
		if(rows != 1)
		{
			String error = "新增保存学生出错，数据库应该返回1,但返回了 "+rows;
			throw new BusinessException(error);
		}
		return student;
	}

	/**
	 * 更新学生
	 *
	 * @param student 实体对象
	 */
	@Override
	public Student updateStudent(Student student){
		//TODO:请在此校验参数的合法性
		this.validateUpdateStudent(student);
		Long rows = this.studentDao.updateStudent(student);
		if(rows != 1)
		{
			String error = "修改保存学生出错，数据库应该返回1,但返回了 "+rows+",数据可能被删除";
			throw new BusinessException(error);
		}
		return student;
	}

	/**
	 * 根据ID删除学生
	 *
	 * @param studentId ID
	 */
	@Override
	public void deleteStudent(Long studentId){
		//TODO:请在此校验参数的合法性
		this.validateDeleteStudent(studentId);

		Map<Class<? extends BaseEntity>,EntityUsage> entityUsageMap = this.checkForeignEntity(Student.class, studentId);
		if(entityUsageMap != null && entityUsageMap.size() >0){
			StringBuilder errors = new StringBuilder();
			errors.append("计划删除的数据正在被以下数引用\n");
			for(EntityUsage entityUsage : entityUsageMap.values()){
				errors.append("\t").append(entityUsage.getEntityLabel()).append("\n");
				for(Map.Entry<Long,String> entry : entityUsage.getUsageIdNames().entrySet() ){
					errors.append("\t\t").append(entry.getKey()).append("\t").append(entry.getValue()).append("\n");
				}
			}
			errors.append("，不能删除，请检查处理后再删除");
			throw  new BusinessException(errors.toString());
		}

		Long rows = this.studentDao.deleteStudent(studentId);
		if(rows != 1){
			String error = "删除学生出错，数据可能已经被删除";
			throw new BusinessException(error);
		}
	}

	//TODO:---------------验证-------------------

	private void validateFindStudents(PageDTO pageDTO) {
	//TODO:请使用下面方法添加数据过滤条件
	//		pageDTO.addFilter("creatorId",this.getLoginUserId());
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateStudent()写法
	}

	private void validateFindStudentsWithIdNameByName(String studentName) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateStudent()写法
	}


	private void validateFindAllStudentsWithIdName() {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateStudent()写法
	}

	private void validateFindStudentsWithIdNameById(Long studentId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateStudent()写法
	}

	private void validateFindStudent(Long studentId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateStudent()写法
	}

	private void validateFindStudentWithForeignName(Long studentId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateStudent()写法
	}

	private void validateSaveStudent(Student student) {
	//不为空判断
	if (student.getEid() != null || student.getCreatorId() != null || student.getCreateDatetime() != null) {
	throw new BusinessException("非法请求");
	}
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateStudent()写法
	}

	private void validateUpdateStudent(Student student) {
	//不为空判断
	if (student.getEid() == null) {
	throw new BusinessException("唯一标识不能为空");
	}
	//是否存在判断
	if (this.studentDao.findStudentTotalCount(PageDTO.create(Student.FIELD_ID, student.getEid())) == 0) {
	throw new BusinessException("修改的学生 " + student.getName() + " 不存在，修改失败，请重试或联系管理员");
	}
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateStudent()写法
	}

	private void validateDeleteStudent(Long studentId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateStudent()写法
	}

	@Override
	public boolean canDownloadAttachment(String formName, Long id) {
	return true;
	}

	@Override
	public List<Student> findStudentsByName(String name) {
		return this.studentDao.findStudentsByName(name);
	}

	@Override
	public Student myUpdateStudent(Student student) {
		//TODO:请在此校验参数的合法性
		this.validateUpdateStudent(student);
		Long rows = this.studentDao.myUpdateStudent(student);
		if(rows != 1)
		{
			String error = "修改保存学生出错，数据库应该返回1,但返回了 "+rows+",数据可能被删除";
			throw new BusinessException(error);
		}
		return student;
	}
}
