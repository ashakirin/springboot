package com.example.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.graphql.entity.Post;
import com.example.graphql.entity.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mutation implements GraphQLMutationResolver {
    @Autowired
    PostRepository postRepository;

    public Post writePost(String title, String category, String authorId) {
        Post post = new Post(title, category, authorId);
        return postRepository.save(post);
    }
}