package app.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import app.entity.Manager;
import app.entity.Role;
import app.entity.User;
import app.repository.ManagerRepository;
import app.util.UserUtil;

@ExtendWith(MockitoExtension.class)
public class ManagerServiceTest
{
    @Mock
    ManagerRepository managerRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    UserUtil userUtil;
    ManagerService managerService;

    @BeforeEach
    void init()
    {
        managerService=new ManagerService(managerRepository, passwordEncoder, userUtil);
    }
    @Test
    void testDeleteById()
    {
        Manager manager=new Manager();
        manager.setId(1L);
        managerService.deleteById(1L);
        Mockito.verify(managerRepository).deleteById(Mockito.anyLong());
    }
    @Test
    void testFindAll()
    {
        List<Manager> companies=List.of(new Manager());
        Mockito.when(managerRepository.findAll()).thenReturn(companies);
        Assertions.assertEquals(companies,managerService.findAll());
    }
    @Test
    void testFindById()
    {
        Manager manager=new Manager();
        manager.setId(1L);
        Mockito.when(managerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(manager));
        Assertions.assertEquals(manager,managerService.findById(1L));
    }
    @Test
    void testFindById_throwsEntityNotFoundException()
    {
        Mockito.when(managerRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,()->managerService.findById(1L));
    }
    @Test
    void testSave()
    {
        Mockito.doNothing().when(userUtil).throwExceptionIfUsernameConflicts("username");
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encoded_password");
        Mockito.when(managerRepository.save(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,Manager.class);
        });
        Manager manager=new Manager();
        User user=new User();
        String username="username",password="password";
        user.setPassword(password);
        user.setUsername(username);
        manager.setUser(user);
        Manager savedManager=managerService.save(manager);
        Assertions.assertEquals(manager,savedManager);
        Assertions.assertNotEquals(password,savedManager.getUser().getPassword());
        Assertions.assertTrue(savedManager.getUser().getRole().equals(Role.MANAGER));
        Assertions.assertTrue(savedManager.getUser().isAccountNonExpired());
        Assertions.assertTrue(savedManager.getUser().isAccountNonLocked());
        Assertions.assertTrue(savedManager.getUser().isCredentialsNonExpired());
        Assertions.assertTrue(savedManager.getUser().isEnabled());
    }
    @Test
    void testUpdate()
    {
        Mockito.doNothing().when(userUtil).throwExceptionIfUsernameConflicts("username");
        Mockito.when(managerRepository.save(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,Manager.class);
        });
        Manager manager=new Manager();
        manager.setId(1L);
        User user=new User();
        String username="username";
        user.setUsername(username);
        manager.setUser(user);
        Manager updatedManager=managerService.update(manager);
        Assertions.assertEquals(manager,updatedManager);
    }
}
