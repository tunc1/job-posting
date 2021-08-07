package app.service;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import app.entity.Admin;
import app.entity.Role;
import app.repository.AdminRepository;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest
{
    @Mock
    AdminRepository adminRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    AdminService adminService;
    @Captor
    ArgumentCaptor<Admin> adminArgumentCaptor;

    @BeforeEach
    void init()
    {
        adminService=new AdminService(adminRepository,passwordEncoder);
    }
    @Test
    void testCreateAdmin_create() throws IllegalArgumentException,IllegalAccessException
    {
        String username="username",password="password";
        Field adminUsernameField=AdminService.class.getDeclaredFields()[0];
        Field adminPasswordField=AdminService.class.getDeclaredFields()[1];
        adminUsernameField.setAccessible(true);
        adminPasswordField.setAccessible(true);
        adminUsernameField.set(adminService,username);
        adminPasswordField.set(adminService,password);
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encoded_password");
        Mockito.when(adminRepository.count()).thenReturn(0L);
        adminService.createAdmin();
        Mockito.verify(adminRepository).save(adminArgumentCaptor.capture());
        Admin admin=adminArgumentCaptor.getValue();
        Assertions.assertTrue(admin.getUser().isAccountNonExpired());
        Assertions.assertTrue(admin.getUser().isAccountNonLocked());
        Assertions.assertTrue(admin.getUser().isCredentialsNonExpired());
        Assertions.assertTrue(admin.getUser().isEnabled());
        Assertions.assertTrue(admin.getUser().getRole().equals(Role.ADMIN));
        Assertions.assertTrue(admin.getUser().getUsername().equals(username));
        Assertions.assertFalse(admin.getUser().getPassword().equals(password));
    }
    @Test
    void testCreateAdmin_notCreate()
    {
        Mockito.when(adminRepository.count()).thenReturn(1L);
        adminService.createAdmin();
        Mockito.verify(adminRepository,Mockito.times(0)).save(Mockito.any());
    }
    @Test
    void testFindByUserUsername()
    {
        Admin admin=new Admin();
        Mockito.when(adminRepository.findByUserUsername(Mockito.anyString())).thenReturn(admin);
        Admin actual=adminService.findByUserUsername("username");
        Assertions.assertEquals(admin, actual);
    }
}