package com.example.ExamManagmentSystem.controller;

import com.example.ExamManagmentSystem.dto.RegionTicketDto;
import com.example.ExamManagmentSystem.entity.Ticket;
import com.example.ExamManagmentSystem.service.ticket.TicketService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    //Region creat new ticket
    @PostMapping("/tickets")
    public ResponseEntity<Ticket> saveTicket(HttpServletRequest request, @RequestBody Ticket newTicket){
        return ticketService.saveTicket(request,newTicket);
    }
    //Fetching all tickets which created each region for region id
    @GetMapping("/tickets/region")
    public ResponseEntity<List<RegionTicketDto>> getTicketsOfRegion(HttpServletRequest request,
                                                                    @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                                                    @RequestParam(name = "pageSize", defaultValue = "30") int pageSize,
                                                                    @RequestParam(name="clas",required = false) String clas,
                                                                    @RequestParam(name="fenn",required = false) String fenn,
                                                                    @RequestParam(name="examId",required = false) Long examId
    ){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ticketService.getTicketsOfRegion(request,pageNumber,pageSize,clas,fenn,examId));
    }
    @GetMapping("/tickets/user")
    public ResponseEntity<List<RegionTicketDto>> getTicketsOfUser(HttpServletRequest request,
                                                                    @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                                                    @RequestParam(name = "pageSize", defaultValue = "30") int pageSize,
                                                                    @RequestParam(name="clas",required = false) String clas,
                                                                    @RequestParam(name="fenn",required = false) String fenn,
                                                                    @RequestParam(name="examId",required = false) Long examId
    ){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ticketService.getTicketsOfUser(request,pageNumber,pageSize,clas,fenn,examId));
    }
}
