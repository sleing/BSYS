package com.mt.tms.service.tsonDutyEntity.impl;

import com.mt.tms.dao.tsonDutyEntity.TsOnDutyDao;

import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.common.core.web.base.BaseEntity;
import com.mt.common.core.web.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.mt.tms.entity.tsonDutyEntity.TsOnDuty;
import com.mt.tms.service.tsonDutyEntity.TsOnDutyService;
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
public class TsOnDutyServiceBean extends BaseService implements TsOnDutyService {

    private static Logger logger = LogManager.getLogger();

	@Autowired
	private TsOnDutyDao tsOnDutyDao;

	@Resource
	private RedisTemplate<String, List<TsOnDuty>> redisTemplate;

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	/**
	 * 根据分页参数查询值班管理集合
	 *
	 * @param pageDTO 分页条件
	 */
	@Override
	public PageResultDTO findTsOnDutys(PageDTO pageDTO){
        pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
		//TODO:请在此校验参数的合法性
		this.validateFindTsOnDutys(pageDTO);
		List<TsOnDuty> tsOnDutyDTOS = this.tsOnDutyDao.findTsOnDutys(pageDTO);
		Long totalCount = this.tsOnDutyDao.findTsOnDutyTotalCount(pageDTO);

		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount(totalCount);
		pageResultDTO.setDatas(tsOnDutyDTOS);

		return pageResultDTO;
	}

	/**
	 * 查询全部值班管理集合
	 *
	 */
	@Override
	public List<TsOnDuty> findAllTsOnDutys(){
		return this.tsOnDutyDao.findAllTsOnDutys();
	}

	/**
	 * 查询所有值班管理集合(只提取ID 和 Name)
	 *
	 */
	@Override
	public List<TsOnDuty> findAllTsOnDutysWithIdName(){
		//TODO:请在此校验参数的合法性
		this.validateFindAllTsOnDutysWithIdName();
		return this.tsOnDutyDao.findAllTsOnDutysWithIdName();
	}

	/**
	 * 根据名称查询值班管理集合(只提取ID 和 Name)
	 *
	 * @param tsOnDutyName 名称
	 */
	@Override
	public List<TsOnDuty> findTsOnDutysWithIdNameByName(String tsOnDutyName){
		//TODO:请在此校验参数的合法性
		this.validateFindTsOnDutysWithIdNameByName(tsOnDutyName);
		//TODO:缓存取对应参数
		Set<String> keys = stringRedisTemplate.keys("searchData:TsOnDuty_where_tsOnDutyName_" + tsOnDutyName);
		List<TsOnDuty> tsOnDutys = new ArrayList<>();
		if (keys.isEmpty()) {
		tsOnDutys = this.tsOnDutyDao.findTsOnDutysWithIdNameByName(tsOnDutyName);
		redisTemplate.opsForValue().set("searchData:TsOnDuty_where_tsOnDutyName_" + tsOnDutyName, tsOnDutys, 30, TimeUnit.DAYS);
		} else {
		tsOnDutys = redisTemplate.opsForValue().get("searchData:TsOnDuty_where_tsOnDutyName_" + tsOnDutyName);
		}
		return tsOnDutys;
	}

	/**
	 * 根据ID查询指定的值班管理(只提取ID 和 Name)
	 *
	 * @param tsOnDutyId Id
	 */
	@Override
	public TsOnDuty findTsOnDutysWithIdNameById(Long tsOnDutyId){
		//TODO:请在此校验参数的合法性
		this.validateFindTsOnDutysWithIdNameById(tsOnDutyId);
		return this.tsOnDutyDao.findTsOnDutysWithIdNameById(tsOnDutyId);
	}

	/**
	 * 根据ID查询指定的值班管理
	 *
	 * @param tsOnDutyId Id
	 */
	@Override
	public TsOnDuty findTsOnDuty(Long tsOnDutyId){
		//TODO:请在此校验参数的合法性
		this.validateFindTsOnDuty(tsOnDutyId);
		return this.tsOnDutyDao.findTsOnDuty(tsOnDutyId);
	}

	/**
	 * 根据ID查询指定的值班管理(包含外键)
	 *
	 * @param tsOnDutyId Id
	 */
	@Override
	public TsOnDuty findTsOnDutyWithForeignName(Long tsOnDutyId){
		//TODO:请在此校验参数的合法性
		this.validateFindTsOnDutyWithForeignName(tsOnDutyId);
		return this.tsOnDutyDao.findTsOnDutyWithForeignName(tsOnDutyId);
	}

	/**
	 * 新增值班管理
	 *
	 * @param tsOnDuty 实体对象
	 */
	@Override
	public TsOnDuty saveTsOnDuty(TsOnDuty tsOnDuty){
		//TODO:请在此校验参数的合法性
		this.validateSaveTsOnDuty(tsOnDuty);
		//TODO:填充公共参数
		this.setSavePulicColumns(tsOnDuty);
		Long rows = this.tsOnDutyDao.saveTsOnDuty(tsOnDuty);
		if(rows != 1)
		{
			String error = "新增保存值班管理出错，数据库应该返回1,但返回了 "+rows;
			throw new BusinessException(error);
		}
		return tsOnDuty;
	}

	/**
	 * 更新值班管理
	 *
	 * @param tsOnDuty 实体对象
	 */
	@Override
	public TsOnDuty updateTsOnDuty(TsOnDuty tsOnDuty){
		//TODO:请在此校验参数的合法性
		this.validateUpdateTsOnDuty(tsOnDuty);
		Long rows = this.tsOnDutyDao.updateTsOnDuty(tsOnDuty);
		if(rows != 1)
		{
			String error = "修改保存值班管理出错，数据库应该返回1,但返回了 "+rows+",数据可能被删除";
			throw new BusinessException(error);
		}
		return tsOnDuty;
	}

	/**
	 * 根据ID删除值班管理
	 *
	 * @param tsOnDutyId ID
	 */
	@Override
	public void deleteTsOnDuty(Long tsOnDutyId){
		//TODO:请在此校验参数的合法性
		this.validateDeleteTsOnDuty(tsOnDutyId);

		Map<Class<? extends BaseEntity>,EntityUsage> entityUsageMap = this.checkForeignEntity(TsOnDuty.class, tsOnDutyId);
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

		Long rows = this.tsOnDutyDao.deleteTsOnDuty(tsOnDutyId);
		if(rows != 1){
			String error = "删除值班管理出错，数据可能已经被删除";
			throw new BusinessException(error);
		}
	}

	//TODO:---------------验证-------------------

	private void validateFindTsOnDutys(PageDTO pageDTO) {
	//TODO:请使用下面方法添加数据过滤条件
	//		pageDTO.addFilter("creatorId",this.getLoginUserId());
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsOnDuty()写法
	}

	private void validateFindTsOnDutysWithIdNameByName(String tsOnDutyName) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsOnDuty()写法
	}


	private void validateFindAllTsOnDutysWithIdName() {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsOnDuty()写法
	}

	private void validateFindTsOnDutysWithIdNameById(Long tsOnDutyId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsOnDuty()写法
	}

	private void validateFindTsOnDuty(Long tsOnDutyId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsOnDuty()写法
	}

	private void validateFindTsOnDutyWithForeignName(Long tsOnDutyId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsOnDuty()写法
	}

	private void validateSaveTsOnDuty(TsOnDuty tsOnDuty) {
	//不为空判断
	if (tsOnDuty.getEid() != null || tsOnDuty.getCreatorId() != null || tsOnDuty.getCreateDatetime() != null) {
	throw new BusinessException("非法请求");
	}
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsOnDuty()写法
	}

	private void validateUpdateTsOnDuty(TsOnDuty tsOnDuty) {
	//不为空判断
	if (tsOnDuty.getEid() == null) {
	throw new BusinessException("唯一标识不能为空");
	}
	//是否存在判断
	if (this.tsOnDutyDao.findTsOnDutyTotalCount(PageDTO.create(TsOnDuty.FIELD_ID, tsOnDuty.getEid())) == 0) {
	throw new BusinessException("修改的值班管理 " + tsOnDuty.getName() + " 不存在，修改失败，请重试或联系管理员");
	}
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsOnDuty()写法
	}

	private void validateDeleteTsOnDuty(Long tsOnDutyId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsOnDuty()写法
	}

	@Override
	public boolean canDownloadAttachment(String formName, Long id) {
	return true;
	}
}
