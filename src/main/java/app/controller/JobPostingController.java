package app.controller;

import app.entity.JobPosting;
import app.entity.Member;
import app.service.JobPostingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

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
	@ResponseStatus(code=HttpStatus.CREATED)
	public JobPosting save(@RequestBody JobPosting jobPosting,Authentication authentication)
	{
		Member member=(Member)authentication.getPrincipal();
		return jobPostingService.save(jobPosting,member);
	}
	@PutMapping("/{id}")
	public JobPosting update(@RequestBody JobPosting jobPosting,@PathVariable Long id,Authentication authentication)
	{
		Member member=(Member)authentication.getPrincipal();
		jobPosting.setId(id);
		return jobPostingService.update(jobPosting,member);
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
	@GetMapping(params="companyId")
	public List<JobPosting> findByCompanyId(@RequestParam long companyId)
	{
		return jobPostingService.findByCompanyId(companyId);
	}
	@GetMapping(params="title")
	public List<JobPosting> searchByTitle(@RequestParam String title)
	{
		return jobPostingService.searchByTitle(title);
	}
	@GetMapping(params="skill")
	public List<JobPosting> findBySkillsId(@RequestParam Long skill)
	{
		return jobPostingService.findBySkillsId(skill);
	}
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id,Authentication authentication)
	{
		Member member=(Member)authentication.getPrincipal();
		jobPostingService.deleteById(id,member);
	}
}