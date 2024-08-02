package com.group3.kindergartenmanagementsystem.repository;

import com.group3.kindergartenmanagementsystem.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
}
