package com.lwp.website.dao;

import com.lwp.website.entity.Vo.AttachmentVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/08/14:41
 * @Description:
 */
@Component
public interface AttachmentDao {

    int insertAttachment(AttachmentVo attachmentVo);

    List<AttachmentVo> getListAttachment();

    String getPathById(String id);

    String getAbsPathById(String id);

    AttachmentVo getAttachmentById(String id);
}
