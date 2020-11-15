package com.lwp.website.entity.Vo;
import com.lwp.website.utils.invalid.dict.IsRepeatName;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * 参照项vo
 * @Auther: liweipeng
 * @Date: 2020/09/29/17:03
 * @Description:
 */
@IsRepeatName(vId = "id",vName = "name",vLastId = "lastId",message = "数据字典分类名称已存在")
public class DictVo implements Serializable {

    private static final long serialVersionUID = 5632727231999551491L;

    private String id;

    @NotBlank(message = "数据字典名称不能为空")
    private String name;

    @NotBlank(message = "数据字典描述不能为空")
    private String describe;

    private String fullName;

    private String series;

    private String lastId;

    private String lastName;

    private String firstName;

    private String separator;

    private String sort;

    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getLastId() {
        return lastId;
    }

    public void setLastId(String lastId) {
        this.lastId = lastId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
