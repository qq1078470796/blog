package hao.blog.www.interceptor;
import hao.blog.www.common.StringUtil;
import hao.blog.www.dao.CategoryMapper;
import hao.blog.www.entity.Category;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import hao.blog.www.common.PageHelper;
import hao.blog.www.dao.UserContentMapper;
import hao.blog.www.entity.UserContent;
import javax.servlet.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;


public class IndexJspFilter implements Filter{
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("===========自定义过滤器==========");
        ServletContext context = request.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
        //最新博文
        UserContentMapper userContentMapper = ctx.getBean(UserContentMapper.class);
        PageHelper.startPage(null, null);//开始分页
        List<UserContent> list =  userContentMapper.findByDate();
        PageHelper.Page endPage = PageHelper.endPage();//分页结束
        endPage=StringUtil.cutCont(endPage);
        request.setAttribute("page", endPage );

        //四个分类
        PageHelper.startPage(1, 4);//开始分页
        List<UserContent> blist= userContentMapper.findByCateFour();
        PageHelper.Page bend = PageHelper.endPage();//分页结束
        request.setAttribute("blist",bend);

        //右上角分类
        CategoryMapper categoryMapper = ctx.getBean(CategoryMapper.class);
        PageHelper.startPage(null,20);
        List<Category> CList = categoryMapper.select(null);
        PageHelper.Page CendPage = PageHelper.endPage();
        request.setAttribute("cpage",CendPage);

        //最多点赞文章
        UserContent userContent = new UserContent();
        userContent=userContentMapper.findMaxUpvote().get(0);
        if(userContent!=null){
            String temp;
            temp=userContent.getContent();
            temp= StringUtil.delHTMLTag(temp);
                if(temp.length()>=10){
                    temp=temp.substring(0,10);
                }
                userContent.setContent(temp);
            request.setAttribute("upvote",userContent);
        }

        chain.doFilter(request, response);
    }

    public void destroy() {

    }
}
