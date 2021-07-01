package app.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class AppliedJob
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Company company;
    @ManyToOne
    private Member member;
    private Date appliedAt;
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
    public Date getAppliedAt()
    {
        return appliedAt;
    }
    public void setAppliedAt(Date appliedAt)
    {
        this.appliedAt=appliedAt;
    }
}