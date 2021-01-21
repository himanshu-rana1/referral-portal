package com.pis.referralportal.controller;

import com.pis.referralportal.model.Job;
import com.pis.referralportal.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hr")
public class HRController {

    @Autowired
    private JobRepository jobRepository;

    @PostMapping("/addJob")
    @CrossOrigin
    public ResponseEntity<Job> addJob(@RequestBody Job jobRequest){
        return ResponseEntity.ok(jobRepository.save(jobRequest));
    }

//    @PutMapping("/edit")
//    public ResponseEntity<JobResponse> editJob(@RequestBody JobRequest jobRequest) {
//
//    }

    @DeleteMapping("/delete")
    @CrossOrigin
    public ResponseEntity<String> deleteJob(@RequestParam("id") int id) {
        jobRepository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/all")
    @CrossOrigin
    public ResponseEntity<List<Job>> getAllJob() {
        List<Job> jobs = (List<Job>) jobRepository.findAll();
        return ResponseEntity.ok(jobs);
    }
}
