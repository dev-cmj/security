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

    public void validatePostOwner(Long postId, String username) {
        postRepository.findById(postId)
                .filter(post -> post.isOwner(username))
                .orElseThrow(() -> new IllegalArgumentException("게시글 작성자만 수정, 삭제 가능합니다."));
    }


    public Page<PostProjection> findPostsPage(PostSearchCondition condition) {
        return postRepository.findPostsPage(condition);
    }

    public Slice<PostProjection> findPostsSlice(PostSearchCondition condition) {
        return postRepository.findPostsSlice(condition);
    }

    public Post findPostWithMemberAndBoardByIdWithEntity(Long postId) {
        return postRepository.findPostByIdWithEntity(postId).orElseThrow();
    }

    public PostProjection findPostWithMemberAndBoardByIdWithProjection(Long postId) {
        return postRepository.findPostByIdWithProjection(postId).orElseThrow();
    }

    @Transactional
    public void increaseViewCount(Long postId, String userId) {
        viewCountService.increaseViewCountInRedis(postId, userId);
    }

    public long getViewCount(Long postId) {
        return viewCountService.getViewCountFromRedis(postId);
    }

    @Transactional
    public long increaseAndGetViewCount(Long postId, String userId) {
        increaseViewCount(postId, userId);
        return getViewCount(postId);
    }

    //조회수 증가 후 게시글 조회
    @Transactional
    public PostProjection increaseAndGetViewCountAndFindPostWithMemberAndBoardById(Long postId, String userId) {
        long viewCount = increaseAndGetViewCount(postId, userId);
        PostProjection post = findPostWithMemberAndBoardByIdWithProjection(postId);
        post.setViewCount(viewCount);
        return post;
    }


}
