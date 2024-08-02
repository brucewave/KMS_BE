package com.group3.kindergartenmanagementsystem.service;

import com.group3.kindergartenmanagementsystem.payload.ChildDTO;

import java.util.List;

public interface ChildService {
    ChildDTO getChildById(Integer id);

    ChildDTO getChildByParentId(Integer parentId);
    List<ChildDTO> getAllChild();

    List<ChildDTO> getAllChildByClassroom(Integer classroomId);

    List<ChildDTO> getAllChildByTeacher(Integer teacherId);

    ChildDTO createNewChild(ChildDTO childDTO);

    List<ChildDTO> addChildToClassroom(List<Integer> childId, Integer classroomId);

//    List<ChildDTO> addChildToTeacher(List<Integer> childId, Integer teacherId);

    ChildDTO updateChildById(Integer id, ChildDTO childDTO);

    String deleteChildById(Integer id);

    Boolean isParentOfLoggedInUser(Integer childId, String username);
}
