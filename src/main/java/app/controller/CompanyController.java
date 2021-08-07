package app.controller;

import app.entity.Company;
import app.service.CompanyService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController
{
	private CompanyService companyService;
	public CompanyController(CompanyService companyService)
	{
		this.companyService=companyService;
	}
	@GetMapping("/{id}")
	public Company findById(@PathVariable Long id)
	{
		return companyService.findById(id);
	}
	@GetMapping
	public List<Company> findAll()
	{
		return companyService.findAll();
	}
	@PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	public Company save(@RequestBody Company company)
	{
		return companyService.save(company);
	}
	@PutMapping("/{id}")
	public Company update(@RequestBody Company company,@PathVariable Long id)
	{
		company.setId(id);
		return companyService.update(company);
	}
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id)
	{
		companyService.deleteById(id);
	}
}