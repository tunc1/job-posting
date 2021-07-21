package app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.JobPosting;

public interface JobPostingRepository extends JpaRepository<JobPosting,Long>
{
    Page<JobPosting> findAll(Specification<JobPosting> specification,Pageable pageable);
}