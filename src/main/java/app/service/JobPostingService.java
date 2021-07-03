package app.service;

import java.util.List;
import org.springframework.stereotype.Service;
import app.entity.JobPosting;
import app.repository.JobPostingRepository;
import javax.persistence.EntityNotFoundException;

@Service
public class JobPostingService
{
	private JobPostingRepository jobPostingRepository;
	public JobPostingService(JobPostingRepository jobPostingRepository)
	{
		this.jobPostingRepository=jobPostingRepository;
	}
	public JobPosting save(JobPosting jobPosting)
	{
		return jobPostingRepository.save(jobPosting);
	}
	public JobPosting update(JobPosting jobPosting)
	{
		return jobPostingRepository.save(jobPosting);
	}
	public void deleteById(Long id)
	{
		jobPostingRepository.deleteById(id);
	}
	public JobPosting findById(Long id)
	{
		return jobPostingRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
	public List<JobPosting> findAll()
	{
		return jobPostingRepository.findAll();
	}
}