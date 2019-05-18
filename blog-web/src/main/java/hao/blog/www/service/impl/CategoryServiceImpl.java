package hao.blog.www.service.impl;

import hao.blog.www.dao.CategoryMapper;
import hao.blog.www.entity.Category;
import hao.blog.www.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Category findById(Long id) {
        Category category = new Category();
        category.setId(id);
        return categoryMapper.selectOne(category);
    }

    @Override
    public List<Category> findAll() {
        return categoryMapper.select(null);
    }

    @Override
    public void update(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public void deleteById(Long id) {
        Category c = new Category();
        c.setId( id );
        categoryMapper.deleteByPrimaryKey( c );
    }

    @Override
    public int add(Category category) {
        return categoryMapper.insert(category);
    }


}
