package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entity.Country;

public interface CountryRepository extends JpaRepository<Country,Long>{}