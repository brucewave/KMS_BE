package com.group3.kindergartenmanagementsystem.repository;

import com.group3.kindergartenmanagementsystem.model.Child;
import com.group3.kindergartenmanagementsystem.model.MedicineReminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicineReminderRepository extends JpaRepository<MedicineReminder, Integer> {
    List<MedicineReminder> findByChild(Child child);
}
