package com.momentum.momentum_back.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.momentum.momentum_back.entity.Pet;
import com.momentum.momentum_back.repository.PetRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PetStatsScheduler {

    private final PetRepository petRepository;

    // Ejecutar cada hora (3600000 ms = 1 hora)
    @Scheduled(fixedRate = 3600000)
    @Transactional
    public void degradePetStats() {
        log.info("Iniciando degradación de estadísticas de mascotas...");
        
        List<Pet> pets = petRepository.findAll();
        
        for (Pet pet : pets) {
            boolean needsUpdate = false;
            
            // Reducir hambre (cada hora -20 de hambre, significa más hambrienta)
            if (pet.getHunger() != null && pet.getHunger() > 0) {
                pet.setHunger(Math.max(0, pet.getHunger() - 20));
                needsUpdate = true;
            }
            
            // Aplicar efectos del hambre (valores bajos de hunger = hambrienta)
            if (pet.getHunger() != null && pet.getHunger() < 100) {
                // Si tiene hambre (valores bajos), reducir salud y energía
                int hungerPenalty = calculateHungerPenalty(pet.getHunger());
                
                if (pet.getHealth() != null && pet.getHealth() > hungerPenalty) {
                    pet.setHealth(Math.max(0, pet.getHealth() - hungerPenalty));
                    needsUpdate = true;
                }
                
                if (pet.getEnergy() != null && pet.getEnergy() > hungerPenalty) {
                    pet.setEnergy(Math.max(0, pet.getEnergy() - hungerPenalty));
                    needsUpdate = true;
                }
            }
            
            if (needsUpdate) {
                pet.setUpdatedAt(LocalDateTime.now());
                petRepository.save(pet);
                log.debug("Actualizadas estadísticas para mascota ID: {}", pet.getPetId());
            }
        }
        
        log.info("Degradación de estadísticas completada para {} mascotas", pets.size());
    }
    
    private int calculateHungerPenalty(int hunger) {
        // Penalización basada en el nivel de hambre (valores bajos = hambrienta)
        if (hunger <= 20) return 3; // Muy hambrienta: -3 salud/energía
        if (hunger <= 40) return 2; // Hambrienta: -2 salud/energía  
        if (hunger <= 60) return 1; // Un poco hambrienta: -1 salud/energía
        return 0; // Sin hambre: sin penalización
    }
}