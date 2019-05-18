package hao.blog.www.controller;

import hao.blog.www.common.PageHelper;
import hao.blog.www.entity.Category;
import hao.blog.www.service.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import hao.blog.www.entity.User;
import hao.blog.www.entity.UserContent;
import hao.blog.www.service.SolrService;
import hao.blog.www.service.UserContentService;

import java.util.Date;
import java.util.List;


@Controller
public class WriteController extends BaseController {
    private final static Logger log = Logger.getLogger(WriteController.class);
    @Autowired
    private UserContentService userContentService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SolrService solrService;

    /**
     * 进入writePage
     * @param model
     * @return
     */
    @RequestMapping("/writePage")
    public String writePage(Model model,@RequestParam(value = "cid",required = false) Long cid) {
        User user = (User) getSession().getAttribute("user");
        if(cid!=null){
            UserContent content = userContentService.findById(cid);
            model.addAttribute("cont",content);
        }
        model.addAttribute("user", user);
        //分类信息
        PageHelper.startPage(null,20);
        List<Category> CList = categoryService.findAll();
        PageHelper.Page CendPage = PageHelper.endPage();
        model.addAttribute("cpage",CendPage);

        return "write/writePage";
    }



    /**
     * 写帖子
     * @param model
     * @param id
     * @param category
     * @param txtT_itle
     * @param content
     * @param private_dream
     * @return
     */
    @RequestMapping("/dowritePage")
    public String dowritePage(Model model, @RequestParam(value = "id",required = false) String id,
                               @RequestParam(value = "cid",required = false) Long cid,
                               @RequestParam(value = "category",required = false) String category,
                               @RequestParam(value = "txtT_itle",required = false) String txtT_itle,
                               @RequestParam(value = "content",required = false) String content,
                               @RequestParam(value = "private_dream",required = false) String private_dream) {
        User user = (User)getSession().getAttribute("user");
        if(user == null){
            //未登录
            model.addAttribute( "error","请先登录！" );
            return "../login";
        }
        UserContent userContent = new UserContent();
        if(cid!=null){
            userContent = userContentService.findById(cid);
        }
        userContent.setCategory( category );
        userContent.setContent( content );
        userContent.setRptTime( new Date(  ) );
        String imgUrl = user.getImgUrl();
        if(StringUtils.isBlank( imgUrl )){
            userContent.setImgUrl( "/images/icon_m.jpg" );
        }else {
            userContent.setImgUrl( imgUrl );
        }
        if("on".equals( private_dream )){
            userContent.setPersonal( "1" );
        }else{
            userContent.setPersonal( "0" );
        }
        userContent.setTitle( txtT_itle );
        userContent.setuId( user.getId() );
        userContent.setNickName( user.getNickName() );

        if(cid ==null){
            userContent.setUpvote( 0 );
            userContent.setDownvote( 0 );
            userContent.setCommentNum( 0 );
            userContentService.addContent( userContent );
            solrService.addUserContent(userContent);

        }else {
            userContentService.updateById(userContent);
            solrService.updateUserContent(userContent);
        }
        model.addAttribute("content",userContent);
        return "write/writesuccess";
    }

    /**
     * 根据文章id查看文章
     * @param model
     * @param cid
     * @return
     */
    @RequestMapping("/watch")
    public String watchContent(Model model, @RequestParam(value = "cid",required = false) Long cid){

        UserContent userContent = userContentService.findById(cid);
        model.addAttribute("cont",userContent);

        PageHelper.startPage(null,null);
        categoryService.findAll();
        PageHelper.Page CendPage = PageHelper.endPage();
        model.addAttribute("cpage",CendPage);

        return "personal/watch";
    }
}