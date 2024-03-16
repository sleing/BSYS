package com.mt.common.system.mapper;

import com.mt.common.core.web.base.PageDTO;
import com.mt.common.system.entity.Attachment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
@Component(value = "attachmentDao")
public interface AttachmentDao {

    /**
    * 根据分页参数查询附件集合
    *
    * @param pageDTO 分页条件
    */
    public List<Attachment> findAttachments(PageDTO pageDTO);

    /**
    * 查询全部附件集合
    *
    */
    public List<Attachment> findAllAttachments();

    /**
    * 查询所有附件集合(只提取ID 和 Name)
    *
    */
    public List<Attachment> findAllAttachmentsWithIdName();

    /**
    * 根据名称查询附件集合(只提取ID 和 Name)
    *
    * @param attachmentName 名称
    */
    public List<Attachment> findAttachmentsWithIdNameByName(@Param("attachmentName") String attachmentName);

    /**
    * 根据ID查询指定的附件(只提取ID 和 Name)
    *
    * @param attachmentId Id
    */
    public Attachment findAttachmentsWithIdNameById(@Param(" attachmentId") Long attachmentId);

    /**
    * 根据分页参数查询附件集合的数量
    *
    * @param pageDTO 分页条件
    */
    public Long findAttachmentTotalCount(PageDTO pageDTO);

    /**
    * 根据ID查询指定的附件
    *
    * @param attachmentId Id
    */
    public Attachment findAttachment(@Param("attachmentId") Long attachmentId);

    /**
    * 根据ID查询指定的附件(包含外键)
    *
    * @param attachmentId Id
    */
    public Attachment findAttachmentWithForeignName(@Param("attachmentId") Long attachmentId);

    /**
    * 新增附件
    *
    * @param attachment 实体对象
    */
    public Long saveAttachment(Attachment attachment);

    /**
    * 更新附件
    *
    * @param attachment 实体对象
    */
    public Long updateAttachment(Attachment attachment);

    /**
    * 根据ID删除附件
    *
    * @param attachmentId ID
    */
    public Long deleteAttachment(@Param("attachmentId") Long attachmentId);

    /**
     * 根据attachmentId删除附件
     *
     * @param attachmentId ID
     */
    public Long deleteAttachmentOfAttId(@Param("attachmentId") String attachmentId);

    /**
     * 根据attachmentId查询指定的附件
     *
     * @param attachmentId Id
     */
    public Attachment findAttachmentByAttachmentId(@Param("attachmentId") String attachmentId);

    //从d4移植
    public Integer deleteUploadedFile(@Param("formId") String formId, @Param("formName") String formName);

    public String getFileRealName(String fileAddress);

    public List<Attachment> findAllUploadedFilesByIdAndName(Attachment attachment);

    public List<Attachment> findAllUploadedFilesByIdAndNameMy(Attachment attachment);

    public Long findLicenseByIdAndName(Attachment attachment);

    public Attachment findAttachmentByIdAndName(@Param("formId") String formId, @Param("formName") String formName,@Param("remark") String remark);
    //根据表和eid进行查询
    public List<Attachment> findUploadedFileByFormIdAndFormName(@Param("formId")String formId, @Param("formName") String formName);

    public List<Attachment> findUploadedFileByFormIdAndFormNameAndGroupName(@Param("formId")String formId, @Param("formName")String formName, @Param("groupName")String groupName);

    public Integer deleteUploadedFileByGroupName(@Param("formId")String formId, @Param("formName")String formName, @Param("groupName")String groupName);

    public List<Attachment> findAttachmentByFormIdAndFromName(@Param("formId") String formId,@Param("formName") String formName);

    public List<Attachment> findAttachmentsWithIdNameByAwardId(Long awardId);
}
