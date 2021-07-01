package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entity.Company;

public interface CompanyRepository extends JpaRepository<Company,Long>{}