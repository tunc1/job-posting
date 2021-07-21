package app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import app.entity.JobPosting;
import app.entity.Member;
import app.repository.JobPostingRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Predicate;

@Service
public class JobPostingService
{
	private JobPostingRepository jobPostingRepository;
	private CompanyService companyService;
	public JobPostingService(JobPostingRepository jobPostingRepository,CompanyService companyService)
	{
		this.jobPostingRepository = jobPostingRepository;
		this.companyService = companyService;
	}
	public JobPosting save(JobPosting jobPosting, Member member)
	{
		companyService.throwExceptionIfNotSameMember(jobPosting.getCompany().getId(),member);
		return jobPostingRepository.save(jobPosting);
	}
	public JobPosting update(JobPosting jobPosting, Member member)
	{
		companyService.throwExceptionIfNotSameMember(jobPosting.getCompany().getId(),member);
		return jobPostingRepository.save(jobPosting);
	}
	public void deleteById(Long id, Member member)
	{
		JobPosting jobPosting=findById(id);
		companyService.throwExceptionIfNotSameMember(jobPosting.getCompany().getId(),member);
		jobPostingRepository.deleteById(id);
	}
	public JobPosting findById(Long id)
	{
		return jobPostingRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
	public Specification<JobPosting> createSpecification(Optional<Long> city,Optional<Long> company,Optional<Long> skill,Optional<String> title)
	{
		return (root,query,criteriaBuilder)->
		{
			List<Predicate> list=new LinkedList<>();
			if(city.isPresent())
				list.add(criteriaBuilder.equal(root.get("city").get("id"),city.get()));
			if(company.isPresent())
				list.add(criteriaBuilder.equal(root.get("company").get("id"),company.get()));
			if(skill.isPresent())
				list.add(criteriaBuilder.equal(root.join("skills").get("id"),skill.get()));
			if(title.isPresent())
				list.add(criteriaBuilder.like(root.get("title"),"%"+title.get()+"%"));
			return criteriaBuilder.and(list.toArray(Predicate[]::new));
		};
	}
	public Page<JobPosting> findAll(Optional<Long> city,Optional<Long> company,Optional<Long> skill,Optional<String> title,Pageable pageable)
	{
		Specification<JobPosting> specification=createSpecification(city, company, skill, title);
		return jobPostingRepository.findAll(specification,pageable);
	}
}