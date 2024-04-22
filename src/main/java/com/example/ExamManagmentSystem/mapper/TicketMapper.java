package com.example.ExamManagmentSystem.mapper;

import com.example.ExamManagmentSystem.dto.RegionTicketDto;
import com.example.ExamManagmentSystem.entity.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;


public interface TicketMapper {
    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);
    @Mappings({
            @Mapping(source = "region.id", target = "regionid"),
            @Mapping(source = "center.id", target = "centerid"),
            @Mapping(source = "center.name", target = "centername"),
            @Mapping(source = "center.code", target = "centercode"),
            @Mapping(source = "exam.id", target = "examid"),
            @Mapping(source = "exam.name", target = "examname"),
            @Mapping(source = "exam.dateandtime", target = "examdateandtime"),
            @Mapping(source = "exam.title", target = "examtitle")
    })
    RegionTicketDto ticketToRegionTicketDto(Ticket ticket);

    List<RegionTicketDto> ticketsToRegionTicketDtos(List<Ticket> tickets);
}
