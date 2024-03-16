package com.mt.ams.service.awardeeEntity.impl;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.mt.ams.dao.awardEntity.AwardDao;
import com.mt.ams.dao.awardeeEntity.AwardeeDao;
import com.mt.ams.dao.competitionEntity.CompetitionDao;
import com.mt.ams.dao.studentEntity.StudentInfoDao;
import com.mt.ams.dao.teacherEntity.TeacherInfoDao;
import com.mt.ams.entity.awardEntity.Award;
import com.mt.ams.entity.awardeeEntity.Awardee;
import com.mt.ams.service.awardeeEntity.AwardeeService;
import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.web.BaseService;
import com.mt.common.core.web.base.BaseEntity;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.common.system.mapper.UserMapper;
import com.mt.common.system.mapper.UserRoleMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Transactional
public class AwardeeServiceBean extends BaseService implements AwardeeService {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private AwardeeDao awardeeDao;

    @Autowired
    private StudentInfoDao studentInfoDao;

    @Autowired
    private AwardDao awardDao;

    @Autowired
    private CompetitionDao competitionDao;

    @Autowired
    private TeacherInfoDao teacherInfoDao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Resource
    private RedisTemplate<String, List<Awardee>> redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 根据分页参数查询获奖学生信息集合
     *
     * @param pageDTO 分页条件
     */
    @Override
    public PageResultDTO findAwardees(PageDTO pageDTO) {
        pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
        //TODO:请在此校验参数的合法性
        this.validateFindAwardees(pageDTO);

        List<Awardee> awardeeDTOS = this.awardeeDao.findAwardees(pageDTO);

        Long totalCount = this.awardeeDao.findAwardeeTotalCount(pageDTO);
        PageResultDTO pageResultDTO = new PageResultDTO();

        Awardee awardee = new Awardee();
        //设置时间格式
        String s = "yyyy-MM-dd";
        SimpleDateFormat f = new SimpleDateFormat(s);

        for (Awardee awardeeDTO : awardeeDTOS) {
            //学生名字  根据学号查询姓名
            String name = this.studentInfoDao.findStudentInfoNumByUniqueId(awardeeDTO.getStudentId());
            awardeeDTO.setStudentName(name);
            //比赛信息
            Award award = this.awardDao.findAward(awardeeDTO.getAwardId());
            //审核状态 审核通过
            if (award.getAuditStatus().equals("审核通过")) {
                awardeeDTO.setName("审核通过");
            } else if (award.getAuditStatus().equals("未审核")) {
                awardeeDTO.setName("未审核");
            } else if (award.getAuditStatus().equals("驳回")) {
                awardeeDTO.setName("驳回");
            }

            if (award.getAwardLevel() == null) {
                award.setAwardLevel("");
            }
            if (award.getAwardGrade() == null) {
                award.setAwardGrade("");
            }
            //设置获奖信息
            String competitionName = this.competitionDao.findCompetition(award.getCompetitionNameId()).getName();
            String str;
            if (award.getAwardGrade().equals("")) {
                str = competitionName + " / " + award.getAwardLevel();

            } else {
                str = competitionName + " / " + award.getAwardLevel() + " / " + award.getAwardGrade();
            }
            //获奖时间
            if (award.getAwardDate() != null) {
                Date d = award.getAwardDate();
                awardeeDTO.setStatus(f.format(d));  //状态存时间
            }
            awardeeDTO.setAwardName(str);

        }

        pageResultDTO.setTotalCount(totalCount);
        pageResultDTO.setDatas(awardeeDTOS);
        return pageResultDTO;
    }

    /**
     * 查询全部获奖学生信息集合
     */
    @Override
    public List<Awardee> findAllAwardees() {
        return this.awardeeDao.findAllAwardees();
    }

    /**
     * 查询所有获奖学生信息集合(只提取ID 和 Name)
     */
    @Override
    public List<Awardee> findAllAwardeesWithIdName() {
        //TODO:请在此校验参数的合法性
        this.validateFindAllAwardeesWithIdName();
        return this.awardeeDao.findAllAwardeesWithIdName();
    }


    /**
     * 根据名称查询获奖学生信息集合(只提取ID 和 Name)
     *
     * @param awardeeName 名称
     */
    @Override
    public List<Awardee> findAwardeesWithIdNameByName(String awardeeName) {
        //TODO:请在此校验参数的合法性
        this.validateFindAwardeesWithIdNameByName(awardeeName);
        //TODO:缓存取对应参数
        Set<String> keys = stringRedisTemplate.keys("searchData:Awardee_where_awardeeName_" + awardeeName);
        List<Awardee> awardees = new ArrayList<>();
        if (keys.isEmpty()) {
            awardees = this.awardeeDao.findAwardeesWithIdNameByName(awardeeName);
            redisTemplate.opsForValue().set("searchData:Awardee_where_awardeeName_" + awardeeName, awardees, 30, TimeUnit.DAYS);
        } else {
            awardees = redisTemplate.opsForValue().get("searchData:Awardee_where_awardeeName_" + awardeeName);
        }
        return awardees;
    }

    /**
     * 根据ID查询指定的获奖学生信息(只提取ID 和 Name)
     *
     * @param awardeeId Id
     */
    @Override
    public Awardee findAwardeesWithIdNameById(Long awardeeId) {
        //TODO:请在此校验参数的合法性
        this.validateFindAwardeesWithIdNameById(awardeeId);
        return this.awardeeDao.findAwardeesWithIdNameById(awardeeId);
    }

    /**
     * 根据ID查询指定的获奖学生信息
     *
     * @param awardeeId Id
     */
    @Override
    public Awardee findAwardee(Long awardeeId) {
        //TODO:请在此校验参数的合法性
        this.validateFindAwardee(awardeeId);
        return this.awardeeDao.findAwardee(awardeeId);
    }

    /**
     * 根据ID查询指定的获奖学生信息(包含外键)
     *
     * @param awardeeId Id
     */
    @Override
    public Awardee findAwardeeWithForeignName(Long awardeeId) {
        //TODO:请在此校验参数的合法性
        this.validateFindAwardeeWithForeignName(awardeeId);
        return this.awardeeDao.findAwardeeWithForeignName(awardeeId);
    }

    /**
     * 新增获奖学生信息
     *
     * @param awardee 实体对象
     */
    @Override
    public Awardee saveAwardee(Awardee awardee) {
        //TODO:请在此校验参数的合法性
        this.validateSaveAwardee(awardee);
        //TODO:填充公共参数
        this.setSavePulicColumns(awardee);
        Long rows = this.awardeeDao.saveAwardee(awardee);
        if (rows != 1) {
            String error = "新增保存获奖学生信息出错，数据库应该返回1,但返回了 " + rows;
            throw new BusinessException(error);
        }
        return awardee;
    }

    /**
     * 更新获奖学生信息
     *
     * @param awardee 实体对象
     */
    @Override
    public Awardee updateAwardee(Awardee awardee) {
        //TODO:请在此校验参数的合法性
        this.validateUpdateAwardee(awardee);
        Long rows = this.awardeeDao.updateAwardee(awardee);
        if (rows != 1) {
            String error = "修改保存获奖学生信息出错，数据库应该返回1,但返回了 " + rows + ",数据可能被删除";
            throw new BusinessException(error);
        }
        return awardee;
    }

    /**
     * 根据ID删除获奖学生信息
     *
     * @param awardeeId ID
     */
    @Override
    public void deleteAwardee(Long awardeeId) {
        //TODO:请在此校验参数的合法性
        this.validateDeleteAwardee(awardeeId);

        Map<Class<? extends BaseEntity>, EntityUsage> entityUsageMap = this.checkForeignEntity(Awardee.class, awardeeId);
        if (entityUsageMap != null && entityUsageMap.size() > 0) {
            StringBuilder errors = new StringBuilder();
            errors.append("计划删除的数据正在被以下数引用\n");
            for (EntityUsage entityUsage : entityUsageMap.values()) {
                errors.append("\t").append(entityUsage.getEntityLabel()).append("\n");
                for (Map.Entry<Long, String> entry : entityUsage.getUsageIdNames().entrySet()) {
                    errors.append("\t\t").append(entry.getKey()).append("\t").append(entry.getValue()).append("\n");
                }
            }
            errors.append("，不能删除，请检查处理后再删除");
            throw new BusinessException(errors.toString());
        }

        Long rows = this.awardeeDao.deleteAwardee(awardeeId);
        if (rows != 1) {
            String error = "删除获奖学生信息出错，数据可能已经被删除";
            throw new BusinessException(error);
        }
    }

    //by myself

    /**
     * 根据awardId删除获奖学生信息
     *
     * @param awardId ID
     */
    @Override
    public void deleteAwardeeByAwardId(@RequestParam Long awardId) {
        Long rows = this.awardeeDao.deleteAwardeeByAwardId(awardId);
        //more than one awardee are going to be delete
//		if(rows != 1){
//			String error = "删除获奖学生信息出错，数据可能已经被删除";
//			throw new BusinessException(error);
//		}
    }

    /**
     * 根据awardId获得获奖学生信息
     *
     * @param awardId ID
     */
    public List<Awardee> findAwardeeByAwardId(@RequestParam Long awardId) {
        //TODO:请在此校验参数的合法性
        return this.awardeeDao.findAwardeeByAwardId(awardId);
    }

    public void exportExcel(HttpServletResponse response, HttpServletRequest request) throws IOException {

        String userId = request.getParameter("userId");
        Long userEid = userMapper.getUserEidById(userId);
        List<String> roleId = userRoleMapper.listCodesByUserId(userEid);
        if (roleId.size() == 0) {
            roleId.add("admin");
        }
        if (roleId.get(0).equals("")) {
            roleId.get(0).replace(roleId.get(0), "admin");
        }
        //查询所有数据
        List<Awardee> list = this.awardeeDao.findAllAwardees();
        //查询所有获奖的信息
        List<Award> awards = this.awardDao.findAllAwards();
        //创建excel表
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("学生获奖信息汇总表");
        String[] heards = {"序号", "学号", "姓名", "竞赛名称", "竞赛级别", "获奖等级"};
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < heards.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(heards[i]);
            cell.setCellValue(text);
        }

        //按照个人获奖进行分组
        Map<Long, List<Awardee>> groupBySId = list.stream().collect(Collectors.groupingBy(Awardee::getStudentId));
        //按照审核状态进行分组
        Map<String, List<Award>> groupByAuditStatus = awards.stream().collect(Collectors.groupingBy(Award::getAuditStatus));
        //遍历取出审核通过

        //数据导出
        int j = 0;
        int length = list.size();

        //对前面得到的list集合进行遍历
        for (int i = length - 1; i > -1; i--) {
            //重新找到这一行数据的行数，也就是在前端的基础上再另起一行
            Integer index = list.get(i).getDisplayIndex();
            Long studentId = list.get(i).getStudentId();
            String studentName = studentInfoDao.findStudentInfoNumByUniqueId(studentId);
            Long awardId = list.get(i).getAwardId();
            Award award = this.awardDao.findAwardNC(awardId);
            String competitionName = new String();
            String awardLevel = new String();
            String awardGrade = new String();
            Long collegeIdA = awardDao.findAward(awardId).getCollegeId();
            System.out.println("collegeIdA:" + collegeIdA);
            if (award.getAuditStatus().equals("审核通过")) {
                if (roleId.get(0).equals("admin")) {
                    j++;
                    row = sheet.createRow(j);
                    competitionName = this.competitionDao.findCompetition(award.getCompetitionNameId()).getName();
                    awardLevel = award.getAwardLevel();
                    awardGrade = award.getAwardGrade();
                    row.createCell(0).setCellValue(index);
                    row.createCell(1).setCellValue(studentId.toString());
                    row.createCell(2).setCellValue(studentName);
                    row.createCell(3).setCellValue(competitionName);
                    row.createCell(4).setCellValue(awardLevel);
                    row.createCell(5).setCellValue(awardGrade);
                } else if (roleId.get(0).equals("teacher")) {
                    Long collegeId = teacherInfoDao.findTeacherInfosById(userId).get(0).getCollegeId();
                    if (collegeId == collegeIdA) {
                        j++;
                        row = sheet.createRow(j);
                        competitionName = this.competitionDao.findCompetition(award.getCompetitionNameId()).getName();
                        awardLevel = award.getAwardLevel();
                        awardGrade = award.getAwardGrade();
                        row.createCell(0).setCellValue(index);
                        row.createCell(1).setCellValue(studentId.toString());
                        row.createCell(2).setCellValue(studentName);
                        row.createCell(3).setCellValue(competitionName);
                        row.createCell(4).setCellValue(awardLevel);
                        row.createCell(5).setCellValue(awardGrade);
                    }
                } else {
                    if (roleId.get(0).equals("student") && userId.equals(String.valueOf(studentId))) {
//                            groupBySId.forEach((key,value)->{
//                                if (key==Long.valueOf(userId)){
//                                    value.forEach(System.out::println);
//                                }
//                            });
                        j++;
                        row = sheet.createRow(j);
                        competitionName = this.competitionDao.findCompetition(award.getCompetitionNameId()).getName();
                        awardLevel = award.getAwardLevel();
                        awardGrade = award.getAwardGrade();
                        row.createCell(0).setCellValue(index);
                        row.createCell(1).setCellValue(studentId.toString());
                        row.createCell(2).setCellValue(studentName);
                        row.createCell(3).setCellValue(competitionName);
                        row.createCell(4).setCellValue(awardLevel);
                        row.createCell(5).setCellValue(awardGrade);
                    }

                }
            } else {
                continue;
            }
        }

        //在内存操作，写出到浏览器，从浏览器下载
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("学生获奖信息汇总表", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        writer.flush(outputStream, true);
        //关闭流
        outputStream.close();
        writer.close();

    }

    //TODO:---------------验证-------------------

    private void validateFindAwardees(PageDTO pageDTO) {
        //TODO:请使用下面方法添加数据过滤条件
        //		pageDTO.addFilter("creatorId",this.getLoginUserId());
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAwardee()写法

        //过滤掉 审核通过 以外的信息

//        List<Awardee> awardeeDTOS = this.awardeeDao.findAwardees(pageDTO);
//        for(Awardee awardee : awardeeDTOS){
//            //比赛信息
//            Award award = this.awardDao.findAward(awardee.getAwardId());
//            if(award.getAuditStatus().equals("审核通过")){
//            }
//        }
        List<String> roles = getLoginUser().getRoles();
        Iterator it = roles.iterator();
        while (it.hasNext()) {
            String r = it.next().toString();
            if (r.equals("admin")) {
                return;
            } else if (r.equals("teacher")) {
                return;
            }
        }
        pageDTO.addFilter("studentId", this.getLoginUser().getNickname());
        pageDTO.addFilter("status", "审核通过");

        System.out.println("ddd-------------" + this.getLoginUser().getNickname());
        //pageDTO.addFilter("awardId",);

    }

    private void validateFindAwardeesWithIdNameByName(String awardeeName) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAwardee()写法
    }


    private void validateFindAllAwardeesWithIdName() {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAwardee()写法
    }

    private void validateFindAwardeesWithIdNameById(Long awardeeId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAwardee()写法
    }

    private void validateFindAwardee(Long awardeeId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAwardee()写法
    }

    private void validateFindAwardeeWithForeignName(Long awardeeId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAwardee()写法
    }

    private void validateSaveAwardee(Awardee awardee) {
        //不为空判断
        if (awardee.getEid() != null || awardee.getCreatorId() != null || awardee.getCreateDatetime() != null) {
            throw new BusinessException("非法请求");
        }
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAwardee()写法
    }

    private void validateUpdateAwardee(Awardee awardee) {
        //不为空判断
        if (awardee.getEid() == null) {
            throw new BusinessException("唯一标识不能为空");
        }
        //是否存在判断
        if (this.awardeeDao.findAwardeeTotalCount(PageDTO.create(Awardee.FIELD_ID, awardee.getEid())) == 0) {
            throw new BusinessException("修改的获奖学生信息 " + awardee.getName() + " 不存在，修改失败，请重试或联系管理员");
        }
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAwardee()写法
    }

    private void validateDeleteAwardee(Long awardeeId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAwardee()写法
    }

    @Override
    public boolean canDownloadAttachment(String formName, Long id) {
        return true;
    }
}
