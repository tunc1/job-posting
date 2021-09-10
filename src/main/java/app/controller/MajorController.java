package app.controller;

import app.entity.Major;
import app.service.MajorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/major")
public class MajorController
{
	private MajorService majorService;
	public MajorController(MajorService majorService)
	{
		this.majorService=majorService;
	}
	@PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	public Major save(@RequestBody Major major)
	{
		return majorService.save(major);
	}
	@PutMapping("/{id}")
	public Major update(@RequestBody Major major,@PathVariable Long id)
	{
		major.setId(id);
		return majorService.update(major);
	}
	@GetMapping("/{id}")
	public Major findById(@PathVariable Long id)
	{
		return majorService.findById(id);
	}
	@GetMapping
	public List<Major> findAll()
	{
		return majorService.findAll();
	}
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id)
	{
		majorService.deleteById(id);
	}
}