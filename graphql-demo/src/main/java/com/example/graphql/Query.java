package com.example.graphql;

import java.util.ArrayList;
import java.util.List;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.graphql.entity.Post;
import com.example.graphql.entity.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Query implements GraphQLQueryResolver {
    @Autowired
    PostRepository postRepository;

    public List<Post> getRecentPosts(int count, int offset) {
        final List<Post> result = new ArrayList<>();
        postRepository.findAll().forEach(result::add);
        return result;
    }
}
