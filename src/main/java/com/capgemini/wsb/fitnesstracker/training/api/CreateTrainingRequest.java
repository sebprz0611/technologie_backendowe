package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.Data;

import java.util.Date;

@Data
public class CreateTrainingRequest {
    private User user; // Użytkownik związany z treningiem
    private Date startTime; // Czas rozpoczęcia
    private Date endTime; // Czas zakończenia
    private ActivityType activityType; // Typ aktywności
    private double distance; // Dystans
    private double averageSpeed; // Średnia prędkość
}
