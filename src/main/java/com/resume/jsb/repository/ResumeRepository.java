package com.resume.jsb.repository;

import com.resume.jsb.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long>{
    Resume findByCandidateName(String name);
	
}