package com.momentum.momentum_back.config;

import com.momentum.momentum_back.entity.PetLevel;
import com.momentum.momentum_back.repository.PetLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private PetLevelRepository petLevelRepository;

    @Override
    public void run(String... args) throws Exception {
        // Only initialize if the table is empty
        if (petLevelRepository.count() == 0) {
            initializePetLevels();
        }
    }

    private void initializePetLevels() {
        PetLevel[] levels = {
            new PetLevel(1, 0, "Baby Dragon", "A tiny hatchling with barely developed wings"),
            new PetLevel(2, 50, "Curious Hatchling", "Starting to explore the world with wonder"),
            new PetLevel(3, 120, "Playful Wyrmling", "Full of energy and mischief"),
            new PetLevel(4, 220, "Spirited Juvenile", "Growing stronger and more confident"),
            new PetLevel(5, 350, "Young Dragon", "Developing impressive abilities"),
            new PetLevel(6, 520, "Agile Adolescent", "Swift and graceful in flight"),
            new PetLevel(7, 730, "Fierce Young Adult", "Showing signs of true dragon power"),
            new PetLevel(8, 980, "Mature Dragon", "Commanding respect from lesser creatures"),
            new PetLevel(9, 1270, "Experienced Adult", "Wise beyond their years"),
            new PetLevel(10, 1600, "Seasoned Veteran", "Battle-tested and formidable"),
            new PetLevel(11, 1970, "Elite Dragon", "Among the most powerful of their kind"),
            new PetLevel(12, 2380, "Noble Wyrm", "Radiating ancient wisdom and strength"),
            new PetLevel(13, 2830, "Ancient Dragon", "Few dare to challenge such a being"),
            new PetLevel(14, 3320, "Legendary Beast", "Tales are told of their mighty deeds"),
            new PetLevel(15, 3850, "Mythical Wyrm", "Existing at the boundary of legend"),
            new PetLevel(16, 4420, "Primordial Force", "Channeling the raw power of creation"),
            new PetLevel(17, 5030, "Celestial Dragon", "Blessed by the heavens themselves"),
            new PetLevel(18, 5680, "Divine Avatar", "A living embodiment of dragon-kind"),
            new PetLevel(19, 6370, "Transcendent Being", "Beyond mortal comprehension"),
            new PetLevel(20, 7100, "Eternal Dragon God", "The ultimate evolution of dragonkind")
        };

        for (PetLevel level : levels) {
            petLevelRepository.save(level);
        }
    }
}