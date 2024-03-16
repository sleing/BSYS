package com.mt.tms.service.tsteacherEntity.impl;

import com.mt.tms.dao.tsteacherEntity.TsTeacherInfoDao;

import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.common.core.web.base.BaseEntity;
import com.mt.common.core.web.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.mt.tms.entity.tsteacherEntity.TsTeacherInfo;
import com.mt.tms.service.tsteacherEntity.TsTeacherInfoService;
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
public class TsTeacherInfoServiceBean extends BaseService implements TsTeacherInfoService {

    private static Logger logger = LogManager.getLogger();

	@Autowired
	private TsTeacherInfoDao tsTeacherInfoDao;

	@Resource
	private RedisTemplate<String, List<TsTeacherInfo>> redisTemplate;

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	/**
	 * 根据分页参数查询老师信息管理集合
	 *
	 * @param pageDTO 分页条件
	 */
	@Override
	public PageResultDTO findTsTeacherInfos(PageDTO pageDTO){
        pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
		//TODO:请在此校验参数的合法性
		this.validateFindTsTeacherInfos(pageDTO);
		List<TsTeacherInfo> tsTeacherInfoDTOS = this.tsTeacherInfoDao.findTsTeacherInfos(pageDTO);
		Long totalCount = this.tsTeacherInfoDao.findTsTeacherInfoTotalCount(pageDTO);

		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount(totalCount);
		pageResultDTO.setDatas(tsTeacherInfoDTOS);

		return pageResultDTO;
	}

	/**
	 * 查询全部老师信息管理集合
	 *
	 */
	@Override
	public List<TsTeacherInfo> findAllTsTeacherInfos(){
		return this.tsTeacherInfoDao.findAllTsTeacherInfos();
	}

	/**
	 * 查询所有老师信息管理集合(只提取ID 和 Name)
	 *
	 */
	@Override
	public List<TsTeacherInfo> findAllTsTeacherInfosWithIdName(){
		//TODO:请在此校验参数的合法性
		this.validateFindAllTsTeacherInfosWithIdName();
		return this.tsTeacherInfoDao.findAllTsTeacherInfosWithIdName();
	}

	/**
	 * 根据名称查询老师信息管理集合(只提取ID 和 Name)
	 *
	 * @param tsTeacherInfoName 名称
	 */
	@Override
	public List<TsTeacherInfo> findTsTeacherInfosWithIdNameByName(String tsTeacherInfoName){
		//TODO:请在此校验参数的合法性
		this.validateFindTsTeacherInfosWithIdNameByName(tsTeacherInfoName);
		//TODO:缓存取对应参数
		Set<String> keys = stringRedisTemplate.keys("searchData:TsTeacherInfo_where_tsTeacherInfoName_" + tsTeacherInfoName);
		List<TsTeacherInfo> tsTeacherInfos = new ArrayList<>();
		if (keys.isEmpty()) {
		tsTeacherInfos = this.tsTeacherInfoDao.findTsTeacherInfosWithIdNameByName(tsTeacherInfoName);
		redisTemplate.opsForValue().set("searchData:TsTeacherInfo_where_tsTeacherInfoName_" + tsTeacherInfoName, tsTeacherInfos, 30, TimeUnit.DAYS);
		} else {
		tsTeacherInfos = redisTemplate.opsForValue().get("searchData:TsTeacherInfo_where_tsTeacherInfoName_" + tsTeacherInfoName);
		}
		return tsTeacherInfos;
	}

	/**
	 * 根据ID查询指定的老师信息管理(只提取ID 和 Name)
	 *
	 * @param tsTeacherInfoId Id
	 */
	@Override
	public TsTeacherInfo findTsTeacherInfosWithIdNameById(Long tsTeacherInfoId){
		//TODO:请在此校验参数的合法性
		this.validateFindTsTeacherInfosWithIdNameById(tsTeacherInfoId);
		return this.tsTeacherInfoDao.findTsTeacherInfosWithIdNameById(tsTeacherInfoId);
	}

	/**
	 * 根据ID查询指定的老师信息管理
	 *
	 * @param tsTeacherInfoId Id
	 */
	@Override
	public TsTeacherInfo findTsTeacherInfo(Long tsTeacherInfoId){
		//TODO:请在此校验参数的合法性
		this.validateFindTsTeacherInfo(tsTeacherInfoId);
		return this.tsTeacherInfoDao.findTsTeacherInfo(tsTeacherInfoId);
	}

	/**
	 * 根据ID查询指定的老师信息管理(包含外键)
	 *
	 * @param tsTeacherInfoId Id
	 */
	@Override
	public TsTeacherInfo findTsTeacherInfoWithForeignName(Long tsTeacherInfoId){
		//TODO:请在此校验参数的合法性
		this.validateFindTsTeacherInfoWithForeignName(tsTeacherInfoId);
		return this.tsTeacherInfoDao.findTsTeacherInfoWithForeignName(tsTeacherInfoId);
	}

	/**
	 * 新增老师信息管理
	 *
	 * @param tsTeacherInfo 实体对象
	 */
	@Override
	public TsTeacherInfo saveTsTeacherInfo(TsTeacherInfo tsTeacherInfo){
		//TODO:请在此校验参数的合法性
		this.validateSaveTsTeacherInfo(tsTeacherInfo);
		//TODO:填充公共参数
		this.setSavePulicColumns(tsTeacherInfo);
		Long rows = this.tsTeacherInfoDao.saveTsTeacherInfo(tsTeacherInfo);
		if(rows != 1)
		{
			String error = "新增保存老师信息管理出错，数据库应该返回1,但返回了 "+rows;
			throw new BusinessException(error);
		}
		return tsTeacherInfo;
	}

	/**
	 * 更新老师信息管理
	 *
	 * @param tsTeacherInfo 实体对象
	 */
	@Override
	public TsTeacherInfo updateTsTeacherInfo(TsTeacherInfo tsTeacherInfo){
		//TODO:请在此校验参数的合法性
		this.validateUpdateTsTeacherInfo(tsTeacherInfo);
		Long rows = this.tsTeacherInfoDao.updateTsTeacherInfo(tsTeacherInfo);
		if(rows != 1)
		{
			String error = "修改保存老师信息管理出错，数据库应该返回1,但返回了 "+rows+",数据可能被删除";
			throw new BusinessException(error);
		}
		return tsTeacherInfo;
	}

	/**
	 * 根据ID删除老师信息管理
	 *
	 * @param tsTeacherInfoId ID
	 */
	@Override
	public void deleteTsTeacherInfo(Long tsTeacherInfoId){
		//TODO:请在此校验参数的合法性
		this.validateDeleteTsTeacherInfo(tsTeacherInfoId);

		Map<Class<? extends BaseEntity>,EntityUsage> entityUsageMap = this.checkForeignEntity(TsTeacherInfo.class, tsTeacherInfoId);
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

		Long rows = this.tsTeacherInfoDao.deleteTsTeacherInfo(tsTeacherInfoId);
		if(rows != 1){
			String error = "删除老师信息管理出错，数据可能已经被删除";
			throw new BusinessException(error);
		}
	}

	//TODO:---------------验证-------------------

	private void validateFindTsTeacherInfos(PageDTO pageDTO) {
	//TODO:请使用下面方法添加数据过滤条件
	//		pageDTO.addFilter("creatorId",this.getLoginUserId());
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsTeacherInfo()写法
	}

	private void validateFindTsTeacherInfosWithIdNameByName(String tsTeacherInfoName) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsTeacherInfo()写法
	}


	private void validateFindAllTsTeacherInfosWithIdName() {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsTeacherInfo()写法
	}

	private void validateFindTsTeacherInfosWithIdNameById(Long tsTeacherInfoId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsTeacherInfo()写法
	}

	private void validateFindTsTeacherInfo(Long tsTeacherInfoId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsTeacherInfo()写法
	}

	private void validateFindTsTeacherInfoWithForeignName(Long tsTeacherInfoId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsTeacherInfo()写法
	}

	private void validateSaveTsTeacherInfo(TsTeacherInfo tsTeacherInfo) {
	//不为空判断
	if (tsTeacherInfo.getEid() != null || tsTeacherInfo.getCreatorId() != null || tsTeacherInfo.getCreateDatetime() != null) {
	throw new BusinessException("非法请求");
	}
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsTeacherInfo()写法
	}

	private void validateUpdateTsTeacherInfo(TsTeacherInfo tsTeacherInfo) {
	//不为空判断
	if (tsTeacherInfo.getEid() == null) {
	throw new BusinessException("唯一标识不能为空");
	}
	//是否存在判断
	if (this.tsTeacherInfoDao.findTsTeacherInfoTotalCount(PageDTO.create(TsTeacherInfo.FIELD_ID, tsTeacherInfo.getEid())) == 0) {
	throw new BusinessException("修改的老师信息管理 " + tsTeacherInfo.getName() + " 不存在，修改失败，请重试或联系管理员");
	}
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsTeacherInfo()写法
	}

	private void validateDeleteTsTeacherInfo(Long tsTeacherInfoId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsTeacherInfo()写法
	}

	@Override
	public boolean canDownloadAttachment(String formName, Long id) {
	return true;
	}
}
