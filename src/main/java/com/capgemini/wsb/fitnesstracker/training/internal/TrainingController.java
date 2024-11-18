package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;

    // Endpoint do pobrania wszystkich treningów
    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        List<Training> trainings = trainingService.getAllTrainings();
        return trainings.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Metoda pomocnicza do konwersji obiektu Training na TrainingDto
    private TrainingDto convertToDto(Training training) {
        return new TrainingDto(
                training.getId(),
                new TrainingDto.UserDto(
                        training.getUser().getId(), // Pobieramy ID użytkownika
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
