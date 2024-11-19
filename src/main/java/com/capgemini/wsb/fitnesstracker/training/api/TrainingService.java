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
