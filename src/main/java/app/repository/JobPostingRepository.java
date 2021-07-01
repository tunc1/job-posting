package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entity.JobPosting;

public interface JobPostingRepository extends JpaRepository<JobPosting,Long>{}