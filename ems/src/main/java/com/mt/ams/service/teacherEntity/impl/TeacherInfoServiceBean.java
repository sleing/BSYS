package com.mt.ams.service.teacherEntity.impl;

import com.mt.ams.dao.teacherEntity.TeacherInfoDao;
import com.mt.ams.entity.teacherEntity.TeacherInfo;
import com.mt.ams.service.teacherEntity.TeacherInfoService;
import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.web.BaseService;
import com.mt.common.core.web.base.BaseEntity;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.common.system.entity.Role;
import com.mt.common.system.entity.User;
import com.mt.common.system.service.UserRoleService;
import com.mt.common.system.service.UserService;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class TeacherInfoServiceBean extends BaseService implements TeacherInfoService {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private TeacherInfoDao teacherInfoDao;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Resource
    private RedisTemplate<String, List<TeacherInfo>> redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 根据分页参数查询老师信息集合
     *
     * @param pageDTO 分页条件
     */
    @Override
    public PageResultDTO findTeacherInfos(PageDTO pageDTO) {
        pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
        //TODO:请在此校验参数的合法性
        this.validateFindTeacherInfos(pageDTO);
        List<TeacherInfo> teacherInfoDTOS = this.teacherInfoDao.findTeacherInfos(pageDTO);
        Long totalCount = this.teacherInfoDao.findTeacherInfoTotalCount(pageDTO);

        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setTotalCount(totalCount);
        pageResultDTO.setDatas(teacherInfoDTOS);

        return pageResultDTO;
    }

    /**
     * 查询全部老师信息集合
     */
    @Override
    public List<TeacherInfo> findAllTeacherInfos() {
        return this.teacherInfoDao.findAllTeacherInfos();
    }

    /**
     * 查询所有老师信息集合(只提取ID 和 Name)
     */
    @Override
    public List<TeacherInfo> findAllTeacherInfosWithIdName() {
        //TODO:请在此校验参数的合法性
        this.validateFindAllTeacherInfosWithIdName();
        return this.teacherInfoDao.findAllTeacherInfosWithIdName();
    }

    /**
     * 根据名称查询老师信息集合(只提取ID 和 Name)
     *
     * @param teacherInfoName 名称
     */
    @Override
    public List<TeacherInfo> findTeacherInfosWithIdNameByName(String teacherInfoName) {
        //TODO:请在此校验参数的合法性
        this.validateFindTeacherInfosWithIdNameByName(teacherInfoName);
        //TODO:缓存取对应参数
        Set<String> keys = stringRedisTemplate.keys("searchData:TeacherInfo_where_teacherInfoName_" + teacherInfoName);
        List<TeacherInfo> teacherInfos = new ArrayList<>();
        if (keys.isEmpty()) {
            teacherInfos = this.teacherInfoDao.findTeacherInfosWithIdNameByName(teacherInfoName);
            redisTemplate.opsForValue().set("searchData:TeacherInfo_where_teacherInfoName_" + teacherInfoName, teacherInfos, 30, TimeUnit.DAYS);
        } else {
            teacherInfos = redisTemplate.opsForValue().get("searchData:TeacherInfo_where_teacherInfoName_" + teacherInfoName);
        }
        return teacherInfos;
    }

    /**
     * 根据ID查询指定的老师信息(只提取ID 和 Name)
     *
     * @param teacherInfoId Id
     */
    @Override
    public TeacherInfo findTeacherInfosWithIdNameById(Long teacherInfoId) {
        //TODO:请在此校验参数的合法性
        this.validateFindTeacherInfosWithIdNameById(teacherInfoId);
        return this.teacherInfoDao.findTeacherInfosWithIdNameById(teacherInfoId);
    }

    /**
     * 根据ID查询指定的老师信息
     *
     * @param teacherInfoId Id
     */
    @Override
    public TeacherInfo findTeacherInfo(Long teacherInfoId) {
        //TODO:请在此校验参数的合法性
        this.validateFindTeacherInfo(teacherInfoId);
        return this.teacherInfoDao.findTeacherInfo(teacherInfoId);
    }

    /**
     * 根据ID查询指定的老师信息(包含外键)
     *
     * @param teacherInfoId Id
     */
    @Override
    public TeacherInfo findTeacherInfoWithForeignName(Long teacherInfoId) {
        //TODO:请在此校验参数的合法性
        this.validateFindTeacherInfoWithForeignName(teacherInfoId);
        return this.teacherInfoDao.findTeacherInfoWithForeignName(teacherInfoId);
    }

    /**
     * 新增老师信息
     *
     * @param teacherInfo 实体对象
     */
    @Override
    public TeacherInfo saveTeacherInfo(TeacherInfo teacherInfo) {
        //TODO:请在此校验参数的合法性
        this.validateSaveTeacherInfo(teacherInfo);
        //TODO:填充公共参数
        this.setSavePulicColumns(teacherInfo);
        Long rows = this.teacherInfoDao.saveTeacherInfo(teacherInfo);
        if (rows != 1) {
            String error = "新增保存老师信息出错，数据库应该返回1,但返回了 " + rows;
            throw new BusinessException(error);
        }
        return teacherInfo;
    }

    /**
     * 更新老师信息
     *
     * @param teacherInfo 实体对象
     */
    @Override
    public TeacherInfo updateTeacherInfo(TeacherInfo teacherInfo) {
        //TODO:请在此校验参数的合法性
        this.validateUpdateTeacherInfo(teacherInfo);
        Long rows = this.teacherInfoDao.updateTeacherInfo(teacherInfo);
        if (rows != 1) {
            String error = "修改保存老师信息出错，数据库应该返回1,但返回了 " + rows + ",数据可能被删除";
            throw new BusinessException(error);
        }
        return teacherInfo;
    }

    /**
     * 根据ID删除老师信息
     *
     * @param teacherInfoId ID
     */
    @Override
    public void deleteTeacherInfo(Long teacherInfoId) {
        //TODO:请在此校验参数的合法性
        this.validateDeleteTeacherInfo(teacherInfoId);

        Map<Class<? extends BaseEntity>, EntityUsage> entityUsageMap = this.checkForeignEntity(TeacherInfo.class, teacherInfoId);
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

        Long rows = this.teacherInfoDao.deleteTeacherInfo(teacherInfoId);
        if (rows != 1) {
            String error = "删除老师信息出错，数据可能已经被删除";
            throw new BusinessException(error);
        }
    }

    //采用工号查询老师信息

    /**
     * 根据工号查询老师信息
     *
     * @param teacherInfoId Id
     */
    @Override
    public List<TeacherInfo> findTeacherInfosById(String teacherInfoId) {
        return this.teacherInfoDao.findTeacherInfosById(teacherInfoId);
    }

    /**
     * 获取流文件
     * @param ins
     * @param file
     */
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * MultipartFile 转 File
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    /**
     * 根据excel导入老师信息
     *
     * @param uploadTeacherInfos
     */
    public int uploadTeacherInfos(@RequestParam("file") MultipartFile uploadTeacherInfos){
        int rows = 0;
        int columns = 0;
        String passWord;
        String phone;
        Long teacherRoleId = 11L;
        Boolean isEmpty = false;
        List<Role>roles = new ArrayList();
        //Date d = new Date(); //Eid CreatorId CreateDateTime 不需要赋值
        //表字段： 姓名  工号  学院  邮箱  密码  电话  角色
        try {
            File file = multipartFileToFile(uploadTeacherInfos);

            //获得上传文件的名称
            //String originalFilename = uploadTeacherInfos.getOriginalFilename();

            //使用字符流去接File的数据
            FileInputStream fileInputStream = new FileInputStream(file);
            //workbook去接fileInputStream
            Workbook workbook = Workbook.getWorkbook(fileInputStream);
            //这样读取到了excel文件，但是需要去判断是哪一个工作簿，要用到Sheet类
            Sheet readfirst = workbook.getSheet(0);
            rows = readfirst.getRows();
            columns = readfirst.getColumns();
            System.out.println("一共导入了" + (rows-1)+"条数据");
            List<TeacherInfo>teacherInfos = new ArrayList();
            for(int i =1;i<rows;i++) {
                TeacherInfo teacherInfo = new TeacherInfo();
                User user = new User();
                for (int j = 0; j < columns; j++) {
                    Cell cell = readfirst.getCell(j, i);  //j在前 i 在后是根据excel下标来判断的
                    String s = cell.getContents();
                    if(s.equals("")){
                        isEmpty = true;
                        break;
                    }
                    if(j == 0){
                        teacherInfo.setName(s);
                    }else if(j == 1){
                        teacherInfo.setTeacherId(s);
                        user.setNickname(s);
                        user.setUsername(s);
                    }else if(j == 2){
                        Long id = Long.valueOf(s);
                        teacherInfo.setCollegeId(id);
                    }else if(j == 3){
                        teacherInfo.setEmail(s);
                        user.setEmail(s);
                    }else if(j == 4){
                        passWord = userService.encodePsw(s);
                        user.setPassword(passWord);
                    }else if(j == 5){
                        phone = s;
                        user.setPhone(phone);
                    }else if(j == 6){
                        user.setUserType(s);
                    }
                }
                if(isEmpty){
                    break;
                }
                teacherInfo.setCreatorName("系统管理员");

                this.saveTeacherInfo(teacherInfo);
                userService.saveUser(user);
                teacherInfos.add(teacherInfo);
                //userRoleService.insertBatchSingle(userService.getUserEidById(user.getUsername()),teacherRoleId);
                System.out.println("-------------------"+user.getUsername());
            }
            rows = teacherInfos.size();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rows;

    }

    @Override
    public Boolean getTeacherEid(String teacherId) {
        String n = teacherId.trim();
        Integer id = this.teacherInfoDao.getTeacherEid(n);
        if(id != null) {
            return true;
        }else
            return false;

    }

    //TODO:---------------验证-------------------

    private void validateFindTeacherInfos(PageDTO pageDTO) {
        //TODO:请使用下面方法添加数据过滤条件
        //		pageDTO.addFilter("creatorId",this.getLoginUserId());
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTeacherInfo()写法
    }

    private void validateFindTeacherInfosWithIdNameByName(String teacherInfoName) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTeacherInfo()写法
    }


    private void validateFindAllTeacherInfosWithIdName() {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTeacherInfo()写法
    }

    private void validateFindTeacherInfosWithIdNameById(Long teacherInfoId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTeacherInfo()写法
    }

    private void validateFindTeacherInfo(Long teacherInfoId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTeacherInfo()写法
    }

    private void validateFindTeacherInfoWithForeignName(Long teacherInfoId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTeacherInfo()写法
    }

    private void validateSaveTeacherInfo(TeacherInfo teacherInfo) {
        //不为空判断
        if (teacherInfo.getEid() != null || teacherInfo.getCreatorId() != null || teacherInfo.getCreateDatetime() != null) {
            throw new BusinessException("非法请求");
        }
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTeacherInfo()写法
    }

    private void validateUpdateTeacherInfo(TeacherInfo teacherInfo) {
        //不为空判断
        if (teacherInfo.getEid() == null) {
            throw new BusinessException("唯一标识不能为空");
        }
        //是否存在判断
        if (this.teacherInfoDao.findTeacherInfoTotalCount(PageDTO.create(TeacherInfo.FIELD_ID, teacherInfo.getEid())) == 0) {
            throw new BusinessException("修改的老师信息 " + teacherInfo.getName() + " 不存在，修改失败，请重试或联系管理员");
        }
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTeacherInfo()写法
    }

    private void validateDeleteTeacherInfo(Long teacherInfoId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateTeacherInfo()写法
    }

    @Override
    public boolean canDownloadAttachment(String formName, Long id) {
        return true;
    }
}
