package app.entity;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Member
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name,lastName,title,summary;
    @OneToOne(cascade=CascadeType.ALL)
    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(updatable=false)
    private User user;
    @Column(unique=true,nullable=false)
    private String email;
    @ManyToOne
    private City city;
    @ManyToMany
    @JoinTable(name="member_skill",joinColumns=@JoinColumn(name="member_id"),inverseJoinColumns=@JoinColumn(name="skill_id"))
    private Set<Skill> skills;
    @OneToMany(mappedBy="member",cascade=CascadeType.ALL,orphanRemoval=true)
    private Set<MemberLanguage> languages;
    @OneToMany(mappedBy="member",cascade=CascadeType.ALL,orphanRemoval=true)
    private Set<Experience> experiences;
    @OneToMany(mappedBy="member",cascade=CascadeType.ALL,orphanRemoval=true)
    private Set<Education> educations;
    public User getUser()
    {
        return user;
    }
    public void setUser(User user)
    {
        this.user = user;
    }
    public Set<Education> getEducations()
    {
        return educations;
    }
    public void setEducations(Set<Education> educations)
    {
        educations.forEach(education->education.setMember(this));
        this.educations = educations;
    }
    public Set<Experience> getExperiences()
    {
        return experiences;
    }
    public void setExperiences(Set<Experience> experiences)
    {
        experiences.forEach(experience->experience.setMember(this));
        this.experiences = experiences;
    }
    public Set<MemberLanguage> getLanguages()
    {
        return languages;
    }
    public void setLanguages(Set<MemberLanguage> languages)
    {
        languages.forEach(language->language.setMember(this));
        this.languages = languages;
    }
    public Set<Skill> getSkills()
    {
        return skills;
    }
    public void setSkills(Set<Skill> skills)
    {
        this.skills=skills;
    }
    public City getCity()
    {
        return city;
    }
    public void setCity(City city)
    {
        this.city=city;
    }
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
    public boolean equals(Object obj)
    {
        return id==((Member)obj).id;
    }
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email=email;
    }
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id=id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name=name;
    }
    public String getLastName()
    {
        return lastName;
    }
    public void setLastName(String lastName)
    {
        this.lastName=lastName;
    }
}