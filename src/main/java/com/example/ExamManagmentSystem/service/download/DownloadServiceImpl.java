package com.example.ExamManagmentSystem.service.download;

import com.example.ExamManagmentSystem.entity.Exam;
import com.example.ExamManagmentSystem.entity.Region;
import com.example.ExamManagmentSystem.repository.RegionRepository;
import com.example.ExamManagmentSystem.repository.TicketRepository;
import com.example.ExamManagmentSystem.dto.RegionTicketDto;
import com.example.ExamManagmentSystem.service.auth.jwt.JsonWebTokenService;
import com.example.ExamManagmentSystem.service.ticket.TicketService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.opencsv.CSVWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DownloadServiceImpl implements DownloadService {

    private final TicketRepository ticketRepository;
    private final RegionRepository regionRepository;
    private final TicketService ticketService;
    private final JsonWebTokenService jsonWebTokenService;

    @Override
    public InputStreamResource downloadRegionTicketsAsCSV(HttpServletRequest request) {
        Long regionId = jsonWebTokenService.getUserIdFromToken(request,"tokenR");
        Region existingRegion = regionRepository.findById(regionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no region with this id:" + regionId));

        Pageable pageable = PageRequest.of(0, 100000);
        List<RegionTicketDto> regionTickets = ticketService.getTicketsOfRegion(request,0,10000,null,null,null);


        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             CSVWriter writer = new CSVWriter(new OutputStreamWriter(outputStream))) {
            // Write header
            String[] header = {"ID", "Region ID", "Center ID", "Center Name", "Center Code", "Class", "Subject", "Sector", "Name", "Surname", "Father", "Room", "Place", "Phone", "Exam ID", "Exam Name", "Exam Date and Time", "Exam Title"};
            writer.writeNext(header);

            // Write data rows
            for (RegionTicketDto ticket : regionTickets) {
                String[] row = {
                        String.valueOf(ticket.getId()),
                        String.valueOf(ticket.getRegionid()),
                        String.valueOf(ticket.getCenterid()),
                        ticket.getCentername(),
                        ticket.getCentercode(),
                        ticket.getClas(),
                        ticket.getFenn(),
                        ticket.getSector(),
                        ticket.getName(),
                        ticket.getSurname(),
                        ticket.getFather(),
                        ticket.getRoom(),
                        ticket.getPlace(),
                        ticket.getPhone(),
                        String.valueOf(ticket.getExamid()),
                        ticket.getExamname(),
                        ticket.getExamdateandtime(),
                        ticket.getExamtitle()
                };
                writer.writeNext(row);
            }
            writer.flush();

            // Return the CSV content as InputStreamResource
            return new InputStreamResource(new ByteArrayInputStream(outputStream.toByteArray()));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create CSV file", e);
        }
    }
}
