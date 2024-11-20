package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
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
    private final UserRepository userRepository; // Repozytorium do pobierania użytkownika z bazy

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

    // Metoda do tworzenia nowego treningu
    public TrainingDto createTraining(TrainingDto trainingDto) {
        // Pobierz użytkownika z bazy po ID
        User user = userRepository.findById(trainingDto.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found")); // Obsługuje przypadek, gdy użytkownik nie istnieje

        // Tworzenie obiektu Training z danych z DTO
        Training training = new Training(
                user,
                trainingDto.getStartTime(),
                trainingDto.getEndTime(),
                trainingDto.getActivityType(),
                trainingDto.getDistance(),
                trainingDto.getAverageSpeed()
        );

        // Zapisz trening w bazie
        Training savedTraining = trainingRepository.save(training);

        // Zwróć DTO z zapisanym treningiem
        return convertToDto(savedTraining);
    }

    // Metoda do konwersji z obiektu Training na TrainingDto
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
