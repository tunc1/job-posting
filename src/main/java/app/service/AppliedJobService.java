package app.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import app.entity.AppliedJob;
import app.entity.Member;
import app.exception.UnauthorizedException;
import app.repository.AppliedJobRepository;

@Service
public class AppliedJobService
{
	private AppliedJobRepository appliedJobRepository;
	public AppliedJobService(AppliedJobRepository appliedJobRepository)
	{
		this.appliedJobRepository=appliedJobRepository;
	}
	public void throwExceptionIfNotExists(Long id)
	{
		if(!appliedJobRepository.existsById(id))
			throw new EntityNotFoundException();
	}
	public void throwExceptionIfNotSameMember(Long id,Member member)
	{
		AppliedJob appliedJob=appliedJobRepository.findById(id).get();
		if(!appliedJob.getMember().equals(member))
			throw new UnauthorizedException();
	}
	public AppliedJob save(AppliedJob appliedJob)
	{
		return appliedJobRepository.save(appliedJob);
	}
	public void deleteById(Long id,Member member)
	{
		throwExceptionIfNotExists(id);
		throwExceptionIfNotSameMember(id, member);
		appliedJobRepository.deleteById(id);
	}
	public AppliedJob findById(Long id,Member member)
	{
		throwExceptionIfNotExists(id);
		throwExceptionIfNotSameMember(id, member);
		return appliedJobRepository.findById(id).get();
	}
	public List<AppliedJob> findByMember(Member member)
	{
		return appliedJobRepository.findByMember(member);
	}
}