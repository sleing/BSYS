

package com.mt.common.system.controller;

import com.mt.common.core.annotation.ApiPageParam;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.common.system.dto.system.AttachmentEditDto;
import com.mt.common.system.dto.system.FileUploadDto;
import com.mt.common.system.entity.Attachment;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Api(tags = "附件")
@RestController
@RequestMapping("/api/erp/system/Attachment")
@CrossOrigin(allowCredentials = "true")
public class AttachmentController {
    private static Logger logger = LoggerFactory.getLogger(AttachmentController.class);

    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private UserService userService;

    /**
     * 根据分页参数查询附件集合
     *
     * @param pageDTO 分页条件
     */
    @PreAuthorize("hasAuthority('system:attachment:view')")
    @ApiOperation("分页查询附件")
    @ApiPageParam
    @PostMapping("/findAttachments")
    public PageResultDTO findAttachments(@RequestBody PageDTO pageDTO) {
        return this.attachmentService.findAttachments(pageDTO);
    }

    /**
     * 根据ID查询指定的附件
     *
     * @param attachmentId Id
     */
    @PreAuthorize("hasAuthority('system:attachment:view')")
    @ApiOperation("根据id查询附件")
    @ApiPageParam
    @PostMapping("/findAttachment")
    public Attachment findAttachment(@RequestParam Long attachmentId) {
        return this.attachmentService.findAttachment(attachmentId);
    }

    /**
     * 根据ID查询指定的附件(包含外键名称)
     *
     * @param attachmentId Id
     */
    @PreAuthorize("hasAuthority('system:attachment:view')")
    @ApiOperation("根据ID查询指定的附件(包含外键名称)")
    @PostMapping("/findAttachmentForView")
    public Attachment findAttachmentForView(@RequestParam Long attachmentId) {
        return this.attachmentService.findAttachmentWithForeignName(attachmentId);
    }

    /**
     * 根据ID查询指定的附件(包含附件和外键名称)
     *
     * @param attachmentId Id
     */
    @PreAuthorize("hasAuthority('system:attachment:view')")
    @ApiOperation("根据ID查询指定的附件(包含附件和外键名称)")
    @PostMapping("/findAttachmentForEdit")
    public AttachmentEditDto findAttachmentForEdit(@RequestParam Long attachmentId) {
        AttachmentEditDto attachmentEditDto = new AttachmentEditDto();
        attachmentEditDto.setAttachment(this.attachmentService.findAttachmentWithForeignName(attachmentId));
        this.prepareAttachmentEditDto(attachmentEditDto);
        return attachmentEditDto;
    }

    /**
     * 根据ID查询指定的附件(只提取ID 和 Name)
     *
     * @param attachmentId Id
     */
    @PreAuthorize("hasAuthority('system:attachment:view')")
    @ApiOperation("根据ID查询指定的附件(只提取ID 和 Name)")
    @PostMapping("/findAttachmentsWithIdNameById")
    public Attachment findAttachmentsWithIdNameById(@RequestParam Long attachmentId) {
        return this.attachmentService.findAttachmentsWithIdNameById(attachmentId);
    }

    /**
     * 根据名称查询附件集合(只提取ID 和 Name)
     *
     * @param attachmentName 名称
     */
    @PreAuthorize("hasAuthority('system:attachment:view')")
    @ApiOperation("根据名称查询附件集合(只提取ID 和 Name)")
    @PostMapping("/findAttachmentsWithIdNameByName")
    public List<Attachment> findAttachmentsWithIdNameByName(@RequestParam String attachmentName) {
        return this.attachmentService.findAttachmentsWithIdNameByName(attachmentName);
    }


    /**
     * 创建新的附件
     */
    @PreAuthorize("hasAuthority('system:attachment:add')")
    @ApiOperation("创建新的附件")
    @PostMapping("/createAttachment")
    public AttachmentEditDto createAttachment() {
        AttachmentEditDto attachmentEditDto = new AttachmentEditDto();
        attachmentEditDto.setAttachment(new Attachment());
        this.prepareAttachmentEditDto(attachmentEditDto);
        return attachmentEditDto;
    }

    private void prepareAttachmentEditDto(AttachmentEditDto attachmentEditDto) {
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
        attachmentEditDto.setUploadUserUsers(this.userService.findAllUsersWithIdName());
    }

    /**
     * 新增保存附件
     *
     * @param fileUploadDto
     * @param files
     * @param request
     * @return
     * @throws IOException
     */
//	@PreAuthorize("hasAuthority('system:attachment:add')")
//	/*要写*/
    @ApiOperation("新增保存附件")
    @PostMapping("/saveAttachment")
    public Attachment saveAttachment(@RequestPart("fileUploadDto") FileUploadDto fileUploadDto, @RequestPart("files") List<MultipartFile> files, HttpServletRequest request) throws IOException {
        Attachment a = new Attachment();
        a.setAssociateFormId(fileUploadDto.getAssociateFormId());//行id
        a.setAssociateFormName(fileUploadDto.getAssociateFormName());//表名
        List<Long> eids = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            Long id = attachmentService.uploadMultipartFile(files.get(i), a);
            eids.add(id);
        }
        //返回第一个attachment的eid
        Attachment attach = new Attachment();
        attach.setEid(eids.get(0));
        return attach;
    }

    @ApiOperation("新增保存附件")
    @PostMapping("/saveAttachmentMy")
    public Attachment saveAttachmentMy(@RequestPart("fileUploadDto") FileUploadDto fileUploadDto, @RequestPart("files") List<MultipartFile> files, HttpServletRequest request) throws IOException {
        Attachment a = new Attachment();
        a.setAssociateFormId(fileUploadDto.getAssociateFormId());//行id
        a.setAssociateFormName(fileUploadDto.getAssociateFormName());//表名
        List<Long> eids = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            Long id = attachmentService.uploadMultipartFile(files.get(i), a);
            eids.add(id);
        }
        //返回第一个attachment的eid
        Attachment attach = new Attachment();
        attach.setEid(eids.get(0));
        return attach;
    }



    @PostMapping("/saveAttachmentMsg")
    public Attachment saveAttachmentMsg(@RequestBody Attachment attachment) {
        return this.attachmentService.saveAttachment(attachment);
    }

    /**
     * 修改保存附件
     *
     * @param attachment 实体对象
     */
    @PreAuthorize("hasAuthority('system:attachment:update')")
    @ApiOperation("修改保存附件")
    @PostMapping("/updateAttachment")
    public Attachment updateAttachment(@RequestBody Attachment attachment) {
        return this.attachmentService.updateAttachment(attachment);
    }

    /**
     * 根据ID删除附件
     *
     * @param attachmentId ID
     */
    @PreAuthorize("hasAuthority('system:attachment:remove')")
    @ApiOperation("根据ID删除附件")
    @PostMapping("/deleteAttachment")
    public void deleteAttachment(@RequestParam Long attachmentId) {
        this.attachmentService.deleteAttachment(attachmentId);
    }

    /**
     * 根据attachmentId删除附件
     *
     * @param attachmentId
     */
//	@PreAuthorize("hasAuthority('system:attachment:remove')")
    @ApiOperation("根据attachmentId删除附件")
    @PostMapping("/deleteAttachmentOfAttId")
    public void deleteAttachmentOfAttId(@RequestParam String attachmentId) {
        this.attachmentService.deleteAttachmentOfAttId(attachmentId);
    }


    @GetMapping("/findAttachmentByFormIdAndFromName")
    public List<Attachment> findAttachmentByFormIdAndFromName(String formId, String formName) {
        return this.attachmentService.findAttachmentByFormIdAndFromName(formId, formName);
    }

    /*文件下载*/
//String path="D:\\spring_files\\20180822\\1529499953475_ec-mh_dev201808221703475198.sql";
    @GetMapping("/download")
    public void downLoad(boolean isOnline, String attachmentId) throws Exception {
        attachmentService.downloadAttachment(attachmentId, isOnline);
    }


    /*通过id和name找到所有的附件*/
    @GetMapping("/findAllUploadedFilesByIdAndName")
    public List<Attachment> findAllUploadedFilesByIdAndName(String id, String name) {
        return attachmentService.findAllUploadedFilesByIdAndName(id, name);
    }

    /*通过id和name找到所有的附件*/
    @GetMapping("/findAllUploadedFilesByIdAndNameMy")
    public List<Attachment> findAllUploadedFilesByIdAndNameMy(String id, String name) {
        return attachmentService.findAllUploadedFilesByIdAndNameMy(id, name);
    }

    //返回已上传的附件(资源包)
    @GetMapping("/findFileOfResource")
    public List<Attachment> findFileOfResource(String id, String name) {
        return attachmentService.findFileOfResource(id, name);
    }

    //返回已上传的附件(图片)
    @GetMapping("/findFileOfPicture")
    public List<Attachment> findFileOfPicture(String id, String name) {
        return attachmentService.findFileOfPicture(id, name);
    }

    /*通过表单id和name删除附件*/
    @GetMapping("/deleteUploadedFilesByIdAndName")
    public Integer deleteUploadedFile(String formId, String formName) {
        return attachmentService.deleteUploadedFile(formId, formName);
    }

    /**
     * 根据表单id，表单name和分组名称查询所有附件
     *
     * @param formId
     * @param formName
     * @param groupName
     * @return
     */
    @GetMapping("/findUploadedFileByFormIdAndFormNameAndGroupName")
    public List<Attachment> findUploadedFileByFormIdAndFormNameAndGroupName(String formId, String formName, String groupName) {
        return this.attachmentService.findUploadedFileByFormIdAndFormNameAndGroupName(formId, formName, groupName);
    }

    /**
     * 根据awardID查询指定的附件(只提取ID 和 Name)
     *
     * @param awardId Id
     */
    /*要写*/
    @PostMapping("/findAttachmentsWithIdNameByAwardId")
    public List<Attachment> findAttachmentsWithIdNameByAwardId(@RequestParam Long awardId) {
        return this.attachmentService.findAttachmentsWithIdNameByAwardId(awardId);
    }

    /**
     * 查询所有文件
     */
    @PostMapping("/findAllFiles")
    public List<Attachment>findAllFiles(){
        return this.attachmentService.findAllAttachments();
    }

    /**
     * 文件批量下载
     */
    @GetMapping("/mutiDownload")
    public void mutiDownload() throws IOException {
        this.attachmentService.mutiDownload();
    }

}

