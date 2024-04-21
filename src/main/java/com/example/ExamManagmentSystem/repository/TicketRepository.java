package com.example.ExamManagmentSystem.repository;

import com.example.ExamManagmentSystem.entity.Region;
import com.example.ExamManagmentSystem.entity.Ticket;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    List<Ticket> findByRegion(Region region, Pageable pageable);
    List<Ticket> findByRegionAndClasAndFennAndExam_Id(Region region,Pageable pageable,String clas,String fenn,Long examId);
    List<Ticket> findByRegionAndClasAndFenn(Region region,Pageable pageable,String clas,String fenn);
    List<Ticket> findByRegionAndClasAndExam_Id(Region region,Pageable pageable,String clas,Long examId);
    List<Ticket> findByRegionAndFennAndExam_Id(Region region,Pageable pageable,String fenn,Long examId);
    List<Ticket> findByRegionAndClas(Region region,Pageable pageable,String clas);
    List<Ticket> findByRegionAndFenn(Region region,Pageable pageable,String fenn);
    List<Ticket> findByRegionAndExam_Id(Region region,Pageable pageable,Long examId);


    List<Ticket> findByRegion_Creator_Id(Long userId);


}
