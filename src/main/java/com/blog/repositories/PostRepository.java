package com.blog.repositories;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

//    same as writing a sql query
//    @Query("select p from post where p.title like :key")
//    List<Post> searchByTitle(@Param("key") String title); ("%"+searchKey+"%")

    List<Post> findByPostTitleContaining(String postTitle);
}
