package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import com.capgemini.wsb.fitnesstracker.training.api.CreateTrainingRequest;
import com.capgemini.wsb.fitnesstracker.training.api.UpdateTrainingRequest;


@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository; // Repozytorium do pobierania danych z bazy

    // Metoda zwracająca wszystkie treningi
    public List<TrainingDto> getAllTrainings() {
        return trainingRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Metoda zwracająca treningi dla określonego użytkownika
    public List<TrainingDto> getTrainingsByUserId(Long userId) {
        return trainingRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Metoda zwracająca treningi zakończone po konkretnej dacie
    public List<TrainingDto> getTrainingsEndedAfter(String afterTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(afterTime);

        return trainingRepository.findTrainingsEndedAfter(date)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Metoda zwracająca treningi po konkretnej aktywności
    public List<TrainingDto> getTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

     // Utworzenie nowego treningu
     public TrainingDto createTraining(CreateTrainingRequest request) {
        Training training = new Training(
            request.getUser(),
            request.getStartTime(),
            request.getEndTime(),
            request.getActivityType(),
            request.getDistance(),
            request.getAverageSpeed()
        );
        Training savedTraining = trainingRepository.save(training);
        return convertToDto(savedTraining);
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
    public TrainingDto updateTraining(Long trainingId, UpdateTrainingRequest request) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new IllegalArgumentException("Training not found"));
    
        // Zaktualizuj pole, jeśli zostało dostarczone
        if (request.getDistance() != null) {
            training.setDistance(request.getDistance());
        }
        if (request.getAverageSpeed() != null) {
            training.setAverageSpeed(request.getAverageSpeed());
        }
    
        // Możesz dodać inne pola do zaktualizowania tutaj
    
        // Zapisz zaktualizowany trening
        Training updatedTraining = trainingRepository.save(training);
        return convertToDto(updatedTraining);
    }
}

