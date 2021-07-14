package app.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Education
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JsonProperty(access=Access.WRITE_ONLY)
    private Member member;
    @ManyToOne
    private University university;
    @Enumerated(EnumType.STRING)
    private Degree degree;
    @ManyToOne
    private Major major;
    private Date startedAt,finishedAt;
    public Degree getDegree()
    {
        return degree;
    }
    public void setDegree(Degree degree)
    {
        this.degree = degree;
    }
    public Major getMajor()
    {
        return major;
    }
    public void setMajor(Major major)
    {
        this.major = major;
    }
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id=id;
    }
    public Member getMember()
    {
        return member;
    }
    public void setMember(Member member)
    {
        this.member=member;
    }
    public University getUniversity()
    {
        return university;
    }
    public void setUniversity(University university)
    {
        this.university=university;
    }
    public Date getStartedAt()
    {
        return startedAt;
    }
    public void setStartedAt(Date startedAt)
    {
        this.startedAt=startedAt;
    }
    public Date getFinishedAt()
    {
        return finishedAt;
    }
    public void setFinishedAt(Date finishedAt)
    {
        this.finishedAt=finishedAt;
    }
}