package app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import app.enums.LanguageLevel;
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