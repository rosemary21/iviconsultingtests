package com.example.iviconsultingtest.companies.repository;

import com.example.iviconsultingtest.companies.model.RegisteredCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository  extends JpaRepository<RegisteredCompany,Long> {

    RegisteredCompany findByEmailAddress(String emailAddress);
}
