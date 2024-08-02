package com.group3.kindergartenmanagementsystem.repository;

import com.group3.kindergartenmanagementsystem.model.Child;
import com.group3.kindergartenmanagementsystem.model.CommentForChild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentForChildRepository extends JpaRepository<CommentForChild, Integer> {
    List<CommentForChild> findAllByChild(Child child);
}
