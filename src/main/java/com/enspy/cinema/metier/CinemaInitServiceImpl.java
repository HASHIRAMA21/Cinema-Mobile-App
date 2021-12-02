package com.enspy.cinema.metier;


import com.enspy.cinema.dao.*;
import com.enspy.cinema.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Transactional
public class CinemaInitServiceImpl implements  ICinemaInitService{
    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private
    CinemaRepository cinemaRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private  SeanceRepository seanceRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private  ProjectionRepository projectionRepository;
    @Override
    public void initVille() {
      Stream.of("Casablanca","Marrakech","Rabat","Tanger").forEach(v->{
          Ville ville = new Ville();
          ville.setName("nameVille");
          villeRepository.save(ville);
      });
    }

    @Override
    public void initCinema() {
        villeRepository.findAll().forEach(v->{
            Stream.of("MegaRama","IMAX","FOUNOUN","CHAHRAZAD","DOUALIZ")
                    .forEach(nameCinema->{
                        Cinema cinema= new Cinema();
                        cinema.setName(nameCinema);
                        cinema.setVille(v);
                        cinema.setNombreSalles(3+(int) Math.random()*7);
                    });
        });

    }

    /**
     *
     */
    @Override
    public void initSalle() {
       cinemaRepository.findAll().forEach(cinema -> {
          for(int i = 0;i<cinema.getNombreSalles();i++){
              Salle salle = new Salle();
              salle.setName("Selle"+(i+1));
              salle.setCinemas(cinema);
              salle.setNombrePlace(20+(int)Math.random()*20);
              salleRepository.save(salle);
          }
       });
    }

    @Override
    public void initPlace() {
salleRepository.findAll().forEach(salle -> {
    for(int i= 0; i<salle.getNombrePlace();i++){
        Place place =new Place();
        place.setNumero(i+1);
        place.setSalle(salle);
        placeRepository.save(place);
    }
});
    }

    @Override
    public void initSeance() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
       Stream.of("12:00","15:00","17:00","19:00","21:00").forEach(
               s ->{
                   Seance seance = new Seance();
                   try {
                       seance.setHeureDebut(dateFormat.parse(s));
                       seanceRepository.save(seance);
                   } catch (ParseException e) {
                       e.printStackTrace();
                   }
               }
       );
    }

    @Override
    public void initCategorie() {
       Stream.of("Histoire ","Actions","Fictions","Aventure").forEach(cat ->{
           Categorie categorie = new Categorie();
           categorie.setName(cat);
           categorieRepository.save(categorie);
       });
    }

    @Override
    public void initFilm() {
        double[] duree = new double[] {1,1.5,2,2.5,4};
        List<Categorie> categories = categorieRepository.findAll();
      Stream.of("Game of Thrones","Seigneur des Anneaux","Iron Man","Spider Man").forEach(tif ->{
       Film film = new Film();
       film.setTitre(tif);
       film.setDuree(duree[new Random().nextInt(duree.length)]);
       film.setPhoto(tif.replaceAll(" ",""));
       film.setCategorie(categories.get(new  Random().nextInt(categories.size())));
      });
    }

    @Override
    public void initProjection() {
        double[] prices = new double[] {30,50,60,70,80,90};
         villeRepository.findAll().forEach(ville -> {
             ville.getCinemas().forEach(cinema ->{
                 cinema.getSalles().forEach(salle -> {
                     filmRepository.findAll().forEach(film -> {
                         seanceRepository.findAll().forEach(seance -> {
                             Projection projection= new Projection();
                             projection.setDateProjection(new Date());
                             projection.setFilm(film);
                             projection.setPrix(prices[new Random().nextInt(prices.length)]);
                             projection.setSalle(salle);
                             projection.setSeance(seance);
                             projectionRepository.save(projection);
                         });
                     });
                 });
             });
         });
    }

    @Override
    public void initTicket() {
      projectionRepository.findAll().forEach(p -> {
          p.getSalle().getPlaces().forEach(place -> {
              Ticket  ticket = new Ticket();
              ticket.setPlace(place);
              ticket.setPrix(p.getPrix());
              ticket.setProjection(p);
              ticket.setReserve(false);
              ticketRepository.save(ticket);
          });
      });
    }
}
