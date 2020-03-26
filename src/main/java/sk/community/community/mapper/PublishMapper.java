package sk.community.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import sk.community.community.model.Publish;

@Mapper
public interface PublishMapper {

    @Insert("insert into publish (title,content,gmt_create,gmt_modified,creator,tag) values(#{title},#{content},#{gmtCreate},#{gmtModified},#{creator}<#{tag})")
     void create(Publish publish);
}
