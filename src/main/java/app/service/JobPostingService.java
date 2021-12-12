package app.service;

import app.criteria.JobPostingCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import app.entity.JobPosting;
import app.entity.Manager;
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
	public JobPosting save(JobPosting jobPosting, Manager manager)
	{
		companyService.throwExceptionIfNotSameManager(jobPosting.getCompany().getId(),manager);
		return jobPostingRepository.save(jobPosting);
	}
	public JobPosting update(JobPosting jobPosting, Manager manager)
	{
		companyService.throwExceptionIfNotSameManager(jobPosting.getCompany().getId(),manager);
		return jobPostingRepository.save(jobPosting);
	}
	public void deleteById(Long id, Manager manager)
	{
		JobPosting jobPosting=findById(id);
		companyService.throwExceptionIfNotSameManager(jobPosting.getCompany().getId(),manager);
		jobPostingRepository.deleteById(id);
	}
	public JobPosting findById(Long id)
	{
		return jobPostingRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
	public Specification<JobPosting> createSpecification(JobPostingCriteria criteria)
	{
		return (root,query,criteriaBuilder)->
		{
			List<Predicate> list=new LinkedList<>();
			if(criteria.getCity()!=0)
				list.add(criteriaBuilder.equal(root.get("city").get("id"),criteria.getCity()));
			if(criteria.getCompany()!=0)
				list.add(criteriaBuilder.equal(root.get("company").get("id"),criteria.getCompany()));
			if(criteria.getTitle()!=null)
				list.add(criteriaBuilder.like(root.get("title"),"%"+criteria.getTitle()+"%"));
			return criteriaBuilder.and(list.toArray(Predicate[]::new));
		};
	}
	public Page<JobPosting> findAll(JobPostingCriteria criteria,Pageable pageable)
	{
		Specification<JobPosting> specification=createSpecification(criteria);
		return jobPostingRepository.findAll(specification,pageable);
	}
}