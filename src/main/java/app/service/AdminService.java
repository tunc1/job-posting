package app.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import app.entity.Admin;
import app.entity.Role;
import app.entity.User;
import app.repository.AdminRepository;

@Service
public class AdminService
{
	@Value("${admin-username}")
	private String adminUsername;
	@Value("${admin-password}")
	private String adminPassword;
	private AdminRepository adminRepository;
	private PasswordEncoder passwordEncoder;
	public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder)
	{
		this.adminRepository = adminRepository;
		this.passwordEncoder = passwordEncoder;
	}
	@PostConstruct
	public void createAdmin()
	{
		if(adminRepository.count()==0)
		{
			User user=new User();
			user.setUsername(adminUsername);
			user.setPassword(passwordEncoder.encode(adminPassword));
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