package app.controller;

import app.entity.University;
import app.service.UniversityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/university")
public class UniversityController
{
	private UniversityService universityService;
	public UniversityController(UniversityService universityService)
	{
		this.universityService=universityService;
	}
	@PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	public University save(@RequestBody University university)
	{
		return universityService.save(university);
	}
	@PutMapping("/{id}")
	public University update(@RequestBody University university,@PathVariable Long id)
	{
		university.setId(id);
		return universityService.update(university);
	}
	@GetMapping("/{id}")
	public University findById(@PathVariable Long id)
	{
		return universityService.findById(id);
	}
	@GetMapping
	public List<University> findAll()
	{
		return universityService.findAll();
	}
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id)
	{
		universityService.deleteById(id);
	}
}