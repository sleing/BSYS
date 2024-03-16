package com.mt.tms.service.tsmeetingEntity.impl;

import com.mt.tms.dao.tsmeetingEntity.TsMeetingDao;

import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.common.core.web.base.BaseEntity;
import com.mt.common.core.web.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.mt.tms.entity.tsmeetingEntity.TsMeeting;
import com.mt.tms.service.tsmeetingEntity.TsMeetingService;
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
public class TsMeetingServiceBean extends BaseService implements TsMeetingService {

    private static Logger logger = LogManager.getLogger();

	@Autowired
	private TsMeetingDao tsMeetingDao;

	@Resource
	private RedisTemplate<String, List<TsMeeting>> redisTemplate;

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	/**
	 * 根据分页参数查询会议管理集合
	 *
	 * @param pageDTO 分页条件
	 */
	@Override
	public PageResultDTO findTsMeetings(PageDTO pageDTO){
        pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
		//TODO:请在此校验参数的合法性
		this.validateFindTsMeetings(pageDTO);
		List<TsMeeting> tsMeetingDTOS = this.tsMeetingDao.findTsMeetings(pageDTO);
		Long totalCount = this.tsMeetingDao.findTsMeetingTotalCount(pageDTO);

		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount(totalCount);
		pageResultDTO.setDatas(tsMeetingDTOS);

		return pageResultDTO;
	}

	/**
	 * 查询全部会议管理集合
	 *
	 */
	@Override
	public List<TsMeeting> findAllTsMeetings(){
		return this.tsMeetingDao.findAllTsMeetings();
	}

	/**
	 * 查询所有会议管理集合(只提取ID 和 Name)
	 *
	 */
	@Override
	public List<TsMeeting> findAllTsMeetingsWithIdName(){
		//TODO:请在此校验参数的合法性
		this.validateFindAllTsMeetingsWithIdName();
		return this.tsMeetingDao.findAllTsMeetingsWithIdName();
	}

	/**
	 * 根据名称查询会议管理集合(只提取ID 和 Name)
	 *
	 * @param tsMeetingName 名称
	 */
	@Override
	public List<TsMeeting> findTsMeetingsWithIdNameByName(String tsMeetingName){
		//TODO:请在此校验参数的合法性
		this.validateFindTsMeetingsWithIdNameByName(tsMeetingName);
		//TODO:缓存取对应参数
		Set<String> keys = stringRedisTemplate.keys("searchData:TsMeeting_where_tsMeetingName_" + tsMeetingName);
		List<TsMeeting> tsMeetings = new ArrayList<>();
		if (keys.isEmpty()) {
		tsMeetings = this.tsMeetingDao.findTsMeetingsWithIdNameByName(tsMeetingName);
		redisTemplate.opsForValue().set("searchData:TsMeeting_where_tsMeetingName_" + tsMeetingName, tsMeetings, 30, TimeUnit.DAYS);
		} else {
		tsMeetings = redisTemplate.opsForValue().get("searchData:TsMeeting_where_tsMeetingName_" + tsMeetingName);
		}
		return tsMeetings;
	}

	/**
	 * 根据ID查询指定的会议管理(只提取ID 和 Name)
	 *
	 * @param tsMeetingId Id
	 */
	@Override
	public TsMeeting findTsMeetingsWithIdNameById(Long tsMeetingId){
		//TODO:请在此校验参数的合法性
		this.validateFindTsMeetingsWithIdNameById(tsMeetingId);
		return this.tsMeetingDao.findTsMeetingsWithIdNameById(tsMeetingId);
	}

	/**
	 * 根据ID查询指定的会议管理
	 *
	 * @param tsMeetingId Id
	 */
	@Override
	public TsMeeting findTsMeeting(Long tsMeetingId){
		//TODO:请在此校验参数的合法性
		this.validateFindTsMeeting(tsMeetingId);
		return this.tsMeetingDao.findTsMeeting(tsMeetingId);
	}

	/**
	 * 根据ID查询指定的会议管理(包含外键)
	 *
	 * @param tsMeetingId Id
	 */
	@Override
	public TsMeeting findTsMeetingWithForeignName(Long tsMeetingId){
		//TODO:请在此校验参数的合法性
		this.validateFindTsMeetingWithForeignName(tsMeetingId);
		return this.tsMeetingDao.findTsMeetingWithForeignName(tsMeetingId);
	}

	/**
	 * 新增会议管理
	 *
	 * @param tsMeeting 实体对象
	 */
	@Override
	public TsMeeting saveTsMeeting(TsMeeting tsMeeting){
		//TODO:请在此校验参数的合法性
		this.validateSaveTsMeeting(tsMeeting);
		//TODO:填充公共参数
		this.setSavePulicColumns(tsMeeting);
		Long rows = this.tsMeetingDao.saveTsMeeting(tsMeeting);
		if(rows != 1)
		{
			String error = "新增保存会议管理出错，数据库应该返回1,但返回了 "+rows;
			throw new BusinessException(error);
		}
		return tsMeeting;
	}

	/**
	 * 更新会议管理
	 *
	 * @param tsMeeting 实体对象
	 */
	@Override
	public TsMeeting updateTsMeeting(TsMeeting tsMeeting){
		//TODO:请在此校验参数的合法性
		this.validateUpdateTsMeeting(tsMeeting);
		Long rows = this.tsMeetingDao.updateTsMeeting(tsMeeting);
		if(rows != 1)
		{
			String error = "修改保存会议管理出错，数据库应该返回1,但返回了 "+rows+",数据可能被删除";
			throw new BusinessException(error);
		}
		return tsMeeting;
	}

	/**
	 * 根据ID删除会议管理
	 *
	 * @param tsMeetingId ID
	 */
	@Override
	public void deleteTsMeeting(Long tsMeetingId){
		//TODO:请在此校验参数的合法性
		this.validateDeleteTsMeeting(tsMeetingId);

		Map<Class<? extends BaseEntity>,EntityUsage> entityUsageMap = this.checkForeignEntity(TsMeeting.class, tsMeetingId);
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

		Long rows = this.tsMeetingDao.deleteTsMeeting(tsMeetingId);
		if(rows != 1){
			String error = "删除会议管理出错，数据可能已经被删除";
			throw new BusinessException(error);
		}
	}

	//TODO:---------------验证-------------------

	private void validateFindTsMeetings(PageDTO pageDTO) {
	//TODO:请使用下面方法添加数据过滤条件
	//		pageDTO.addFilter("creatorId",this.getLoginUserId());
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsMeeting()写法
	}

	private void validateFindTsMeetingsWithIdNameByName(String tsMeetingName) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsMeeting()写法
	}


	private void validateFindAllTsMeetingsWithIdName() {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsMeeting()写法
	}

	private void validateFindTsMeetingsWithIdNameById(Long tsMeetingId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsMeeting()写法
	}

	private void validateFindTsMeeting(Long tsMeetingId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsMeeting()写法
	}

	private void validateFindTsMeetingWithForeignName(Long tsMeetingId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsMeeting()写法
	}

	private void validateSaveTsMeeting(TsMeeting tsMeeting) {
	//不为空判断
	if (tsMeeting.getEid() != null || tsMeeting.getCreatorId() != null || tsMeeting.getCreateDatetime() != null) {
	throw new BusinessException("非法请求");
	}
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsMeeting()写法
	}

	private void validateUpdateTsMeeting(TsMeeting tsMeeting) {
	//不为空判断
	if (tsMeeting.getEid() == null) {
	throw new BusinessException("唯一标识不能为空");
	}
	//是否存在判断
	if (this.tsMeetingDao.findTsMeetingTotalCount(PageDTO.create(TsMeeting.FIELD_ID, tsMeeting.getEid())) == 0) {
	throw new BusinessException("修改的会议管理 " + tsMeeting.getName() + " 不存在，修改失败，请重试或联系管理员");
	}
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsMeeting()写法
	}

	private void validateDeleteTsMeeting(Long tsMeetingId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsMeeting()写法
	}

	@Override
	public boolean canDownloadAttachment(String formName, Long id) {
	return true;
	}
}
