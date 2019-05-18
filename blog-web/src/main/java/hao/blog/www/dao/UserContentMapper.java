package hao.blog.www.dao;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import hao.blog.www.entity.Comment;
import hao.blog.www.entity.UserContent;

import java.util.List;

public interface UserContentMapper extends Mapper<UserContent> {
    /**
     * 根据用户id查询出梦分类
     * @param uid
     * @return
     */
    List<UserContent> findCategoryByUid(@Param("uid") long uid);

    /**
     *  插入文章并返回主键id 返回类型只是影响行数  id在UserContent对象中
     * @param userContent
     * @return
     */
    int inserContent(UserContent userContent);

    /**
     * user_content与user连接查询
     * @return
     */
    List<UserContent> findByJoin(UserContent userContent);

    /**
     * 找出upvote最多的帖子
     * @return
     */
    List<UserContent> findMaxUpvote();

    /**
     * 查找四个分类并找出各个分类下的一篇文章
     * @return
     */
    List<UserContent> findByCateFour();
    /**
     * 查找最新发布的
     * @return
     */
    List<UserContent> findByDate();
}
