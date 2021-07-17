package app.service;

import java.util.List;
import org.springframework.stereotype.Service;
import app.entity.JobPosting;
import app.entity.Member;
import app.repository.JobPostingRepository;
import javax.persistence.EntityNotFoundException;

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
		if(companyService.belongsTo(jobPosting.getCompany().getId(),member))
			return jobPostingRepository.save(jobPosting);
		return null;
	}
	public JobPosting update(JobPosting jobPosting, Member member)
	{
		if(companyService.belongsTo(jobPosting.getCompany().getId(),member))
			return jobPostingRepository.save(jobPosting);
		return null;
	}
	public void deleteById(Long id, Member member)
	{
		JobPosting jobPosting=findById(id);
		if(companyService.belongsTo(jobPosting.getCompany().getId(),member))
			jobPostingRepository.deleteById(id);
	}
	public JobPosting findById(Long id)
	{
		return jobPostingRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
	public List<JobPosting> findAll()
	{
		return jobPostingRepository.findAll();
	}
    public List<JobPosting> findByCompanyId(long companyId)
    {
        return jobPostingRepository.findByCompanyId(companyId);
    }
    public List<JobPosting> searchByTitle(String title)
    {
        return jobPostingRepository.findByTitleContaining(title);
    }
    public List<JobPosting> findBySkillsId(Long id)
    {
        return jobPostingRepository.findBySkillsId(id);
    }
}