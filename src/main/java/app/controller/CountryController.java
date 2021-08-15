package app.controller;

import app.entity.Country;
import app.service.CountryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/country")
public class CountryController
{
	private CountryService countryService;
	public CountryController(CountryService countryService)
	{
		this.countryService=countryService;
	}
	@PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	public Country save(@RequestBody Country country)
	{
		return countryService.save(country);
	}
	@PutMapping("/{id}")
	public Country update(@RequestBody Country country,@PathVariable Long id)
	{
		country.setId(id);
		return countryService.update(country);
	}
	@GetMapping("/{id}")
	public Country findById(@PathVariable Long id)
	{
		return countryService.findById(id);
	}
	@GetMapping
	public List<Country> findAll()
	{
		return countryService.findAll();
	}
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id)
	{
		countryService.deleteById(id);
	}
}