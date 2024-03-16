package com.mt.tms.service.tsdepartmentEntity.impl;

import com.mt.tms.dao.tsdepartmentEntity.TsDepartmentDao;

import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.common.core.web.base.BaseEntity;
import com.mt.common.core.web.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.mt.tms.entity.tsdepartmentEntity.TsDepartment;
import com.mt.tms.service.tsdepartmentEntity.TsDepartmentService;
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
public class TsDepartmentServiceBean extends BaseService implements TsDepartmentService {

    private static Logger logger = LogManager.getLogger();

	@Autowired
	private TsDepartmentDao tsDepartmentDao;

	@Resource
	private RedisTemplate<String, List<TsDepartment>> redisTemplate;

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	/**
	 * 根据分页参数查询部门信息管理集合
	 *
	 * @param pageDTO 分页条件
	 */
	@Override
	public PageResultDTO findTsDepartments(PageDTO pageDTO){
        pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
		//TODO:请在此校验参数的合法性
		this.validateFindTsDepartments(pageDTO);
		List<TsDepartment> tsDepartmentDTOS = this.tsDepartmentDao.findTsDepartments(pageDTO);
		Long totalCount = this.tsDepartmentDao.findTsDepartmentTotalCount(pageDTO);

		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount(totalCount);
		pageResultDTO.setDatas(tsDepartmentDTOS);

		return pageResultDTO;
	}

	/**
	 * 查询全部部门信息管理集合
	 *
	 */
	@Override
	public List<TsDepartment> findAllTsDepartments(){
		return this.tsDepartmentDao.findAllTsDepartments();
	}

	/**
	 * 查询所有部门信息管理集合(只提取ID 和 Name)
	 *
	 */
	@Override
	public List<TsDepartment> findAllTsDepartmentsWithIdName(){
		//TODO:请在此校验参数的合法性
		this.validateFindAllTsDepartmentsWithIdName();
		return this.tsDepartmentDao.findAllTsDepartmentsWithIdName();
	}

	/**
	 * 根据名称查询部门信息管理集合(只提取ID 和 Name)
	 *
	 * @param tsDepartmentName 名称
	 */
	@Override
	public List<TsDepartment> findTsDepartmentsWithIdNameByName(String tsDepartmentName){
		//TODO:请在此校验参数的合法性
		this.validateFindTsDepartmentsWithIdNameByName(tsDepartmentName);
		//TODO:缓存取对应参数
		Set<String> keys = stringRedisTemplate.keys("searchData:TsDepartment_where_tsDepartmentName_" + tsDepartmentName);
		List<TsDepartment> tsDepartments = new ArrayList<>();
		if (keys.isEmpty()) {
		tsDepartments = this.tsDepartmentDao.findTsDepartmentsWithIdNameByName(tsDepartmentName);
		redisTemplate.opsForValue().set("searchData:TsDepartment_where_tsDepartmentName_" + tsDepartmentName, tsDepartments, 30, TimeUnit.DAYS);
		} else {
		tsDepartments = redisTemplate.opsForValue().get("searchData:TsDepartment_where_tsDepartmentName_" + tsDepartmentName);
		}
		return tsDepartments;
	}

	/**
	 * 根据ID查询指定的部门信息管理(只提取ID 和 Name)
	 *
	 * @param tsDepartmentId Id
	 */
	@Override
	public TsDepartment findTsDepartmentsWithIdNameById(Long tsDepartmentId){
		//TODO:请在此校验参数的合法性
		this.validateFindTsDepartmentsWithIdNameById(tsDepartmentId);
		return this.tsDepartmentDao.findTsDepartmentsWithIdNameById(tsDepartmentId);
	}

	/**
	 * 根据ID查询指定的部门信息管理
	 *
	 * @param tsDepartmentId Id
	 */
	@Override
	public TsDepartment findTsDepartment(Long tsDepartmentId){
		//TODO:请在此校验参数的合法性
		this.validateFindTsDepartment(tsDepartmentId);
		return this.tsDepartmentDao.findTsDepartment(tsDepartmentId);
	}

	/**
	 * 根据ID查询指定的部门信息管理(包含外键)
	 *
	 * @param tsDepartmentId Id
	 */
	@Override
	public TsDepartment findTsDepartmentWithForeignName(Long tsDepartmentId){
		//TODO:请在此校验参数的合法性
		this.validateFindTsDepartmentWithForeignName(tsDepartmentId);
		return this.tsDepartmentDao.findTsDepartmentWithForeignName(tsDepartmentId);
	}

	/**
	 * 新增部门信息管理
	 *
	 * @param tsDepartment 实体对象
	 */
	@Override
	public TsDepartment saveTsDepartment(TsDepartment tsDepartment){
		//TODO:请在此校验参数的合法性
		this.validateSaveTsDepartment(tsDepartment);
		//TODO:填充公共参数
		this.setSavePulicColumns(tsDepartment);
		Long rows = this.tsDepartmentDao.saveTsDepartment(tsDepartment);
		if(rows != 1)
		{
			String error = "新增保存部门信息管理出错，数据库应该返回1,但返回了 "+rows;
			throw new BusinessException(error);
		}
		return tsDepartment;
	}

	/**
	 * 更新部门信息管理
	 *
	 * @param tsDepartment 实体对象
	 */
	@Override
	public TsDepartment updateTsDepartment(TsDepartment tsDepartment){
		//TODO:请在此校验参数的合法性
		this.validateUpdateTsDepartment(tsDepartment);
		Long rows = this.tsDepartmentDao.updateTsDepartment(tsDepartment);
		if(rows != 1)
		{
			String error = "修改保存部门信息管理出错，数据库应该返回1,但返回了 "+rows+",数据可能被删除";
			throw new BusinessException(error);
		}
		return tsDepartment;
	}

	/**
	 * 根据ID删除部门信息管理
	 *
	 * @param tsDepartmentId ID
	 */
	@Override
	public void deleteTsDepartment(Long tsDepartmentId){
		//TODO:请在此校验参数的合法性
		this.validateDeleteTsDepartment(tsDepartmentId);

		Map<Class<? extends BaseEntity>,EntityUsage> entityUsageMap = this.checkForeignEntity(TsDepartment.class, tsDepartmentId);
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

		Long rows = this.tsDepartmentDao.deleteTsDepartment(tsDepartmentId);
		if(rows != 1){
			String error = "删除部门信息管理出错，数据可能已经被删除";
			throw new BusinessException(error);
		}
	}

	//TODO:---------------验证-------------------

	private void validateFindTsDepartments(PageDTO pageDTO) {
	//TODO:请使用下面方法添加数据过滤条件
	//		pageDTO.addFilter("creatorId",this.getLoginUserId());
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsDepartment()写法
	}

	private void validateFindTsDepartmentsWithIdNameByName(String tsDepartmentName) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsDepartment()写法
	}


	private void validateFindAllTsDepartmentsWithIdName() {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsDepartment()写法
	}

	private void validateFindTsDepartmentsWithIdNameById(Long tsDepartmentId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsDepartment()写法
	}

	private void validateFindTsDepartment(Long tsDepartmentId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsDepartment()写法
	}

	private void validateFindTsDepartmentWithForeignName(Long tsDepartmentId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsDepartment()写法
	}

	private void validateSaveTsDepartment(TsDepartment tsDepartment) {
	//不为空判断
	if (tsDepartment.getEid() != null || tsDepartment.getCreatorId() != null || tsDepartment.getCreateDatetime() != null) {
	throw new BusinessException("非法请求");
	}
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsDepartment()写法
	}

	private void validateUpdateTsDepartment(TsDepartment tsDepartment) {
	//不为空判断
	if (tsDepartment.getEid() == null) {
	throw new BusinessException("唯一标识不能为空");
	}
	//是否存在判断
	if (this.tsDepartmentDao.findTsDepartmentTotalCount(PageDTO.create(TsDepartment.FIELD_ID, tsDepartment.getEid())) == 0) {
	throw new BusinessException("修改的部门信息管理 " + tsDepartment.getName() + " 不存在，修改失败，请重试或联系管理员");
	}
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsDepartment()写法
	}

	private void validateDeleteTsDepartment(Long tsDepartmentId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsDepartment()写法
	}

	@Override
	public boolean canDownloadAttachment(String formName, Long id) {
	return true;
	}
}
