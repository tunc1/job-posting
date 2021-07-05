package app.controller;

import app.entity.AppliedJob;
import app.entity.Member;
import app.service.AppliedJobService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/appliedJob")
public class AppliedJobController
{
	private AppliedJobService appliedJobService;
	public AppliedJobController(AppliedJobService appliedJobService)
	{
		this.appliedJobService=appliedJobService;
	}
	@PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	public AppliedJob save(@RequestBody AppliedJob appliedJob,Authentication authentication)
	{
		Member member=(Member)authentication.getPrincipal();
		appliedJob.setMember(member);
		return appliedJobService.save(appliedJob);
	}
	@GetMapping("/{id}")
	public AppliedJob findById(@PathVariable Long id,Authentication authentication)
	{
		Member member=(Member)authentication.getPrincipal();
		return appliedJobService.findById(id,member);
	}
	@GetMapping
	public List<AppliedJob> findByMember(Authentication authentication)
	{
		Member member=(Member)authentication.getPrincipal();
		return appliedJobService.findByMember(member);
	}
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id,Authentication authentication)
	{
		Member member=(Member)authentication.getPrincipal();
		appliedJobService.deleteById(id,member);
	}
}