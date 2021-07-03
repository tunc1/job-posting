package app.entity;

import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Member implements UserDetails
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name,lastName,title;
    @Column(unique=true,nullable=false)
    private String email,username;
    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    String password;
    @ManyToOne
    private Company company;
    private boolean accountNonExpired,accountNonLocked,credentialsNonExpired,enabled;
    public Member(){}
    public Member(String name,String lastName,String username,String title,String email,String password,
            Company company,boolean accountNonExpired,boolean accountNonLocked,boolean credentialsNonExpired,
            boolean enabled)
    {
        this.name=name;
        this.lastName=lastName;
        this.username=username;
        this.title=title;
        this.email=email;
        this.password=password;
        this.company=company;
        this.accountNonExpired=accountNonExpired;
        this.accountNonLocked=accountNonLocked;
        this.credentialsNonExpired=credentialsNonExpired;
        this.enabled=enabled;
    }
    public Member(Long id,String name,String lastName,String username,String title,String email,String password,
            Company company,boolean accountNonExpired,boolean accountNonLocked,boolean credentialsNonExpired,
            boolean enabled)
    {
        this(name,lastName,username,title,email,password,company,accountNonExpired,accountNonLocked,credentialsNonExpired,enabled);
        this.id=id;
    }
    public boolean isAccountNonExpired()
    {
        return accountNonExpired;
    }
    public void setAccountNonExpired(boolean accountNonExpired)
    {
        this.accountNonExpired=accountNonExpired;
    }
    public boolean isAccountNonLocked()
    {
        return accountNonLocked;
    }
    public void setAccountNonLocked(boolean accountNonLocked)
    {
        this.accountNonLocked=accountNonLocked;
    }
    public boolean isCredentialsNonExpired()
    {
        return credentialsNonExpired;
    }
    public void setCredentialsNonExpired(boolean credentialsNonExpired)
    {
        this.credentialsNonExpired=credentialsNonExpired;
    }
    public boolean isEnabled()
    {
        return enabled;
    }
    public void setEnabled(boolean enabled)
    {
        this.enabled=enabled;
    }
    public void setUsername(String username)
    {
        this.username=username;
    }
    public void setPassword(String password)
    {
        this.password=password;
    }
    public String getUsername()
    {
        return username;
    }
    public String getPassword()
    {
        return password;
    }
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email=email;
    }
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id=id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name=name;
    }
    public String getLastName()
    {
        return lastName;
    }
    public void setLastName(String lastName)
    {
        this.lastName=lastName;
    }
    public Company getCompany()
    {
        return company;
    }
    public void setCompany(Company company)
    {
        this.company=company;
    }
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title=title;
    }
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return List.of(new SimpleGrantedAuthority("ROLE_Member"));
    }
}