package app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import app.entity.Manager;
import app.consts.Role;
import app.repository.ManagerRepository;
import app.util.UserUtil;

import javax.persistence.EntityNotFoundException;

@Service
public class ManagerService
{
	private ManagerRepository managerRepository;
	private UserUtil userUtil;
	public ManagerService(ManagerRepository managerRepository, UserUtil userUtil)
	{
		this.managerRepository = managerRepository;
		this.userUtil = userUtil;
	}
	public Manager save(Manager manager)
	{
		userUtil.throwExceptionIfUsernameConflicts(manager.getUser().getUsername());
		manager.getUser().setPassword(userUtil.encodePassword(manager.getUser().getPassword()));
		manager.getUser().setRole(Role.MANAGER);
		manager.getUser().setCredentialsNonExpired(true);
		manager.getUser().setAccountNonLocked(true);
		manager.getUser().setAccountNonExpired(true);
		manager.getUser().setEnabled(true);
		return managerRepository.save(manager);
	}
	public Manager update(Manager manager)
	{
		userUtil.throwExceptionIfUsernameConflicts(manager.getUser().getUsername());
		return managerRepository.save(manager);
	}
	public void deleteById(Long id)
	{
		managerRepository.deleteById(id);
	}
	public Manager findById(Long id)
	{
		return managerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
	public List<Manager> findAll()
	{
		return managerRepository.findAll();
	}
}