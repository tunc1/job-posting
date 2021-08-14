package app.controller;

import app.entity.Language;
import app.service.LanguageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/language")
public class LanguageController
{
	private LanguageService languageService;
	public LanguageController(LanguageService languageService)
	{
		this.languageService=languageService;
	}
	@PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	public Language save(@RequestBody Language language)
	{
		return languageService.save(language);
	}
	@PutMapping("/{id}")
	public Language update(@RequestBody Language language,@PathVariable Long id)
	{
		language.setId(id);
		return languageService.update(language);
	}
	@GetMapping("/{id}")
	public Language findById(@PathVariable Long id)
	{
		return languageService.findById(id);
	}
	@GetMapping
	public List<Language> findAll()
	{
		return languageService.findAll();
	}
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id)
	{
		languageService.deleteById(id);
	}
}