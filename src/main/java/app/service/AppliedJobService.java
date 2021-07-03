package app.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import app.entity.AppliedJob;
import app.repository.AppliedJobRepository;

@Service
public class AppliedJobService
{
	private AppliedJobRepository appliedJobRepository;
	public AppliedJobService(AppliedJobRepository appliedJobRepository)
	{
		this.appliedJobRepository=appliedJobRepository;
	}
	public AppliedJob save(AppliedJob appliedJob)
	{
		return appliedJobRepository.save(appliedJob);
	}
	public AppliedJob update(AppliedJob appliedJob)
	{
		return appliedJobRepository.save(appliedJob);
	}
	public void deleteById(Long id)
	{
		appliedJobRepository.deleteById(id);
	}
	public AppliedJob findById(Long id)
	{
		return appliedJobRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
	public List<AppliedJob> findAll()
	{
		return appliedJobRepository.findAll();
	}
}