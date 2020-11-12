package com.lwp.website.service;

import com.lwp.website.entity.Vo.DictVo;
import com.lwp.website.entity.Vo.UserVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/09/29/17:53
 * @Description:
 */
@Service
public interface DictService {

    List getDictList();

    int getCountDictByNameId(String id, String name, String lastId);

    String getSort(String type, String lastId);

    DictVo getDictById(String id);

    String saveDict(DictVo dictVo, UserVo userVo);

    String getDictValue(String dictType,String key);

    List getSubData(String id);

    String getSeriesById(String id);

    List getListByName(String name);

    Boolean updateDictWithType(String type, String id, UserVo userVo);
}
