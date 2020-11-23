package com.lwp.website.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSONObject;
import com.lwp.website.config.SysConfig;
import com.lwp.website.dao.UserDao;
import com.lwp.website.entity.Vo.RoleVo;
import com.lwp.website.entity.Vo.UserVo;
import com.lwp.website.exception.TipException;
import com.lwp.website.service.RoleService;
import com.lwp.website.service.UserRoleService;
import com.lwp.website.service.UserService;
import com.lwp.website.utils.StringUtil;
import com.lwp.website.utils.TaleUtils;
import com.lwp.website.utils.UUID;
import com.lwp.website.utils.UploadUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(com.lwp.website.service.UserService.class);

    @Resource
    private UserDao userDao;

    @Resource
    private RoleService roleService;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private SysConfig sysConfig;

    @Override
    public UserVo queryUserByUserName(String userName) {
        UserVo userVo = userDao.selectUserByName(userName);
        return userVo;
    }

    @Override
    public Integer insertUser(UserVo userVo) {
        int j =  userDao.insert(userVo);
        LOGGER.info(String.valueOf(j));
        return null;
    }

    @Override
    public UserVo queryUserById(String uid) {
        UserVo userVo = userDao.selectByPrimaryKey(uid);
        return userVo;
    }

    @Override
    @Deprecated
    public UserVo login(String username, String password) {
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            throw new TipException("用户名和密码不能为空");
        }
        //UserVoExample example = new UserVoExample();
        //UserVoExample.Criteria criteria = example.createCriteria();
        //criteria.andUsernameEqualTo(username);
        //long count = userVoDao.countByExample(example);
/*        long count = userVoDao.countByUsername(username);
        if(count < 1){
            throw new TipException("用户名或密码错误");
        }
        String pwd = TaleUtils.MD5encode(username + password);
        criteria.andPasswordEqualTo(pwd);
        List<UserVo> userVos = userVoDao.selectByExample(example);
        if(userVos.size() != 1){
            throw new TipException("用户名或密码错误");
        }
        return userVos.get(0);*/
        return null;
    }

    @Override
    public void updateByUid(UserVo userVo) {

    }


    /**
     * 修改密码
     * @param oldPwd
     * @param newPwd
     * @param enPwd
     * @param userVo
     * @return
     */
    @Override
    public Map changePwd(String oldPwd, String newPwd, String enPwd, UserVo userVo) {
        Map map = new HashMap();
        validPwd(oldPwd,newPwd,enPwd,userVo,map);
        if(!"6".equals(String.valueOf(map.get("code")))){
            map.put("code","-1");
            return map;
        }
        //修改密码
        String username = userVo.getUsername();
        String newPassword = TaleUtils.MD5encode(username+newPwd);
        int n = userDao.updatePwd(userVo.getId(),newPassword);
        if(n > 0){
            map.put("code","1");
            map.put("msg","修改密码成功，请重新登录");
        }else {
            map.put("code","-1");
            map.put("msg","修改密码失败，请刷新页面重试");
        }
        return map;
    }

    /**
     * 获取普通用户列表
     * 增加条件查询
     * @return
     */
    @Override
    public List<UserVo> getCommonUserList(String searchKey) {

        //获取普通用户未删除的列表

        Map<String,Object> map = new HashMap();
        List<String> status = new ArrayList();
        //正常启用
        status.add("0");
        //未启用的
        status.add("1");
        List<String> type = new ArrayList();
        //普通用户
        type.add("0");
        map.put("status",status);
        map.put("type",type);
        if(StrUtil.isEmpty(searchKey)){
            map.put("searchKey",null);
        }else {
            map.put("searchKey",searchKey);
        }
        List<UserVo> lists = userDao.getUserListByStatus(map);

        return lists;
    }

    @Override
    public List<UserVo> getAdminUserList(String searchKey) {
        //获取管理员用户未删除的列表

        Map<String,Object> map = new HashMap();
        List<String> status = new ArrayList();
        //正常启用
        status.add("0");
        //未启用的
        status.add("1");
        List<String> type = new ArrayList();
        //普通用户
        type.add("1");
        map.put("status",status);
        map.put("type",type);
        if(StrUtil.isEmpty(searchKey)){
            map.put("searchKey",null);
        }else {
            map.put("searchKey",searchKey);
        }
        List<UserVo> lists = userDao.getUserListByStatus(map);

        return lists;
    }

    @Override
    public boolean updateUser(String ids, String type, UserVo userVo) {
        Boolean bool = false;
        if(type != null){
            switch (type){
                case "1":
                    bool = this.updateStatus(ids,userVo);
                    break;
                case "2":
                    bool = this.updateDelete(ids,userVo);
                    break;
                default:
                    break;
            }
        }
        return bool;
    }

    @Override
    public String saveUser(UserVo userVo, UserVo userLoginVo) {

        JSONObject jsonObject = new JSONObject();
        int num = 0;
        try {
            if(StrUtil.isEmpty(userVo.getId())){
                String id = UUID.createID();
                userVo.setId(id);
                //密码加密
                String pwd = "123";
                if(StrUtil.isNotEmpty(userVo.getPassword())){
                    pwd = userVo.getPassword();
                }
                String newPassword = TaleUtils.MD5encode(userVo.getUsername()+pwd);
                userVo.setPassword(newPassword);
                num = userDao.insert(userVo);
                if(num > 0){
                    //新增用户后添加 用户 角色关系表
                    userRoleService.insertData(userVo);
                    jsonObject.put("code", "100000");
                    jsonObject.put("msg", "添加成功");
                } else {
                    jsonObject.put("code", "100001");
                    jsonObject.put("msg", "添加失败");
                }
            }else {
                num = userDao.updateByPrimaryKey(userVo);
                if (num > 0) {
                    userRoleService.insertData(userVo);
                    jsonObject.put("code", "100000");
                    jsonObject.put("msg", "修改成功");
                } else {
                    jsonObject.put("code", "100002");
                    jsonObject.put("msg", "修改失败");
                }
            }
        }catch (Exception e){
            jsonObject.put("code","100003");
            jsonObject.put("msg","出现错误，请刷新页面重试");
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @Override
    public long getCountByName(String userName, String id) {
        long count = userDao.countByUsername(userName,id);

        return count;
    }

    /**
     *导入数据
     * @param list
     * @return
     */
    @Override
    public Map importUsers(List<UserVo> list, File file,int cellCount,String prefix) {

        Map<String,Object> result = new HashMap();
        //校验是否有值
        if(null == list || list.size() == 0){
            result.put("errCode","-1");
            result.put("errMsg","导入数据为空，请重新选择文件");
            return result;
        }
        //
        Map<String,Object> map1 = validRepeat(list);
        if(!(Boolean)map1.get("result")){
            result.put("errCode","-2");
            result.put("errMsg","导入失败，有重复登录名，请检查");
            //写入excel错误信息中，返回下载路径
            List<Integer> counts = (ArrayList)map1.get("count");
            String url = writeExcel(file,cellCount,prefix,counts,"导入失败，有重复登录名，请检查");
            if(StrUtil.isNotEmpty(url)){
                result.put("errFileCode","1");
                result.put("errFile",url);
            }else {
                result.put("errFileCode","-1");
            }
            return result;
        }
        //列表没有重复的就去数据库查找是否有重复的
        List<String> nameList = (ArrayList)map1.get("nameList");
        Map map2 = validRepeatSystem(nameList);
        if(!(Boolean)map2.get("result")){
            result.put("errCode","-2");
            result.put("errMsg","导入失败，系统中已存在导入的用户名，请检查");
            //写入excel，返回下载路径
            List<Integer> counts = (ArrayList)map2.get("count");
            String url = writeExcel(file,cellCount,prefix,counts,"导入失败，系统中已存在导入的用户名，请检查");
            if(StrUtil.isNotEmpty(url)){
                result.put("errFileCode","1");
                result.put("errFile",url);
            }else {
                result.put("errFileCode","-1");
            }
            return result;
        }
        //写入数据库中
        for (int i = 0; i < list.size(); i++) {
            UserVo userVo = list.get(i);
            if(userVo !=null) {
                //添加id
                userVo.setId(UUID.createID());
                String pwd = sysConfig.getDefaultPwd();
                if(StrUtil.isNotEmpty(userVo.getPassword())){
                    pwd = userVo.getPassword();
                }
                String newPassword = TaleUtils.MD5encode(userVo.getUsername()+pwd);
                userVo.setPassword(newPassword);
                userVo.setStatus("0");
                userVo.setType("0");
                //导入数据 中权限添加默认的权限
                String roleId = sysConfig.getDefaultRoleId();
                userVo.setGroupName(roleId);
                this.insertUser(userVo);
            }
        }
        result.put("errCode","1");
        result.put("errMsg","导入成功");
        return result;
    }

    /**
     * 根据用户信息获取 角色信息
     * @param userVo
     * @return
     */
    @Override
    public String getRoleName(UserVo userVo) {
        String roleIds = userVo.getGroupName();
        if(StrUtil.isEmpty(roleIds)){
            return "";
        }
        String roleName = "";
        String[] temp = roleIds.split(",");
        for (int i = 0; i < temp.length; i++) {
            String roleId = temp[i];
            RoleVo roleVo = roleService.getRoleById(roleId);
            if(null != roleVo){
                roleName = roleName + roleVo.getName() + ",";
            }
        }
        if(StrUtil.isNotEmpty(roleName)){
            if(roleName.endsWith(",")){
                roleName = roleName.substring(0,roleName.length()-1);
            }
        }
        return roleName;
    }

    /**
     * 初始化密码
     * @param ids
     * @return
     */
    @Override
    public Boolean defaultPwd(String ids) {

        List<String> idList = this.toList(ids);
        for (int i = 0; i < idList.size(); i++) {
            String id = idList.get(i);
            UserVo userVo = userDao.selectByPrimaryKey(id);
            String username = userVo.getUsername();
            String pwd = sysConfig.getDefaultPwd();
            String newPassword = TaleUtils.MD5encode(username+pwd);
            try {
                userDao.updatePwd(userVo.getId(),newPassword);
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }

        }
        return true;
    }


    /**
     * 将错误信息写入到excel中 返回地址
     * @param
     * @param counts
     * @param message
     */
    private String writeExcel(File file,int xCount,String prefix,List<Integer> counts,String message){
        ExcelWriter excelWriter = new ExcelWriter(file);

        String url = "";
        int size = message.length()+30;
        excelWriter.setColumnWidth(xCount,size);
        Font font = excelWriter.createFont();
        font.setBold(true);
        font.setColor(Font.COLOR_RED);
        font.setItalic(true);
        excelWriter.getStyleSet().setFont(font, true);
        for (int i = 0; i < counts.size(); i++) {
            int yCount = counts.get(i)+1;
            excelWriter.writeCellValue(xCount,yCount,message);
        }
        excelWriter.flush();
        try {
            InputStream inputStream =new FileInputStream(file);
            Map map = UploadUtil.getMouldPath();
            String path = map.get("path").toString();
            url = map.get("url").toString();
            String name = StringUtil.getDate(new Date(),"yyyyMMdd")+"/"+System.currentTimeMillis()+prefix;
            String newPath = path+ name;
            url=url+name;
            File uploadFile = new File(newPath);
            if(!uploadFile.getParentFile().exists()){
                uploadFile.getParentFile().mkdirs();
            }
            FileOutputStream output = new FileOutputStream(uploadFile);
            FileCopyUtils.copy(inputStream,output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }


    /**
     * 去数据库校验是否有重复的
     * @param nameList
     * @return
     */
    private Map validRepeatSystem(List<String> nameList){

        Map<String,Object> result = new HashMap();
        Map<String,Object> map = new HashMap();
        map.put("names",nameList);
        List<UserVo> list = userDao.getListByName(map);

        if(null == list || list.size() <= 0){
            result.put("result",true);
        }else {
            result.put("result",false);
            for (int i = 0; i < list.size(); i++) {
                UserVo userVo = list.get(i);
                String name = userVo.getUsername();
                for (int j = 0; j < nameList.size(); j++) {
                    String tempName = nameList.get(j);
                    if(tempName.equals(name)){
                        if(result.containsKey("count")){
                            List<Integer> list1 = (ArrayList)result.get("count");
                            list1.add(i);
                            result.put("count",list1);
                        }else {
                            List<Integer> list1 = new ArrayList();
                            list1.add(i);
                            result.put("count",list1);
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * 校验登录名是否重复
     * @param list
     * @return
     */
    private Map validRepeat(List<UserVo> list){
        Map<String,Object> map = new HashMap();
        List<String> nameList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            UserVo userVo = list.get(i);
            String name = userVo.getUsername();
            if(null != nameList && nameList.contains(name)){
                map.put("result",false);
                if(map.containsKey("count")){
                    List<Integer> list1 = (ArrayList)map.get("count");
                    list1.add(i);
                    map.put("count",list1);
                }else {
                    List<Integer> list1 = new ArrayList();
                    list1.add(i);
                    map.put("count",list1);
                }
                return map;
            }
            nameList.add(name);
        }
        map.put("result",true);
        map.put("nameList",nameList);
        return map;
    }

    /**
     * 更新状态 已启用的修改为未启用 未启用的修改成已启用
     *
     * @param ids
     * @return
     */
    private boolean updateStatus(String ids,UserVo userVo){
        List<String> temp = this.toList(ids);
        if(!StringUtil.isNull(temp)){
            Map<String,Object> map = new HashMap<>();
            map.put("ids",temp);
            int count = userDao.updateUserWithStatus(map);
            if(count > 0){
                return true;
            }
        }

        return false;
    }


    /**
     * 更新删除状态，用户删除
     * @param ids
     * @return
     */
    private boolean updateDelete(String ids,UserVo userVo){
        List<String> temp = this.toList(ids);
        if(!StringUtil.isNull(temp)){
            Map<String,Object> map = new HashMap<>();
            map.put("ids",temp);
            int count = userDao.updateUserWithDelete(map);
            if(count > 0){
                return true;
            }
        }
        return false;
    }

    private Map validPwd(String oldPwd,String newPwd,String enPwd,UserVo userVo , Map map){
        if(StringUtil.isNull(userVo)){
            map.put("code","7");
            map.put("msg","用户未登录，请登录后重试");
            return map;
        }
        if(StringUtil.isNull(oldPwd)){
            map.put("code","1");
            map.put("msg","原密码不能为空");
            return map;
        }
        if(StringUtil.isNull(newPwd)){
            map.put("code","2");
            map.put("msg","新密码不能为空");
            return map;
        }
        if(StringUtil.isNull(enPwd)){
            map.put("code","3");
            map.put("msg","确认密码不能为空");
            return map;
        }
        if(!newPwd.equals(enPwd)){
            map.put("code","4");
            map.put("msg","新密码与确认密码不匹配，请重新确认");
            return map;
        }
        //校验原密码
        String username = userVo.getUsername();
        String password = userVo.getPassword();
        if(!password.equals(TaleUtils.MD5encode(username+oldPwd))){
            map.put("code","5");
            map.put("msg","原密码不正确，请重新输入");
            return map;
        }
        map.put("code","6");
        return map;
    }

    /**
     * 将 ids 转化成list
     * @param ids
     * @return
     */
    private List<String> toList(String ids){
        List<String> list = new ArrayList();
        String[] args = ids.split(",");
        if(null != args && args.length > 0){
            for(int i = 0;i < args.length;i++){
                list.add(args[i]);
            }
        }
        return list;
    }
}
