package com.pis.referralportal.repository;

import com.pis.referralportal.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    public Role findByRoleName(String roleName);
}
