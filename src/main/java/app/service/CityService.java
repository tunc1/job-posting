package app.service;

import java.util.List;
import org.springframework.stereotype.Service;
import app.entity.City;
import app.repository.CityRepository;
import javax.persistence.EntityNotFoundException;

@Service
public class CityService
{
	private CityRepository cityRepository;
	public CityService(CityRepository cityRepository)
	{
		this.cityRepository=cityRepository;
	}
	public City save(City city)
	{
		return cityRepository.save(city);
	}
	public City update(City city)
	{
		return cityRepository.save(city);
	}
	public void deleteById(Long id)
	{
		cityRepository.deleteById(id);
	}
	public City findById(Long id)
	{
		return cityRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
	public List<City> findAll()
	{
		return cityRepository.findAll();
	}
}