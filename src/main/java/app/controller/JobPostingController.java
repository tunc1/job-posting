package app.controller;

import app.criteria.JobPostingCriteria;
import app.entity.JobPosting;
import app.entity.Manager;
import app.service.JobPostingService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/jobPosting")
public class JobPostingController
{
	private final int pageSize=5;
	private JobPostingService jobPostingService;
	public JobPostingController(JobPostingService jobPostingService)
	{
		this.jobPostingService=jobPostingService;
	}
	@PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	public JobPosting save(@RequestBody JobPosting jobPosting,Authentication authentication)
	{
		Manager manager=(Manager)authentication.getPrincipal();
		return jobPostingService.save(jobPosting,manager);
	}
	@PutMapping("/{id}")
	public JobPosting update(@RequestBody JobPosting jobPosting,@PathVariable Long id,Authentication authentication)
	{
		Manager manager=(Manager)authentication.getPrincipal();
		jobPosting.setId(id);
		return jobPostingService.update(jobPosting,manager);
	}
	@GetMapping("/{id}")
	public JobPosting findById(@PathVariable Long id)
	{
		return jobPostingService.findById(id);
	}
	@GetMapping
	public Page<JobPosting> findAll(JobPostingCriteria criteria,Pageable pageable)
	{
		return jobPostingService.findAll(criteria,pageable);
	}
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id,Authentication authentication)
	{
		Manager manager=(Manager)authentication.getPrincipal();
		jobPostingService.deleteById(id,manager);
	}
}