package app.service;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import app.entity.Company;
import app.entity.Manager;
import app.exception.UnauthorizedException;
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
	public Company save(Company company)
	{
		return companyRepository.save(company);
	}
	public Company update(Company company)
	{
		return companyRepository.save(company);
	}
	public void deleteById(Long id)
	{
		companyRepository.deleteById(id);
	}
	public void throwExceptionIfNotSameManager(Long id,Manager manager)
	{
		Company company=findById(id);
		if(!company.getManager().equals(manager))
			throw new UnauthorizedException();
	}
}