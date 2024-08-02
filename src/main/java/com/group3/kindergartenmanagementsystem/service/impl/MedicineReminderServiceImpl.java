package com.group3.kindergartenmanagementsystem.service.impl;

import com.group3.kindergartenmanagementsystem.exception.ForbiddenAccessException;
import com.group3.kindergartenmanagementsystem.exception.ResourceNotFoundException;
import com.group3.kindergartenmanagementsystem.model.Child;
import com.group3.kindergartenmanagementsystem.model.MedicineReminder;
import com.group3.kindergartenmanagementsystem.payload.MedicineReminderDTO;
import com.group3.kindergartenmanagementsystem.repository.ChildRepository;
import com.group3.kindergartenmanagementsystem.repository.MedicineReminderRepository;
import com.group3.kindergartenmanagementsystem.service.MedicineReminderService;
import com.group3.kindergartenmanagementsystem.service.SecurityService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MedicineReminderServiceImpl implements MedicineReminderService {
    MedicineReminderRepository medicineReminderRepository;
    ChildRepository childRepository;
    SecurityService securityService;
    @Override
    public MedicineReminderDTO getMedicineReminderById(Integer id) {
        MedicineReminder medicineReminder = medicineReminderRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Medicine reminder", "id", id));
        if (!securityService.isParentOrTeacherOfChild(medicineReminder.getChild()))
            throw new ForbiddenAccessException(HttpStatus.BAD_REQUEST,
                    "User with username: "+securityService.getUsername()+" can't access to this child");
        return mapToDTO(medicineReminder);
    }

    @Override
    public List<MedicineReminderDTO> getMedicineReminderByChildId(Integer childId) {
        Child child = childRepository.findById(childId).orElseThrow(()->new ResourceNotFoundException("child", "id", childId));
        if (!securityService.isParentOrTeacherOfChild(child))
            throw new ForbiddenAccessException(HttpStatus.BAD_REQUEST,
                    "User with username: "+securityService.getUsername()+" can't access to this child");
        List<MedicineReminder> medicineReminders = medicineReminderRepository.findByChild(child);
        return medicineReminders.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<MedicineReminderDTO> getAllMedicineReminder() {
        List<MedicineReminder> medicineReminders = medicineReminderRepository.findAll();
        return medicineReminders.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    @Override
    @Transactional(rollbackOn = Exception.class)
    public MedicineReminderDTO createNewMedicineReminder(MedicineReminderDTO medicineReminderDTO) {
        Child child = childRepository.findById(medicineReminderDTO.getChildId())
                .orElseThrow(()-> new ResourceNotFoundException("Child", "id", medicineReminderDTO.getChildId()));
        if (!securityService.isParentOrTeacherOfChild(child))
            throw new ForbiddenAccessException(HttpStatus.BAD_REQUEST,
                    "User with username: "+securityService.getUsername()+" can't access to this child");
        MedicineReminder medicineReminder = new MedicineReminder();
        medicineReminder.setComment(medicineReminderDTO.getComment());
        medicineReminder.setCurrentStatus(medicineReminderDTO.getCurrentStatus());
        medicineReminder.setChild(child);
        medicineReminder.setCreatedDate(LocalDateTime.now());
        medicineReminder.setUpdatedDate(LocalDateTime.now());
        MedicineReminder newMedicineReminder = medicineReminderRepository.save(medicineReminder);
        return mapToDTO(newMedicineReminder);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public MedicineReminderDTO updateMedicineReminderById(Integer id, MedicineReminderDTO medicineReminderDTO) {
        MedicineReminder medicineReminder = medicineReminderRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Medicine reminder", "id", id));
        if (!securityService.isParentOrTeacherOfChild(medicineReminder.getChild()))
            throw new ForbiddenAccessException(HttpStatus.BAD_REQUEST,
                    "User with username: "+securityService.getUsername()+" can't access to this child");
        medicineReminder.setComment(medicineReminderDTO.getComment());
        medicineReminder.setCurrentStatus(medicineReminderDTO.getCurrentStatus());
        medicineReminder.setUpdatedDate(LocalDateTime.now());
        MedicineReminder updatedMedicineReminder = medicineReminderRepository.save(medicineReminder);
        return mapToDTO(updatedMedicineReminder);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deleteMedicineReminderById(Integer id) {
        MedicineReminder medicineReminder = medicineReminderRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Medicine reminder", "id", id));
        if (!securityService.isParentOrTeacherOfChild(medicineReminder.getChild()))
            throw new ForbiddenAccessException(HttpStatus.BAD_REQUEST,
                    "User with username: "+securityService.getUsername()+" can't access to this child");
        medicineReminderRepository.delete(medicineReminder);
    }

    private MedicineReminderDTO mapToDTO(MedicineReminder medicineReminder){
        return MedicineReminderDTO.builder()
                .id(medicineReminder.getId())
                .comment(medicineReminder.getComment())
                .currentStatus(medicineReminder.getCurrentStatus())
                .childId(medicineReminder.getChild().getId())
                .createdDate(medicineReminder.getCreatedDate())
                .updatedDate(medicineReminder.getUpdatedDate())
                .build();
    }

//    private MedicineReminder mapToEntity(MedicineReminderDTO medicineReminderDTO){
//        Child child = childRepository.findById(medicineReminderDTO.getChildId())
//                .orElseThrow(()-> new ResourceNotFoundException("Child", "id", medicineReminderDTO.getChildId()));
//        return new MedicineReminder(
//                medicineReminderDTO.getId(),
//                medicineReminderDTO.getComment(),
//                child
//        );
//    }
}
