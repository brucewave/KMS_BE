package com.group3.kindergartenmanagementsystem.controller;

import com.group3.kindergartenmanagementsystem.model.MedicineReminder;
import com.group3.kindergartenmanagementsystem.payload.MedicineReminderDTO;
import com.group3.kindergartenmanagementsystem.service.MedicineReminderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicine-reminder")
@AllArgsConstructor
public class MedicineReminderController {
    MedicineReminderService medicineReminderService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MedicineReminderDTO>> getAllMedicineReminder(){
        return ResponseEntity.ok(medicineReminderService.getAllMedicineReminder());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'PARENT')")
    public ResponseEntity<MedicineReminderDTO> getMedicineReminderById(@PathVariable Integer id){
        return ResponseEntity.ok(medicineReminderService.getMedicineReminderById(id));
    }

    @GetMapping("/children/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'PARENT')")
    public ResponseEntity<List<MedicineReminderDTO>> getMedicineReminderByChildId(@PathVariable Integer id){
        return ResponseEntity.ok(medicineReminderService.getMedicineReminderByChildId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('PARENT')")
    public ResponseEntity<MedicineReminderDTO> createNewMedicineReminder(@RequestBody MedicineReminderDTO medicineReminderDTO){
        return new ResponseEntity<>(medicineReminderService.createNewMedicineReminder(medicineReminderDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PARENT')")
    public ResponseEntity<MedicineReminderDTO> updateMedicineReminder(@PathVariable Integer id,
                                                                      @RequestBody MedicineReminderDTO medicineReminderDTO){
        return ResponseEntity.ok(medicineReminderService.updateMedicineReminderById(id, medicineReminderDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PARENT')")
    public ResponseEntity<String> deleteMedicineReminder(@PathVariable Integer id){
        medicineReminderService.deleteMedicineReminderById(id);
        return ResponseEntity.ok("Delete medicine reminder with id: "+id+" successfully");
    }
}
