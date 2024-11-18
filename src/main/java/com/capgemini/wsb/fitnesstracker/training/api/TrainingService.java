package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository; // Repozytorium do pobierania danych z bazy

    // Metoda zwracająca wszystkie treningi
    public List<Training> getAllTrainings() {
        return trainingRepository.findAll(); // Zwracamy wszystkie treningi z bazy
    }

    // Metoda zwracająca treningi dla określonego użytkownika
    public List<Training> getTrainingsByUserId(Long userId) {
        return trainingRepository.findByUserId(userId); // Zwracamy treningi użytkownika o podanym ID
    }
}
