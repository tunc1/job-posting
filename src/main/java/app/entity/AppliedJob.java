package app.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"member_id","job_posting_id"})})
public class AppliedJob
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private JobPosting jobPosting;
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
    public JobPosting getJobPosting()
    {
        return jobPosting;
    }
    public void setJobPosting(JobPosting jobPosting)
    {
        this.jobPosting=jobPosting;
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