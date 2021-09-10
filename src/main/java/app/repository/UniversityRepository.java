package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entity.University;

public interface UniversityRepository extends JpaRepository<University,Long>{}