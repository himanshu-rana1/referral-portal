package com.pis.referralportal.model;

import javax.persistence.*;

@Entity
@Table(name = "candidate")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String candidateName;
    private Integer relevantExperience;
    private Integer jobId;
    private String referredBy;
    private Integer referredByEmployeeId;
    private String status;
    @Column(length = 16777215)
    private byte[] resume;

    public Candidate() {
    }

    public Candidate(String candidateName, Integer relevantExperience, Integer jobId, String referredBy, Integer referredByEmployeeId, String status, byte[] resume) {
        this.candidateName = candidateName;
        this.relevantExperience = relevantExperience;
        this.jobId = jobId;
        this.referredBy = referredBy;
        this.referredByEmployeeId = referredByEmployeeId;
        this.status = status;
        this.resume = resume;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public Integer getRelevantExperience() {
        return relevantExperience;
    }

    public void setRelevantExperience(Integer relevantExperience) {
        this.relevantExperience = relevantExperience;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getReferredBy() {
        return referredBy;
    }

    public void setReferredBy(String referredBy) {
        this.referredBy = referredBy;
    }

    public Integer getReferredByEmployeeId() {
        return referredByEmployeeId;
    }

    public void setReferredByEmployeeId(Integer referredByEmployeeId) {
        this.referredByEmployeeId = referredByEmployeeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte[] getResume() {
        return resume;
    }

    public void setResume(byte[] resume) {
        this.resume = resume;
    }
}
