package com.capgemini.wsb.fitnesstracker.training.api;

import lombok.Data;

@Data
public class UpdateTrainingRequest {
    private Double distance;  // Pole do zaktualizowania
    private Double averageSpeed;  // Możesz dodać inne pola, które chcesz edytować
}
