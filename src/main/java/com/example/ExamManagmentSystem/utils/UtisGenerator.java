package com.example.ExamManagmentSystem.utils;

import com.example.ExamManagmentSystem.entity.Region;
import com.example.ExamManagmentSystem.entity.Ticket;
import com.example.ExamManagmentSystem.repository.RegionRepository;
import com.example.ExamManagmentSystem.repository.TicketRepository;
import com.example.ExamManagmentSystem.service.auth.jwt.JsonWebTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Random;
@Service
public class UtisGenerator {
    private final TicketRepository ticketRepository;
    private final RegionRepository regionRepository;
    private final JsonWebTokenService jsonWebTokenService;
    public UtisGenerator(TicketRepository ticketRepository, RegionRepository regionRepository, JsonWebTokenService jsonWebTokenService) {
        this.ticketRepository = ticketRepository;
        this.regionRepository = regionRepository;
        this.jsonWebTokenService = jsonWebTokenService;
    }

    public  String generateUniqueUtis(HttpServletRequest request) {
        Long regionId = jsonWebTokenService.getUserIdFromToken(request,"tokenR");
        Region exsistingRegion = regionRepository.findById(regionId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"There is not user for your region"));
        List<Ticket> tickets = ticketRepository.findByRegion_Creator_Id(exsistingRegion.getCreator().getId());
        Random random = new Random();
        String uniqueUTIS;
        boolean isUnique;
        do {
            uniqueUTIS = String.format("%07d", random.nextInt(10000000));
            isUnique = isUTISUnique(uniqueUTIS, tickets);
        } while (!isUnique);
        return uniqueUTIS;
    }
    private  boolean isUTISUnique(String uti, List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            if (ticket.getUtis().equals(uti)) {
                return false;
            }
        }
        return true;
    }
}
