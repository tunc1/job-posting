package app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import app.entity.Skill;
import app.repository.SkillRepository;

@Service
public class SkillService
{
    private SkillRepository skillRepository;
	public SkillService(SkillRepository skillRepository)
	{
		this.skillRepository=skillRepository;
	}
	public Skill save(Skill skill)
	{
		return skillRepository.save(skill);
	}
	public void deleteById(Long id)
	{
		skillRepository.deleteById(id);
	}
    public Skill update(Skill skill)
    {
        return skillRepository.save(skill);
    }
	public Skill findById(Long id)
	{
		return skillRepository.findById(id).get();
	}
	public List<Skill> findAll()
	{
		return skillRepository.findAll();
	}
}