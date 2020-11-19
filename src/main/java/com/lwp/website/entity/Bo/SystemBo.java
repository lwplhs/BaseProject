package com.lwp.website.entity.Bo;

import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/13/17:31
 * @Description:
 */
public class SystemBo implements Serializable {

    private static final long serialVersionUID = 5632727233999551491L;

    private String logo;

    private String copyright;

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
