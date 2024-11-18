package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    // Dodajemy metodę do wyszukiwania treningów na podstawie ID użytkownika
    List<Training> findByUserId(Long userId);
}
