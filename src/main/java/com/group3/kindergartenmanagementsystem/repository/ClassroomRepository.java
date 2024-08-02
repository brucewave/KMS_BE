package com.group3.kindergartenmanagementsystem.repository;

import com.group3.kindergartenmanagementsystem.model.Classroom;
import com.group3.kindergartenmanagementsystem.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {
    @Transactional
    @Modifying
    @Query(value = "update Classroom c set c.teacher=null where c.id = ?1")
    void disassociateChildrenAndTeacher(Integer classroomId);
    @Transactional
    @Modifying
    void deleteById(Integer classroomId);
    Classroom findByTeacher(User teacher);
}
