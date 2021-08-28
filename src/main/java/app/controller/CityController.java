package app.controller;

import app.entity.City;
import app.service.CityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/city")
public class CityController
{
	private CityService cityService;
	public CityController(CityService cityService)
	{
		this.cityService=cityService;
	}
	@PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	public City save(@RequestBody City city)
	{
		return cityService.save(city);
	}
	@PutMapping("/{id}")
	public City update(@RequestBody City city,@PathVariable Long id)
	{
		city.setId(id);
		return cityService.update(city);
	}
	@GetMapping("/{id}")
	public City findById(@PathVariable Long id)
	{
		return cityService.findById(id);
	}
	@GetMapping
	public List<City> findAll()
	{
		return cityService.findAll();
	}
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id)
	{
		cityService.deleteById(id);
	}
}