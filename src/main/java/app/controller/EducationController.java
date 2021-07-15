package app.controller;

import app.entity.Education;
import app.entity.Member;
import app.service.EducationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/education")
public class EducationController
{
	private EducationService educationService;
	public EducationController(EducationService educationService)
	{
		this.educationService=educationService;
	}
	@PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	public Education save(@RequestBody Education education,Authentication authentication)
	{
		Member member=(Member)authentication.getPrincipal();
		education.setMember(member);
		return educationService.save(education);
	}
	@PutMapping("/{id}")
	public Education update(@RequestBody Education education,@PathVariable Long id,Authentication authentication)
	{
		Member member=(Member)authentication.getPrincipal();
		education.setId(id);
		return educationService.update(education,member);
	}
	@GetMapping("/{id}")
	public Education findById(@PathVariable Long id)
	{
		return educationService.findById(id);
	}
	@GetMapping
	public List<Education> findByMember(Authentication authentication)
	{
		Member member=(Member)authentication.getPrincipal();
		return educationService.findByMember(member);
	}
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id,Authentication authentication)
	{
		Member member=(Member)authentication.getPrincipal();
		educationService.deleteById(id,member);
	}
}