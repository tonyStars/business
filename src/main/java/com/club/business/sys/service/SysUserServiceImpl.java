package com.club.business.sys.service;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.club.business.common.VinPageHelp;
import com.club.business.config.ImageConfig;
import com.club.business.excel.common.*;
import com.club.business.sys.vo.RoleBaseModel;
import com.club.business.sys.vo.SysDictionary;
import com.club.business.sys.vo.SysUser;
import com.club.business.sys.dao.SysUserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.club.business.sys.vo.SysUserRole;
import com.club.business.util.CryptographyUtils;
import com.club.business.util.JsonUtils;
import com.club.business.util.StrUtils;
import com.club.business.util.StringsUtils;
import com.club.business.util.exception.BusinessException;
import com.club.business.util.redisson.LockUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 
 * @date 2019-11-19
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUser> {

    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    private final static String REDISSION_KEY = "REDISSION_KEY";

    @Autowired
    private SysUserRoleServiceImpl userRoleService;

    @Autowired
    private SysDictionaryServiceImpl dictionaryService;

    /**
     * 用户查询
     * @param page 当前页
     * @param pageSize 页面大小
     * @param userName 用户名称
     * @param sex 用户性别
     * @param status 状态
     * @return
     */
    public Map<String, Object> search(int page, int pageSize, String userName, String sex, String status) throws Exception {
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        if(StringUtils.isNotBlank(userName)){
            qw.like("USER_NAME",userName);
        }
        if(StringUtils.isNotBlank(sex)){
            qw.eq("SEX",sex);
        }
        if(StringUtils.isNotBlank(status)){
            qw.eq("STATUS",status);
        }
        Page<SysUser> pageInfo = new Page<>();
        pageInfo.setCurrent(page);
        pageInfo.setSize(pageSize);
        Page<SysUser> viewPage = pageInfo.setRecords(this.page(pageInfo,qw).getRecords());
        return VinPageHelp.getPage(viewPage);
    }

    /**
     * 新增用户
     * @param model 用户对象
     * @param user 当前用户
     */
    @Transactional(rollbackFor=Exception.class)
    public void add(SysUser model, SysUser user) throws Exception {
        model.setPassword(CryptographyUtils.encMd5(model.getPassword(),model.getUserCode()));
        model.setEntryUserId(user.getUserId());
        model.setEntryUserName(user.getUserName());
        model.setEntryUserTime(LocalDateTime.now());
        this.save(model);
    }

    /**
     * 启用用户
     * @param ids id集合
     */
    @Transactional(rollbackFor=Exception.class)
    public void ok(String ids) throws Exception {
        List<SysUser> list = new ArrayList<>();
        String[] idsTemp = ids.split(",");
        for (int i = 0; i < idsTemp.length; i++) {
            SysUser model = new SysUser();
            model.setUserId(Integer.valueOf(idsTemp[i]));
            model = this.getById(idsTemp[i]);
            if (model == null) {
                throw new BusinessException("未找到要操作的数据！");
            }
            if (model.getStatus().toString().equals("0")) {
                throw new BusinessException("请勿选择已启用的数据！");
            } else {
                model.setStatus(0);
            }
            list.add(model);
        }
        this.updateBatchById(list);
    }

    /**
     * 停用用户
     * @param ids id集合
     */
    @Transactional(rollbackFor=Exception.class)
    public void del(String ids) throws Exception {
        List<SysUser> list = new ArrayList<>();
        String[] idsTemp = ids.split(",");
        for (int i = 0; i < idsTemp.length; i++) {
            SysUser model = new SysUser();
            model.setUserId(Integer.valueOf(idsTemp[i]));
            model = this.getById(idsTemp[i]);
            if (model == null) {
                throw new BusinessException("未找到要操作的数据！");
            }
            if (model.getStatus().toString().equals("-1")) {
                throw new BusinessException("请勿选择已停用的数据！");
            } else {
                model.setStatus(-1);
            }
            list.add(model);
        }
        this.updateBatchById(list);
    }

    /**
     * 修改用户
     * @param model 用户对象
     * @param user 当前操作用户
     */
    @Transactional(rollbackFor=Exception.class)
    public void edit(SysUser model, SysUser user) throws Exception {
        /**使用Redisson加锁*/
        LockUtil.tryLock(REDISSION_KEY + model.getUserId());
        log.info("----> 使用Redisson加锁成功");
        try {
            SysUser userOld = this.getById(model.getUserId());
            if(!userOld.getPassword().equals(model.getPassword())){
                model.setPassword(CryptographyUtils.encMd5(model.getPassword(),model.getUserCode()));
            }
            model.setUpdateUserId(user.getUserId());
            model.setUpdateUserName(user.getUserName());
            model.setUpdateUserTime(LocalDateTime.now());
            this.updateById(model);
        } catch (Exception e) {
            throw new BusinessException("用户数据修改失败！");
        }finally {
            /**使用Redisson释放锁*/
            LockUtil.unlock(REDISSION_KEY + model.getUserId());
            log.info("----> 使用Redisson释放锁成功");
        }
    }

    /**
     * 保存选择的角色
     * @param userId
     * @param datas
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public void addRoleAuth(String userId, String datas) throws Exception {
        List<RoleBaseModel> list = JsonUtils.getJsonToList(datas, RoleBaseModel.class);
        if(list != null && list.size() > 0){
            List<SysUserRole> listUr = new ArrayList<>();
            for(RoleBaseModel m : list){
                SysUserRole model = new SysUserRole();
                model.setRoleId(m.getValue());
                model.setUserId(Integer.parseInt(userId));
                listUr.add(model);
            }
            UpdateWrapper<SysUserRole> uw = new UpdateWrapper<>();
            uw.eq("USER_ID",userId);
            userRoleService.remove(uw);
            userRoleService.saveBatch(listUr);
        }else{
            UpdateWrapper<SysUserRole> uw = new UpdateWrapper<>();
            uw.eq("USER_ID",userId);
            userRoleService.remove(uw);
        }
    }

    /**
     * 根据id获取对象
     * @param id userId
     * @return
     */
    public SysUser getModelById(String id) throws Exception {
        return baseMapper.getModelById(id);
    }

    /**
     * Excel导出
     * @param paramMap
     * @return
     */
    public ExportView getExportView(Map<String, Object> paramMap) throws Exception {
        HashMap<Map<String,Boolean>, List<ExcelExportEntity>> addMap = new HashMap<>();
        HashMap<String, Boolean> map = new HashMap<>();
        map.put("用户信息",true);
        List<ExcelExportEntity> excelExportEntities = new ArrayList<>();
        addMap.put(map,excelExportEntities);
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        ExportView exportView = new ExportView("用户信息"+time,"用户信息","用户信息");
        exportView.setExportMode(ExportModeEnum.SINGLEADD);
        exportView.setEntityCls(SysUser.class);
        exportView.setAddMap(addMap);
        ExportParams exportParams = exportView.getExportParams();
        exportParams.setType(ExcelType.HSSF);
        exportParams.setTitleHeight((short) 15);
        exportParams.setStyle(ExcelExportStylerImpl.class);
        List<SysUser> userList = baseMapper.selectExportList(paramMap);
        exportView.setDataList(userList);
        return exportView;
    }

    /**
     * 下载Excel模板
     * @return
     */
    public ExportView getTempExportView() throws Exception {
        ExportView exportView = new ExportView("用户导入模板","用户导入","用户导入");
        exportView.setExportMode(ExportModeEnum.MAP);
        exportView.setMap(createTempletHead());
        List<Map<Object,Object>> dataList = new ArrayList<>() ;
        Map<Object,Object> map = new HashMap<>() ;
        map.put(1, "Mum");
        map.put(2, "测试员");
        map.put(3, 1);
        map.put(4, "420110101100010081");
        map.put(5, "18900010001");
        map.put(6, 0);
        map.put(7, 1);
        map.put(8, "备注");
        map.put(9, "1");
        dataList.add(map);
        exportView.setDataList(dataList);
        return exportView;
    }

    /**
     * 创建导出模板表头
     * @return
     */
    public Map<String,Object> createTempletHead() throws Exception{
        List<SysDictionary> dic1 = dictionaryService.queryByName("员工职务");
        List<SysDictionary> dic2 = dictionaryService.queryByName("工作类型");
        List<String> items = dic1.stream().map(sysIdreplace -> sysIdreplace.getValue()+"-"+sysIdreplace.getItemName()).collect(Collectors.toList());
        List<String> items1 = dic2.stream().map(sysIdreplace -> sysIdreplace.getValue()+"-"+sysIdreplace.getItemName()).collect(Collectors.toList());
        String orderType = String.join("、", items);
        String orderType1 = String.join("、", items1);
        Map<String,Object> entitymap = new LinkedHashMap<>();
        entitymap.put("员工账号(必填)",1);
        entitymap.put("员工姓名(必填)",2);
        entitymap.put("员工职务"+"(可选值："+orderType+")",3);
        entitymap.put("身份证号(非必填)",4);
        entitymap.put("手机号码(非必填)",5);
        entitymap.put("性别(必填)(可选值：0男,1女)",6);
        entitymap.put("工作类型(必填)"+"(可选值："+orderType1+")",7);
        entitymap.put("备注(非必填)",8);
        entitymap.put("所属公司id(必填)",9);
        return entitymap;
    }

    /**
     * 导入验证excel数据
     * @param file
     * @return
     */
    public List<SysUser> analysisExcel(MultipartFile file,SysUser user) throws Exception {
        List<SysUser> userLists = new ArrayList<>();
        if(file.getSize() > 0){
            ImportView importView = new ImportView();
            importView.setMultipartFile(file);
            importView.setEntityCls(Map.class);
            ImportParams importPrams = importView.getImportPrams();
            importPrams.setHeadRows(1);
            importPrams.setTitleRows(1);
            List<Object> data = ExcelImportlHelper.importExcel(importView);
            if(data.size()>0){
                Map<String, Object> entityMap = this.createTempletHead();
                Set<String> keySet = entityMap.keySet();
                List<SysDictionary> dic1 = dictionaryService.queryByName("员工职务");
                List<SysDictionary> dic2 = dictionaryService.queryByName("工作类型");
                List<String> items = dic1.stream().map(sysIdreplace -> sysIdreplace.getValue()+"-"+sysIdreplace.getItemName()).collect(Collectors.toList());
                List<String> items1 = dic2.stream().map(sysIdreplace -> sysIdreplace.getValue()+"-"+sysIdreplace.getItemName()).collect(Collectors.toList());
                String orderType = String.join("、", items);
                String orderType1 = String.join("、", items1);
                int count = 2;//Excel导入模板是从第三行开始填写数据的
                Set<String> users = new HashSet<>();
                List<String> userCopes = new ArrayList<>();
                for(Object d:data){
                    LinkedHashMap<String,Object> map = (LinkedHashMap<String,Object>)d;
                    Object o2 = map.get("员工账号(必填)");
                    if(o2 != null){
                        users.add(o2.toString());
                        userCopes.add(o2.toString());
                    }
                }
                List<String> cops = StringsUtils.getDuplicateElements(userCopes);
                if(null != cops && cops.size() > 0){
                    throw new BusinessException("Excel数据中有重复员工账号：" + cops);
                }
                QueryWrapper<SysUser> qw = new QueryWrapper<>();
                qw.in("USER_CODE",users.stream().collect(Collectors.toList()));
                List<SysUser> hasUsers = this.list(qw);
                for (Object d:data) {
                    count ++;
                    LinkedHashMap<String,Object> map = (LinkedHashMap<String,Object>)d;
                    Object o1 = map.get("员工账号(必填)");
                    if(null == o1){
                        throw new BusinessException("第"+count+"行的员工账号必须填写,若此行为空,请检查Excel格式!");
                    }else{
                        Optional<SysUser> optional = hasUsers.stream().filter(setSku -> setSku.getUserCode().equals(o1.toString())).findFirst();
                        if(!optional.equals(Optional.empty())){
                            throw new BusinessException("第"+count+"行的员工账号已存在,请修改员工账号！");
                        }
                    }
                    Object o2 = map.get("员工姓名(必填)");
                    if(null == o2){
                        throw new BusinessException("第"+count+"行的员工姓名必须填写,若此行为空,请检查Excel格式!");
                    }
                    Object o3 = map.get("员工职务"+"(可选值："+orderType+")");
                    if(null == o3){
                        throw new BusinessException("第"+count+"行的员工职务必须填写,若此行为空,请检查Excel格式!");
                    }
                    Object o5 = map.get("性别(必填)(可选值：0男,1女)");
                    if(null == o5){
                        throw new BusinessException("第"+count+"行的性别必须填写,若此行为空,请检查Excel格式!");
                    }
                    Object o6 = map.get("工作类型(必填)"+"(可选值："+orderType1+")");
                    if(null == o6){
                        throw new BusinessException("第"+count+"行的工作类型必须填写,若此行为空,请检查Excel格式!");
                    }
                    Object o7 = map.get("所属公司id(必填)");
                    if(null == o7){
                        throw new BusinessException("第"+count+"行的所属公司id必须填写,若此行为空,请检查Excel格式!");
                    }
                    SysUser sysUser = new SysUser();
                    for (String key:keySet) {
                        Object value = map.get(key);
                        int num = (int) entityMap.get(key);
                        this.setExcelValue(num,sysUser,value,count,key);
                    }
                    sysUser.setEntryUserId(user.getUserId());
                    sysUser.setEntryUserName(user.getUserName());
                    sysUser.setEntryUserTime(LocalDateTime.now());
                    userLists.add(sysUser);
                }
            }else{
                throw new BusinessException("未检索到数据！");
            }
        }else{
            throw new BusinessException("未检索到文件！");
        }
        return userLists;
    }

    /**
     * 设置对象属性
     * @param num 字段的位置
     * @param sysUser 需要设置属性的对象
     * @param value 属性值
     * @param count Excel行数
     * @param key 属性名称
     * @throws BusinessException
     */
    private void setExcelValue(int num, SysUser sysUser, Object value, int count, String key) throws BusinessException {
        try {
            if (value == null) {
                return;
            }
            String strValue = value.toString();
            switch (num) {
                case (1):
                    sysUser.setUserCode(strValue);
                    break;
                case (2):
                    sysUser.setUserName(strValue);
                    break;
                case (3):
                    sysUser.setUserDuty(Integer.parseInt(strValue));
                    break;
                case (4):
                    sysUser.setIdCard(strValue);
                    break;
                case (5):
                    sysUser.setPhone(strValue);
                    break;
                case (6):
                    sysUser.setSex(Integer.parseInt(strValue));
                    break;
                case (7):
                    sysUser.setType(Integer.parseInt(strValue));
                    break;
                case (8):
                    sysUser.setNote(strValue);
                    break;
                case (9):
                    sysUser.setCompanyId(Integer.parseInt(strValue));
                    break;
                default:
                    break;
            }
        }catch (Exception e){
            throw new BusinessException("获取第"+count+"行的 "+key+"属性失败，请查证！");
        }
    }

    /**
     * 保存excel上传的数据
     * @param lists 上传的数据
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public Map<String, Object> saveExcelData(String lists) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<SysUser> users = JSONObject.parseArray(lists, SysUser.class);
        if(null == users || users.size() == 0){
            throw new BusinessException("未解析出用户数据，请联系管理员！");
        }
        this.saveBatch(users);
        map.put("code",0);
        return map;
    }

    /**
     * 上传头像保存
     * @param files 图片文件(支持多张)
     * @return
     */
    public Map<String, Object> saveImg(MultipartFile[] files) throws Exception {
        Map<String,Object> resMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        /**图片保存目录*/
        String userPath = ImageConfig.IMG_UPLOAD_USER;
        String realPath = ImageConfig.IMG_REAL_PATH_USER;
        String mapperPath = ImageConfig.IMG_MAPPER_PATH_USER;
        String picUrl = null;
        for (MultipartFile file : files) {
            /**获取上传文件名称*/
            String originalName = file.getOriginalFilename();
            /**获取上传文件类型后缀*/
            String proFix = originalName.substring(originalName.lastIndexOf("."));
            /**设置保存文件名称*/
            String fileName = "USER_" + StrUtils.getRandomNum(6) + proFix;
            /**根据配置的类型名称(userImage)后接当月(201912)作为存储路径 - userImage/201912*/
            userPath = userPath + "/" + sdf.format(new Date());
            /**拼接图片存储完整路径 - D:/imageUpload/userImage/201912/文件名.jpg*/
            File destFile = new File(realPath + "/" +userPath + "/" + fileName);
            /**拼接待存储文件的文件夹路径*/
            File destDir = new File(realPath + "/" + userPath);
            /**判断待存储文件的文件夹路径是否存在,不存在则创建*/
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
            /**执行文件上传*/
            file.transferTo(destFile);
            /**支持多文件上传,则返回路径用","号进行拼接*/
            if(picUrl == null) {
                picUrl = mapperPath + "/" + userPath + "/" + fileName;
            }else {
                picUrl = picUrl + "," + mapperPath + "/" + userPath + "/" + fileName;
            }
        }
        /**文件循环上传完成,则返回访问路径*/
        resMap.put("picUrl", picUrl);
        return resMap;
    }

    /**
     * 删除头像图片
     * @param picUrl 图片访问路径
     */
    public void delImg(String picUrl) throws Exception {
        if (StringUtils.isBlank(picUrl)) {
            log.info(String.format("url为%s的图片地址不存在！", picUrl));
        } else {
            /**获取用户头像图片存储的真实路径*/
            String realPath = ImageConfig.IMG_REAL_PATH_USER;
            /**用真实路径加上访问路径去掉第一个盘符的后置路径,即为图片存储的完整路径*/
            File file = new File(realPath + picUrl.substring(picUrl.indexOf("/")));
            /**存在则删除*/
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * 搜索框搜索用户数据
     * @param page 当前页
     * @param pageSize 页面条数
     * @param userName 用户名称
     * @return
     */
    public Map<String, Object> searchSelect(int page, int pageSize, String userName) throws Exception {
        QueryWrapper<SysUser> qw = new QueryWrapper();
        qw.eq("STATUS",0);
        if(StringUtils.isNotBlank(userName)){
            qw.like("USER_NAME",userName);
        }
        Page<SysUser> pageInfo = new Page<>();
        pageInfo.setCurrent(page);
        pageInfo.setSize(pageSize);
        Page<SysUser> viewPage = pageInfo.setRecords(this.page(pageInfo,qw).getRecords());
        return VinPageHelp.getPage(viewPage);
    }
}
