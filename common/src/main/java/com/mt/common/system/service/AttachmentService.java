package com.mt.common.system.service;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.common.system.entity.Attachment;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface AttachmentService {
    /**
     * 根据分页参数查询附件集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findAttachments(PageDTO pageDTO);

    /**
     * 查询全部附件集合
     *
     */
    public List<Attachment> findAllAttachments();

    /**
     * 根据名称查询附件集合(只提取ID 和 Name)
     *
     * @param attachmentName 名称
     */
    public List<Attachment> findAttachmentsWithIdNameByName(String attachmentName);

    /**
     * 查询所有附件集合(只提取ID 和 Name)
     *
     */
    public List<Attachment> findAllAttachmentsWithIdName();

    /**
     * 根据ID查询指定的附件(只提取ID 和 Name)
     *
     * @param attachmentId Id
     */
    public Attachment findAttachmentsWithIdNameById(Long attachmentId);

    /**
     * 根据ID查询指定的附件
     *
     * @param attachmentId Id
     */
    public Attachment findAttachment(Long attachmentId);

    /**
     * 根据ID查询指定的附件(包含外键)
     *
     * @param attachmentId Id
     */
    public Attachment findAttachmentWithForeignName(Long attachmentId);

    /**
     * 新增附件
     *
     * @param attachment 实体对象
     */
    public Attachment saveAttachment(Attachment attachment);

    /**
     * 更新附件
     *
     * @param attachment 实体对象
     */
    public Attachment updateAttachment(Attachment attachment);

    /**
     * 根据ID删除附件
     *
     * @param attachmentId ID
     */
    public void deleteAttachment(Long attachmentId);
    /**
     * 根据attachmentId删除附件
     *
     * @param attachmentId ID
     */
    public void deleteAttachmentOfAttId(String attachmentId);
    /**
     * 根据attachmentId查询指定的附件
     *
     * @param attachmentId Id
     */
    public Attachment findAttachmentByAttachmentId(@Param("attachmentId") String attachmentId);


    //d4移植
    public Long uploadMultipartFile(MultipartFile file, Attachment attachment) throws IOException;
    public Long uploadMultipartFileMy(MultipartFile file, Attachment attachment) throws IOException;


    public OutputStream downloadAttachment(String attachmentId, boolean isOnline) throws UnsupportedEncodingException;

    public List<Attachment> findAllUploadedFilesByIdAndName(String id, String name);

    public List<Attachment> findAllUploadedFilesByIdAndNameMy(String id, String name);

    public List<Attachment> findFileOfResource(String id, String name);

    public List<Attachment> findFileOfPicture(String id, String name);

    public List<Attachment> findUploadedFileByFormIdAndFormNameAndGroupName(String id, String name,String groupName);

    public Integer deleteUploadedFile(String formId, String formName);

    public Integer deleteUploadedFileByGroupName(String formId, String formName,String groupName);

    public  void getZipFile(String[] fileAddress, String[] fileNames, String compressFileName)  throws IOException ;

    public List<Attachment> findAttachmentByFormIdAndFromName(String formId,String formName);

    public List<Attachment> findAttachmentsWithIdNameByAwardId(Long awardId);

    void mutiDownload() throws IOException;
}
