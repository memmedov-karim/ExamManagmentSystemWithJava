package com.example.ExamManagmentSystem.controller;

import com.example.ExamManagmentSystem.service.download.DownloadService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
public class DownloadController {
    private final DownloadService downloadService;

    public DownloadController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }
    //Region download all tickets which creat herself with region id
    @GetMapping("/download-region-tickets")
    public ResponseEntity<InputStreamResource> downloadRegionTickets(HttpServletRequest request) throws IOException {
        return downloadService.downloadRegionTicketsAsCSV(request);

    }
}
