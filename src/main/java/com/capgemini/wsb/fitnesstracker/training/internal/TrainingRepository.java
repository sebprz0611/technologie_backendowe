package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Date;
import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    // Metoda zwracająca treningi dla określonego użytkownika
    List<Training> findByUserId(Long userId);

    // Metoda zwracająca treningi zakończone po konkretnej dacie
    @Query("SELECT t FROM Training t WHERE t.endTime > :date")
    List<Training> findTrainingsEndedAfter(@Param("date") Date date);

    // Metoda zwracająca treningi po konkretnej aktywności
    @Query("SELECT t FROM Training t WHERE t.activityType = :activityType")
    List<Training> findByActivityType(@Param("activityType") ActivityType activityType);
}
