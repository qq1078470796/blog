package hao.blog.www.controller;

import hao.blog.www.common.PageHelper;
import hao.blog.www.dao.UserContentMapper;
import hao.blog.www.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import hao.blog.www.common.DateUtils;
import hao.blog.www.common.PageHelper.Page;
import hao.blog.www.common.StringUtil;
import hao.blog.www.service.*;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Controller
public class IndexJspController extends BaseController {
    private final static Logger log = Logger.getLogger(IndexJspController.class);
    @Autowired
    private UserContentService userContentService;
    @Autowired
    private UpvoteService upvoteService;

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/index_list")
    public String findAllList(Model model,
                                    @RequestParam(value = "pageNum",required = false) Integer pageNum ,
                                    @RequestParam(value = "pageSize",required = false) Integer pageSize) {

        User user = (User)getSession().getAttribute("user");
        if(user!=null){
            model.addAttribute( "user",user );
        }
        //分类信息
        PageHelper.startPage(null,20);
        categoryService.findAll();
        PageHelper.Page CendPage = PageHelper.endPage();
        model.addAttribute("cpage",CendPage);

        //文章信息
        Page<UserContent> page =  findAll(pageNum,pageSize);
        page=StringUtil.cutCont(page);
        model.addAttribute( "page",page );

        ServletContext context = getRequest().getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
        UserContentMapper userContentMapper = ctx.getBean(UserContentMapper.class);
        PageHelper.startPage(1, 4);//开始分页
        List<UserContent> blist= userContentMapper.findByCateFour();
        PageHelper.Page bend = PageHelper.endPage();//分页结束
        model.addAttribute("blist",bend);

        //最高点赞文章
        UserContent userContent = new UserContent();
        userContent=userContentService.findByUpvote();
        if(userContent!=null){
            String temp;
            temp=userContent.getContent();
            temp= StringUtil.delHTMLTag(temp);
            if(temp.length()>=10){
                temp=temp.substring(0,10);
            }
            userContent.setContent(temp);
            model.addAttribute("upvote",userContent);
        }
        return "../index";
    }

    /**
     * 点赞或踩
     * @param model
     * @param id
     * @param uid
     * @param upvote
     * @return
     */
    @RequestMapping("/upvote")
    @ResponseBody
    public Map<String,Object> upvote(Model model, @RequestParam(value = "id",required = false) long id,
                                     @RequestParam(value = "uid",required = false) Long uid,
                                     @RequestParam(value = "upvote",required = false) int upvote) {

        Map map = new HashMap<String,Object>(  );
        User user = (User)getSession().getAttribute("user");

        if(user == null){
            map.put( "data","fail" );
            return map;
        }
        Upvote up = new Upvote();
        up.setContentId( id );
        up.setuId( user.getId() );
        Upvote upv = upvoteService.findByUidAndConId( up );
        if(upv!=null){
            log.info( upv.toString()+"============" );
        }
        UserContent userContent =   userContentService.findById( id );
        if(upvote == -1){
            if(upv != null ){
                if( "1".equals( upv.getDownvote() ) ){
                    map.put( "data","down" );
                    return map;
                }else {
                    upv.setDownvote( "1" );
                    upv.setUpvoteTime( new Date(  ) );
                    upv.setIp( getClientIpAddress() );
                    upvoteService.update(upv);
                }

            }else {
                up.setDownvote( "1" );
                up.setUpvoteTime( new Date(  ) );
                up.setIp( getClientIpAddress() );
                upvoteService.add(up);
            }

            userContent.setDownvote( userContent.getDownvote()+upvote);
        }else {
            if(upv != null){
                if( "1".equals( upv.getUpvote() ) ){
                    map.put( "data","done" );
                    return map;
                }else {
                    upv.setUpvote( "1" );
                    upv.setUpvoteTime( new Date(  ) );
                    upv.setIp( getClientIpAddress() );
                    upvoteService.update(upv);
                }

            }else {
                up.setUpvote( "1" );
                up.setUpvoteTime( new Date(  ) );
                up.setIp( getClientIpAddress() );
                upvoteService.add(up);
            }


            userContent.setUpvote( userContent.getUpvote() + upvote );
        }
        userContentService.updateById( userContent );
        map.put( "data","success" );
        return map;

    }

    /**
     * 点击展开列表
     * @param model
     * @param content_id
     * @return
     */
    @RequestMapping("/reply")
    @ResponseBody
    public Map<String,Object> reply(Model model, @RequestParam(value = "content_id",required = false) Long content_id) {
        Map map = new HashMap<String,Object>(  );
        List<Comment> list = commentService.findAllFirstComment(content_id);
        if(list!=null && list.size()>0){
            for(Comment c:list){
                List<Comment> coments = commentService.findAllChildrenComment( c.getConId(), c.getChildren() );
                if(coments!=null && coments.size()>0){
                    for(Comment com:coments){
                        if(com.getById()!=null ){
                            User byUser = userService.findById( com.getById() );
                            com.setByUser( byUser );
                        }

                    }
                }
                c.setComList( coments );
            }
        }

        map.put( "list",list );

        return map;

    }

    /**
     * 点击评论按钮
     * @param model
     * @param id
     * @param content_id
     * @param uid
     * @param bid
     * @param oSize
     * @param comment_time
     * @param upvote
     * @return
     */
    @RequestMapping("/comment")
    @ResponseBody
    public Map<String,Object> comment(Model model, @RequestParam(value = "id",required = false) Long id ,
                                      @RequestParam(value = "content_id",required = false) Long content_id ,
                                      @RequestParam(value = "uid",required = false) Long uid ,
                                      @RequestParam(value = "by_id",required = false) Long bid ,
                                      @RequestParam(value = "oSize",required = false) String oSize,
                                      @RequestParam(value = "comment_time",required = false) String comment_time,
                                      @RequestParam(value = "upvote",required = false) Integer upvote) {
        Map map = new HashMap<String,Object>(  );
        User user = (User)getSession().getAttribute("user");
        if(user == null){
            map.put( "data","fail" );
            return map;
        }
        if(id==null ){

            Date date = DateUtils.StringToDate( comment_time, "yyyy-MM-dd HH:mm:ss" );

            Comment comment = new Comment();
            comment.setComContent( oSize );
            comment.setCommTime( date );
            comment.setConId( content_id );
            comment.setComId( uid );
            if(upvote==null){
                upvote = 0;
            }
            comment.setById( bid );
            comment.setUpvote( upvote );
            User u = userService.findById( uid );
            comment.setUser( u );
            commentService.add( comment );
            map.put( "data",comment );

            UserContent userContent = userContentService.findById( content_id );
            Integer num = userContent.getCommentNum();
            userContent.setCommentNum( num+1 );
            userContentService.updateById( userContent );

        }else {
            //点赞
            Comment c = commentService.findById( id );
            c.setUpvote( upvote );
            commentService.update( c );


        }

        return map;

    }

    /**
     * 删除评论
     * @param model
     * @param id
     * @param uid
     * @param con_id
     * @param fid
     * @return
     */
    @RequestMapping("/deleteComment")
    @ResponseBody
    public Map<String,Object>  deleteComment(Model model, @RequestParam(value = "id",required = false) Long id,@RequestParam(value = "uid",required = false) Long uid,
                                            @RequestParam(value = "con_id",required = false) Long con_id,@RequestParam(value = "fid",required = false) Long fid) {
        int num = 0;
        Map map = new HashMap<String,Object>(  );
        User user = (User)getSession().getAttribute("user");
        if(user==null){
            map.put( "data","fail" );
        }else{
            if(user.getId().equals( uid )){
                Comment comment = commentService.findById( id );
                if(StringUtils.isBlank( comment.getChildren() )){
                    if(fid!=null){
                        //去除id
                        Comment fcomm = commentService.findById( fid );
                        String child = StringUtil.getString( fcomm.getChildren(), id );
                        fcomm.setChildren( child );
                        commentService.update( fcomm );
                    }
                    commentService.deleteById(id);
                    num = num + 1;
                }else {
                    String children = comment.getChildren();
                    commentService.deleteChildrenComment(children);
                    String[] arr = children.split( "," );

                    commentService.deleteById( id );

                    num = num + arr.length + 1;

                }
                UserContent content = userContentService.findById( con_id );
                if(content!=null){
                    if(content.getCommentNum() - num >= 0){
                        content.setCommentNum( content.getCommentNum() - num );
                    }else {
                        content.setCommentNum( 0 );
                    }

                    userContentService.updateById( content );
                }
                map.put( "data",content.getCommentNum() );
            }else {
                map.put( "data","no-access" );
            }
        }

        return  map;
    }

    /**
     * 点击一级评论块的评论按钮
     * @param model
     * @param id
     * @param content_id
     * @param uid
     * @param bid
     * @param oSize
     * @param comment_time
     * @param upvote
     * @return
     */
    @RequestMapping("/comment_child")
    @ResponseBody
    public Map<String,Object> addCommentChild(Model model, @RequestParam(value = "id",required = false) Long id ,
                                              @RequestParam(value = "content_id",required = false) Long content_id ,
                                              @RequestParam(value = "uid",required = false) Long uid ,
                                              @RequestParam(value = "by_id",required = false) Long bid ,
                                              @RequestParam(value = "oSize",required = false) String oSize,
                                              @RequestParam(value = "comment_time",required = false) String comment_time,
                                              @RequestParam(value = "upvote",required = false) Integer upvote) {
        System.out.println("---------进入一级评论-----------");
        Map map = new HashMap<String,Object>(  );
        User user = (User)getSession().getAttribute("user");
        if(user == null){
            map.put( "data","fail" );
            return map;
        }

        Date date = DateUtils.StringToDate( comment_time, "yyyy-MM-dd HH:mm:ss" );

        Comment comment = new Comment();
        comment.setComContent( oSize );
        comment.setCommTime( date );
        comment.setConId( content_id );
        comment.setComId( uid );
        if(upvote==null){
            upvote = 0;
        }
        comment.setById( bid );
        comment.setUpvote( upvote );
        User u = userService.findById( uid );
        comment.setUser( u );
        commentService.add( comment );

        Comment com = commentService.findById( id );
        if(StringUtils.isBlank( com.getChildren() )){
            com.setChildren( comment.getId().toString() );
        }else {
            com.setChildren( com.getChildren()+","+comment.getId() );
        }
        commentService.update( com );
        map.put( "data",comment );

        UserContent userContent = userContentService.findById( content_id );
        Integer num = userContent.getCommentNum();
        userContent.setCommentNum( num+1 );
        userContentService.updateById( userContent );
        return map;

    }

}