package app.service;

import java.util.List;
import org.springframework.stereotype.Service;
import app.entity.JobPosting;
import app.repository.JobPostingRepository;

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
		return jobPostingRepository.findById(id).orElse(null);
	}
	public List<JobPosting> findAll()
	{
		return jobPostingRepository.findAll();
	}
}