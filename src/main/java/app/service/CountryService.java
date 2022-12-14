package app.service;

import java.util.List;
import org.springframework.stereotype.Service;
import app.entity.Country;
import app.repository.CountryRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CountryService
{
	private CountryRepository countryRepository;
	public CountryService(CountryRepository countryRepository)
	{
		this.countryRepository=countryRepository;
	}
	public Country save(Country country)
	{
		return countryRepository.save(country);
	}
	public Country update(Country country)
	{
		return countryRepository.save(country);
	}
	public void deleteById(Long id)
	{
		countryRepository.deleteById(id);
	}
	public Country findById(Long id)
	{
		return countryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
	public List<Country> findAll()
	{
		return countryRepository.findAll();
	}
}