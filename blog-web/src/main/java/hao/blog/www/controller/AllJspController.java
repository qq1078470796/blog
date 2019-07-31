package hao.blog.www.controller;

import hao.blog.www.common.PageHelper;
import hao.blog.www.common.StringUtil;
import hao.blog.www.entity.Category;
import hao.blog.www.entity.User;
import hao.blog.www.entity.UserContent;
import hao.blog.www.service.CategoryService;
import hao.blog.www.service.SolrService;

import org.apache.commons.lang3.StringUtils;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class AllJspController extends BaseController {
    private final static Logger log = Logger.getLogger(AllJspController.class);
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SolrService solrService;

    @RequestMapping("/all_list")
    public String findAllList(Model model,
                              @RequestParam(value = "keyword", required = false)
                                      String keyword,
                              @RequestParam(value = "pageNum", required = false)
                                      Integer pageNum,
                              @RequestParam(value = "pageSize", required = false)
                                      Integer pageSize) {
        User user = (User) getSession().getAttribute("user");

        if (user != null) {
            model.addAttribute("user", user);
        }

        //分类
        PageHelper.startPage(null, 20);
        categoryService.findAll();
        PageHelper.Page CendPage = PageHelper.endPage();
        model.addAttribute("cpage", CendPage);

        //搜索
        if (StringUtils.isNotBlank(keyword)) {
            PageHelper.Page<UserContent> page = solrService.findByKeyWords(keyword,
                    pageNum, pageSize);
            if(page!=null)
            page = StringUtil.cutCont(page);
            model.addAttribute("keyword", keyword);
            model.addAttribute("page", page);
        } else {
            PageHelper.Page<UserContent> page = findAll(null, pageNum, pageSize);
            page = StringUtil.cutCont(page);
            model.addAttribute("page", page);
            model.addAttribute("type","all_list?");
        }

        return "../all";
    }

    @RequestMapping("/byCate")
    public String findListByCate(Model model,
                                 @RequestParam(value = "category", required = false)
                                         String category,
                                 @RequestParam(value = "pageNum", required = false)
                                         Integer pageNum,
                                 @RequestParam(value = "pageSize", required = false)
                                         Integer pageSize) {
        category=StringUtil.decode(category);
        /*System.out.println("-----"+category);*/
        PageHelper.Page<UserContent> page = findAllByCate(category, pageNum,
                pageSize);

        page = StringUtil.cutCont(page);
        model.addAttribute("page", page);

        PageHelper.startPage(null, 20);

        categoryService.findAll();
        PageHelper.Page CendPage = PageHelper.endPage();

        model.addAttribute("cpage", CendPage);
        model.addAttribute("type","byCate?category="+category+"&&");
        return "../all";
    }
}
