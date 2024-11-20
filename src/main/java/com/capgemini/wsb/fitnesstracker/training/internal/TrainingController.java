package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.List;


@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;

    // Metoda zwracająca wszystkie treningi
    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.getAllTrainings();
    }

    // Metoda zwracająca treningi dla określonego użytkownika
    @GetMapping("/{userId}")
    public List<TrainingDto> getTrainingsByUserId(@PathVariable Long userId) {
        return trainingService.getTrainingsByUserId(userId);
    }

    // Metoda zwracająca treningi zakończone po konkretnej dacie
    @GetMapping("/finished/{afterTime}")
    public List<TrainingDto> getTrainingsEndedAfter(@PathVariable String afterTime) throws ParseException {
        return trainingService.getTrainingsEndedAfter(afterTime);
    }

    // Metoda zwracająca treningi po konkretnej aktywności
    @GetMapping("/activityType")
    public List<TrainingDto> getTrainingsByActivityType(@RequestParam ActivityType activityType) {
        return trainingService.getTrainingsByActivityType(activityType);
    }
}

