package app.criteria;

public class JobPostingCriteria
{
    private long company,city;
    private String title;
    public long getCompany()
    {
        return company;
    }
    public void setCompany(long company)
    {
        this.company=company;
    }
    public long getCity()
    {
        return city;
    }
    public void setCity(long city)
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
}
