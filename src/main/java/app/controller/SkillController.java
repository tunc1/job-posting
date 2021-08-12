package app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import app.entity.Skill;
import app.service.SkillService;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/skill")
public class SkillController
{
    private SkillService skillService;
    public SkillController(SkillService skillService)
    {
        this.skillService=skillService;
    }
    @GetMapping
    public List<Skill> findAll()
    {
        return skillService.findAll();
    }
    @PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	public Skill save(@RequestBody Skill skill)
	{
		return skillService.save(skill);
	}
	@PutMapping("/{id}")
	public Skill update(@RequestBody Skill skill,@PathVariable Long id)
	{
		skill.setId(id);
		return skillService.update(skill);
	}
	@GetMapping("/{id}")
	public Skill findById(@PathVariable Long id)
	{
		return skillService.findById(id);
	}
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id)
	{
		skillService.deleteById(id);
	}
}