package app.service;

import java.util.List;
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
	public Company findById(Long id)
	{
		return companyRepository.findById(id).orElse(null);
	}
	public List<Company> findAll()
	{
		return companyRepository.findAll();
	}
}