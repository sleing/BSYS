package com.mt.tms.service.tsCollegeEntity.impl;

import com.mt.tms.dao.tsCollegeEntity.TsCollegeDao;

import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.common.core.web.base.BaseEntity;
import com.mt.common.core.web.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.mt.tms.entity.tsCollegeEntity.TsCollege;
import com.mt.tms.service.tsCollegeEntity.TsCollegeService;
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
public class TsCollegeServiceBean extends BaseService implements TsCollegeService {

    private static Logger logger = LogManager.getLogger();

	@Autowired
	private TsCollegeDao tsCollegeDao;

	@Resource
	private RedisTemplate<String, List<TsCollege>> redisTemplate;

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	/**
	 * 根据分页参数查询院系信息集合
	 *
	 * @param pageDTO 分页条件
	 */
	@Override
	public PageResultDTO findTsColleges(PageDTO pageDTO){
        pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
		//TODO:请在此校验参数的合法性
		this.validateFindTsColleges(pageDTO);
		List<TsCollege> tsCollegeDTOS = this.tsCollegeDao.findTsColleges(pageDTO);
		Long totalCount = this.tsCollegeDao.findTsCollegeTotalCount(pageDTO);

		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount(totalCount);
		pageResultDTO.setDatas(tsCollegeDTOS);

		return pageResultDTO;
	}

	/**
	 * 查询全部院系信息集合
	 *
	 */
	@Override
	public List<TsCollege> findAllTsColleges(){
		return this.tsCollegeDao.findAllTsColleges();
	}

	/**
	 * 查询所有院系信息集合(只提取ID 和 Name)
	 *
	 */
	@Override
	public List<TsCollege> findAllTsCollegesWithIdName(){
		//TODO:请在此校验参数的合法性
		this.validateFindAllTsCollegesWithIdName();
		return this.tsCollegeDao.findAllTsCollegesWithIdName();
	}

	/**
	 * 根据名称查询院系信息集合(只提取ID 和 Name)
	 *
	 * @param tsCollegeName 名称
	 */
	@Override
	public List<TsCollege> findTsCollegesWithIdNameByName(String tsCollegeName){
		//TODO:请在此校验参数的合法性
		this.validateFindTsCollegesWithIdNameByName(tsCollegeName);
		//TODO:缓存取对应参数
		Set<String> keys = stringRedisTemplate.keys("searchData:TsCollege_where_tsCollegeName_" + tsCollegeName);
		List<TsCollege> tsColleges = new ArrayList<>();
		if (keys.isEmpty()) {
		tsColleges = this.tsCollegeDao.findTsCollegesWithIdNameByName(tsCollegeName);
		redisTemplate.opsForValue().set("searchData:TsCollege_where_tsCollegeName_" + tsCollegeName, tsColleges, 30, TimeUnit.DAYS);
		} else {
		tsColleges = redisTemplate.opsForValue().get("searchData:TsCollege_where_tsCollegeName_" + tsCollegeName);
		}
		return tsColleges;
	}

	/**
	 * 根据ID查询指定的院系信息(只提取ID 和 Name)
	 *
	 * @param tsCollegeId Id
	 */
	@Override
	public TsCollege findTsCollegesWithIdNameById(Long tsCollegeId){
		//TODO:请在此校验参数的合法性
		this.validateFindTsCollegesWithIdNameById(tsCollegeId);
		return this.tsCollegeDao.findTsCollegesWithIdNameById(tsCollegeId);
	}

	/**
	 * 根据ID查询指定的院系信息
	 *
	 * @param tsCollegeId Id
	 */
	@Override
	public TsCollege findTsCollege(Long tsCollegeId){
		//TODO:请在此校验参数的合法性
		this.validateFindTsCollege(tsCollegeId);
		return this.tsCollegeDao.findTsCollege(tsCollegeId);
	}

	/**
	 * 根据ID查询指定的院系信息(包含外键)
	 *
	 * @param tsCollegeId Id
	 */
	@Override
	public TsCollege findTsCollegeWithForeignName(Long tsCollegeId){
		//TODO:请在此校验参数的合法性
		this.validateFindTsCollegeWithForeignName(tsCollegeId);
		return this.tsCollegeDao.findTsCollegeWithForeignName(tsCollegeId);
	}

	/**
	 * 新增院系信息
	 *
	 * @param tsCollege 实体对象
	 */
	@Override
	public TsCollege saveTsCollege(TsCollege tsCollege){
		//TODO:请在此校验参数的合法性
		this.validateSaveTsCollege(tsCollege);
		//TODO:填充公共参数
		this.setSavePulicColumns(tsCollege);
		Long rows = this.tsCollegeDao.saveTsCollege(tsCollege);
		if(rows != 1)
		{
			String error = "新增保存院系信息出错，数据库应该返回1,但返回了 "+rows;
			throw new BusinessException(error);
		}
		return tsCollege;
	}

	/**
	 * 更新院系信息
	 *
	 * @param tsCollege 实体对象
	 */
	@Override
	public TsCollege updateTsCollege(TsCollege tsCollege){
		//TODO:请在此校验参数的合法性
		this.validateUpdateTsCollege(tsCollege);
		Long rows = this.tsCollegeDao.updateTsCollege(tsCollege);
		if(rows != 1)
		{
			String error = "修改保存院系信息出错，数据库应该返回1,但返回了 "+rows+",数据可能被删除";
			throw new BusinessException(error);
		}
		return tsCollege;
	}

	/**
	 * 根据ID删除院系信息
	 *
	 * @param tsCollegeId ID
	 */
	@Override
	public void deleteTsCollege(Long tsCollegeId){
		//TODO:请在此校验参数的合法性
		this.validateDeleteTsCollege(tsCollegeId);

		Map<Class<? extends BaseEntity>,EntityUsage> entityUsageMap = this.checkForeignEntity(TsCollege.class, tsCollegeId);
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

		Long rows = this.tsCollegeDao.deleteTsCollege(tsCollegeId);
		if(rows != 1){
			String error = "删除院系信息出错，数据可能已经被删除";
			throw new BusinessException(error);
		}
	}

	//TODO:---------------验证-------------------

	private void validateFindTsColleges(PageDTO pageDTO) {
	//TODO:请使用下面方法添加数据过滤条件
	//		pageDTO.addFilter("creatorId",this.getLoginUserId());
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsCollege()写法
	}

	private void validateFindTsCollegesWithIdNameByName(String tsCollegeName) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsCollege()写法
	}


	private void validateFindAllTsCollegesWithIdName() {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsCollege()写法
	}

	private void validateFindTsCollegesWithIdNameById(Long tsCollegeId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsCollege()写法
	}

	private void validateFindTsCollege(Long tsCollegeId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsCollege()写法
	}

	private void validateFindTsCollegeWithForeignName(Long tsCollegeId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsCollege()写法
	}

	private void validateSaveTsCollege(TsCollege tsCollege) {
	//不为空判断
	if (tsCollege.getEid() != null || tsCollege.getCreatorId() != null || tsCollege.getCreateDatetime() != null) {
	throw new BusinessException("非法请求");
	}
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsCollege()写法
	}

	private void validateUpdateTsCollege(TsCollege tsCollege) {
	//不为空判断
	if (tsCollege.getEid() == null) {
	throw new BusinessException("唯一标识不能为空");
	}
	//是否存在判断
	if (this.tsCollegeDao.findTsCollegeTotalCount(PageDTO.create(TsCollege.FIELD_ID, tsCollege.getEid())) == 0) {
	throw new BusinessException("修改的院系信息 " + tsCollege.getName() + " 不存在，修改失败，请重试或联系管理员");
	}
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsCollege()写法
	}

	private void validateDeleteTsCollege(Long tsCollegeId) {
	//TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTsCollege()写法
	}

	@Override
	public boolean canDownloadAttachment(String formName, Long id) {
	return true;
	}
}
