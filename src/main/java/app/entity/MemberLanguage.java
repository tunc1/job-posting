package app.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"member_id","language_id"})})
public class MemberLanguage
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(updatable=false)
    private Member member;
    @ManyToOne
    private Language language;
    @Enumerated(EnumType.STRING)
    private LanguageLevel level;
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
    public Language getLanguage()
    {
        return language;
    }
    public void setLanguage(Language language)
    {
        this.language=language;
    }
    public LanguageLevel getLevel()
    {
        return level;
    }
    public void setLevel(LanguageLevel level)
    {
        this.level = level;
    }
}