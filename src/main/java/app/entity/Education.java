package app.entity;

import java.util.Date;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import app.enums.Degree;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import org.hibernate.annotations.Check;

@Entity
@Check(constraints="started_at<finished_at")
public class Education
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JsonProperty(access=Access.WRITE_ONLY)
    @JoinColumn(updatable=false)
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