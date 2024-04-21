package com.example.ExamManagmentSystem.dto.dtomapper;

import com.example.ExamManagmentSystem.dto.RegionTicketDto;
import com.example.ExamManagmentSystem.entity.Exam;
import com.example.ExamManagmentSystem.entity.Ticket;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class MapTo {

    public List<RegionTicketDto> regionTicketDto(List<Ticket> tickets){
        List<RegionTicketDto> allRegionTickets = tickets.stream()
                .map(ticket -> {
                    RegionTicketDto regionTicketDto = new RegionTicketDto();
                    regionTicketDto.setId(ticket.getId());
                    regionTicketDto.setRegionid(ticket.getRegion().getId());
                    regionTicketDto.setCenterid(ticket.getCenter().getId());
                    regionTicketDto.setCentername(ticket.getCenter().getName());
                    regionTicketDto.setCentercode(ticket.getCenter().getCode());
                    regionTicketDto.setClas(ticket.getClas());
                    regionTicketDto.setFenn(ticket.getFenn());
                    regionTicketDto.setSector(ticket.getSector());
                    regionTicketDto.setName(ticket.getName());
                    regionTicketDto.setSurname(ticket.getSurname());
                    regionTicketDto.setFather(ticket.getFather());
                    regionTicketDto.setRoom(ticket.getRoom());
                    regionTicketDto.setPlace(ticket.getPlace());
                    regionTicketDto.setPhone(ticket.getPhone());
                    regionTicketDto.setUtis(ticket.getUtis());
                    Exam exam = ticket.getExam();
                    if (exam != null) {
                        regionTicketDto.setExamid(exam.getId());
                        regionTicketDto.setExamname(exam.getName());
                        regionTicketDto.setExamdateandtime(exam.getDateandtime());
                        regionTicketDto.setExamtitle(exam.getTitle());
                    }
                    return regionTicketDto;
                })
                .toList();

        return allRegionTickets;
    }
}
