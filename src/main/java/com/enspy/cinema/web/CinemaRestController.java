package com.enspy.cinema.web;


import com.enspy.cinema.dao.FilmRepository;
import com.enspy.cinema.dao.TicketRepository;
import com.enspy.cinema.entities.Film;
import com.enspy.cinema.entities.Ticket;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@RestController
public class CinemaRestController {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @GetMapping(path = "/imageFilm/{id}",produces=MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable (name = "id") Long id) throws IOException {
        Film f = filmRepository.findById(id).get();
        String photoName = f.getPhoto();
        File file = new File(System.getProperty("user.home")+"/Desktop/Projects/Intellij/Cinema-MobileApp/src/main/resources/static/images/"+photoName+".jpg");
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }
    @PostMapping("/payerTickes")
    @Transactional
    public List<Ticket> ticketList(@RequestBody TicketFrom ticketFrom){
        List<Ticket> listTicket  = new ArrayList<>();
       ticketFrom.getTickets().forEach(idTicket ->{
           Ticket ticket = ticketRepository.findById(idTicket).get();
           ticket.setReserve(true);
           ticketRepository.save(ticket);
           listTicket.add(ticket);
       });
       return listTicket;
    }
}
@Data
class TicketFrom{
    private String nomClient;
    private List<Long> tickets = new ArrayList<>();
}
