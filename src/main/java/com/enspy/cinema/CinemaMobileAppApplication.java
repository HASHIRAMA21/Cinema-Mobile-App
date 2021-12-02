package com.enspy.cinema;

import com.enspy.cinema.metier.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CinemaMobileAppApplication implements CommandLineRunner {
    private  ICinemaInitService iCinemaInitService;

    public CinemaMobileAppApplication(ICinemaInitService iCinemaInitService) {
        this.iCinemaInitService = iCinemaInitService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CinemaMobileAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        iCinemaInitService.initVille();
        iCinemaInitService.initCinema();
        iCinemaInitService.initSalle();
        iCinemaInitService.initPlace();
        iCinemaInitService.initSeance();
        iCinemaInitService.initCategorie();
        iCinemaInitService.initFilm();
        iCinemaInitService.initProjection();
        iCinemaInitService.initTicket();
    }
}
