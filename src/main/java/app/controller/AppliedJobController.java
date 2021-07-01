package app.controller;

import app.entity.AppliedJob;
import app.service.AppliedJobService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
	public AppliedJob save(@RequestBody AppliedJob appliedJob)
	{
		return appliedJobService.save(appliedJob);
	}
	@PutMapping("/{id}")
	public AppliedJob update(@RequestBody AppliedJob appliedJob,@PathVariable Long id)
	{
		appliedJob.setId(id);
		return appliedJobService.update(appliedJob);
	}
	@GetMapping("/{id}")
	public AppliedJob findById(@PathVariable Long id)
	{
		return appliedJobService.findById(id);
	}
	@GetMapping
	public List<AppliedJob> findAll()
	{
		return appliedJobService.findAll();
	}
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id)
	{
		appliedJobService.deleteById(id);
	}
}