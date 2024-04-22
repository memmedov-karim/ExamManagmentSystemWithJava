package com.example.ExamManagmentSystem.service.ticket;

import com.example.ExamManagmentSystem.dto.NewTicketDto;
import com.example.ExamManagmentSystem.dto.RegionTicketDto;
import com.example.ExamManagmentSystem.entity.Ticket;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public interface TicketService {
    public Ticket saveTicket(HttpServletRequest request, NewTicketDto newTicket);
    public List<RegionTicketDto> getTicketsOfRegion(HttpServletRequest request,int pageNumber,int pageSize,String clas,String fenn,Long examId);

    public List<RegionTicketDto> getTicketsOfUser(HttpServletRequest request,int pageNumber,int pageSize,String clas,String fenn,Long examId);

    private List<Ticket> filterTickets(int pageNumber, int pageSize, String clas, String fenn, Long examId) {
        return new ArrayList<>();
    }
}
