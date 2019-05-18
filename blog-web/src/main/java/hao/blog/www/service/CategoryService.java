package hao.blog.www.service;

import hao.blog.www.entity.Category;

import java.util.List;

public interface CategoryService {
    /**
     * 通过id查找分类
     * @param id
     * @return
     */
    Category findById(Long id);

    /**
     * 查找所有分类
     * @return
     */
    List<Category> findAll();

    /**
     * 更新一个分类信息（不更新已经发布的文章的分类信息）
     * @param category
     */
    void update(Category category);

    /**
     * 根据id删除一个分类（不删除其下的文章）
     * @param id
     */
    void deleteById(Long id);

    /**
     * 添加一个分类
     * @param category
     */
    int add(Category category);

}
