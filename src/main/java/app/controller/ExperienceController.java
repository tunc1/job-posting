package app.controller;

import app.entity.Experience;
import app.entity.Member;
import app.service.ExperienceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

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
	@ResponseStatus(code=HttpStatus.CREATED)
	public Experience save(@RequestBody Experience experience,Authentication authentication)
	{
		Member member=(Member)authentication.getPrincipal();
		experience.setMember(member);
		return experienceService.save(experience);
	}
	@PutMapping("/{id}")
	public Experience update(@RequestBody Experience experience,@PathVariable Long id,Authentication authentication)
	{
		Member member=(Member)authentication.getPrincipal();
		experience.setId(id);
		return experienceService.update(experience,member);
	}
	@GetMapping("/{id}")
	public Experience findById(@PathVariable Long id,Authentication authentication)
	{
		Member member=(Member)authentication.getPrincipal();
		return experienceService.findById(id,member);
	}
	@GetMapping
	public List<Experience> findByMember(Authentication authentication)
	{
		Member member=(Member)authentication.getPrincipal();
		return experienceService.findByMember(member);
	}
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id,Authentication authentication)
	{
		Member member=(Member)authentication.getPrincipal();
		experienceService.deleteById(id,member);
	}
}