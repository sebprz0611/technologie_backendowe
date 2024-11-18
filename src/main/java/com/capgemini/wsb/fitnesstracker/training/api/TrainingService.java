package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository; // Repozytorium do pobierania danych z bazy

    public List<Training> getAllTrainings() {
        return trainingRepository.findAll(); // Zwracamy wszystkie treningi z bazy
    }
}

