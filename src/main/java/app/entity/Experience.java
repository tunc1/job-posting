package app.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@Entity
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
    public Experience(){}
    public Experience(Company company,Member member,Date startedAt,Date leftAt)
    {
        this.company=company;
        this.member=member;
        this.startedAt=startedAt;
        this.leftAt=leftAt;
    }
    public Experience(Long id,Company company,Member member,Date startedAt,Date leftAt)
    {
        this(company,member,startedAt,leftAt);
        this.id=id;
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