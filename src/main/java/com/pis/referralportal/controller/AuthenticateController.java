package com.pis.referralportal.controller;

import com.pis.referralportal.model.Candidate;
import com.pis.referralportal.model.request.AuthenticationRequest;
import com.pis.referralportal.model.response.AuthenticationResponse;
import com.pis.referralportal.model.response.ReferralStats;
import com.pis.referralportal.repository.CandidateRepository;
import com.pis.referralportal.service.AuthenticationService;
import com.pis.referralportal.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthenticateController {

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthenticationService authService;
    @Autowired
    private CandidateRepository candidateRepository;

    @PostMapping("/login")
    @CrossOrigin
    public ResponseEntity<AuthenticationResponse> authentication(@RequestBody AuthenticationRequest authRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

            String token = jwtUtil.generateToken(authRequest.getEmail());
            System.out.println(token);
            return ResponseEntity.ok().body(authService.getAuthResponse(token, authRequest.getEmail()));
        } catch (Exception ex) {
            System.out.println("Invalid Email/Password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/getStats")
    @CrossOrigin
    public ResponseEntity<ReferralStats> getReferralsStats(){
        List<Candidate> candidates = (List<Candidate>) candidateRepository.findAll();
        ReferralStats stats = new ReferralStats();
        stats.setTotalReferrals(candidates.size());
        candidates.forEach(c -> {
            //Status : IN-PROCESS, HIRED, REJECT
            if("HIRED".equalsIgnoreCase(c.getStatus())){
                stats.setSelectedCandidates(stats.getSelectedCandidates()+1);
            } else if("REJECT".equalsIgnoreCase(c.getStatus())){
                stats.setRejectedCandidates(stats.getRejectedCandidates()+1);
            } else {
                stats.setInProcess(stats.getInProcess()+1);
            }
        });
        return ResponseEntity.ok(stats);
    }
}
