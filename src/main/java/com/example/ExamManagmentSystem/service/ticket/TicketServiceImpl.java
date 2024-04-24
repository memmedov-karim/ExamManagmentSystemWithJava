package com.example.ExamManagmentSystem.service.ticket;
import com.example.ExamManagmentSystem.dto.NewTicketDto;
import com.example.ExamManagmentSystem.dto.RegionTicketDto;
import com.example.ExamManagmentSystem.entity.Region;
import com.example.ExamManagmentSystem.entity.Exam;
import com.example.ExamManagmentSystem.entity.Center;
import com.example.ExamManagmentSystem.entity.Ticket;
import com.example.ExamManagmentSystem.exceptions.NotFoundException;
import com.example.ExamManagmentSystem.exceptions.ProcessNotAllowedException;
import com.example.ExamManagmentSystem.mapper.TicketMapper;
import com.example.ExamManagmentSystem.repository.CenterRepository;
import com.example.ExamManagmentSystem.repository.ExamRepository;
import com.example.ExamManagmentSystem.repository.RegionRepository;
import com.example.ExamManagmentSystem.repository.TicketRepository;
import com.example.ExamManagmentSystem.service.auth.jwt.JsonWebTokenService;
import com.example.ExamManagmentSystem.utils.UtisGenerator;
import com.example.ExamManagmentSystem.validator.DtoValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final ExamRepository examRepository;
    private final RegionRepository regionRepository;
    private final TicketRepository ticketRepository;
    private final CenterRepository centerRepository;
    private final JsonWebTokenService jsonWebTokenService;
    private final UtisGenerator utisGenerator;
    private final DtoValidator dtoValidator;
    @Override
    public Ticket saveTicket(HttpServletRequest request,@Valid @NotNull NewTicketDto newTicket){
        Long regionId = jsonWebTokenService.getUserIdFromToken(request,"tokenR");
        dtoValidator.validateDto(newTicket);
        Region exsistingRegion = regionRepository.findById(regionId)
                .orElseThrow(()->new NotFoundException("There is not region with this id:"+regionId));
        Long examId = newTicket.getExam();
        Exam exsistingExam = examRepository.findById(examId)
                .orElseThrow(()->new NotFoundException("There is not exam with this id:"+examId));
        Long centerId = newTicket.getCenter();
        Center exsistingCenter = centerRepository.findById(centerId)
                .orElseThrow(()->new NotFoundException("There is not center with this id:"+centerId));
        Ticket newTicketInstance = new Ticket();
        newTicketInstance.setRegion(exsistingRegion);
        newTicketInstance.setExam(exsistingExam);
        newTicketInstance.setCenter(exsistingCenter);
        if(!Objects.equals(exsistingCenter.getRegion().getId(), regionId)){
            throw new ProcessNotAllowedException("This process is not allowed for you");

        }
        if(!Objects.equals(exsistingRegion.getCreator().getId(), exsistingExam.getCreator().getId())){
            throw new ProcessNotAllowedException("This process is not allowed for you");

        }
        newTicketInstance.setClas(newTicket.getClas());
        newTicketInstance.setSector(newTicket.getSector());
        newTicketInstance.setName(newTicket.getName());
        newTicketInstance.setSurname(newTicket.getSurname());
        newTicketInstance.setFather(newTicket.getFather());
        newTicketInstance.setRoom(newTicket.getRoom());
        newTicketInstance.setPlace(newTicket.getPlace());
        newTicketInstance.setPhone(newTicket.getPhone());
        String uniqueUtis = utisGenerator.generateUniqueUtis(request);
        newTicketInstance.setUtis(uniqueUtis);
        return ticketRepository.save(newTicketInstance);
    }

    @Override
    public List<RegionTicketDto> getTicketsOfRegion(HttpServletRequest request,int pageNumber,int pageSize,String clas,String fenn,Long examId){
        Long regionid = jsonWebTokenService.getUserIdFromToken(request,"tokenR");
        Region exsistingRegion = regionRepository.findById(regionid)
                .orElseThrow(()->new NotFoundException("There is not region with this id:"+regionid));
        List<Ticket> filteredtickets = this.filterTickets(exsistingRegion,pageNumber,pageSize,clas,fenn,examId);
        List<RegionTicketDto> allRegionTickets = TicketMapper.INSTANCE.ticketsToRegionTicketDtos(filteredtickets);
        return allRegionTickets;
    }
    @Override
    public List<RegionTicketDto> getTicketsOfUser(HttpServletRequest request,int pageNumber,int pageSize,String clas,String fenn,Long examId){
        Long userid = jsonWebTokenService.getUserIdFromToken(request,"tokenU");
//        List<Ticket> tickets = ticketRepository.findAll().stream().filter(ticket -> Objects.equals(ticket.getRegion().getCreator().getId(), userid)).toList();
        List<Ticket> tickets = ticketRepository.findByRegion_Creator_Id(userid);
        List<RegionTicketDto> allUserTickets = TicketMapper.INSTANCE.ticketsToRegionTicketDtos(tickets);
        return allUserTickets;
    }













    private List<Ticket> filterTickets(Region exsistingRegion,int pageNumber,int pageSize,String clas,String fenn,Long examId){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Ticket> filteredtickets=new ArrayList<>();
        if(clas!=null && fenn!=null && examId!=null){
            filteredtickets = ticketRepository.findByRegionAndClasAndFennAndExam_Id(exsistingRegion,pageable,clas,fenn,examId);
        }else if(clas!=null && fenn!=null){
            filteredtickets = ticketRepository.findByRegionAndClasAndFenn(exsistingRegion,pageable,clas,fenn);
        }else if(clas!=null && examId!=null){
            filteredtickets = ticketRepository.findByRegionAndClasAndExam_Id(exsistingRegion,pageable,clas,examId);
        }else if(fenn!=null && examId!=null){
            filteredtickets = ticketRepository.findByRegionAndFennAndExam_Id(exsistingRegion,pageable,fenn,examId);
        } else if (clas!=null) {
            filteredtickets = ticketRepository.findByRegionAndClas(exsistingRegion,pageable,clas);
        } else if (fenn!=null) {
            filteredtickets = ticketRepository.findByRegionAndFenn(exsistingRegion,pageable,fenn);
        }else if(examId!=null){
            filteredtickets = ticketRepository.findByRegionAndExam_Id(exsistingRegion,pageable,examId);
        }else{
            filteredtickets = ticketRepository.findByRegion(exsistingRegion,pageable);
        }
        return filteredtickets;
    }

}
