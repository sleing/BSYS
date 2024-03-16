package com.mt.tms.service.tspositionEntity.impl;

import com.mt.tms.dao.tspositionEntity.TsPositionDao;

import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.common.core.web.base.BaseEntity;
import com.mt.common.core.web.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.mt.tms.entity.tspositionEntity.TsPosition;
import com.mt.tms.service.tspositionEntity.TsPositionService;
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
public class TsPositionServiceBean extends BaseService implements TsPositionService {

    private static Logger logger = LogManager.getLogger();

	@Autowired
	private TsPositionDao tsPositionDao;

	@Resource
	private RedisTemplate<String, List<TsPosition>> redisTemplate;

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	/**
	 * 根据分页参数查询职务管理集合
	 *
	 * @param pageDTO 分页条件
	 */
	@Override
	public PageResultDTO findTsPositions(PageDTO pageDTO){
        pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
		//TODO:请在此校验参数的合法性
		this.validateFindTsPositions(pageDTO);
		List<TsPosition> tsPositionDTOS = this.tsPositionDao.findTsPositions(pageDTO);
		Long totalCount = this.tsPositionDao.findTsPositionTotalCount(pageDTO);

		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount(totalCount);
		pageResultDTO.setDatas(tsPositionDTOS);

		return pageResultDTO;
	}

	/**
	 * 查询全部职务管理集合
	 *
	 */
	@Override
	public List<TsPosition> findAllTsPositions(){
		return this.tsPositionDao.findAllTsPositions();
	}

	/**
	 * 查询所有职务管理集合(只提取ID 和 Name)
	 *
	 */
	@Override
	public List<TsPosition> findAllTsPositionsWithIdName(){
		//TODO:请在此校验参数的合法性
		this.validateFindAllTsPositionsWithIdName();
		return this.tsPositionDao.findAllTsPositionsWithIdName();
	}

	/**
	 * 根据名称查询职务管理集合(只提取ID 和 Name)
	 *
	 * @param tsPositionName 名称
	 */
	@Override
	public List<TsPosition> findTsPositionsWithIdNameByName(String tsPositionName){
		//TODO:请在此校验参数的合法性
		this.validateFindTsPositionsWithIdNameByName(tsPositionName);
		//TODO:缓存取对应参数
		Set<String> keys = stringRedisTemplate.keys("searchData:TsPosition_where_tsPositionName_" + tsPositionName);
		List<TsPosition> tsPositions = new ArrayList<>();
		if (keys.isEmpty()) {
		tsPositions = this.tsPositionDao.findTsPositionsWithIdNameByName(tsPositionName);
		redisTemplate.opsForValue().set("searchData:TsPosition_where_tsPositionName_" + tsPositionName, tsPositions, 30, TimeUnit.DAYS);
		} else {
		tsPositions = redisTemplate.opsForValue().get("searchData:TsPosition_where_tsPositionName_" + tsPositionName);
		}
		return tsPositions;
	}

	/**
	 * 根据ID查询指定的职务管理(只提取ID 和 Name)
	 *
	 * @param tsPositionId Id
	 */
	@Override
	public TsPosition findTsPositionsWithIdNameById(Long tsPositionId){
		//TODO:请在此校验参数的合法性
		this.validateFindTsPositionsWithIdNameById(tsPositionId);
		return this.tsPositionDao.findTsPositionsWithIdNameById(tsPositionId);
	}

	/**
	 * 根据ID查询指定的职务管理
	 *
	 * @param tsPositionId Id
	 */
	@Override
	public TsPosition findTsPosition(Long tsPositionId){
		//TODO:请在此校验参数的合法性
		this.validateFindTsPosition(tsPositionId);
		return this.tsPositionDao.findTsPosition(tsPositionId);
	}

	/**
	 * 根据ID查询指定的职务管理(包含外键)
	 *
	 * @param tsPositionId Id
	 */
	@Override
	public TsPosition findTsPositionWithForeignName(Long tsPositionId){
		//TODO:请在此校验参数的合法性
		this.validateFindTsPositionWithForeignName(tsPositionId);
		return this.tsPositionDao.findTsPositionWithForeignName(tsPositionId);
	}

	/**
	 * 新增职务管理
	 *
	 * @param tsPosition 实体对象
	 */
	@Override
	public TsPosition saveTsPosition(TsPosition tsPosition){
		//TODO:请在此校验参数的合法性
		this.validateSaveTsPosition(tsPosition);
		//TODO:填充公共参数
		this.setSavePulicColumns(tsPosition);
		Long rows = this.tsPositionDao.saveTsPosition(tsPosition);
		if(rows != 1)
		{
			String error = "新增保存职务管理出错，数据库应该返回1,但返回了 "+rows;
			throw new BusinessException(error);
		}
		return tsPosition;
	}

	/**
	 * 更新职务管理
	 *
	 * @param tsPosition 实体对象
	 */
	@Override
	public TsPosition updateTsPosition(TsPosition tsPosition){
		//TODO:请在此校验参数的合法性
		this.validateUpdateTsPosition(tsPosition);
		Long rows = this.tsPositionDao.updateTsPosition(tsPosition);
		if(rows != 1)
		{
			String error = "修改保存职务管理出错，数据库应该返回1,但返回了 "+rows+",数据可能被删除";
			throw new BusinessException(error);
		}
		return tsPosition;
	}

	/**
	 * 根据ID删除职务管理
	 *
	 * @param tsPositionId ID
	 */
	@Override
	public void deleteTsPosition(Long tsPositionId){
		//TODO:请在此校验参数的合法性
		this.validateDeleteTsPosition(tsPositionId);

		Map<Class<? extends BaseEntity>,EntityUsage> entityUsageMap = this.checkForeignEntity(TsPosition.class, tsPositionId);
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

		Long rows = this.tsPositionDao.deleteTsPosition(tsPositionId);
		if(rows != 1){
			String error = "删除职务管理出错，数据可能已经被删除";
			throw new BusinessException(error);
		}
	}

	//TODO:---------------验证-------------------

	private void validateFindTsPositions(PageDTO pageDTO) {
	//TODO:请使用下面方法添加数据过滤条件
	//		pageDTO.addFilter("creatorId",this.getLoginUserId());
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsPosition()写法
	}

	private void validateFindTsPositionsWithIdNameByName(String tsPositionName) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsPosition()写法
	}


	private void validateFindAllTsPositionsWithIdName() {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsPosition()写法
	}

	private void validateFindTsPositionsWithIdNameById(Long tsPositionId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsPosition()写法
	}

	private void validateFindTsPosition(Long tsPositionId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsPosition()写法
	}

	private void validateFindTsPositionWithForeignName(Long tsPositionId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsPosition()写法
	}

	private void validateSaveTsPosition(TsPosition tsPosition) {
	//不为空判断
	if (tsPosition.getEid() != null || tsPosition.getCreatorId() != null || tsPosition.getCreateDatetime() != null) {
	throw new BusinessException("非法请求");
	}
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsPosition()写法
	}

	private void validateUpdateTsPosition(TsPosition tsPosition) {
	//不为空判断
	if (tsPosition.getEid() == null) {
	throw new BusinessException("唯一标识不能为空");
	}
	//是否存在判断
	if (this.tsPositionDao.findTsPositionTotalCount(PageDTO.create(TsPosition.FIELD_ID, tsPosition.getEid())) == 0) {
	throw new BusinessException("修改的职务管理 " + tsPosition.getName() + " 不存在，修改失败，请重试或联系管理员");
	}
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsPosition()写法
	}

	private void validateDeleteTsPosition(Long tsPositionId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsPosition()写法
	}

	@Override
	public boolean canDownloadAttachment(String formName, Long id) {
	return true;
	}
}
