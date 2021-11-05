package app.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import app.entity.Admin;
import app.consts.Role;
import app.entity.User;
import app.repository.AdminRepository;
import app.util.UserUtil;

@Service
public class AdminService
{
	@Value("${admin-username}")
	private String adminUsername;
	@Value("${admin-password}")
	private String adminPassword;
	private AdminRepository adminRepository;
	private UserUtil userUtil;
	public AdminService(AdminRepository adminRepository, UserUtil userUtil)
	{
		this.adminRepository = adminRepository;
		this.userUtil = userUtil;
	}
	@PostConstruct
	public void createAdmin()
	{
		if(adminRepository.count()==0)
		{
			User user=new User();
			user.setUsername(adminUsername);
			user.setPassword(userUtil.encodePassword(adminPassword));
			user.setRole(Role.ADMIN);
			user.setCredentialsNonExpired(true);
			user.setAccountNonLocked(true);
			user.setAccountNonExpired(true);
			user.setEnabled(true);
			Admin admin=new Admin();
			admin.setUser(user);
			adminRepository.save(admin);
		}
	}
	public Admin findByUserUsername(String username)
	{
		return adminRepository.findByUserUsername(username);
	}
}