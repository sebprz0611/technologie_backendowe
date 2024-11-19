package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import java.text.ParseException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;

    // Metoda zwracająca wszystkie treningi
    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        List<Training> trainings = trainingService.getAllTrainings();
        return trainings.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Metoda zwracająca treningi dla określonego użytkownika
    @GetMapping("/{userId}")
    public List<TrainingDto> getTrainingsByUserId(@PathVariable Long userId) {
        List<Training> trainings = trainingService.getTrainingsByUserId(userId);
        return trainings.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
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

    private TrainingDto convertToDto(Training training) {
        return new TrainingDto(
                training.getId(),
                new TrainingDto.UserDto(
                        training.getUser().getId(),
                        training.getUser().getFirstName(),
                        training.getUser().getLastName(),
                        training.getUser().getEmail()
                ),
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed()
        );
    }
}
