package com.tmkcomputers.springjwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tmkcomputers.springjwt.models.Employee;
import com.tmkcomputers.springjwt.models.EmployeeIdentity;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, EmployeeIdentity> {
    List<Employee> findByEmployeeIdentityCompanyId(String companyId);
}
