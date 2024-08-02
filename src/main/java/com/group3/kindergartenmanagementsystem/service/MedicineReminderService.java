package com.group3.kindergartenmanagementsystem.service;

import com.group3.kindergartenmanagementsystem.payload.MedicineReminderDTO;

import java.util.List;

public interface MedicineReminderService {
    MedicineReminderDTO getMedicineReminderById(Integer id);
    List<MedicineReminderDTO> getMedicineReminderByChildId(Integer childId);
    List<MedicineReminderDTO> getAllMedicineReminder();
    MedicineReminderDTO createNewMedicineReminder(MedicineReminderDTO medicineReminderDTO);
    MedicineReminderDTO updateMedicineReminderById(Integer id, MedicineReminderDTO medicineReminderDTO);
    void deleteMedicineReminderById(Integer id);

}
