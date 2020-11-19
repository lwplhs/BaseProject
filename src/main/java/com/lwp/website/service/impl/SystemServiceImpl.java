package com.lwp.website.service.impl;

import com.lwp.website.dao.SystemDao;
import com.lwp.website.entity.Bo.SystemBo;
import com.lwp.website.entity.Vo.SystemVo;
import com.lwp.website.service.SystemService;
import com.lwp.website.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/13/17:49
 * @Description:
 */
@Service
public class SystemServiceImpl implements SystemService {

    @Resource
    private SystemDao systemDao;

    @Override
    public SystemBo getSystemConfig() {

        List<SystemVo> list = systemDao.getSystem();
        Map<String,Object> map = toMap(list);
        SystemBo systemBo = new SystemBo();
        String value = StringUtil.isNotNull(map.get("logo"))?map.get("logo").toString():"";
        systemBo.setLogo(value);
        value = StringUtil.isNotNull(map.get("copyright"))?map.get("copyright").toString():"";
        systemBo.setCopyright(value);
        return systemBo;
    }

    @Override
    public Map saveSystem(SystemBo systemBo) {

        String key = "logo";
        String value = systemBo.getLogo();
        systemDao.saveSystem(key,value);
        key = "copyright";
        value = systemBo.getCopyright();
        systemDao.saveSystem(key,value);
        Map map = new HashMap();
        map.put("errCode","1");
        map.put("errMsg","保存成功");
        return map;
    }

    private Map<String,Object> toMap(List<SystemVo> list){
        Map<String,Object> map = new HashMap();

        for (int i = 0; i < list.size(); i++) {
            SystemVo systemVo = list.get(i);
            String key = systemVo.getKey();
            String value = systemVo.getValue();
            map.put(key,value);
        }
        return map;
    }
}
