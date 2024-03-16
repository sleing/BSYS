package com.mt.common.system.dto.system;


import java.util.List;
import com.mt.common.system.entity.Attachment;
import com.mt.common.system.entity.User;

public class AttachmentEditDto {

    private Attachment attachment;


    //外键实体是：User
    private List<User> uploadUserUsers;


    public Attachment getAttachment() {
        return this.attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }


    public List<User> getUploadUserUsers() {
        return this.uploadUserUsers;
    }

    public void setUploadUserUsers(List<User> uploadUserUsers) {
        this.uploadUserUsers = uploadUserUsers;
    }
                                                                                                                                                                        }
