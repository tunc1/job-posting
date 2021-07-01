package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entity.Experience;

public interface ExperienceRepository extends JpaRepository<Experience,Long>{}