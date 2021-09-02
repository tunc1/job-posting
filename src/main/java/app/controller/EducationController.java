package app.controller;

import app.entity.Education;
import app.entity.HasUser;
import app.entity.Member;
import app.service.EducationService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/member/{memberId}/education")
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
	@GetMapping
	public List<Education> findAllByMemberId(@PathVariable Long memberId,Authentication authentication)
	{
		HasUser hasUser=(HasUser)authentication.getPrincipal();
		return educationService.findAllByMemberId(memberId,hasUser);
	}
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id,Authentication authentication)
	{
		Member member=(Member)authentication.getPrincipal();
		educationService.deleteById(id,member);
	}
}