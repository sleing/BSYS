package com.mt.common.system.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.mt.common.core.config.Config;
import com.mt.common.core.utils.SpringContextUtils;
import com.mt.common.core.utils.StringUtils;
import com.mt.common.core.web.PageParam;
import com.mt.common.system.entity.Organization;
import com.mt.common.system.service.OrganizationService;
import com.mt.common.system.mapper.AttachmentDao;

import com.mt.common.core.exception.BusinessException;
import com.mt.common.core.web.base.PageDTO;
import com.mt.common.core.web.base.PageResultDTO;
import com.mt.common.core.web.base.BaseEntity;
import com.mt.common.core.web.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.mt.common.system.entity.Attachment;
import com.mt.common.system.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Service
@Transactional
public class AttachmentServiceBean extends BaseService implements AttachmentService {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private AttachmentDao attachmentDao;

    @Autowired
    private HttpServletResponse response;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private OrganizationService organizationService;

    /**
     * 根据分页参数查询附件集合
     *
     * @param pageDTO 分页条件
     */
    @Override
    public PageResultDTO findAttachments(PageDTO pageDTO) {
        pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
        List<Attachment> attachmentDTOS = this.attachmentDao.findAttachments(pageDTO);
        Long totalCount = this.attachmentDao.findAttachmentTotalCount(pageDTO);

        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setTotalCount(totalCount);
        pageResultDTO.setDatas(attachmentDTOS);

        return pageResultDTO;
    }

    /**
     * 查询全部附件集合
     */
    @Override
    public List<Attachment> findAllAttachments() {
        return this.attachmentDao.findAllAttachments();
    }

    /**
     * 查询所有附件集合(只提取ID 和 Name)
     */
    @Override
    public List<Attachment> findAllAttachmentsWithIdName() {
        return this.attachmentDao.findAllAttachmentsWithIdName();
    }

    /**
     * 根据名称查询附件集合(只提取ID 和 Name)
     *
     * @param attachmentName 名称
     */
    @Override
    public List<Attachment> findAttachmentsWithIdNameByName(String attachmentName) {
        return this.attachmentDao.findAttachmentsWithIdNameByName(attachmentName);
    }

    /**
     * 根据ID查询指定的附件(只提取ID 和 Name)
     *
     * @param attachmentId Id
     */
    @Override
    public Attachment findAttachmentsWithIdNameById(Long attachmentId) {
        return this.attachmentDao.findAttachmentsWithIdNameById(attachmentId);
    }

    /**
     * 根据ID查询指定的附件
     *
     * @param attachmentId Id
     */
    @Override
    public Attachment findAttachment(Long attachmentId) {
        return this.attachmentDao.findAttachment(attachmentId);
    }

    /**
     * 根据ID查询指定的附件(包含外键)
     *
     * @param attachmentId Id
     */
    @Override
    public Attachment findAttachmentWithForeignName(Long attachmentId) {
        return this.attachmentDao.findAttachmentWithForeignName(attachmentId);
    }

    /**
     * 新增附件
     *
     * @param attachment 实体对象
     */
    @Override
    public Attachment saveAttachment(Attachment attachment) {
        //TODO:请在此校验参数的合法性
        this.setSavePulicColumns(attachment);
        Long rows = this.attachmentDao.saveAttachment(attachment);
        if (rows != 1) {
            String error = "新增保存附件出错，数据库应该返回1,但返回了 " + rows;
            throw new BusinessException(error);
        }
        return attachment;
    }

    /**
     * 更新附件
     *
     * @param attachment 实体对象
     */
    @Override
    public Attachment updateAttachment(Attachment attachment) {
        //TODO:请在此校验参数的合法性

        Long rows = this.attachmentDao.updateAttachment(attachment);
        if (rows != 1) {
            String error = "修改保存附件出错，数据库应该返回1,但返回了 " + rows + ",数据可能被删除";
            throw new BusinessException(error);
        }
        return attachment;
    }

    /**
     * 根据ID删除附件
     *
     * @param attachmentId ID
     */
    @Override
    public void deleteAttachment(Long attachmentId) {
        Map<Class<? extends BaseEntity>, EntityUsage> entityUsageMap = this.checkForeignEntity(Attachment.class, attachmentId);
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

        Long rows = this.attachmentDao.deleteAttachment(attachmentId);
        if (rows != 1) {
            String error = "删除附件出错，数据可能已经被删除";
            throw new BusinessException(error);
        }
    }

    /**
     * 根据attachmentId删除附件
     *
     * @param attachmentId ID
     */
    @Override
    public void deleteAttachmentOfAttId(String attachmentId) {
        //通过attachmentId获取附件表
        Attachment attachment = attachmentDao.findAttachmentByAttachmentId(attachmentId);
        Map<Class<? extends BaseEntity>, EntityUsage> entityUsageMap = this.checkForeignEntity(Attachment.class, attachment.getEid());
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
        Long rows = this.attachmentDao.deleteAttachmentOfAttId(attachmentId);
        if (rows != 1) {
            String error = "删除附件出错，数据可能已经被删除";
            throw new BusinessException(error);
        } else {
            if (!(attachment.getAttachmentAddr().equals("") || attachment.getAttachmentAddr() == null)) {
                //删除文件
                deleteFile(Config.uploadFileAddress + attachment.getAttachmentAddr());
            }
        }
    }

    /**
     * 根据attachmentId查找附件
     *
     * @param attachmentId Id
     * @return
     */
    @Override
    public Attachment findAttachmentByAttachmentId(String attachmentId) {
        return this.attachmentDao.findAttachmentByAttachmentId(attachmentId);
    }


    //上传附件
    @Override
    public Long uploadMultipartFile(MultipartFile file, Attachment attachment) throws IOException {
        if (file == null) {
            throw new BusinessException("必须选择一个文件");
        }
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String ymd = dateFormat.format(date);
        //获取当前登陆单位的id
        Long orgCode = this.getCurrentOrganizationId();
        String fileDirectory = Config.uploadFileAddress + "/" + orgCode + "/" + ymd;
        if (!fileIsExists(fileDirectory)) {
            File f = new File(fileDirectory);
            if (!f.mkdirs()) {
                throw new BusinessException("文件夹创建失败,请检查配置路径是否正确");
            }
        }

        if (attachment.getAssociateFormId() == "" || attachment.getAssociateFormId() == null || attachment.getAssociateFormName() == ""
                || attachment.getAssociateFormName() == null) {
            throw new BusinessException("关联表单Id和关联表单名称不能为空");
        }

        String[] fileInfo = getFileInfo(file);
        String toPrefix = fileInfo[0] + generateSuffix();
        String toSuffix = fileInfo[1];
        String logicalFileName = toPrefix + toSuffix;
        if (!attachment.getAssociateFormName().equals("sys_organizationRegit")) {
//            this.setSavePulicColumns(attachment);
            attachment.setOrganizationId(this.getCurrentOrganizationId());
            //获取机构名称并赋值
            PageParam<Organization> pageParam = new PageParam<>();
            pageParam.put("eid", this.getCurrentOrganizationId());
            List<Organization> records = organizationService.listAll(pageParam.getNoPageParam());
            attachment.setOrganizationName(pageParam.getOne(records).getOrganizationName());
            attachment.setCreateDatetime(new Date());
        } else {
            attachment.setCreateDatetime(new Date());
        }
        attachment.setUploadUserId(this.getLoginUserId());
        attachment.setUploadTime(date);
        attachment.setAssociateSize(file.getSize());
        attachment.setAttachmentRealName(file.getOriginalFilename());
        attachment.setAttachmentLogicalName(logicalFileName);
        String attachmentAddr = "/" + orgCode + "/" + ymd + "/" + logicalFileName;
        //attachmentId加密
        String attachmentId = SecureUtil.md5(attachmentAddr + RandomUtil.randomLong());
        attachment.setAttachmentId(attachmentId);
        attachment.setAttachmentAddr(attachmentAddr);
        if (attachment.getIsEffective() == null) {
            attachment.setIsEffective(true);
        }
        try {
            file.transferTo(new File(Config.uploadFileAddress + attachmentAddr));
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("请检查磁盘大小是否充足或文件夹是否存在");
        }
        Long rows;
        // 判断文件是否更新
        Long eid = isUpdate(attachment);
        if (eid != null) {
            attachment.setEid(eid);
            rows = this.attachmentDao.updateAttachment(attachment);
        } else {
            rows = this.attachmentDao.saveAttachment(attachment);
        }

        if (rows != 1) {
            String error = "新增保存附件出错，数据库应该返回1,但返回了 " + rows;
            throw new BusinessException(error);
        }
        return attachment.getEid();
    }

    //上传附件
    @Override
    public Long uploadMultipartFileMy(MultipartFile file, Attachment attachment) throws IOException {
        if (file == null) {
            throw new BusinessException("必须选择一个文件");
        }
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String ymd = dateFormat.format(date);
        //获取当前登陆单位的id
        Long orgCode = this.getCurrentOrganizationId();
        String fileDirectory = Config.uploadFileAddress + "/" + orgCode + "/" + ymd;
        if (!fileIsExists(fileDirectory)) {
            File f = new File(fileDirectory);
            if (!f.mkdirs()) {
                throw new BusinessException("文件夹创建失败,请检查配置路径是否正确");
            }
        }

        if (attachment.getAssociateFormId() == "" || attachment.getAssociateFormId() == null || attachment.getAssociateFormName() == ""
                || attachment.getAssociateFormName() == null) {
            throw new BusinessException("关联表单Id和关联表单名称不能为空");
        }

        String[] fileInfo = getFileInfo(file);
        String toPrefix = fileInfo[0] + generateSuffix();
        String toSuffix = fileInfo[1];
        String logicalFileName = toPrefix + toSuffix;
        if (!attachment.getAssociateFormName().equals("sys_organizationRegit")) {
//            this.setSavePulicColumns(attachment);
            attachment.setOrganizationId(this.getCurrentOrganizationId());
            //获取机构名称并赋值
            PageParam<Organization> pageParam = new PageParam<>();
            pageParam.put("eid", this.getCurrentOrganizationId());
            List<Organization> records = organizationService.listAll(pageParam.getNoPageParam());
            attachment.setOrganizationName(pageParam.getOne(records).getOrganizationName());
            attachment.setCreateDatetime(new Date());
        } else {
            attachment.setCreateDatetime(new Date());
        }
        attachment.setUploadUserId(this.getLoginUserId());
        attachment.setUploadTime(date);
        attachment.setAssociateSize(file.getSize());
        attachment.setAttachmentRealName(file.getOriginalFilename());
        attachment.setAttachmentLogicalName(logicalFileName);
        String attachmentAddr = "/" + orgCode + "/" + ymd + "/" + logicalFileName;
        //attachmentId加密
        String attachmentId = SecureUtil.md5(attachmentAddr + RandomUtil.randomLong());
        attachment.setAttachmentId(attachmentId);
        attachment.setAttachmentAddr(attachmentAddr);
        if (attachment.getIsEffective() == null) {
            attachment.setIsEffective(true);
        }
        try {
            file.transferTo(new File(Config.uploadFileAddress + attachmentAddr));
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("请检查磁盘大小是否充足或文件夹是否存在");
        }
        Long rows;
        // 判断文件是否更新
        Long eid = isUpdate(attachment);
        if (eid != null) {
            attachment.setEid(eid);
            rows = this.attachmentDao.updateAttachment(attachment);
        } else {
            rows = this.attachmentDao.saveAttachment(attachment);
        }

        if (rows != 1) {
            String error = "新增保存附件出错，数据库应该返回1,但返回了 " + rows;
            throw new BusinessException(error);
        }
        return attachment.getEid();
    }

    /**
     * 判断文件是否更新
     *
     * @param attachment
     * @return
     */
    private Long isUpdate(Attachment attachment) {
        Long result = this.attachmentDao.findLicenseByIdAndName(attachment);
        return result;
    }

    public static String[] getFileInfo(MultipartFile from) {
        String fileName = from.getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        String toPrefix = "";
        String toSuffix = "";
        if (index == -1) {
            toPrefix = fileName;
        } else {
            //去除所有特殊字符
            toPrefix = fileName.substring(0, index).replaceAll("[^a-zA-Z_\u4e00-\u9fa5]", "");
            toSuffix = fileName.substring(index, fileName.length());
        }
        return new String[]{toPrefix, toSuffix};
    }

    public static String generateSuffix() {
        // 获得当前时间
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        // 转换为字符串
        String formatDate = format.format(new Date());
        // 随机生成文件编号
        int random = new Random().nextInt(10000);
        return new StringBuffer().append(formatDate).append(
                random).toString();
    }

    //判断文件是否存在
    public boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }


    @Override
    public OutputStream downloadAttachment(String attachmentId, boolean isOnline) throws UnsupportedEncodingException {
        Attachment attachment = attachmentDao.findAttachmentByAttachmentId(attachmentId);
        String fileAddress = attachment.getAttachmentAddr();

        if (fileAddress == null || fileAddress.equals("")) {
            throw new BusinessException("文件地址不能为空或请求地址过长");
        }
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");

        String fileName = fileAddress;
        String path = Config.uploadFileAddress;
        String agent = request.getHeader("User-Agent");
        try {
            // 打开文件
            // 获取到要下载文件的全路径
            // 得到要下载的文件名，小伙伴可以根据自己的实际文件名更改，这里是博主自己定义的文件名
            String destinationfileName = fileName;
            // 得到要下载的文件的所在目录，可以根据自己项目更改内容
            String uploadpath = path;
            // 得到要下载的文件
            File file = new File(uploadpath + destinationfileName);

            //如果文件不存在，则显示下载失败
            if (!file.exists()) {
                throw new BusinessException("该文件不存在,下载失败");
            } else {
                String fileRealName = attachmentDao.getFileRealName(fileAddress);//从数据库中得到文件的真实名称
                //isOnline表示附件是否支持在线查看,附件仅支持图片
                if (isOnline) {

                    BufferedInputStream br = new BufferedInputStream(new FileInputStream(uploadpath + "\\" + destinationfileName));
//                    BufferedInputStream br = new BufferedInputStream(new FileInputStream(uploadpath + "/" + destinationfileName));


                    byte[] buf = new byte[1024];
                    int len = 0;
                    URL u = new URL("file:///" + file.getPath());
                    response.setContentType(u.openConnection().getContentType());
                    if (agent.contains("Firefox")) {

                        response.setHeader("Content-Disposition", "inline; filename=" + new String(fileRealName.getBytes("UTF-8"), "iso-8859-1"));
                    } else {
                        response.setHeader("Content-Disposition", "inline; filename=" + URLEncoder.encode(fileRealName, "utf-8"));
                    }
                    OutputStream out = response.getOutputStream();
                    while ((len = br.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    br.close();
                    out.close();
                    return out;
                } else {
                    // 设置相应头，控制浏览器下载该文件，这里就是会出现当你点击下载后，出现的下载地址框
                    if (agent.contains("Firefox")) {
                        response.setHeader("content-disposition",
                                "attachment;filename=" + new String(fileRealName.getBytes("UTF-8"), "iso-8859-1"));
                    } else {
                        response.setHeader("content-disposition",
                                "attachment;filename=" + URLEncoder.encode(fileRealName, "utf-8"));
                    }
                    // 读取要下载的文件，保存到文件输入流
                    //FileInputStream in = new FileInputStream(uploadpath + "\\" + destinationfileName);
                    FileInputStream in = new FileInputStream(uploadpath + "/" + destinationfileName);

                    // 创建输出流
                    OutputStream out = response.getOutputStream();
                    // 创建缓冲区
                    byte buffer[] = new byte[1024];
                    int len = 0;
                    // 循环将输入流中的内容读取到缓冲区中
                    while ((len = in.read(buffer)) > 0) {
                        // 输出缓冲区内容到浏览器，实现文件下载
                        out.write(buffer, 0, len);
                    }
                    // 关闭文件流
                    in.close();
                    // 关闭输出流
                    out.close();
                    return out;
                }
            }
        } catch (Exception e) {
            throw new BusinessException("下载附件出错,请确认是否成功上传该附件");
        }
    }

    public OutputStream downloadAttachment(String fileAddress, String compressFileName) throws UnsupportedEncodingException {
        if (fileAddress == null || fileAddress.equals("")) {
            throw new BusinessException("文件地址不能为空或请求地址过长");
        }
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");

        String fileName = fileAddress;
        String path = Config.uploadFileAddress;
        String agent = request.getHeader("User-Agent");
        try {
            // 打开文件
            // 获取到要下载文件的全路径
            // 得到要下载的文件名，小伙伴可以根据自己的实际文件名更改，这里是博主自己定义的文件名
            String destinationfileName = fileName;
            // 得到要下载的文件的所在目录，可以根据自己项目更改内容
            String uploadpath = path;
            // 得到要下载的文件
            File file = new File(uploadpath + destinationfileName);

            //如果文件不存在，则显示下载失败
            if (!file.exists()) {
                throw new BusinessException("该文件不存在,下载失败");
            } else {
                //isOnline表示附件是否支持在线查看,附件仅支持图片
                // 设置相应头，控制浏览器下载该文件，这里就是会出现当你点击下载后，出现的下载地址框
                if (agent != null && agent.contains("Firefox")) {
                    response.setHeader("content-disposition",
                            "attachment;filename=" + new String(compressFileName.getBytes("UTF-8"), "iso-8859-1"));
                } else {
                    response.setHeader("content-disposition",
                            "attachment;filename=" + URLEncoder.encode(compressFileName, "utf-8"));
                }
                // 读取要下载的文件，保存到文件输入流
                FileInputStream in = new FileInputStream(uploadpath + "\\" + destinationfileName);
                // 创建输出流
                OutputStream out = response.getOutputStream();
                // 创建缓冲区
                byte buffer[] = new byte[1024];
                int len = 0;
                // 循环将输入流中的内容读取到缓冲区中
                while ((len = in.read(buffer)) > 0) {
                    // 输出缓冲区内容到浏览器，实现文件下载
                    out.write(buffer, 0, len);
                }
                // 关闭文件流
                in.close();
                // 关闭输出流
                out.close();
                return out;
            }
        } catch (Exception e) {
            throw new BusinessException("下载附件出错,请确认是否成功上传该附件");
        }

    }

    @Override
    public List<Attachment> findAllUploadedFilesByIdAndName(String id, String name) {
        Attachment attachment = new Attachment();
        attachment.setAssociateFormId(id);
        attachment.setAssociateFormName(name);
        return attachmentDao.findAllUploadedFilesByIdAndName(attachment);
    }

    @Override
    public List<Attachment> findAllUploadedFilesByIdAndNameMy(String id, String name) {
        Attachment attachment = new Attachment();
        attachment.setAssociateFormId(id);
        attachment.setAssociateFormName(name);
        return attachmentDao.findAllUploadedFilesByIdAndNameMy(attachment);
    }

    @Override
    public List<Attachment> findFileOfResource(String id, String name) {
        Attachment attachment = new Attachment();
        attachment.setAssociateFormId(id);
        attachment.setAssociateFormName(name);
        List<Attachment> list = attachmentDao.findAllUploadedFilesByIdAndNameMy(attachment);

        for (Iterator<Attachment> it = list.iterator(); it.hasNext(); ) {
            Attachment i = it.next();
            String fileName = i.getAttachmentRealName();
            String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
//            System.out.println(fileType);
            if (!(fileType.equals(".7z") || fileType.equals(".zip") || fileType.equals(".rar"))) {
                list.remove(i);
                break;
            }
        }

//        System.out.println(list);
        return list;
    }

    @Override
    public List<Attachment> findFileOfPicture(String id, String name) {
        Attachment attachment = new Attachment();
        attachment.setAssociateFormId(id);
        attachment.setAssociateFormName(name);
        List<Attachment> list = attachmentDao.findAllUploadedFilesByIdAndName(attachment);
        for (Iterator<Attachment> it = list.iterator(); it.hasNext(); ) {
            Attachment i = it.next();
            String fileName = i.getAttachmentRealName();
            String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            System.out.println(fileType);
            if (fileType.equals(".7z") || fileType.equals(".zip") || fileType.equals(".rar")) {
                list.remove(i);
                break;
            }
        }

        System.out.println(list);
        return list;
    }

    @Override
    public List<Attachment> findUploadedFileByFormIdAndFormNameAndGroupName(String id, String name, String groupName) {
        return this.attachmentDao.findUploadedFileByFormIdAndFormNameAndGroupName(id, name, groupName);
    }

    @Override
    public Integer deleteUploadedFile(String formId, String formName) {
        List<Attachment> attachments = this.attachmentDao.findUploadedFileByFormIdAndFormName(formId, formName);
        if (attachments == null || attachments.size() == 0) {
            return 0;
        }
        Integer rows = this.attachmentDao.deleteUploadedFile(formId, formName);

        for (Attachment attachment : attachments) {
            if (!(attachment.getAttachmentAddr().equals("") || attachment.getAttachmentAddr() == null)) {
                deleteFile(Config.uploadFileAddress + attachment.getAttachmentAddr());
            }
        }
        return rows;
    }

    @Override
    public Integer deleteUploadedFileByGroupName(String formId, String formName, String groupName) {
        List<Attachment> attachments = this.attachmentDao.findUploadedFileByFormIdAndFormNameAndGroupName(formId, formName, groupName);
        if (attachments == null || attachments.size() == 0) {
            return 0;
        }
        Integer rows = this.attachmentDao.deleteUploadedFileByGroupName(formId, formName, groupName);

        for (Attachment attachment : attachments) {
            if (!(attachment.getAttachmentAddr().equals("") || attachment.getAttachmentAddr() == null)) {
                deleteFile(Config.uploadFileAddress + attachment.getAttachmentAddr());
            }
        }
        return rows;
    }

    /*
     * @parms fileAddress 数据库的 attachment_addr字段数组(必填)
     * @parms fileNames 数据库的attachment_real_name 字段数组(必填)
     * @parms compressFileName要下载的压缩文件名，如果不填默认为当前时间戳 如(压缩文件.zip)
     */
    @Override
    public void getZipFile(String[] fileAddress, String[] fileNames, String compressFileName) throws IOException {
        if (fileAddress != null && fileAddress.length > 0) {

            if (compressFileName == null || compressFileName.equals("") || !compressFileName.contains(".zip")) {
                compressFileName = System.currentTimeMillis() + "" + (int) (Math.random() * 10000) + ".zip";
            }
            File zipFile = new File(Config.uploadFileAddress + "\\" + compressFileName);
            //实例化 ZipOutputStream对象
            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
            //创建ZipEntry对象
            ZipEntry zipEntry = null;
            FileInputStream fileInputStream = null;
            File[] files = new File[fileAddress.length];
            for (int i = 0; i < fileAddress.length; i++) {
                String file = Config.uploadFileAddress + fileAddress[i];
                files[i] = new File(file);
                if (!files[i].exists()) {
                    throw new BusinessException(fileNames[i] + "文件不存在请重新上传");
                }
                fileInputStream = new FileInputStream(files[i]);
                zipEntry = new ZipEntry(fileNames[i]);
                zipOutputStream.putNextEntry(zipEntry);
                int len;
                byte[] buffer = new byte[1024];
                while ((len = fileInputStream.read(buffer)) > 0) {
                    zipOutputStream.write(buffer, 0, len);
                }
                zipOutputStream.closeEntry();
            }
            try {
                zipOutputStream.close();
                fileInputStream.close();
                this.downloadAttachment("/" + zipFile.getName(), compressFileName);
                zipFile.delete();
            } catch (Exception e) {
                zipFile.delete();
                throw new BusinessException(e.getMessage());
            }
        } else {
            throw new BusinessException("传入文件地址不能为空");
        }
    }

    //查找附件
    @Override
    public List<Attachment> findAttachmentByFormIdAndFromName(String formId, String formName) {
        String businessServiceClassName = StringUtils.indexOf(formName, "_") > 0 ? StringUtils.substringBefore(formName, "_") : formName;
        businessServiceClassName += "Service";
        BaseService businessService = (BaseService) SpringContextUtils.getBean(businessServiceClassName);
        if (businessService.canDownloadAttachment(formName, Long.parseLong(formId))) {
            return this.attachmentDao.findAttachmentByFormIdAndFromName(formId, formName);
        } else {

            return null;
        }
    }

    public void deleteFile(String fileAddress) {
        //删除原来的文件
        File file = new File(fileAddress);
        if (file.exists()) {
            file.delete();
        }
    }

    @Override
    public boolean canDownloadAttachment(String formName, Long id) {

        return true;
    }

    /**
     * 根据awardID查询指定的附件(只提取ID 和 Name)
     *
     * @param awardId Id
     */
    @Override
    public List<Attachment> findAttachmentsWithIdNameByAwardId(Long awardId) {
        return this.attachmentDao.findAttachmentsWithIdNameByAwardId(awardId);
    }


    /**
     * 批量下载文件
     */

    @Override
    public void mutiDownload() throws IOException {
        List<String> paths = this.attachmentDao.getAttachmentAddresses();
        int count = paths.size();
        List<String> finalPaths = new ArrayList<String>();
        for (String path : paths){
            finalPaths.add("D:\\spring_files\\"+path);
        }
        ZipOutputStream zipOutputStream = null;
        try {
            if (CollectionUtils.isEmpty(paths)) {
                return;
            }

            final CountDownLatch countDownLatch = new CountDownLatch(count);
            ExecutorService pools = Executors.newWorkStealingPool();
            List<TempData> lists = new ArrayList<>(count);
            finalPaths.forEach(p -> {
                WorkCallable workCallable = new WorkCallable(p);
                Future<BufferedInputStream> submit = pools.submit(workCallable);
                countDownLatch.countDown();
                try {
                    TempData tempData = new TempData();
                    tempData.setBis(submit.get());
                    tempData.setPath(p);
                    lists.add(tempData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            if (CollectionUtils.isEmpty(lists)) {
                return;
            }

            //压缩后的zip包名
            String zipName = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE) + ".zip";

            packageResponse(request, response, zipName);
            zipOutputStream = new ZipOutputStream(response.getOutputStream());
            zipOutputStream.setMethod(ZipOutputStream.DEFLATED);
            final int BUFFER = 1024;
            for (TempData tempData : lists) {
                try {
                    ZipEntry entry = new ZipEntry(new File(tempData.getPath()).getName());
                    zipOutputStream.putNextEntry(entry);
                    byte[] data = new byte[BUFFER];
                    while ((tempData.getBis().read(data, 0, BUFFER)) != -1) {
                        zipOutputStream.write(data, 0, data.length);
                    }
                } catch (Exception e) {
                    throw e;
                } finally {
                    tempData.getBis().close();
                    tempData.getBis().close();
                    zipOutputStream.flush();
                    zipOutputStream.closeEntry();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != zipOutputStream) {
                zipOutputStream.close();
            }
        }
    }

    private void packageResponse(HttpServletRequest request, HttpServletResponse response, String zipName) throws UnsupportedEncodingException {
        response.setHeader("content-type", "application/octet-stream");
        response.setCharacterEncoding("utf-8");
        final String userAgent = request.getHeader("user-agent");
        boolean flag = null != userAgent && (userAgent.indexOf("Firefox") >= 0
                || userAgent.indexOf("Chrome") >= 0 || userAgent.indexOf("Safari") >= 0);
        if (flag) {
            zipName = new String(zipName.getBytes(), "ISO8859-1");
        } else {
            zipName = URLEncoder.encode(zipName, "UTF8");
        }
        response.setHeader("Content-disposition",
                "attachment;filename=" + new String(zipName.getBytes("gbk"), "iso8859-1"));
    }
}

class WorkCallable implements Callable<BufferedInputStream> {
    private String path;

    public WorkCallable(String path) {
        this.path = path;
    }

    @Override
    public BufferedInputStream call() throws Exception {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
        return bis;
    }
}

class TempData {

    private BufferedInputStream bis;
    private String path;

    public BufferedInputStream getBis() {
        return bis;
    }

    public void setBis(BufferedInputStream bis) {
        this.bis = bis;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}



