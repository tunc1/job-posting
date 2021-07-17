package app.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.Check;

import java.util.Date;

@Entity
@Check(constraints="started_at<left_at")
public class Experience
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Company company;
    @ManyToOne
    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    private Member member;
    private Date startedAt,leftAt;
    private String title,summary;
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title=title;
    }
    public String getSummary()
    {
        return summary;
    }
    public void setSummary(String summary)
    {
        this.summary=summary;
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
    public Member getMember()
    {
        return member;
    }
    public void setMember(Member member)
    {
        this.member=member;
    }
    public Date getStartedAt()
    {
        return startedAt;
    }
    public void setStartedAt(Date startedAt)
    {
        this.startedAt=startedAt;
    }
    public Date getLeftAt()
    {
        return leftAt;
    }
    public void setLeftAt(Date leftAt)
    {
        this.leftAt=leftAt;
    }
}