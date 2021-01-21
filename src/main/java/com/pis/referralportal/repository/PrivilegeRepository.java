package com.pis.referralportal.repository;

import com.pis.referralportal.model.Privilege;
import com.pis.referralportal.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface PrivilegeRepository extends CrudRepository<Privilege, String> {

    public Privilege findByGroupName(String groupName);
}
