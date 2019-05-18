package hao.blog.www.controller;

import hao.blog.www.common.PageHelper;
import hao.blog.www.common.StringUtil;
import hao.blog.www.entity.Category;
import hao.blog.www.entity.User;
import hao.blog.www.entity.UserContent;
import hao.blog.www.service.CategoryService;
import hao.blog.www.service.OpenUserService;
import hao.blog.www.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class ManageController extends BaseController{
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private OpenUserService openUserService;

    @RequestMapping("/admin")
    public String findAllList(Model model,
                              @RequestParam(value = "type",required = false) String type,
                              @RequestParam(value = "pageNum",required = false) Integer pageNum ,
                              @RequestParam(value = "pageSize",required = false) Integer pageSize) {

        User user = (User)getSession().getAttribute("user");
        if(user!=null){
            if(user.getState().equals("5")){
                model.addAttribute( "user",user );
            }
            else{
                model.addAttribute("error", "notAdmin");
                return "../login";
            }
        }else{
            return "../login";
        }
        if(user!=null){
            model.addAttribute( "user",user );
        }
        if(type==null){
            type="user";
        }
        if(type.equals("cate")){
            //分类信息
            System.out.println("inCate");
            PageHelper.startPage(null,20);
            categoryService.findAll();
            PageHelper.Page CendPage = PageHelper.endPage();
            model.addAttribute("page",CendPage);

            model.addAttribute("moxing","cate");
        }
       else if(type.equals("content")){
            //文章信息
            PageHelper.Page<UserContent> page =  findAll(pageNum,pageSize);
            page= StringUtil.cutCont(page);
            model.addAttribute( "page",page );
        }
       else {
            PageHelper.Page<User> page = userService.findAll(pageNum,pageSize);
            model.addAttribute("page",page);
            model.addAttribute("moxing","user");
        }

        return "/manage/manageIndex";
    }

    /**
     * 根据用户id修改用户信息
     * @param id
     * @return
     */
    @RequestMapping("/updateUserInfo")
    public String updateUserInfo(@RequestParam(value = "id",required = false) Long id,
                                @RequestParam(value = "nickName",required = false) String nickName,
                                @RequestParam(value = "password",required = false) String password,
                                @RequestParam(value = "email",required = false) String email,
                                @RequestParam(value = "phone",required = false) String phone,
                                @RequestParam(value = "state",required = false) String state,
                                @RequestParam(value = "enable",required = false) String enable) {

        User user = new User();
        user.setId(id);
        user.setEnable(enable);user.setNickName(nickName);
        user.setState(state);user.setPassword(password);
        user.setPhone(phone);user.setEmail(email);
        System.out.println("----nickName----"+user.getEmail());
        userService.update(user);
        return "redirect:/admin?type=user";
    }

    /**
     * 修改分类信息
     * @param id
     * @param name
     * @return
     */
    @RequestMapping("/updateCateInfo")
    public String updateCateInfo(@RequestParam(value = "cid",required = false) Long id,
                                @RequestParam(value = "cName",required = false) String name) {

        Category category = new Category();
        category.setId(id);
        category.setName(name);
        categoryService.update(category);
        return "redirect:/admin?type=cate";
    }

    /**
     * 根据邮箱删除用户
     * @param email
     * @return
     */
    @RequestMapping("/deleteUserInfo")
    public String deleteUserInfo(@RequestParam(value = "id",required = false) Long id,
                                 @RequestParam(value = "email",required = false) String email
                                 ) {

        System.out.println(email+"——————————————————"+id);
         userService.deleteByEmail(email);
        openUserService.deleteByUidAndType(id,"QQ");

        return "redirect:/admin?type=user";
    }

    /**
     * 根据id删除分类
     * @param id
     * @return
     */
    @RequestMapping("/deleteCateInfo")
    public String deleteCateInfo(@RequestParam(value = "cid",required = false)Long id) {
        categoryService.deleteById(id);
        return "redirect:/admin?type=cate";
    }

    @RequestMapping("/insertCateInfo")
    public String insertCateInfo(@RequestParam(value = "ncName",required = false)String ncName) {
        Category category = new Category();
        category.setName(ncName);
        categoryService.add(category);
        return "redirect:/admin?type=cate";
    }

}
