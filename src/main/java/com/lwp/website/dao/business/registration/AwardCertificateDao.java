package com.lwp.website.dao.business.registration;

import com.lwp.website.entity.Vo.business.registration.AwardCertificateVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/21/21:16
 * @Description:
 */
public interface AwardCertificateDao {

    int insert(AwardCertificateVo awardCertificateVo);

    int update(AwardCertificateVo awardCertificateVo);

    int deleteById(String id);

    int deleteByMasterId(String masterId);

    List<AwardCertificateVo> getListData(String masterId);

    List<String> getListId(String masterId);




}
