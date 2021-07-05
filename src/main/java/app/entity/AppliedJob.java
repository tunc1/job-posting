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
    private JobPosting jobPosting;
    @ManyToOne
    private Member member;
    private Date appliedAt;
    public AppliedJob(){}
    public AppliedJob(Long id,JobPosting jobPosting,Member member,Date appliedAt)
    {
        this(jobPosting,member,appliedAt);
        this.id=id;
    }
    public AppliedJob(JobPosting jobPosting, Member member, Date appliedAt)
    {
        this.jobPosting = jobPosting;
        this.member = member;
        this.appliedAt = appliedAt;
    }
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