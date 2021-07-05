package app.controller;

import app.entity.Company;
import app.service.CompanyService;
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
}