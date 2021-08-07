package app.controller;

import app.entity.Manager;
import app.service.ManagerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/manager")
public class ManagerController
{
	private ManagerService managerService;
	public ManagerController(ManagerService managerService)
	{
		this.managerService=managerService;
	}
	@PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	public Manager save(@RequestBody Manager manager)
	{
		return managerService.save(manager);
	}
	@PutMapping("/{id}")
	public Manager update(@RequestBody Manager manager,@PathVariable Long id)
	{
		manager.setId(id);
		return managerService.update(manager);
	}
	@GetMapping("/{id}")
	public Manager findById(@PathVariable Long id)
	{
		return managerService.findById(id);
	}
	@GetMapping
	public List<Manager> findAll()
	{
		return managerService.findAll();
	}
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id)
	{
		managerService.deleteById(id);
	}
}