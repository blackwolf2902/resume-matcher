package com.resume.jsb.controller;

import com.resume.jsb.model.Resume;
import com.resume.jsb.service.ResumeService;
import com.resume.jsb.util.ResumeScorer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.tika.Tika;
import java.nio.file.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/resumes")
public class ResumeController {

    private final ResumeService service;
    private final ResumeScorer scorer;  

    public ResumeController(ResumeService service, ResumeScorer scorer) {
        this.service = service;
        this.scorer = scorer;
    }

    @PostMapping("/upload")
    public Resume uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("candidateName") String name) throws Exception {

        String uploadDir = "uploads/";
        Files.createDirectories(Paths.get(uploadDir));
        String filePath = uploadDir + file.getOriginalFilename();
        file.transferTo(Paths.get(filePath));

        Tika tika = new Tika();
        String text = tika.parseToString(Paths.get(filePath));

        Resume resume = new Resume();
        resume.setCandidateName(name);
        resume.setFilePath(filePath);
        resume.setTextContent(text);
        resume.setKeywords("");

        return service.saveResume(resume);
    }

    @PostMapping("/match")
    public Map<String, Object> matchResume(
            @RequestParam("jdText") String jdText,
            @RequestParam("resumeText") String resumeText) {

        return scorer.calculateScore(jdText, resumeText);
    }

    @GetMapping
    public List<Resume> getAll() {
        return service.getAllResumes();
    }
}
