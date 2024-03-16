package com.mt.tms.service.tsworklaceEntity.impl;

import com.mt.tms.dao.tsworklaceEntity.TsWorkPlaceDao;

import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.common.core.web.base.BaseEntity;
import com.mt.common.core.web.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.mt.tms.entity.tsworklaceEntity.TsWorkPlace;
import com.mt.tms.service.tsworklaceEntity.TsWorkPlaceService;
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
public class TsWorkPlaceServiceBean extends BaseService implements TsWorkPlaceService {

    private static Logger logger = LogManager.getLogger();

	@Autowired
	private TsWorkPlaceDao tsWorkPlaceDao;

	@Resource
	private RedisTemplate<String, List<TsWorkPlace>> redisTemplate;

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	/**
	 * 根据分页参数查询办公地点管理集合
	 *
	 * @param pageDTO 分页条件
	 */
	@Override
	public PageResultDTO findTsWorkPlaces(PageDTO pageDTO){
        pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
		//TODO:请在此校验参数的合法性
		this.validateFindTsWorkPlaces(pageDTO);
		List<TsWorkPlace> tsWorkPlaceDTOS = this.tsWorkPlaceDao.findTsWorkPlaces(pageDTO);
		Long totalCount = this.tsWorkPlaceDao.findTsWorkPlaceTotalCount(pageDTO);

		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount(totalCount);
		pageResultDTO.setDatas(tsWorkPlaceDTOS);

		return pageResultDTO;
	}

	/**
	 * 查询全部办公地点管理集合
	 *
	 */
	@Override
	public List<TsWorkPlace> findAllTsWorkPlaces(){
		return this.tsWorkPlaceDao.findAllTsWorkPlaces();
	}

	/**
	 * 查询所有办公地点管理集合(只提取ID 和 Name)
	 *
	 */
	@Override
	public List<TsWorkPlace> findAllTsWorkPlacesWithIdName(){
		//TODO:请在此校验参数的合法性
		this.validateFindAllTsWorkPlacesWithIdName();
		return this.tsWorkPlaceDao.findAllTsWorkPlacesWithIdName();
	}

	/**
	 * 根据名称查询办公地点管理集合(只提取ID 和 Name)
	 *
	 * @param tsWorkPlaceName 名称
	 */
	@Override
	public List<TsWorkPlace> findTsWorkPlacesWithIdNameByName(String tsWorkPlaceName){
		//TODO:请在此校验参数的合法性
		this.validateFindTsWorkPlacesWithIdNameByName(tsWorkPlaceName);
		//TODO:缓存取对应参数
		Set<String> keys = stringRedisTemplate.keys("searchData:TsWorkPlace_where_tsWorkPlaceName_" + tsWorkPlaceName);
		List<TsWorkPlace> tsWorkPlaces = new ArrayList<>();
		if (keys.isEmpty()) {
		tsWorkPlaces = this.tsWorkPlaceDao.findTsWorkPlacesWithIdNameByName(tsWorkPlaceName);
		redisTemplate.opsForValue().set("searchData:TsWorkPlace_where_tsWorkPlaceName_" + tsWorkPlaceName, tsWorkPlaces, 30, TimeUnit.DAYS);
		} else {
		tsWorkPlaces = redisTemplate.opsForValue().get("searchData:TsWorkPlace_where_tsWorkPlaceName_" + tsWorkPlaceName);
		}
		return tsWorkPlaces;
	}

	/**
	 * 根据ID查询指定的办公地点管理(只提取ID 和 Name)
	 *
	 * @param tsWorkPlaceId Id
	 */
	@Override
	public TsWorkPlace findTsWorkPlacesWithIdNameById(Long tsWorkPlaceId){
		//TODO:请在此校验参数的合法性
		this.validateFindTsWorkPlacesWithIdNameById(tsWorkPlaceId);
		return this.tsWorkPlaceDao.findTsWorkPlacesWithIdNameById(tsWorkPlaceId);
	}

	/**
	 * 根据ID查询指定的办公地点管理
	 *
	 * @param tsWorkPlaceId Id
	 */
	@Override
	public TsWorkPlace findTsWorkPlace(Long tsWorkPlaceId){
		//TODO:请在此校验参数的合法性
		this.validateFindTsWorkPlace(tsWorkPlaceId);
		return this.tsWorkPlaceDao.findTsWorkPlace(tsWorkPlaceId);
	}

	/**
	 * 根据ID查询指定的办公地点管理(包含外键)
	 *
	 * @param tsWorkPlaceId Id
	 */
	@Override
	public TsWorkPlace findTsWorkPlaceWithForeignName(Long tsWorkPlaceId){
		//TODO:请在此校验参数的合法性
		this.validateFindTsWorkPlaceWithForeignName(tsWorkPlaceId);
		return this.tsWorkPlaceDao.findTsWorkPlaceWithForeignName(tsWorkPlaceId);
	}

	/**
	 * 新增办公地点管理
	 *
	 * @param tsWorkPlace 实体对象
	 */
	@Override
	public TsWorkPlace saveTsWorkPlace(TsWorkPlace tsWorkPlace){
		//TODO:请在此校验参数的合法性
		this.validateSaveTsWorkPlace(tsWorkPlace);
		//TODO:填充公共参数
		this.setSavePulicColumns(tsWorkPlace);
		Long rows = this.tsWorkPlaceDao.saveTsWorkPlace(tsWorkPlace);
		if(rows != 1)
		{
			String error = "新增保存办公地点管理出错，数据库应该返回1,但返回了 "+rows;
			throw new BusinessException(error);
		}
		return tsWorkPlace;
	}

	/**
	 * 更新办公地点管理
	 *
	 * @param tsWorkPlace 实体对象
	 */
	@Override
	public TsWorkPlace updateTsWorkPlace(TsWorkPlace tsWorkPlace){
		//TODO:请在此校验参数的合法性
		this.validateUpdateTsWorkPlace(tsWorkPlace);
		Long rows = this.tsWorkPlaceDao.updateTsWorkPlace(tsWorkPlace);
		if(rows != 1)
		{
			String error = "修改保存办公地点管理出错，数据库应该返回1,但返回了 "+rows+",数据可能被删除";
			throw new BusinessException(error);
		}
		return tsWorkPlace;
	}

	/**
	 * 根据ID删除办公地点管理
	 *
	 * @param tsWorkPlaceId ID
	 */
	@Override
	public void deleteTsWorkPlace(Long tsWorkPlaceId){
		//TODO:请在此校验参数的合法性
		this.validateDeleteTsWorkPlace(tsWorkPlaceId);

		Map<Class<? extends BaseEntity>,EntityUsage> entityUsageMap = this.checkForeignEntity(TsWorkPlace.class, tsWorkPlaceId);
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

		Long rows = this.tsWorkPlaceDao.deleteTsWorkPlace(tsWorkPlaceId);
		if(rows != 1){
			String error = "删除办公地点管理出错，数据可能已经被删除";
			throw new BusinessException(error);
		}
	}

	//TODO:---------------验证-------------------

	private void validateFindTsWorkPlaces(PageDTO pageDTO) {
	//TODO:请使用下面方法添加数据过滤条件
	//		pageDTO.addFilter("creatorId",this.getLoginUserId());
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsWorkPlace()写法
	}

	private void validateFindTsWorkPlacesWithIdNameByName(String tsWorkPlaceName) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsWorkPlace()写法
	}


	private void validateFindAllTsWorkPlacesWithIdName() {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsWorkPlace()写法
	}

	private void validateFindTsWorkPlacesWithIdNameById(Long tsWorkPlaceId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsWorkPlace()写法
	}

	private void validateFindTsWorkPlace(Long tsWorkPlaceId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsWorkPlace()写法
	}

	private void validateFindTsWorkPlaceWithForeignName(Long tsWorkPlaceId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsWorkPlace()写法
	}

	private void validateSaveTsWorkPlace(TsWorkPlace tsWorkPlace) {
	//不为空判断
	if (tsWorkPlace.getEid() != null || tsWorkPlace.getCreatorId() != null || tsWorkPlace.getCreateDatetime() != null) {
	throw new BusinessException("非法请求");
	}
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsWorkPlace()写法
	}

	private void validateUpdateTsWorkPlace(TsWorkPlace tsWorkPlace) {
	//不为空判断
	if (tsWorkPlace.getEid() == null) {
	throw new BusinessException("唯一标识不能为空");
	}
	//是否存在判断
	if (this.tsWorkPlaceDao.findTsWorkPlaceTotalCount(PageDTO.create(TsWorkPlace.FIELD_ID, tsWorkPlace.getEid())) == 0) {
	throw new BusinessException("修改的办公地点管理 " + tsWorkPlace.getName() + " 不存在，修改失败，请重试或联系管理员");
	}
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsWorkPlace()写法
	}

	private void validateDeleteTsWorkPlace(Long tsWorkPlaceId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsWorkPlace()写法
	}

	@Override
	public boolean canDownloadAttachment(String formName, Long id) {
	return true;
	}
}
