package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entity.JobPosting;

public interface JobPostingRepository extends JpaRepository<JobPosting,Long>
{
    List<JobPosting> findByCompanyId(long companyId);
    List<JobPosting> findByTitleContaining(String query);
}