package com.cmj.app.domain.post.repository;

import com.cmj.app.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    @Modifying
    @Query("update Post p set p.viewCount = p.viewCount + 1 where p.id = :postId")
    void increaseViewCount(Long postId);
}
