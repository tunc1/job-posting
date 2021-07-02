package app.entity;

import javax.persistence.*;
import java.util.Date;

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
    public JobPosting(){}
    public JobPosting(Long id,Company company,Date publishedAt,String title,String description,boolean isActive)
    {
        this(company,publishedAt,title,description,isActive);
        this.id=id;
    }
    public JobPosting(Company company,Date publishedAt,String title,String description,boolean isActive) {
        this.company=company;
        this.publishedAt=publishedAt;
        this.title=title;
        this.description=description;
        this.isActive=isActive;
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