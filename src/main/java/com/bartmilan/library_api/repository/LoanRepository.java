package com.bartmilan.library_api.repository;

import com.bartmilan.library_api.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LoanRepository extends JpaRepository<Loan, Loan>, JpaSpecificationExecutor<Loan> {
}
