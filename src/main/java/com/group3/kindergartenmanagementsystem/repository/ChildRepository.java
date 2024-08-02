package com.group3.kindergartenmanagementsystem.repository;

import com.group3.kindergartenmanagementsystem.model.Child;
import com.group3.kindergartenmanagementsystem.model.Classroom;
import com.group3.kindergartenmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
public interface ChildRepository extends JpaRepository<Child, Integer> {
    @Transactional
    @Modifying
    @Query(value = "update Child c set c.classroom=null, c.teacher=null where c.classroom.id = ?1")
    void setClassAndTeacherToNull(int classId);
    List<Child> findAllByTeacher(User teacher);
    @Query(value = "select id from child as c where c.teacher_id=:teacherId", nativeQuery = true)
    List<Integer> findAllIdByTeacherId(@Param("teacherId") Integer teacherId);
    List<Child> findAllByClassroom(Classroom classroom);
    @Query(value = "select id from child as c where c.classroom_id=:classroomId", nativeQuery = true)
    List<Integer> findAllIdByClassroomId(@Param("classroomId") Integer classroomId);
    Child findByParent(User parent);
    @Modifying
    @Query(nativeQuery = true, value = "update child as c set c.classroom_id= :classroomId, c.teacher_id =" +
            "(select teacher_id from classroom as cls where cls.id = :classroomId ) where c.id IN :childIds")
    void addChildToClassroom(@Param("classroomId") Integer classroomId, @Param("childIds") List<Integer> childIds);

    @Modifying
    @Query(nativeQuery = true, value = "update child as c set c.teacher_id = :teacherId where c.id in :childIds")
    void updateTeacherForChild(@Param("teacherId") Integer teacherId, @Param("childIds") List<Integer> childIds);

    @Query(value = "select c from Child c where c.id in :ids")
    Set<Child> findByIds(@Param("ids") Set<Integer> childIds);
}
