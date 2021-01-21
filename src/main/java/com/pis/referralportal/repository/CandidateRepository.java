package com.pis.referralportal.repository;

import com.pis.referralportal.model.Candidate;
import org.springframework.data.repository.CrudRepository;

public interface CandidateRepository extends CrudRepository<Candidate, Integer> {

    public Candidate findCandidateByReferredByEmployeeId(Integer employeeId);

    public Candidate findCandidateByJobId(Integer jobId);
}
