package app.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Company
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private Member manager;
    public Company(){}
    public Company(Long id, String name, Member manager)
    {
        this(name, manager);
        this.id = id;
    }
    public Company(String name, Member manager)
    {
        this.name=name;
        this.manager = manager;
    }
    public Member getManager()
    {
        return manager;
    }
    public void setManager(Member manager)
    {
        this.manager = manager;
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
}