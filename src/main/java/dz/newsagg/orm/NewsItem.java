package dz.newsagg.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * News entity
 *
 * @author dzevako
 * @since Nov 4, 2017
 */
@Entity
@Table(name = "newsitem", uniqueConstraints = { @UniqueConstraint(columnNames = "id") })
public class NewsItem
{
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final int DEFAULT_TEXT_COLUMN_LENGTH = 20000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID, unique = true, nullable = false)
    private long id;

    @Column(name = TITLE, nullable = false)
    private String title;

    @Column(length = DEFAULT_TEXT_COLUMN_LENGTH, name = DESCRIPTION, nullable = false)
    private String description;

    public NewsItem()
    {

    }

    public NewsItem(String title, String description)
    {
        this.title = title;
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public long getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    @Override
    public String toString()
    {
        int l = 70;
        return getTitle().length() > l ? (getTitle().substring(0, l) + "...") : getTitle();
    }
}
