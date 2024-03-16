package com.mt.tms.service.tsstudentEntity.impl;

import com.mt.tms.dao.tsstudentEntity.TsStudentInfoDao;

import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.common.core.web.base.BaseEntity;
import com.mt.common.core.web.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.mt.tms.entity.tsstudentEntity.TsStudentInfo;
import com.mt.tms.service.tsstudentEntity.TsStudentInfoService;
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
public class TsStudentInfoServiceBean extends BaseService implements TsStudentInfoService {

    private static Logger logger = LogManager.getLogger();

	@Autowired
	private TsStudentInfoDao tsStudentInfoDao;

	@Resource
	private RedisTemplate<String, List<TsStudentInfo>> redisTemplate;

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	/**
	 * 根据分页参数查询团学会学生信息管理集合
	 *
	 * @param pageDTO 分页条件
	 */
	@Override
	public PageResultDTO findTsStudentInfos(PageDTO pageDTO){
        pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
		//TODO:请在此校验参数的合法性
		this.validateFindTsStudentInfos(pageDTO);
		List<TsStudentInfo> tsStudentInfoDTOS = this.tsStudentInfoDao.findTsStudentInfos(pageDTO);
		Long totalCount = this.tsStudentInfoDao.findTsStudentInfoTotalCount(pageDTO);

		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount(totalCount);
		pageResultDTO.setDatas(tsStudentInfoDTOS);

		return pageResultDTO;
	}

	/**
	 * 查询全部团学会学生信息管理集合
	 *
	 */
	@Override
	public List<TsStudentInfo> findAllTsStudentInfos(){
		return this.tsStudentInfoDao.findAllTsStudentInfos();
	}

	/**
	 * 查询所有团学会学生信息管理集合(只提取ID 和 Name)
	 *
	 */
	@Override
	public List<TsStudentInfo> findAllTsStudentInfosWithIdName(){
		//TODO:请在此校验参数的合法性
		this.validateFindAllTsStudentInfosWithIdName();
		return this.tsStudentInfoDao.findAllTsStudentInfosWithIdName();
	}

	/**
	 * 根据名称查询团学会学生信息管理集合(只提取ID 和 Name)
	 *
	 * @param tsStudentInfoName 名称
	 */
	@Override
	public List<TsStudentInfo> findTsStudentInfosWithIdNameByName(String tsStudentInfoName){
		//TODO:请在此校验参数的合法性
		this.validateFindTsStudentInfosWithIdNameByName(tsStudentInfoName);
		//TODO:缓存取对应参数
		Set<String> keys = stringRedisTemplate.keys("searchData:TsStudentInfo_where_tsStudentInfoName_" + tsStudentInfoName);
		List<TsStudentInfo> tsStudentInfos = new ArrayList<>();
		if (keys.isEmpty()) {
		tsStudentInfos = this.tsStudentInfoDao.findTsStudentInfosWithIdNameByName(tsStudentInfoName);
		redisTemplate.opsForValue().set("searchData:TsStudentInfo_where_tsStudentInfoName_" + tsStudentInfoName, tsStudentInfos, 30, TimeUnit.DAYS);
		} else {
		tsStudentInfos = redisTemplate.opsForValue().get("searchData:TsStudentInfo_where_tsStudentInfoName_" + tsStudentInfoName);
		}
		return tsStudentInfos;
	}

	/**
	 * 根据ID查询指定的团学会学生信息管理(只提取ID 和 Name)
	 *
	 * @param tsStudentInfoId Id
	 */
	@Override
	public TsStudentInfo findTsStudentInfosWithIdNameById(Long tsStudentInfoId){
		//TODO:请在此校验参数的合法性
		this.validateFindTsStudentInfosWithIdNameById(tsStudentInfoId);
		return this.tsStudentInfoDao.findTsStudentInfosWithIdNameById(tsStudentInfoId);
	}

	/**
	 * 根据ID查询指定的团学会学生信息管理
	 *
	 * @param tsStudentInfoId Id
	 */
	@Override
	public TsStudentInfo findTsStudentInfo(Long tsStudentInfoId){
		//TODO:请在此校验参数的合法性
		this.validateFindTsStudentInfo(tsStudentInfoId);
		return this.tsStudentInfoDao.findTsStudentInfo(tsStudentInfoId);
	}

	/**
	 * 根据ID查询指定的团学会学生信息管理(包含外键)
	 *
	 * @param tsStudentInfoId Id
	 */
	@Override
	public TsStudentInfo findTsStudentInfoWithForeignName(Long tsStudentInfoId){
		//TODO:请在此校验参数的合法性
		this.validateFindTsStudentInfoWithForeignName(tsStudentInfoId);
		return this.tsStudentInfoDao.findTsStudentInfoWithForeignName(tsStudentInfoId);
	}

	/**
	 * 新增团学会学生信息管理
	 *
	 * @param tsStudentInfo 实体对象
	 */
	@Override
	public TsStudentInfo saveTsStudentInfo(TsStudentInfo tsStudentInfo){
		//TODO:请在此校验参数的合法性
		this.validateSaveTsStudentInfo(tsStudentInfo);
		//TODO:填充公共参数
		this.setSavePulicColumns(tsStudentInfo);
		Long rows = this.tsStudentInfoDao.saveTsStudentInfo(tsStudentInfo);
		if(rows != 1)
		{
			String error = "新增保存团学会学生信息管理出错，数据库应该返回1,但返回了 "+rows;
			throw new BusinessException(error);
		}
		return tsStudentInfo;
	}

	/**
	 * 更新团学会学生信息管理
	 *
	 * @param tsStudentInfo 实体对象
	 */
	@Override
	public TsStudentInfo updateTsStudentInfo(TsStudentInfo tsStudentInfo){
		//TODO:请在此校验参数的合法性
		this.validateUpdateTsStudentInfo(tsStudentInfo);
		Long rows = this.tsStudentInfoDao.updateTsStudentInfo(tsStudentInfo);
		if(rows != 1)
		{
			String error = "修改保存团学会学生信息管理出错，数据库应该返回1,但返回了 "+rows+",数据可能被删除";
			throw new BusinessException(error);
		}
		return tsStudentInfo;
	}

	/**
	 * 根据ID删除团学会学生信息管理
	 *
	 * @param tsStudentInfoId ID
	 */
	@Override
	public void deleteTsStudentInfo(Long tsStudentInfoId){
		//TODO:请在此校验参数的合法性
		this.validateDeleteTsStudentInfo(tsStudentInfoId);

		Map<Class<? extends BaseEntity>,EntityUsage> entityUsageMap = this.checkForeignEntity(TsStudentInfo.class, tsStudentInfoId);
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

		Long rows = this.tsStudentInfoDao.deleteTsStudentInfo(tsStudentInfoId);
		if(rows != 1){
			String error = "删除团学会学生信息管理出错，数据可能已经被删除";
			throw new BusinessException(error);
		}
	}

	//TODO:---------------验证-------------------

	private void validateFindTsStudentInfos(PageDTO pageDTO) {
	//TODO:请使用下面方法添加数据过滤条件
	//		pageDTO.addFilter("creatorId",this.getLoginUserId());
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsStudentInfo()写法
	}

	private void validateFindTsStudentInfosWithIdNameByName(String tsStudentInfoName) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsStudentInfo()写法
	}


	private void validateFindAllTsStudentInfosWithIdName() {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsStudentInfo()写法
	}

	private void validateFindTsStudentInfosWithIdNameById(Long tsStudentInfoId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsStudentInfo()写法
	}

	private void validateFindTsStudentInfo(Long tsStudentInfoId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsStudentInfo()写法
	}

	private void validateFindTsStudentInfoWithForeignName(Long tsStudentInfoId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsStudentInfo()写法
	}

	private void validateSaveTsStudentInfo(TsStudentInfo tsStudentInfo) {
	//不为空判断
	if (tsStudentInfo.getEid() != null || tsStudentInfo.getCreatorId() != null || tsStudentInfo.getCreateDatetime() != null) {
	throw new BusinessException("非法请求");
	}
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsStudentInfo()写法
	}

	private void validateUpdateTsStudentInfo(TsStudentInfo tsStudentInfo) {
	//不为空判断
	if (tsStudentInfo.getEid() == null) {
	throw new BusinessException("唯一标识不能为空");
	}
	//是否存在判断
	if (this.tsStudentInfoDao.findTsStudentInfoTotalCount(PageDTO.create(TsStudentInfo.FIELD_ID, tsStudentInfo.getEid())) == 0) {
	throw new BusinessException("修改的团学会学生信息管理 " + tsStudentInfo.getName() + " 不存在，修改失败，请重试或联系管理员");
	}
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsStudentInfo()写法
	}

	private void validateDeleteTsStudentInfo(Long tsStudentInfoId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsStudentInfo()写法
	}

	@Override
	public boolean canDownloadAttachment(String formName, Long id) {
	return true;
	}
}
