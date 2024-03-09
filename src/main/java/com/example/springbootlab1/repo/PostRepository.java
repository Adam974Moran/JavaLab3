package com.example.springbootlab1.repo;

import com.example.springbootlab1.service.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
