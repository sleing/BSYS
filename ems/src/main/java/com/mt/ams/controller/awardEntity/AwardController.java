

package com.mt.ams.controller.awardEntity;

import com.mt.ams.dto.awardEntity.AwardEditDto;
import com.mt.ams.entity.awardEntity.Award;
import com.mt.ams.service.awardEntity.AwardService;
import com.mt.ams.service.awardeeEntity.AwardeeService;
import com.mt.ams.service.collegeEntity.CollegeService;
import com.mt.ams.service.competitionEntity.CompetitionService;
import com.mt.ams.service.instructorEntity.InstructorService;
import com.mt.ams.service.studentEntity.StudentInfoService;
import com.mt.ams.service.teacherEntity.TeacherInfoService;
import com.mt.ams.service.unitEntity.UnitService;
import com.mt.common.core.annotation.ApiPageParam;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.common.system.controller.FileController;
import com.mt.common.system.service.AttachmentService;
import com.mt.common.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.List;
import java.util.UUID;


@Api(tags = "获奖登记")
@RestController
@RequestMapping("/api/ams/awardEntity/Award")
@CrossOrigin(allowCredentials = "true")
public class AwardController {
    private static Logger logger = LoggerFactory.getLogger(AwardController.class);


    @Autowired
    private AwardService awardService;
    @Autowired
    private InstructorService instructorService;
    @Autowired
    private AwardeeService awardeeService;
    @Autowired
    private StudentInfoService studentInfoService;
    @Autowired
    private CollegeService collegeService;
    @Autowired
    private CompetitionService competitionService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private TeacherInfoService teacherInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    private FileController fileController;
    @Autowired
    private AttachmentService attachmentService;

    /**
     * 根据分页参数查询获奖登记集合
     *
     * @param pageDTO 分页条件
     */
    @PreAuthorize("hasAuthority('ams:awardEntity:Award:view')")
    @ApiOperation("分页查询获奖登记")
    @ApiPageParam
    @PostMapping("/findAwards")
    public PageResultDTO findAwards(@RequestBody PageDTO pageDTO) {
        return this.awardService.findAwards(pageDTO);
    }

    /**
     * 根据ID查询指定的获奖登记
     *
     * @param awardId Id
     */
    @PreAuthorize("hasAuthority('ams:awardEntity:Award:view')")
    @ApiOperation("根据id查询获奖登记")
    @ApiPageParam
    @PostMapping("/findAward")
    public Award findAward(@RequestParam Long awardId) {
        return this.awardService.findAward(awardId);
    }

    /**
     * 根据ID查询指定的获奖登记(包含外键名称)
     *
     * @param awardId Id
     */
    @PreAuthorize("hasAuthority('ams:awardEntity:Award:view')")
    @ApiOperation("根据ID查询指定的获奖登记(包含外键名称)")
    @PostMapping("/findAwardForView")
    public Award findAwardForView(@RequestParam Long awardId) {
        return this.awardService.findAwardWithForeignName(awardId);
    }

    /**
     * 根据ID查询指定的获奖登记(包含获奖登记和外键名称)
     *
     * @param awardId Id
     */
    @PreAuthorize("hasAuthority('ams:awardEntity:Award:view')")
    @ApiOperation("根据ID查询指定的获奖登记(包含获奖登记和外键名称)")
    @PostMapping("/findAwardForEdit")
    public AwardEditDto findAwardForEdit(@RequestParam Long awardId) {
        AwardEditDto awardEditDto = new AwardEditDto();
        awardEditDto.setAward(this.awardService.findAwardWithForeignName(awardId));

        this.prepareAwardEditDto(awardEditDto);

        return awardEditDto;
    }

    /**
     * 根据ID查询指定的获奖登记(只提取ID 和 Name)
     *
     * @param awardId Id
     */
    @PreAuthorize("hasAuthority('ams:awardEntity:Award:view')")
    @ApiOperation("根据ID查询指定的获奖登记(只提取ID 和 Name)")
    @PostMapping("/findAwardsWithIdNameById")
    public Award findAwardsWithIdNameById(@RequestParam Long awardId) {
        return this.awardService.findAwardsWithIdNameById(awardId);
    }

    /**
     * 根据名称查询获奖登记集合(只提取ID 和 Name)
     *
     * @param awardName 名称
     */
    @PreAuthorize("hasAuthority('ams:awardEntity:Award:view')")
    @ApiOperation("根据名称查询获奖登记集合(只提取ID 和 Name)")
    @PostMapping("/findAwardsWithIdNameByName")
    public List<Award> findAwardsWithIdNameByName(@RequestParam String awardName) {
        return this.awardService.findAwardsWithIdNameByName(awardName);
    }


    /**
     * 创建新的获奖登记
     */
    @PreAuthorize("hasAuthority('ams:awardEntity:Award:add')")
    @ApiOperation("创建新的获奖登记")
    @PostMapping("/createAward")
    public AwardEditDto createAward() {
        AwardEditDto awardEditDto = new AwardEditDto();
        awardEditDto.setAward(new Award());

        this.prepareAwardEditDto(awardEditDto);
        return awardEditDto;
    }

    //文件上传
    @PreAuthorize("hasAuthority('ams:awardEntity:Award:view')")
    @ApiOperation("分页查询获奖登记")
    @ApiPageParam
    @PostMapping("/fileUrl")
    public String fileUrl(Long eid) {
        return this.awardService.fileUrl(eid);
    }

    private void prepareAwardEditDto(AwardEditDto awardEditDto) {
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
        awardEditDto.setFirstAwardeeStudentInfos(this.studentInfoService.findAllStudentInfosWithIdName());
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
        awardEditDto.setCollegeColleges(this.collegeService.findAllCollegesWithIdName());
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
        awardEditDto.setCompetitionNameCompetitions(this.competitionService.findAllCompetitionsWithIdName());
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
        awardEditDto.setOrganizationUnits(this.unitService.findAllUnitsWithIdName());
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
        awardEditDto.setFirstInstructorTeacherInfos(this.teacherInfoService.findAllTeacherInfosWithIdName());
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
        awardEditDto.setReviewerUsers(this.userService.findAllUsersWithIdName());
    }

    /**
     * 新增保存获奖登记
     *
     * @param award 实体对象
     */
    @PreAuthorize("hasAuthority('ams:awardEntity:Award:add')")
    @ApiOperation("新增保存获奖登记")
    @PostMapping("/saveAward")
    public Award saveAward(@RequestBody Award award) {
        return this.awardService.saveAward(award);
    }

    /**
     * 修改保存获奖登记
     *
     * @param award 实体对象
     */
    @PreAuthorize("hasAuthority('ams:awardEntity:Award:update')")
    @ApiOperation("修改保存获奖登记")
    @PostMapping("/updateAward")
    public Award updateAward(@RequestBody Award award) {
        return this.awardService.updateAward(award);
    }

    /**
     * 根据ID删除获奖登记
     *
     * @param awardId ID
     */
    @PreAuthorize("hasAuthority('ams:awardEntity:Award:remove')")
    @ApiOperation("根据ID删除获奖登记")
    @PostMapping("/deleteAward")
    public void deleteAward(@RequestParam Long awardId) {

        this.awardeeService.deleteAwardeeByAwardId(awardId);
        this.instructorService.deleteInstructorByAwardId(awardId);

//        if(this.attachmentService.findAttachmentByFormIdAndFromName(String.valueOf(awardId),"award") != null){
//            //删除上传的附件
//            this.attachmentService.deleteAttachmentByFormIdAndFromName(String.valueOf(awardId),"award");
//        }
        this.attachmentService.deleteUploadedFile(String.valueOf(awardId),"award");

        this.awardService.deleteAward(awardId);

    }


    /**
     * 根据分页参数查询获奖登记集合
     *
     * @param pageDTO 分页条件
     */
    @PreAuthorize("hasAuthority('ams:awardEntity:Award:view')")
    @ApiOperation("分页查询获奖登记")
    @ApiPageParam
    @PostMapping("/MyfindAwards")
    public PageResultDTO MyfindAwards(@RequestBody PageDTO pageDTO) {
        return this.awardService.MyfindAwards(pageDTO);
    }

    @PreAuthorize("hasAuthority('ams:awardEntity:Award:view')")
    @ApiOperation("分页查询获奖登记")
    @ApiPageParam
    @PostMapping("/myUpdate")
    public void myUpdate(Long awardId) {
        this.awardService.myUpdate(awardId);
    }

    @PreAuthorize("hasAuthority('ams:awardEntity:Award:view')")
    @ApiOperation("分页查询获奖登记")
    @ApiPageParam
    @PostMapping("/auditForCon")
    public void auditForCon(Long awardId, String remarkContent) {
        this.awardService.auditForCon(awardId, remarkContent);
    }

    @PreAuthorize("hasAuthority('ams:awardEntity:Award:remove')")
    @ApiOperation("文件上传")
    @PostMapping("/file")
    public void upload(@RequestParam MultipartFile avatar) throws IOException {

        String fileName = avatar.getOriginalFilename();
        UUID uuid = UUID.randomUUID();
        String url = avatar.getOriginalFilename().replace(".", "") + uuid;

        System.out.println(url);
    }

    //下载模板
    @PreAuthorize("hasAuthority('ams:awardEntity:Award:view')")
    @ApiOperation("分页查询获奖登记")
    @ApiPageParam
    @GetMapping("/downloadMould")
    public HttpServletResponse downloadMould(HttpServletResponse response, HttpServletRequest request) {
        try {
            String filePath = "C:\\upload\\mould.zip";
            File file = new File(filePath);
            // 获取文件名
            String filename = file.getName().toString();
            //通过流把文件内容写入到客户端
            InputStream fis = new BufferedInputStream(new FileInputStream(filePath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(), "ISO-8859-1"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }

    //下载资源包
    @PreAuthorize("hasAuthority('ams:awardEntity:Award:view')")
    @ApiOperation("分页查询获奖登记")
    @ApiPageParam
    @GetMapping("/downloadFile")
    public HttpServletResponse downloadFile(HttpServletResponse response, HttpServletRequest request) {
        try {
            String id = request.getParameter("name").toString();
            Long rowId = Long.valueOf(id);
            String path = this.awardService.resourcesUrl(rowId);
            //http://localhost:8081/api/file/resources20220518/out.zip
            String[] paths = path.split("/");
            // 要下载的文件的全路径名
            String timePath = paths[5].substring(9, 17);
            String filePath = "C:\\upload\\file\\" + timePath + "\\" + paths[6];
            File file = new File(filePath);
            // 获取文件名
            String filename = file.getName().toString();
            //通过流把文件内容写入到客户端
            InputStream fis = new BufferedInputStream(new FileInputStream(filePath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            if (fis != null) {
                fis.close();
            }
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(), "ISO-8859-1"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            if (toClient != null) {
                toClient.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }

    /**
     * 更新审核人信息
     */
    @PreAuthorize("hasAuthority('ams:awardEntity:Award:view')")
    @ApiOperation("更新审核人信息")
    @ApiPageParam
    @PostMapping("/updateReviewer")
    public void updateReviewer(@RequestParam("userId") String Id,@RequestParam("rowId") String eid) {
        Long userId = Long.valueOf(Id);
        Long Aeid = Long.valueOf(eid);

        this.awardService.updateReviewer(userId,Aeid);

    }


}

