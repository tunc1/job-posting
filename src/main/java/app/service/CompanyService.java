package app.service;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import app.entity.Company;
import app.entity.Member;
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
	public boolean belongsTo(Long id,Member member)
	{
		Company company=findById(id);
		if(company.getManager().equals(member))
			return true;
		throw new UnauthorizedException();
	}
}