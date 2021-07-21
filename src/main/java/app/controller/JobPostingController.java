package app.controller;

import app.entity.JobPosting;
import app.entity.Member;
import app.service.JobPostingService;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Sort;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
	public Page<JobPosting> findAll(@RequestParam(name="city") Optional<Long> city
		,@RequestParam(name="company") Optional<Long> company
		,@RequestParam(name="skill") Optional<Long> skill
		,@RequestParam(name="title") Optional<String> title
		,@RequestParam(name="page",defaultValue="0") int page
		,@RequestParam(name="order",defaultValue="id") String order)
	{
		return jobPostingService.findAll(city,company,skill,title,PageRequest.of(page,pageSize,Sort.by(order)));
	}
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id,Authentication authentication)
	{
		Member member=(Member)authentication.getPrincipal();
		jobPostingService.deleteById(id,member);
	}
}