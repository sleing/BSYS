package com.mt.ams.service.awardEntity.impl;

import com.mt.ams.dao.awardEntity.AwardDao;
import com.mt.ams.dao.awardeeEntity.AwardeeDao;
import com.mt.ams.dao.collegeEntity.CollegeDao;
import com.mt.ams.dao.competitionEntity.CompetitionDao;
import com.mt.ams.dao.instructorEntity.InstructorDao;
import com.mt.ams.dao.studentEntity.StudentInfoDao;
import com.mt.ams.dao.teacherEntity.TeacherInfoDao;
import com.mt.ams.dao.unitEntity.UnitDao;
import com.mt.ams.entity.awardEntity.Award;
import com.mt.ams.entity.awardeeEntity.Awardee;
import com.mt.ams.entity.instructorEntity.Instructor;
import com.mt.ams.service.awardEntity.AwardService;
import com.mt.ams.service.awardeeEntity.AwardeeService;
import com.mt.ams.service.instructorEntity.InstructorService;
import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.web.BaseService;
import com.mt.common.core.web.base.BaseEntity;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class AwardServiceBean extends BaseService implements AwardService {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private AwardDao awardDao;

    @Autowired
    private InstructorDao instructorDao;

    @Autowired
    private UnitDao unitDao;

    @Autowired
    private CompetitionDao competitionDao;

    @Autowired
    private AwardeeDao awardeeDao;

    @Autowired
    private TeacherInfoDao teacherInfoDao;

    @Autowired
    private StudentInfoDao studentInfoDao;

    @Autowired
    private CollegeDao collegeDao;

    @Autowired
    private AwardeeService awardeeService;

    @Autowired
    private InstructorService instructorService;

    @Resource
    private RedisTemplate<String, List<Award>> redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    /**
     * 根据分页参数查询获奖登记集合
     *
     * @param pageDTO 分页条件
     */
    @Override
    public PageResultDTO findAwards(PageDTO pageDTO) {
        pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
        //TODO:请在此校验参数的合法性
        this.validateFindAwards(pageDTO);
        List<Award> awardDTOS = this.awardDao.findAwards(pageDTO);
        Long totalCount = this.awardDao.findAwardTotalCount(pageDTO);

        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setTotalCount(totalCount);
        pageResultDTO.setDatas(awardDTOS);

        for (Award awardDTO : awardDTOS) {
            //所有获奖学生
            List<Long> studentId = awardeeDao.findStudentNameById(awardDTO.getEid());
            List<String> studentName = new ArrayList<>();
            for (Long sId : studentId) {
                //System.out.println("studentId:" + sId);
                String name = this.studentInfoDao.findStudentInfoNumByUniqueId(sId);
                studentName.add(name);
            }
            awardDTO.setAllStudents(studentName.toString());
            //所有指导老师
            if (awardDTO.getFirstInstructorId() != null) {
                List<Instructor> instructorsTemp = instructorDao.findInstructorByAwardId(awardDTO.getEid());
                List<String> instructorsName = new ArrayList<>();
                for (Instructor instructor : instructorsTemp) {
                    instructorsName.add(teacherInfoDao.findTeacherById(instructor.getTeacherId().toString()).get(0).getName());
                }
                awardDTO.setAllInstructors(instructorsName.toString());
            }
            //审核人
            if (awardDTO.getReviewerId() != null) {
                String reviewerId = String.valueOf(awardDTO.getReviewerId());
                String reviewerName = teacherInfoDao.findTeacherById(reviewerId).get(0).getName();
                awardDTO.setReviewerName(reviewerName);
            }
        }

        return pageResultDTO;
    }

    /**
     * 查询全部获奖登记集合
     */
    @Override
    public List<Award> findAllAwards() {
        return this.awardDao.findAllAwards();
    }

    /**
     * 查询所有获奖登记集合(只提取ID 和 Name)
     */
    @Override
    public List<Award> findAllAwardsWithIdName() {
        //TODO:请在此校验参数的合法性
        this.validateFindAllAwardsWithIdName();
        return this.awardDao.findAllAwardsWithIdName();
    }

    /**
     * 根据名称查询获奖登记集合(只提取ID 和 Name)
     *
     * @param awardName 名称
     */
    @Override
    public List<Award> findAwardsWithIdNameByName(String awardName) {
        //TODO:请在此校验参数的合法性
        this.validateFindAwardsWithIdNameByName(awardName);
        //TODO:缓存取对应参数
        Set<String> keys = stringRedisTemplate.keys("searchData:Award_where_awardName_" + awardName);
        List<Award> awards = new ArrayList<>();
        if (keys.isEmpty()) {
            awards = this.awardDao.findAwardsWithIdNameByName(awardName);
            redisTemplate.opsForValue().set("searchData:Award_where_awardName_" + awardName, awards, 30, TimeUnit.DAYS);
        } else {
            awards = redisTemplate.opsForValue().get("searchData:Award_where_awardName_" + awardName);
        }
        return awards;
    }

    /**
     * 根据ID查询指定的获奖登记(只提取ID 和 Name)
     *
     * @param awardId Id
     */
    @Override
    public Award findAwardsWithIdNameById(Long awardId) {
        //TODO:请在此校验参数的合法性
        this.validateFindAwardsWithIdNameById(awardId);
        return this.awardDao.findAwardsWithIdNameById(awardId);
    }

    /**
     * 根据ID查询指定的获奖登记
     *
     * @param awardId Id
     */
    @Override
    public Award findAward(Long awardId) {
        //TODO:请在此校验参数的合法性
        this.validateFindAward(awardId);
        Award award = this.awardDao.findAward(awardId);
        //第一获奖人姓名
        award.setFirstAwardeeName(studentInfoDao.findStudentInfo(award.getFirstAwardeeId()).getName());
        //获奖成员姓名
        List<Long> studentId = awardeeDao.findStudentNameById(award.getEid());
        List<String> studentName = new ArrayList<>();
        for (Long sId : studentId) {
            //System.out.println("studentId:" + sId);
            String name = this.studentInfoDao.findStudentInfoNumByUniqueId(sId);
            studentName.add(name);
        }
        award.setAllStudents(studentName.toString());

        //所有指导老师
        if (award.getFirstInstructorId() != null) {
            List<Instructor> instructorsTemp = instructorDao.findInstructorByAwardId(award.getEid());
            List<String> instructorsName = new ArrayList<>();
            for (Instructor instructor : instructorsTemp) {
                instructorsName.add(teacherInfoDao.findTeacherById(instructor.getTeacherId().toString()).get(0).getName());
            }
            award.setAllInstructors(instructorsName.toString());
        }

        //审核人名称
        if (award.getReviewerId() != null) {
            String reviewerId = String.valueOf(award.getReviewerId());
            String reviewerName = teacherInfoDao.findTeacherById(reviewerId).get(0).getName();
            award.setReviewerName(reviewerName);
        }
        //学院名称
        award.setCollegeName(collegeDao.findCollege(award.getCollegeId()).getName());
        //获奖名称
        award.setCompetitionNameName(competitionDao.findCompetition(award.getCompetitionNameId()).getName());
        //举办单位
        if (award.getOrganizationId() != null) {
            award.setOrganizationName(unitDao.findUnit(award.getOrganizationId()).getName());
        }
        //第一指导老师
        if (award.getFirstInstructorId() != null) {
            award.setFirstInstructorName(teacherInfoDao.findTeacherInfo(award.getFirstInstructorId()).getName());
        }

        return award;
    }

    /**
     * 根据ID查询指定的获奖登记(包含外键)
     *
     * @param awardId Id
     */
    @Override
    public Award findAwardWithForeignName(Long awardId) {
        //TODO:请在此校验参数的合法性
        this.validateFindAwardWithForeignName(awardId);
        Award award = this.awardDao.findAwardWithForeignName(awardId);

        //List<Awardee> awardees = awardeeDao.findAwardeeByAwardId(awardId);
        List<Awardee> awardees = new ArrayList<>();
        //List<Instructor> instructors = instructorDao.findInstructorByAwardId(awardId);
        List<Instructor> instructors = new ArrayList<>();

        award.setInstructors(instructors);
        award.setAwardees(awardees);

        return award;
    }

    /**
     * 新增获奖登记
     *
     * @param award 实体对象
     */
    @Override
    public Award saveAward(Award award) {
        //TODO:请在此校验参数的合法性
        this.validateSaveAward(award);
        //TODO:填充公共参数
        this.setSavePulicColumns(award);

        award.setName(competitionDao.findCompetition(award.getCompetitionNameId()).getName());
        Long rows = this.awardDao.saveAward(award);
        Long rowss = 1L;
        //award.getAwardees()
        if (award.getAwardees() != null) {

            Long aEid = award.getEid();
            // Awardee awardeeTemp;
            List<Awardee> awardeeTemp = new ArrayList<>();
            //System.out.println("aEid is" + aEid);
            awardeeTemp = award.getAwardees();
            System.out.println("awardees size is " + award.getAwardees().size());
            for (int i = 0; i < award.getAwardees().size(); i++) {
                Awardee awardee = new Awardee();
                awardee.setDisplayIndex(i + 1);
                awardee.setAwardId(aEid);
                //System.out.println("awardeeTemp.get(i).getStudentId() is" + awardeeTemp.get(i).getStudentId());
                awardee.setStudentId(awardeeTemp.get(i).getStudentId());
                if (i == 0) {
                    awardee.setRemark("队长");
                } else {
                    awardee.setRemark("队员");
                }
                awardee.setStatus("未审核");   //设置审核状态
                awardee.setCreateDatetime(award.getAwardDate());    //设置获奖时间
                awardee.setCreatorId(award.getCompetitionNameId()); //设置获奖名称的eid
                awardee.setCreatorName(award.getName());    //设置获奖名称
                rowss = this.awardeeDao.saveAwardee(awardee);
            }
        }
        Long rowst = 1L;
        //award.getInstructors()
        if (award.getInstructors() != null) {
            Long tEid = award.getEid();
            List<Instructor> instructorsTemp = new ArrayList<>();
            System.out.printf("tEid is " + tEid);
            instructorsTemp = award.getInstructors();
            for (int i = 0; i < award.getInstructors().size(); i++) {
                Instructor instructor = new Instructor();
                Date date = new Date();
                instructor.setCreateDatetime(date);
                instructor.setDisplayIndex(i + 1);
                instructor.setAwardId(tEid);
                instructor.setTeacherId(instructorsTemp.get(i).getTeacherId());
                //System.out.println("instructor" + instructor);
                if (i == 0) {
                    instructor.setRemark("第一指导教师");
                } else {
                    instructor.setRemark("指导教师");
                }
                rowst = this.instructorDao.saveInstructor(instructor);
            }
        }
        if (rows != 1 || rowss != 1 || rowst != 1) {
            String error = "新增保存获奖登记出错，数据库应该返回1,但返回了 " + rows;
            throw new BusinessException(error);
        }
        return award;
    }

    /**
     * 队列比较*
     *@param List<Awardee>
     *@param a
     *@param b
     *@return
     */
    public static boolean AwardeeListCompare(List<Awardee> a, List<Awardee> b) {
        if (a.size() != b.size())
            return false;
        for (int i = 0; i < a.size(); i++) {
            if (!(a.get(i).getDisplayIndex().equals(b.get(i).getDisplayIndex()) && a.get(i).getStudentId().equals(b.get(i).getStudentId()))) return false;
        }
        return true;
    }

    /**
     * 队列比较*
     *@param List<Instructor>
     *@param a
     *@param b
     *@return
     */
    public static boolean InstructorListCompare(List<Instructor> a, List<Instructor> b) {
        if (a.size() != b.size())
            return false;
        for (int i = 0; i < a.size(); i++) {
            if (!(a.get(i).getDisplayIndex().equals(b.get(i).getDisplayIndex()) && a.get(i).getTeacherId().equals(b.get(i).getTeacherId()))) return false;
        }
        return true;
    }

    /**
     * 更新获奖登记
     *
     * @param award 实体对象
     */
    @Override
    public Award updateAward(Award award) {
        //TODO:请在此校验参数的合法性
        this.validateUpdateAward(award);
        //更新获奖学生
        Long rowss = 1L;
        List<Awardee> as = awardeeDao.findAwardeeByAwardId(award.getEid());
        //System.out.println("as==============>" + ListCompare(award.getAwardees(),as));

        if (!AwardeeListCompare(award.getAwardees(),as)) {
            Long aEid = award.getEid();
            //删除以前的数据
            awardeeService.deleteAwardeeByAwardId(aEid);
            //增加获奖学生
            List<Awardee> awardeeTemp = award.getAwardees();
            Collections.sort(awardeeTemp, (a, b) -> {      //按照disPlayIndex排序
                return a.getDisplayIndex() - b.getDisplayIndex();
            });
            //System.out.println("awardees size is " + award.getAwardees().size());
            for (int i = 0; i < award.getAwardees().size(); i++) {
                Awardee awardee = new Awardee();
                Date date = new Date();
                awardee.setCreateDatetime(date);
                //按照displayIndex排序
                awardee.setDisplayIndex(i + 1);
                awardee.setAwardId(aEid);
                awardee.setStudentId(awardeeTemp.get(i).getStudentId());
                if (awardeeTemp.get(i).getDisplayIndex() == 1) {
                    awardee.setRemark("队长");
                } else {
                    awardee.setRemark("队员");
                }
                awardee.setStatus("未审核");   //设置审核状态
                awardee.setCreateDatetime(award.getAwardDate());    //设置获奖时间
                awardee.setCreatorId(award.getCompetitionNameId()); //设置获奖名称的eid
                awardee.setCreatorName(award.getName());    //设置获奖名称
                rowss = this.awardeeDao.saveAwardee(awardee);
            }
        }
        //更新指导老师
        Long rowst = 1L;
        //award.getInstructors()
        List<Instructor> is = instructorDao.findInstructorByAwardId(award.getEid());
        if (!InstructorListCompare(award.getInstructors(),is)) {
            Long tEid = award.getEid();
            //删除以前的数据
            instructorService.deleteInstructorByAwardId(tEid);
            //增加指导老师
            List<Instructor> instructorsTemp = award.getInstructors();
            Collections.sort(instructorsTemp, (a, b) -> {      //按照disPlayIndex排序
                return a.getDisplayIndex() - b.getDisplayIndex();
            });
            for (int i = 0; i < award.getInstructors().size(); i++) {
                Instructor instructor = new Instructor();
                Date date = new Date();
                instructor.setCreateDatetime(date);
                instructor.setDisplayIndex(i + 1);
                instructor.setAwardId(tEid);
                instructor.setTeacherId(instructorsTemp.get(i).getTeacherId());
                if (instructorsTemp.get(i).getDisplayIndex() == 1) {
                    instructor.setRemark("第一指导教师");
                } else {
                    instructor.setRemark("指导教师");
                }
                rowst = this.instructorDao.saveInstructor(instructor);
            }
        }

        Long rows = this.awardDao.updateAward(award);

        if (rows != 1) {
            String error = "修改保存获奖登记出错，数据库应该返回1,但返回了 " + rows + ",数据可能被删除";
            throw new BusinessException(error);
        }
        return award;
    }

    /**
     * 根据ID删除获奖登记
     *
     * @param awardId ID
     */
    @Override
    public void deleteAward(Long awardId) {
        //TODO:请在此校验参数的合法性
        this.validateDeleteAward(awardId);

        Map<Class<? extends BaseEntity>, EntityUsage> entityUsageMap = this.checkForeignEntity(Award.class, awardId);
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

        Long rows = this.awardDao.deleteAward(awardId);
        if (rows != 1) {
            String error = "删除获奖登记出错，数据可能已经被删除";
            throw new BusinessException(error);
        }
    }

    @Override
    public PageResultDTO MyfindAwards(PageDTO pageDTO) {
        pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
        //TODO:请在此校验参数的合法性
        this.myValidateFindAwards(pageDTO);
        List<Award> awardDTOS = this.awardDao.findAwards(pageDTO);
        Long totalCount = this.awardDao.findAwardTotalCount(pageDTO);

        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setTotalCount(totalCount);
        pageResultDTO.setDatas(awardDTOS);


        for (Award awardDTO : awardDTOS) {
            List<Long> studentId = awardeeDao.findStudentNameById(awardDTO.getEid());
            List<String> studentName = new ArrayList<>();
            Boolean iflag = true;

            for (Long sId : studentId) {
                String name = this.studentInfoDao.findStudentInfoNumByUniqueId(sId);
                studentName.add(name);
                if (iflag) {
                    awardDTO.setFirstAwardeeId(sId);
                    iflag = false;
                }
            }
            awardDTO.setAllStudents(studentName.toString());
        }

        return pageResultDTO;
    }

    @Override
    public void myUpdate(Long awardId) {
        //完善awardee表 将审核情况赋值给status字段
        List<Awardee> awardees = awardeeService.findAwardeeByAwardId(awardId);
        for (int i = 0; i < awardees.size(); i++) {
            awardees.get(i).setStatus("审核通过");
            awardeeDao.updateAwardee(awardees.get(i));
        }


        this.awardDao.myUpdate(awardId);
    }

    @Override
    public void auditForCon(Long awardId, String remarkContent) {
        //完善awardee表 将审核情况赋值给status字段
        List<Awardee> awardees = awardeeService.findAwardeeByAwardId(awardId);
        for (int i = 0; i < awardees.size(); i++) {
            awardees.get(i).setStatus("驳回");
            awardeeDao.updateAwardee(awardees.get(i));
        }

        this.awardDao.auditForCon(awardId, remarkContent);
    }


    //文件上传
    @Override
    public String fileUrl(Long eid) {
        return this.awardDao.fileUrl(eid);
    }

    @Override
    public String resourcesUrl(Long eid) {
        return this.awardDao.resourcesUrl(eid);
    }

    @Override
    public void updateReviewer(Long Id, Long eid) {
        this.awardDao.updateReviewer(Id, eid);
    }


    //TODO:---------------验证-------------------


    private void myValidateFindAwards(PageDTO pageDTO) {
        String userID = getLoginUser().getNickname();

        Long collegeId = teacherInfoDao.findTeacherById(userID).get(0).getCollegeId();
        Boolean isAdmin = false;
        Boolean isTeacher = false;

        List<String> roles = getLoginUser().getRoles();
        Iterator it = roles.iterator();
        while (it.hasNext()) {
            String r = it.next().toString();

            if (r.equals("admin")) {
                isAdmin = true;
            }
            if (r.equals("teacher")) {
                isTeacher = true;
            }
        }
        if (isAdmin) {
            pageDTO.addFilter("auditStatus", "未审核");
        }
        if (isTeacher) {
            pageDTO.addFilter("collegeId", collegeId);
            pageDTO.addFilter("auditStatus", "未审核");
        }
        //TODO:请使用下面方法添加数据过滤条件
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAward()写法
    }

    private void validateFindAwards(PageDTO pageDTO) {
        //TODO:请使用下面方法添加数据过滤条件

        List<String> roles = getLoginUser().getRoles();
        Iterator it = roles.iterator();
        while (it.hasNext()) {
            String r = it.next().toString();
            //System.out.println(r + "-------role");
            if (!r.equals("admin") && !r.equals("teacher")) {
                pageDTO.addFilter("creatorName", this.getUser().getNickname());
                break;
            } else if (r.equals("teacher")) {
                String userID = getLoginUser().getNickname();
                Long collegeId = teacherInfoDao.findTeacherInfosById(userID).get(0).getCollegeId();
                pageDTO.addFilter("collegeId", collegeId);
                break;
            }
        }

        System.out.println(getLoginUser().getRoles() + "------roles");
        System.out.println(getUser().getNickname() + "-------Nickname");
        //		pageDTO.addFilter("creatorId",this.getLoginUserId());
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAward()写法
    }

    private void validateFindAwardsWithIdNameByName(String awardName) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAward()写法
    }


    private void validateFindAllAwardsWithIdName() {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAward()写法
    }

    private void validateFindAwardsWithIdNameById(Long awardId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAward()写法
    }

    private void validateFindAward(Long awardId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAward()写法
    }

    private void validateFindAwardWithForeignName(Long awardId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAward()写法
    }

    private void validateSaveAward(Award award) {
        //不为空判断
        if (award.getEid() != null || award.getCreatorId() != null || award.getCreateDatetime() != null) {
            throw new BusinessException("非法请求");
        }
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAward()写法
    }

    private void validateUpdateAward(Award award) {
        //不为空判断
        if (award.getEid() == null) {
            throw new BusinessException("唯一标识不能为空");
        }
        //是否存在判断
        if (this.awardDao.findAwardTotalCount(PageDTO.create(Award.FIELD_ID, award.getEid())) == 0) {
            throw new BusinessException("修改的获奖登记 " + award.getName() + " 不存在，修改失败，请重试或联系管理员");
        }
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAward()写法
    }

    private void validateDeleteAward(Long awardId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateAward()写法
    }

    @Override
    public boolean canDownloadAttachment(String formName, Long id) {
        return true;
    }
}
