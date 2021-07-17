package app.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class JobPosting
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Company company;
    private Date publishedAt;
    private String title,description;
    private boolean isActive;
    @ManyToMany
    @JoinTable(name="job_posting_skill",joinColumns=@JoinColumn(name="job_posting_id"),inverseJoinColumns=@JoinColumn(name="skill_id"))
    private Set<Skill> skills;
    public Set<Skill> getSkills()
    {
        return skills;
    }
    public void setSkills(Set<Skill> skills)
    {
        this.skills=skills;
    }
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id=id;
    }
    public Company getCompany()
    {
        return company;
    }
    public void setCompany(Company company)
    {
        this.company=company;
    }
    public Date getPublishedAt()
    {
        return publishedAt;
    }
    public void setPublishedAt(Date publishedAt)
    {
        this.publishedAt=publishedAt;
    }
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title=title;
    }
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description=description;
    }
    public boolean isActive()
    {
        return isActive;
    }
    public void setActive(boolean active)
    {
        isActive=active;
    }
}