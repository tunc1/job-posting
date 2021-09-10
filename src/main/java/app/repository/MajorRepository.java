package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entity.Major;

public interface MajorRepository extends JpaRepository<Major,Long>{}