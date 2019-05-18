package hao.blog.www.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import hao.blog.www.common.PageHelper;
import hao.blog.www.common.PageHelper.Page;
import hao.blog.www.dao.CommentMapper;
import hao.blog.www.dao.UserContentMapper;
import hao.blog.www.entity.Comment;
import hao.blog.www.entity.UserContent;
import hao.blog.www.service.UserContentService;

import java.util.List;


@Service
public class UserContentServiceImpl implements UserContentService {
    @Autowired
    private UserContentMapper userContentMapper;
    @Autowired
    private CommentMapper commentMapper;
    public int addContent(UserContent content) {
       return userContentMapper.inserContent(content);
    }

    public List<UserContent> findByUserId(Long uid) {
        UserContent userContent = new UserContent();
        userContent.setuId(uid);
        List<UserContent> list = userContentMapper.select( userContent );
        return list;
    }

    public List<UserContent> findAll() {
        return userContentMapper.select( null );
    }

    public Page<UserContent> findAll(UserContent content, Integer pageNum, Integer pageSize) {
        //分页查询
        PageHelper.startPage(pageNum, pageSize);//开始分页
        List<UserContent> list = userContentMapper.findByJoin(content);
        Page endPage = PageHelper.endPage();//分页结束
        return endPage;
    }

    public Page<UserContent> findAll(UserContent content, Comment comment, Integer pageNum, Integer pageSize) {
        //分页查询
        PageHelper.startPage(pageNum, pageSize);//开始分页
        List<UserContent> list =  userContentMapper.select( content );
        List<Comment> comments = commentMapper.select( comment );

        Page endPage = PageHelper.endPage();//分页结束
        return endPage;
    }

    public Page<UserContent> findAllByUpvote(UserContent content, Integer pageNum, Integer pageSize) {
        Example e = new Example(UserContent.class);
        e.setOrderByClause("upvote DESC");
        PageHelper.startPage(pageNum, pageSize);//开始分页
        userContentMapper.selectByExample(e);
        Page endPage = PageHelper.endPage();//分页结束
        return endPage;
    }

    public UserContent findById(long id) {
        UserContent userContent = new UserContent();
        userContent.setId( id );
        List<UserContent> list = userContentMapper.findByJoin(userContent);
        if(list!=null && list.size()>0){
            return list.get(0);
        }else {
            return null;
        }
    }


    public void updateById(UserContent content) {
        userContentMapper.updateByPrimaryKeySelective( content );
    }

    @Override
    public List<UserContent> findCategoryByUid(Long uid) {
        return userContentMapper.findCategoryByUid(uid);
    }

    @Override
    public Page<UserContent> findByCategory(String category,Long uid,Integer pageNum, Integer pageSize) {
        UserContent userContent = new UserContent();
        if(StringUtils.isNotBlank(category) && !"null".equals(category)){

            userContent.setCategory(category);
        }
        userContent.setuId(uid);
        userContent.setPersonal("0");
        PageHelper.startPage(pageNum, pageSize);//开始分页
        userContentMapper.select(userContent);
        Page endPage = PageHelper.endPage();//分页结束
        return endPage;
    }

    @Override
    public Page<UserContent> findByCategory(String category, Integer pageNum, Integer pageSize) {
        UserContent userContent = new UserContent();
        if(StringUtils.isNotBlank(category) && !"null".equals(category)){
           /* System.out.println("++++++++++++++++++"+category);*/
            userContent.setCategory(category);
        }
        /*System.out.println("----------------"+category);*/
        userContent.setPersonal("0");
        PageHelper.startPage(pageNum, pageSize);//开始分页
        userContentMapper.select(userContent);
        Page endPage = PageHelper.endPage();//分页结束
        return endPage;
    }

    @Override
    public Page<UserContent> findPersonal(Long uid, Integer pageNum, Integer pageSize) {
        UserContent userContent = new UserContent();
        userContent.setuId(uid);
        userContent.setPersonal("1");
        PageHelper.startPage(pageNum, pageSize);//开始分页
        userContentMapper.select(userContent);
        Page endPage = PageHelper.endPage();//分页结束
        return endPage;
    }

    @Override
    public void deleteById(Long cid) {
       userContentMapper.deleteByPrimaryKey(cid);
    }

    @Override
    public Page<UserContent> findAll(Integer pageNum, Integer pageSize) {
        //分页查询
        PageHelper.startPage(pageNum, pageSize);//开始分页
        userContentMapper.findByDate();
        Page endPage = PageHelper.endPage();//分页结束
        return endPage;
    }

    @Override
    public UserContent findByUpvote() {
        List<UserContent> list = userContentMapper.findMaxUpvote();
        if(list.isEmpty()){
            return null;
        }
        else {
            return list.get(0);
        }
    }

    @Override
    public List<UserContent> findByCateFour() {
        List<UserContent> list = userContentMapper.findByCateFour();
        if(list.isEmpty()){
            return null;
        }
        else {
            return list;
        }
    }


}
