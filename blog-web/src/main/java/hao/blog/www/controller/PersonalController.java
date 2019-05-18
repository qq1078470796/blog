package hao.blog.www.controller;

import hao.blog.www.entity.*;
import hao.blog.www.mail.SendEmail;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import hao.blog.www.common.Constants;
import hao.blog.www.common.DateUtils;
import hao.blog.www.common.MD5Util;
import hao.blog.www.common.PageHelper;
import hao.blog.www.service.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Controller
public class PersonalController extends BaseController{
    private final static Logger log = Logger.getLogger(PersonalController.class);
    @Autowired
    private UserContentService userContentService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    private OpenUserService openUserService;
    @Autowired// redis数据库操作模板
    private RedisTemplate<String, String> redisTemplate;// jdbcTemplate HibernateTemplate


    @Autowired
    private SolrService solrService;

    /**
     * 初始化个人主页数据
     * @param model
     * @param id
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/list")
    public String findList(Model model, @RequestParam(value = "id",required = false) String id,
                           @RequestParam(value = "manage",required = false) String manage ,
                           @RequestParam(value = "pageNum",required = false) Integer pageNum,
                           @RequestParam(value = "pageSize",required = false) Integer pageSize) {
        User user = (User)getSession().getAttribute("user");
        UserContent content = new UserContent();
        UserContent uc = new UserContent();
        if(user!=null){
            model.addAttribute( "user",user );
            content.setuId( user.getId() );
            uc.setuId(user.getId());
        }else{
            return "../login";
        }
        log.info("初始化个人主页信息");

        if(StringUtils.isNotBlank(manage)){
            model.addAttribute("manage",manage);
        }

        //查询分类
        List<UserContent> categorys = userContentService.findCategoryByUid(user.getId());
        model.addAttribute( "categorys",categorys );
        //发布的文章，不包含私密文章
        content.setPersonal("0");
        pageSize = 10; //默认每页显示10条数据
        PageHelper.Page<UserContent> page =  findAll(content,pageNum,  pageSize); //分页

        model.addAttribute( "page",page );

        //查询私密文章
        uc.setPersonal("1");
        PageHelper.Page<UserContent> page2 =  findAll(uc,pageNum,  pageSize);
        model.addAttribute( "page2",page2 );


        return "personal/personal";
    }

    /**
     * 根据分类名称查询所有文章
     * @param model
     * @param category
     * @return
     */
    @RequestMapping("/findByCategory")
    @ResponseBody
    public Map<String,Object> findByCategory(Model model, @RequestParam(value = "category",required = false) String category,@RequestParam(value = "pageNum",required = false) Integer pageNum ,
                                             @RequestParam(value = "pageSize",required = false) Integer pageSize) {

        Map map = new HashMap<String,Object>(  );
        User user = (User)getSession().getAttribute("user");
        if(user==null) {
            map.put("pageCate","fail");
            return map;
        }
        pageSize = 10; //默认每页显示10条数据
        PageHelper.Page<UserContent> pageCate = userContentService.findByCategory(category,user.getId(),pageNum,pageSize);
        map.put("pageCate",pageCate);
        return map;
    }

    /**
     * 根据用户id查询私密文章
     * @param model
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/findPersonal")
    @ResponseBody
    public Map<String,Object> findPersonal(Model model,@RequestParam(value = "pageNum",required = false) Integer pageNum , @RequestParam(value = "pageSize",required = false) Integer pageSize) {

        Map map = new HashMap<String,Object>(  );
        User user = (User)getSession().getAttribute("user");
        if(user==null) {
            map.put("page2","fail");
            return map;
        }
        pageSize = 10; //默认每页显示10条数据
        PageHelper.Page<UserContent> page = userContentService.findPersonal(user.getId(),pageNum,pageSize);
        map.put("page2",page);
        return map;
    }

    /**
     * 查询出所有文章并分页 根据点赞数倒序排列
     * @param model
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/findAllHotContents")
    @ResponseBody
    public Map<String,Object> findAllHotContents(Model model, @RequestParam(value = "pageNum",required = false) Integer pageNum , @RequestParam(value = "pageSize",required = false) Integer pageSize) {

        Map map = new HashMap<String,Object>(  );
        User user = (User)getSession().getAttribute("user");
        if(user==null) {
            map.put("hotPage","fail");
            return map;
        }
        pageSize = 15; //默认每页显示15条数据
        UserContent uct = new UserContent();
        uct.setPersonal("0");
        PageHelper.Page<UserContent> hotPage =  findAllByUpvote(uct,pageNum,  pageSize);
        map.put("hotPage",hotPage);
        return map;
    }

    /**
     * 根据文章id删除文章
     * @param model
     * @param cid
     * @return
     */
    @RequestMapping("/deleteContent")
    public String deleteContent(Model model, @RequestParam(value = "cid",required = false) Long cid) {

        User user = (User)getSession().getAttribute("user");
        if(user==null) {
            return "../login";
        }
        userContentService.deleteById(cid);
        solrService.deleteById(cid);
        return "redirect:/list?manage=manage";
    }

    /**
     * 进入个人资料修改页面
     * @param model
     * @return
     */
    @RequestMapping("/profile")
    public String profile(Model model, @RequestParam(value = "email",required = false) String email,
                          @RequestParam(value = "password",required = false) String password,
                          @RequestParam(value = "phone",required = false) String phone) {
        User user = (User)getSession().getAttribute("user");
        if(user==null){
            return "../login";
        }

        if(StringUtils.isNotBlank(email)){
            user.setEmail(email);
            user.setPassword(MD5Util.encodeToHex(Constants.SALT+password));
            user.setPhone(phone);
            //邮件激活码
            String validateCode = MD5Util.encodeToHex("salt"+email + password);
            redisTemplate.opsForValue().set(email, validateCode, 24, TimeUnit.HOURS);// 24小时 有效激活 redis保存激活码
            SendEmail.sendEmailMessage(email, validateCode);
            userService.update(user);

        }
        List<OpenUser> openUsers = openUserService.findByUid(user.getId());
        setAttribute(openUsers,model);
        UserInfo userInfo =   userInfoService.findByUid(user.getId());
        model.addAttribute("user",user);
        model.addAttribute("userInfo",userInfo);

        return "personal/profile";
    }

    /**
     * 保存个人头像
     * @param model
     * @param url
     * @return
     */
    @RequestMapping("/saveImage")
    @ResponseBody
    public  Map<String,Object>  saveImage(Model model,@RequestParam(value = "url",required = false) String url) {
        Map map = new HashMap<String,Object>(  );
        User user = (User)getSession().getAttribute("user");
        user.setImgUrl(url);
        userService.update(user);
        map.put("msg","success");
        return map;
    }

    /**
     * 保存用户信息
     * @param model
     * @param name
     * @param nickName
     * @param sex
     * @param address
     * @param birthday
     * @return
     */
    @RequestMapping("/saveUserInfo")
    public String saveUserInfo(Model model, @RequestParam(value = "name",required = false) String name ,
                               @RequestParam(value = "nick_name",required = false) String nickName,
                               @RequestParam(value = "sex",required = false) String sex,
                               @RequestParam(value = "address",required = false) String address,
                               @RequestParam(value = "birthday",required = false) String birthday){
        User user = (User) getSession().getAttribute("user");
        if(user==null){
            return "../login";
        }
        UserInfo userInfo = userInfoService.findByUid(user.getId());
        boolean flag = false;
        if(userInfo == null){
            userInfo = new UserInfo();
        }else {
            flag = true;
        }
        userInfo.setName(name);
        userInfo.setAddress(address);
        userInfo.setSex(sex);
        Date bir =  DateUtils.StringToDate(birthday,"yyyy-MM-dd");
        userInfo.setBirthday(bir);
        userInfo.setuId(user.getId());
        if(!flag){
            userInfoService.add(userInfo);
        }else {
            userInfoService.update(userInfo);
        }

        user.setNickName(nickName);
        userService.update(user);

        model.addAttribute("user",user);
        model.addAttribute("userInfo",userInfo);
        return "personal/profile";
    }

    /**
     * 进入修改密码页面
     * @param model
     * @return
     */
    @RequestMapping("/repassword")
    public String repassword(Model model) {
        User user = (User) getSession().getAttribute("user");
        if(user!=null) {
            model.addAttribute("user",user);
            return "personal/repassword";
        }
        return "../login";
    }

    /**
     * 修改密码
     * @param model
     * @param oldPassword
     * @param password
     * @return
     */
    @RequestMapping("/updatePassword")
    public String updatePassword(Model model, @RequestParam(value = "old_password",required = false) String oldPassword,
                                 @RequestParam(value = "password",required = false) String password){

        User user = (User) getSession().getAttribute("user");
        if(user!=null) {
                oldPassword = MD5Util.encodeToHex(Constants.SALT + oldPassword);
                if (user.getPassword().equals(oldPassword)) {
                    password = MD5Util.encodeToHex(Constants.SALT + password);
                    user.setPassword(password);
                    userService.update(user);
                    model.addAttribute("message", "success");
                } else {
                    model.addAttribute("message", "fail");
                }
        }
        model.addAttribute("user",user);
        return "personal/passwordSuccess";
    }

    /**
     * 解除qq绑定
     * @param model
     * @return
     */
    @RequestMapping("/remove_qq")
    public String removeQQ(Model model){
        User user = (User) getSession().getAttribute("user");
        if(user == null){
            return "../login";
        }
        openUserService.deleteByUidAndType(user.getId(),Constants.OPEN_TYPE_QQ);
        List<OpenUser> openUsers = openUserService.findByUid(user.getId());
        setAttribute(openUsers,model);
        UserInfo userInfo =   userInfoService.findByUid(user.getId());
        model.addAttribute("user",user);
        model.addAttribute("userInfo",userInfo);
        return "personal/profile";
    }

    public void setAttribute(List<OpenUser> openUsers,Model model){
        if(openUsers!=null && openUsers.size()>0){
            for(OpenUser openUser:openUsers){
                if(Constants.OPEN_TYPE_QQ.equals(openUser.getOpenType())){
                    model.addAttribute("qq",openUser.getOpenType());
                }else if(Constants.OPEN_TYPE_WEIBO.equals(openUser.getOpenType())){
                    model.addAttribute("weibo",openUser.getOpenType());
                }else if(Constants.OPEN_TYPE_WEIXIN.equals(openUser.getOpenType())){
                    model.addAttribute("weixin",openUser.getOpenType());
                }
            }
        }
    }
}
