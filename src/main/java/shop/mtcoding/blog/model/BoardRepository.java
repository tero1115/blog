package shop.mtcoding.blog.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BoardRepository {
    public int insert(@Param("title") String title, @Param("content") String content, @Param("userId") int userId);

    public User findById(int id);

    public List<Board> findAll();

    public int updateById(@Param("id") int id, @Param("title") int title, @Param("content") String content);

    public int deleteById(int id);

}
