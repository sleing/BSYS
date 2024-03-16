package com.mt.ams.service.studentEntity.impl;

import com.mt.ams.dao.studentEntity.StudentInfoDao;
import com.mt.ams.entity.studentEntity.StudentInfo;
import com.mt.ams.service.studentEntity.StudentInfoService;
import com.mt.ams.service.teacherEntity.TeacherInfoService;
import com.mt.ams.utils.MailUtils;
import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.web.BaseService;
import com.mt.common.core.web.base.BaseEntity;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.common.system.entity.Role;
import com.mt.common.system.entity.User;
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
public class StudentInfoServiceBean extends BaseService implements StudentInfoService {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private StudentInfoDao studentInfoDao;

    @Autowired
    private TeacherInfoService teacherInfoService;

    @Autowired
    private UserService userService;

    @Resource
    private RedisTemplate<String, List<StudentInfo>> redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MailUtils mailUtils;

    HashMap<String, String> cacheCode = new HashMap<String, String>();

    /**
     * 根据分页参数查询学生信息集合
     *
     * @param pageDTO 分页条件
     */
    @Override
    public PageResultDTO findStudentInfos(PageDTO pageDTO) {
        pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
        //TODO:请在此校验参数的合法性
        this.validateFindStudentInfos(pageDTO);
        List<StudentInfo> studentInfoDTOS = this.studentInfoDao.findStudentInfos(pageDTO);
        Long totalCount = this.studentInfoDao.findStudentInfoTotalCount(pageDTO);

        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setTotalCount(totalCount);
        pageResultDTO.setDatas(studentInfoDTOS);

        return pageResultDTO;
    }

    /**
     * 查询全部学生信息集合
     */
    @Override
    public List<StudentInfo> findAllStudentInfos() {
        return this.studentInfoDao.findAllStudentInfos();
    }

    /**
     * 查询所有学生信息集合(只提取ID 和 Name)
     */
    @Override
    public List<StudentInfo> findAllStudentInfosWithIdName() {
        //TODO:请在此校验参数的合法性
        this.validateFindAllStudentInfosWithIdName();
        return this.studentInfoDao.findAllStudentInfosWithIdName();
    }

    /**
     * 根据名称查询学生信息集合(只提取ID 和 Name)
     *
     * @param studentInfoName 名称
     */
    @Override
    public List<StudentInfo> findStudentInfosWithIdNameByName(String studentInfoName) {
        //TODO:请在此校验参数的合法性
        this.validateFindStudentInfosWithIdNameByName(studentInfoName);
        //TODO:缓存取对应参数
        Set<String> keys = stringRedisTemplate.keys("searchData:StudentInfo_where_studentInfoName_" + studentInfoName);
        List<StudentInfo> studentInfos = new ArrayList<>();
        if (keys.isEmpty()) {
            studentInfos = this.studentInfoDao.findStudentInfosWithIdNameByName(studentInfoName);
            redisTemplate.opsForValue().set("searchData:StudentInfo_where_studentInfoName_" + studentInfoName, studentInfos, 30, TimeUnit.DAYS);
        } else {
            studentInfos = redisTemplate.opsForValue().get("searchData:StudentInfo_where_studentInfoName_" + studentInfoName);
        }
        return studentInfos;
    }

    /**
     * 根据ID查询指定的学生信息(只提取ID 和 Name)
     *
     * @param studentInfoId Id
     */
    @Override
    public StudentInfo findStudentInfosWithIdNameById(Long studentInfoId) {
        //TODO:请在此校验参数的合法性
        this.validateFindStudentInfosWithIdNameById(studentInfoId);
        return this.studentInfoDao.findStudentInfosWithIdNameById(studentInfoId);
    }

    /**
     * 根据ID查询指定的学生信息
     *
     * @param studentInfoId Id
     */
    @Override
    public StudentInfo findStudentInfo(Long studentInfoId) {
        //TODO:请在此校验参数的合法性
        this.validateFindStudentInfo(studentInfoId);
        return this.studentInfoDao.findStudentInfo(studentInfoId);
    }

    /**
     * 根据ID查询指定的学生信息(包含外键)
     *
     * @param studentInfoId Id
     */
    @Override
    public StudentInfo findStudentInfoWithForeignName(Long studentInfoId) {
        //TODO:请在此校验参数的合法性
        this.validateFindStudentInfoWithForeignName(studentInfoId);
        return this.studentInfoDao.findStudentInfoWithForeignName(studentInfoId);
    }

    /**
     * 新增学生信息
     *
     * @param studentInfo 实体对象
     */
    @Override
    public StudentInfo saveStudentInfo(StudentInfo studentInfo) {
        //TODO:请在此校验参数的合法性
        this.validateSaveStudentInfo(studentInfo);
        //TODO:填充公共参数
        //this.setSavePulicColumns(studentInfo);
        if(studentInfo.getRemark().equals("1")) {
            studentInfo.setRemark("本科生");
        }else if(studentInfo.getRemark().equals("2")) {
            studentInfo.setRemark("研究生");
        }
        Long rows = this.studentInfoDao.saveStudentInfo(studentInfo);
        if (rows != 1) {
            String error = "新增保存学生信息出错，数据库应该返回1,但返回了 " + rows;
            throw new BusinessException(error);
        }
        return studentInfo;
    }

    /**
     * 更新学生信息
     *
     * @param studentInfo 实体对象
     */
    @Override
    public StudentInfo updateStudentInfo(StudentInfo studentInfo) {
        //TODO:请在此校验参数的合法性
        this.validateUpdateStudentInfo(studentInfo);
        Long rows = this.studentInfoDao.updateStudentInfo(studentInfo);
        if (rows != 1) {
            String error = "修改保存学生信息出错，数据库应该返回1,但返回了 " + rows + ",数据可能被删除";
            throw new BusinessException(error);
        }
        return studentInfo;
    }

    /**
     * 根据ID删除学生信息
     *
     * @param studentInfoId ID
     */
    @Override
    public void deleteStudentInfo(Long studentInfoId) {
        //TODO:请在此校验参数的合法性
        this.validateDeleteStudentInfo(studentInfoId);

        Map<Class<? extends BaseEntity>, EntityUsage> entityUsageMap = this.checkForeignEntity(StudentInfo.class, studentInfoId);
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

        Long rows = this.studentInfoDao.deleteStudentInfo(studentInfoId);
        if (rows != 1) {
            String error = "删除学生信息出错，数据可能已经被删除";
            throw new BusinessException(error);
        }
    }

    //采用学号查询学生信息

    /**
     * 根据学号查询学生信息
     *
     * @param studentInfoId Id
     */
    @Override
    public List<StudentInfo> findStudentInfoNumById(String studentInfoId) {
        return this.studentInfoDao.findStudentInfoNumById(studentInfoId);
    }

    @Override
    public Long getStudentEid(String studentId) {
        List<StudentInfo> allStudentInfos = this.studentInfoDao.findAllStudentInfos();
        List<String> allStudentId = new ArrayList<>();
        for (StudentInfo allStudent : allStudentInfos) {
            String i = allStudent.getStudentId();
            /*int i1 = Integer.parseInt(i);*/
            allStudentId.add(i);
        }
        int i = 0;
        for (String aLong : allStudentId) {
            if (aLong.equals(studentId)) {
                i += 1;
            }
        }

        if (i == 0)
            return this.studentInfoDao.getStudentEid(studentId);
        else
            return -1L;  //要转换为Long型
    }


    @Override
    public Long getStudentEidByEmail(String email) {
        List<StudentInfo> allStudentInfos = this.studentInfoDao.findAllStudentInfos();
        List<String> allStudentEmail = new ArrayList<>();
        for (StudentInfo allStudent : allStudentInfos) {
            String i = allStudent.getEmail();
            allStudentEmail.add(i);
        }
        int i = 0;
        for (String aLong : allStudentEmail) {
            if (aLong.equals(email)) {
                i += 1;
            }
        }

        if (i == 0)
            return this.studentInfoDao.getStudentEidByEmail(email);
        else
            return -1L;  //要转换为Long型
    }

    @Override
    public void updateStudentInfoMy(String studentId, String name, String major,
                                    String collegeId, String grade, String classGrade,
                                    String contactTel, String email,String remark) {
        System.out.println("学生类型之修改学生表："+remark);
        if(remark.equals("1")||remark.equals("本科生")) {
            remark = "本科生";
        }else if(remark.equals("2")||remark.equals("研究生")) {
            remark = "研究生";
        }
        System.out.println("更新学生表之remark2："+remark);
        this.studentInfoDao.updateStudentInfoMy(studentId, name, major, collegeId,
                grade, classGrade, contactTel, email,remark);
    }

    @Override
    //@Cacheable(value="cacheSpace",key="#email")
    public String sendCheckCode(String email) {
        System.out.println("email--------::" + email);
        //发送验证码
        MailUtils mailUtils = new MailUtils();
        //生成随机6位验证码,并且存入数组
        String code = mailUtils.randomCode();
        //TODO  static 优化
        mailUtils.sendMail(email, code, "来自基于OCR学生获奖智能管理系统的验证码");
        System.out.println(mailUtils.toString());
        cacheCode.put(email, code);
        return code;
    }

    public boolean checkCode(String code, String email) {
        System.out.println("code-----------" + code);
        System.out.println("email----------" + email);
        String txtCode = null;
        //Session验证前端发来的验证码
        //txtCode = new String(httpSession.getAttribute("code").toString());
        //txtCode = mailUtils.getCode(email);
        txtCode = cacheCode.get(email);

        //校验验证码
        //读取文件
        // 读取文件内容 (输入流)
//        File file = new File("D:\\Workspace\\DDD5\\ems\\src\\main\\java\\com\\mt\\ams\\utils", "checkCode.txt");
//        FileInputStream out = new FileInputStream(file);
//        InputStreamReader isr = new InputStreamReader(out);
//        int ch = 0;
//        StringBuffer stringBuffer = new StringBuffer();
//        while ((ch = isr.read()) != -1) {
//            System.out.print((char) ch);
//            stringBuffer.append((char) ch);
//        }
//        txtCode = stringBuffer.substring(0, 6);
//        System.out.println("读取的验证码：" + stringBuffer.substring(0, 6));
//        System.out.println("读取的邮箱@前的符号：" + stringBuffer.substring(7));
        if (code.equals(txtCode)) {
            System.out.println("验证码匹配成功！");
            return true;
        } else {
            System.out.println("验证码匹配失败！");
            return false;
        }
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
     * 根据excel导入学生信息
     *
     * @param uploadStudentInfos
     */
    public int uploadStudentInfos(@RequestParam("file")MultipartFile uploadStudentInfos){

        int rows = 0;
        int columns = 0;
        int i = 1;
        int j = 0;
        String passWord;
        String phone;
        Long studentInfoId = 10L;
        Boolean isNull = false;
        List<Role>roles = new ArrayList();
        //表字段： 姓名  学号  学院  邮箱  密码  电话  角色
        try {
            File file = multipartFileToFile(uploadStudentInfos);
            //使用字符流去接File的数据
            FileInputStream fileInputStream = new FileInputStream(file);
            //workbook去接fileInputStream
            Workbook workbook = Workbook.getWorkbook(fileInputStream);
            //这样读取到了excel文件，但是需要去判断是哪一个工作簿，要用到Sheet类
            Sheet readfirst = workbook.getSheet(0);
            rows = readfirst.getRows();
            columns = readfirst.getColumns();
            System.out.println("一共导入了" + (rows-1)+"条数据");
            List<StudentInfo>studentInfos = new ArrayList();
            for(i =1;i<rows;i++) {
                StudentInfo studentInfo = new StudentInfo();
                User user = new User();
                for (j = 0; j < columns; j++) {
                    Cell cell = readfirst.getCell(j, i);  //j在前 i 在后是根据excel下标来判断的
                    String s = cell.getContents();
                    if(s.equals("")){   //判断如果为空就跳过
                        isNull = true;
                        break;
                    }
                    if(j == 0){
                        studentInfo.setName(s);
                        System.out.println("name ====>"+s);
                    }else if(j == 1){
                        studentInfo.setStudentId(s);
                        System.out.println("id ====>"+s);
                        user.setNickname(s);
                        user.setUsername(s);
                    }else if(j == 2){
                        Long id = Long.valueOf(s);
                        studentInfo.setCollegeId(id);
                    }else if(j == 3){
                        studentInfo.setEmail(s);
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
                if(isNull){
                    break;
                }
                studentInfo.setCreatorName("系统管理员");

                this.saveStudentInfo(studentInfo);
                userService.saveUser(user);
                studentInfos.add(studentInfo);
                //userRoleService.insertBatchSingle(userService.getUserEidById(user.getUsername()),teacherRoleId);
            }
            rows = studentInfos.size();
        }
        catch (Exception e) {
            //throw new BusinessException("第"+i+"行第"+j+"列的数据格式有问题");
            e.printStackTrace();
        }
        return rows;
    }

    @Override
    public void saveStudentInfoMy(String studentId, String name, String major, String collegeId, String grade, String classGrade, String contactTel, String email, String remark) {
        if(remark.equals("1")) {
            remark = "本科生";
            this.studentInfoDao.saveStudentInfoMy(studentId, name, major, collegeId,
                    grade, classGrade, contactTel, email,remark);
        }else if(remark.equals("2")) {
            remark = "研究生";
            this.studentInfoDao.saveStudentInfoMy(studentId, name, major, collegeId,
                    grade, classGrade, contactTel, email,remark);
        }

    }


    //TODO:---------------验证-------------------

    private void validateFindStudentInfos(PageDTO pageDTO) {
        //TODO:请使用下面方法添加数据过滤条件
        List<String> roles = getLoginUser().getRoles();
        Iterator it = roles.iterator();
        while (it.hasNext()) {
            String r = it.next().toString();
            System.out.println("---findStudentInfos方法中------role,当前人角色: "+r);
            if (r.equals("admin")) {
                return;
            }
            if(r.equals("teacher")){
                return;
            }
        }
        pageDTO.addFilter("studentId", this.getUser().getNickname());
    }

    private void validateFindStudentInfosWithIdNameByName(String studentInfoName) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateStudentInfo()写法
    }


    private void validateFindAllStudentInfosWithIdName() {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateStudentInfo()写法
    }

    private void validateFindStudentInfosWithIdNameById(Long studentInfoId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateStudentInfo()写法
    }

    private void validateFindStudentInfo(Long studentInfoId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateStudentInfo()写法
    }

    private void validateFindStudentInfoWithForeignName(Long studentInfoId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateStudentInfo()写法
    }

    private void validateSaveStudentInfo(StudentInfo studentInfo) {
        //不为空判断
        if (studentInfo.getEid() != null || studentInfo.getCreatorId() != null || studentInfo.getCreateDatetime() != null) {
            throw new BusinessException("非法请求");
        }
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateStudentInfo()写法
    }

    private void validateUpdateStudentInfo(StudentInfo studentInfo) {
        //不为空判断
        if (studentInfo.getEid() == null) {
            throw new BusinessException("唯一标识不能为空");
        }
        //是否存在判断
        if (this.studentInfoDao.findStudentInfoTotalCount(PageDTO.create(StudentInfo.FIELD_ID, studentInfo.getEid())) == 0) {
            throw new BusinessException("修改的学生信息 " + studentInfo.getName() + " 不存在，修改失败，请重试或联系管理员");
        }
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateStudentInfo()写法
    }

    private void validateDeleteStudentInfo(Long studentInfoId) {
        //TODO:请完善数据校验规则和数据权限判断，如果有问题请抛出异常，参看下面validateUpdateStudentInfo()写法
    }

    @Override
    public boolean canDownloadAttachment(String formName, Long id) {
        return true;
    }
}
