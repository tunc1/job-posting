package app.service;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import app.entity.Company;
import app.repository.CompanyRepository;

@Service
public class CompanyService
{
	private CompanyRepository companyRepository;
	public CompanyService(CompanyRepository companyRepository)
	{
		this.companyRepository=companyRepository;
	}
	public Company findById(Long id)
	{
		return companyRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
	public List<Company> findAll()
	{
		return companyRepository.findAll();
	}
}