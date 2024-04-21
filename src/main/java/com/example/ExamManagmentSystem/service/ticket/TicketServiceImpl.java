package com.example.ExamManagmentSystem.service.ticket;
import com.example.ExamManagmentSystem.dto.RegionTicketDto;
import com.example.ExamManagmentSystem.dto.dtomapper.MapTo;
import com.example.ExamManagmentSystem.entity.Region;
import com.example.ExamManagmentSystem.entity.Exam;
import com.example.ExamManagmentSystem.entity.Center;
import com.example.ExamManagmentSystem.entity.Ticket;
import com.example.ExamManagmentSystem.repository.CenterRepository;
import com.example.ExamManagmentSystem.repository.ExamRepository;
import com.example.ExamManagmentSystem.repository.RegionRepository;
import com.example.ExamManagmentSystem.repository.TicketRepository;
import com.example.ExamManagmentSystem.service.auth.jwt.JsonWebTokenService;
import com.example.ExamManagmentSystem.utils.UtisGenerator;
import jakarta.servlet.http.HttpServletRequest;
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
public class TicketServiceImpl implements TicketService {
    private final ExamRepository examRepository;
    private final RegionRepository regionRepository;
    private final TicketRepository ticketRepository;
    private final CenterRepository centerRepository;
    private final JsonWebTokenService jsonWebTokenService;
    private final UtisGenerator utisGenerator;
    private final MapTo mapTo;
    public TicketServiceImpl(ExamRepository examRepository, RegionRepository regionRepository, TicketRepository ticketRepository, CenterRepository centerRepository, JsonWebTokenService jsonWebTokenService, UtisGenerator utisGenerator, MapTo mapTo){
        this.examRepository = examRepository;
        this.ticketRepository = ticketRepository;
        this.regionRepository = regionRepository;
        this.centerRepository = centerRepository;
        this.jsonWebTokenService = jsonWebTokenService;
        this.utisGenerator = utisGenerator;
        this.mapTo = mapTo;
    }
    public ResponseEntity<Ticket> saveTicket(HttpServletRequest request, Ticket newTicket){
        Long regionId = jsonWebTokenService.getUserIdFromToken(request,"tokenR");
        Region exsistingRegion = regionRepository.findById(regionId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"There is not region with this id:"+regionId));
        Long examId = newTicket.getExam().getId();
        Exam exsistingExam = examRepository.findById(examId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"There is not exam with this id:"+examId));
        Long centerId = newTicket.getCenter().getId();
        Center exsistingCenter = centerRepository.findById(centerId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"There is not center with this id:"+centerId));
        newTicket.setRegion(exsistingRegion);
        newTicket.setExam(exsistingExam);
        newTicket.setCenter(exsistingCenter);
        if(!Objects.equals(exsistingCenter.getRegion().getId(), regionId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Your region is not same with region of this center");

        }
        if(!Objects.equals(exsistingRegion.getCreator().getId(), exsistingExam.getCreator().getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"region creator is not same with exam creator");

        }
        if(newTicket.getClas()==null || newTicket.getClas().isEmpty() || newTicket.getSector()==null || newTicket.getSector().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"class,sector is required");
        }
        String uniqueUtis = utisGenerator.generateUniqueUtis(request);
        newTicket.setUtis(uniqueUtis);
        Ticket savedTicket = ticketRepository.save(newTicket);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTicket);
    }

    @Override
    public List<RegionTicketDto> getTicketsOfRegion(HttpServletRequest request,int pageNumber,int pageSize,String clas,String fenn,Long examId){
        Long regionid = jsonWebTokenService.getUserIdFromToken(request,"tokenR");
        Region exsistingRegion = regionRepository.findById(regionid)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"There is not region with this id:"+regionid));
        List<Ticket> filteredtickets = this.filterTickets(exsistingRegion,pageNumber,pageSize,clas,fenn,examId);
        List<RegionTicketDto> allRegionTickets = mapTo.regionTicketDto(filteredtickets);
        return allRegionTickets;
    }
    @Override
    public List<RegionTicketDto> getTicketsOfUser(HttpServletRequest request,int pageNumber,int pageSize,String clas,String fenn,Long examId){
        Long userid = jsonWebTokenService.getUserIdFromToken(request,"tokenU");
//        List<Ticket> tickets = ticketRepository.findAll().stream().filter(ticket -> Objects.equals(ticket.getRegion().getCreator().getId(), userid)).toList();
        List<Ticket> tickets = ticketRepository.findByRegion_Creator_Id(userid);
        List<RegionTicketDto> allUserTickets = mapTo.regionTicketDto(tickets);
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
