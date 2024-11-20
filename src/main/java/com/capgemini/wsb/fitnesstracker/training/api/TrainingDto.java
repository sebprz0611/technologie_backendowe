package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
public class TrainingDto {

    @NotNull
    private Long id; // ID treningu

    @NotNull
    private UserDto user; // Pełny obiekt użytkownika (możesz stworzyć specjalny UserDto)

    @NotNull
    private Date startTime; // Czas rozpoczęcia treningu

    @NotNull
    private Date endTime; // Czas zakończenia treningu

    @NotNull
    private ActivityType activityType; // Typ aktywności

    @Min(0)
    private double distance; // Dystans

    @Min(0)
    private double averageSpeed; // Średnia prędkość

    @Data
    @AllArgsConstructor
    public static class UserDto {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
    }
}
