package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TrainingDto {

    private Long id; // ID treningu
    private UserDto user; // Pełny obiekt użytkownika (możesz stworzyć specjalny UserDto)
    private Date startTime; // Czas rozpoczęcia treningu
    private Date endTime; // Czas zakończenia treningu
    private ActivityType activityType; // Typ aktywności
    private double distance; // Dystans
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
