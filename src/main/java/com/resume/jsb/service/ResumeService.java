package com.resume.jsb.service;

import com.resume.jsb.model.Resume;
import com.resume.jsb.repository.ResumeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ResumeService {

	private final ResumeRepository repo;
	
	public ResumeService(ResumeRepository repo){
	this.repo=repo;
	}
	
	public Resume saveResume(Resume r){
	return repo.save(r);
	}
	
	public List<Resume> getAllResumes(){
	return repo.findAll();
	}
}