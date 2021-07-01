package app.controller;

import app.entity.JobPosting;
import app.service.JobPostingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobPosting")
public class JobPostingController
{
	private JobPostingService jobPostingService;
	public JobPostingController(JobPostingService jobPostingService)
	{
		this.jobPostingService=jobPostingService;
	}
	@PostMapping
	public JobPosting save(@RequestBody JobPosting jobPosting)
	{
		return jobPostingService.save(jobPosting);
	}
	@PutMapping("/{id}")
	public JobPosting update(@RequestBody JobPosting jobPosting,@PathVariable Long id)
	{
		jobPosting.setId(id);
		return jobPostingService.update(jobPosting);
	}
	@GetMapping("/{id}")
	public JobPosting findById(@PathVariable Long id)
	{
		return jobPostingService.findById(id);
	}
	@GetMapping
	public List<JobPosting> findAll()
	{
		return jobPostingService.findAll();
	}
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id)
	{
		jobPostingService.deleteById(id);
	}
}