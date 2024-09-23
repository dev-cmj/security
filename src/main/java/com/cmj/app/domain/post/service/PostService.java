package com.cmj.app.domain.post.service;

import com.cmj.app.domain.post.entity.Post;
import com.cmj.app.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    //CRUD operations
    public long savePost(Post post) {
        return postRepository.save(post).getId();
    }

    public Post findPostById(long id) {
        return postRepository.findById(id).orElseThrow();
    }

    public void updatePostById(long id, Post post) {
        Post postToUpdate = postRepository.findById(id).orElseThrow();
        postToUpdate.update(post);
    }

    public void deletePostById(long id) {
        postRepository.deleteById(id);
    }

    //Other operations



}
