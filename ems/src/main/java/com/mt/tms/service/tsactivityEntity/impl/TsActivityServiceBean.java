package com.mt.tms.service.tsactivityEntity.impl;

import com.mt.tms.dao.tsactivityEntity.TsActivityDao;

import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.common.core.web.base.BaseEntity;
import com.mt.common.core.web.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.mt.tms.entity.tsactivityEntity.TsActivity;
import com.mt.tms.service.tsactivityEntity.TsActivityService;
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
public class TsActivityServiceBean extends BaseService implements TsActivityService {

    private static Logger logger = LogManager.getLogger();

	@Autowired
	private TsActivityDao tsActivityDao;

	@Resource
	private RedisTemplate<String, List<TsActivity>> redisTemplate;

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	/**
	 * 根据分页参数查询活动中心集合
	 *
	 * @param pageDTO 分页条件
	 */
	@Override
	public PageResultDTO findTsActivitys(PageDTO pageDTO){
        pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
		//TODO:请在此校验参数的合法性
		this.validateFindTsActivitys(pageDTO);
		List<TsActivity> tsActivityDTOS = this.tsActivityDao.findTsActivitys(pageDTO);
		Long totalCount = this.tsActivityDao.findTsActivityTotalCount(pageDTO);

		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount(totalCount);
		pageResultDTO.setDatas(tsActivityDTOS);

		return pageResultDTO;
	}

	/**
	 * 查询全部活动中心集合
	 *
	 */
	@Override
	public List<TsActivity> findAllTsActivitys(){
		return this.tsActivityDao.findAllTsActivitys();
	}

	/**
	 * 查询所有活动中心集合(只提取ID 和 Name)
	 *
	 */
	@Override
	public List<TsActivity> findAllTsActivitysWithIdName(){
		//TODO:请在此校验参数的合法性
		this.validateFindAllTsActivitysWithIdName();
		return this.tsActivityDao.findAllTsActivitysWithIdName();
	}

	/**
	 * 根据名称查询活动中心集合(只提取ID 和 Name)
	 *
	 * @param tsActivityName 名称
	 */
	@Override
	public List<TsActivity> findTsActivitysWithIdNameByName(String tsActivityName){
		//TODO:请在此校验参数的合法性
		this.validateFindTsActivitysWithIdNameByName(tsActivityName);
		//TODO:缓存取对应参数
		Set<String> keys = stringRedisTemplate.keys("searchData:TsActivity_where_tsActivityName_" + tsActivityName);
		List<TsActivity> tsActivitys = new ArrayList<>();
		if (keys.isEmpty()) {
		tsActivitys = this.tsActivityDao.findTsActivitysWithIdNameByName(tsActivityName);
		redisTemplate.opsForValue().set("searchData:TsActivity_where_tsActivityName_" + tsActivityName, tsActivitys, 30, TimeUnit.DAYS);
		} else {
		tsActivitys = redisTemplate.opsForValue().get("searchData:TsActivity_where_tsActivityName_" + tsActivityName);
		}
		return tsActivitys;
	}

	/**
	 * 根据ID查询指定的活动中心(只提取ID 和 Name)
	 *
	 * @param tsActivityId Id
	 */
	@Override
	public TsActivity findTsActivitysWithIdNameById(Long tsActivityId){
		//TODO:请在此校验参数的合法性
		this.validateFindTsActivitysWithIdNameById(tsActivityId);
		return this.tsActivityDao.findTsActivitysWithIdNameById(tsActivityId);
	}

	/**
	 * 根据ID查询指定的活动中心
	 *
	 * @param tsActivityId Id
	 */
	@Override
	public TsActivity findTsActivity(Long tsActivityId){
		//TODO:请在此校验参数的合法性
		this.validateFindTsActivity(tsActivityId);
		return this.tsActivityDao.findTsActivity(tsActivityId);
	}

	/**
	 * 根据ID查询指定的活动中心(包含外键)
	 *
	 * @param tsActivityId Id
	 */
	@Override
	public TsActivity findTsActivityWithForeignName(Long tsActivityId){
		//TODO:请在此校验参数的合法性
		this.validateFindTsActivityWithForeignName(tsActivityId);
		return this.tsActivityDao.findTsActivityWithForeignName(tsActivityId);
	}

	/**
	 * 新增活动中心
	 *
	 * @param tsActivity 实体对象
	 */
	@Override
	public TsActivity saveTsActivity(TsActivity tsActivity){
		//TODO:请在此校验参数的合法性
		this.validateSaveTsActivity(tsActivity);
		//TODO:填充公共参数
		this.setSavePulicColumns(tsActivity);
		Long rows = this.tsActivityDao.saveTsActivity(tsActivity);
		if(rows != 1)
		{
			String error = "新增保存活动中心出错，数据库应该返回1,但返回了 "+rows;
			throw new BusinessException(error);
		}
		return tsActivity;
	}

	/**
	 * 更新活动中心
	 *
	 * @param tsActivity 实体对象
	 */
	@Override
	public TsActivity updateTsActivity(TsActivity tsActivity){
		//TODO:请在此校验参数的合法性
		this.validateUpdateTsActivity(tsActivity);
		Long rows = this.tsActivityDao.updateTsActivity(tsActivity);
		if(rows != 1)
		{
			String error = "修改保存活动中心出错，数据库应该返回1,但返回了 "+rows+",数据可能被删除";
			throw new BusinessException(error);
		}
		return tsActivity;
	}

	/**
	 * 根据ID删除活动中心
	 *
	 * @param tsActivityId ID
	 */
	@Override
	public void deleteTsActivity(Long tsActivityId){
		//TODO:请在此校验参数的合法性
		this.validateDeleteTsActivity(tsActivityId);

		Map<Class<? extends BaseEntity>,EntityUsage> entityUsageMap = this.checkForeignEntity(TsActivity.class, tsActivityId);
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

		Long rows = this.tsActivityDao.deleteTsActivity(tsActivityId);
		if(rows != 1){
			String error = "删除活动中心出错，数据可能已经被删除";
			throw new BusinessException(error);
		}
	}

	//TODO:---------------验证-------------------

	private void validateFindTsActivitys(PageDTO pageDTO) {
	//TODO:请使用下面方法添加数据过滤条件
	//		pageDTO.addFilter("creatorId",this.getLoginUserId());
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsActivity()写法
	}

	private void validateFindTsActivitysWithIdNameByName(String tsActivityName) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsActivity()写法
	}


	private void validateFindAllTsActivitysWithIdName() {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsActivity()写法
	}

	private void validateFindTsActivitysWithIdNameById(Long tsActivityId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsActivity()写法
	}

	private void validateFindTsActivity(Long tsActivityId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsActivity()写法
	}

	private void validateFindTsActivityWithForeignName(Long tsActivityId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsActivity()写法
	}

	private void validateSaveTsActivity(TsActivity tsActivity) {
	//不为空判断
	if (tsActivity.getEid() != null || tsActivity.getCreatorId() != null || tsActivity.getCreateDatetime() != null) {
	throw new BusinessException("非法请求");
	}
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsActivity()写法
	}

	private void validateUpdateTsActivity(TsActivity tsActivity) {
	//不为空判断
	if (tsActivity.getEid() == null) {
	throw new BusinessException("唯一标识不能为空");
	}
	//是否存在判断
	if (this.tsActivityDao.findTsActivityTotalCount(PageDTO.create(TsActivity.FIELD_ID, tsActivity.getEid())) == 0) {
	throw new BusinessException("修改的活动中心 " + tsActivity.getName() + " 不存在，修改失败，请重试或联系管理员");
	}
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsActivity()写法
	}

	private void validateDeleteTsActivity(Long tsActivityId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsActivity()写法
	}

	@Override
	public boolean canDownloadAttachment(String formName, Long id) {
	return true;
	}
}
