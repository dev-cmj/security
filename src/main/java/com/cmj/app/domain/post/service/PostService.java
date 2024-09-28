package com.cmj.app.domain.post.service;

import com.cmj.app.domain.post.dto.PostSearchCondition;
import com.cmj.app.domain.post.entity.Post;
import com.cmj.app.domain.post.entity.PostProjection;
import com.cmj.app.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    private final ViewCountService viewCountService;

    //CRUD operations
    @Transactional
    public long savePost(Post post) {
        return postRepository.save(post).getId();
    }


    public Post findPostById(long id) {
        return postRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void updatePostById(long id, Post post) {
        Post postToUpdate = postRepository.findById(id).orElseThrow();
        postToUpdate.update(post);
    }

    @Transactional
    public void deletePostById(long id) {
        postRepository.deleteById(id);
    }


    public Page<PostProjection> findPostsPage(PostSearchCondition condition) {
        return postRepository.findPostsPage(condition);
    }

    public Slice<PostProjection> findPostsSlice(PostSearchCondition condition) {
        return postRepository.findPostsSlice(condition);
    }

    public Optional<Post> findPostWithMemberAndBoardById(Long postId) {
        return postRepository.findPostWithMemberAndBoardById(postId);
    }

    @Transactional
    public Optional<Post> findPostWithMemberAndBoardByIdWithViewCount(Long postId, String username) {
        Optional<Post> post = postRepository.findPostWithMemberAndBoardById(postId);
        viewCountService.increaseViewCount(post.orElseThrow(), username);
        return post;
    }


}
