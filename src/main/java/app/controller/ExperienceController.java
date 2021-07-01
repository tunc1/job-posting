package app.controller;

import app.entity.Experience;
import app.service.ExperienceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/experience")
public class ExperienceController
{
	private ExperienceService experienceService;
	public ExperienceController(ExperienceService experienceService)
	{
		this.experienceService=experienceService;
	}
	@PostMapping
	public Experience save(@RequestBody Experience experience)
	{
		return experienceService.save(experience);
	}
	@PutMapping("/{id}")
	public Experience update(@RequestBody Experience experience,@PathVariable Long id)
	{
		experience.setId(id);
		return experienceService.update(experience);
	}
	@GetMapping("/{id}")
	public Experience findById(@PathVariable Long id)
	{
		return experienceService.findById(id);
	}
	@GetMapping
	public List<Experience> findAll()
	{
		return experienceService.findAll();
	}
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id)
	{
		experienceService.deleteById(id);
	}
}