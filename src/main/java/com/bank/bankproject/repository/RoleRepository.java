package com.bank.bankproject.repository;

import com.bank.bankproject.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

    @Query(value = "SELECT role From Role role " +
            "WHERE role.description =:description")
    Optional<Role> findRoleByDescription(@Param("description") String description);

}
