package com.example.ExamManagmentSystem.service.download;

import com.example.ExamManagmentSystem.dto.RegionTicketDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface DownloadService {
    public InputStreamResource downloadRegionTicketsAsCSV(HttpServletRequest request) throws IOException;
}
