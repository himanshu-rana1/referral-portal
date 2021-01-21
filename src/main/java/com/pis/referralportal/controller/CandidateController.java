package com.pis.referralportal.controller;

import com.pis.referralportal.model.Candidate;
import com.pis.referralportal.model.request.CandidateRequest;
import com.pis.referralportal.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateRepository candidateRepository;

    @PostMapping(path = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @CrossOrigin
    public ResponseEntity<Candidate> addCandidate(@ModelAttribute CandidateRequest request){
        try {
            Candidate candidate = new Candidate(request.getCandidateName(), request.getRelevantExperience(), request.getJobId(), request.getReferredBy(), request.getReferredByEmployeeId(), request.getStatus(), request.getResume().getBytes());
            return ResponseEntity.ok(candidateRepository.save(candidate));
        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/get/{id}")
    @CrossOrigin
    public ResponseEntity<Candidate> getCandidate(@PathVariable Integer id){
        return ResponseEntity.ok(candidateRepository.findById(id).orElse(new Candidate()));
    }

    @GetMapping("/getByEmployee/{employeeId}")
    @CrossOrigin
    public ResponseEntity<Candidate> getCandidateByEmployeeId(@PathVariable Integer employeeId){
        return ResponseEntity.ok(candidateRepository.findCandidateByReferredByEmployeeId(employeeId));
    }

    @GetMapping("/getByJob/{jobId}")
    public ResponseEntity<Candidate> getCandidateByJobId(@PathVariable Integer jobId){
        return ResponseEntity.ok(candidateRepository.findCandidateByJobId(jobId));
    }
}
