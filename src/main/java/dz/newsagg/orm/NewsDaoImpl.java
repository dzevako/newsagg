package dz.newsagg.orm;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Релализация NewsDao
 *
 * @author dzevako
 * @since Nov 6, 2017
 */
@Component
public class NewsDaoImpl implements NewsDao
{
    @Inject
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public List<NewsItem> getLastNews(String filter, int maxResult)
    {
        CriteriaBuilder cb = getSession().getCriteriaBuilder();
        CriteriaQuery<NewsItem> cq = cb.createQuery(NewsItem.class);
        Root<NewsItem> root = cq.from(NewsItem.class);
        cq.select(root);
        cq.distinct(true);

        Predicate p = filter == null || filter.isEmpty() ? null
                : cb.like(cb.lower(root.<String> get(NewsItem.TITLE)), "%" + filter.toLowerCase() + "%");
        if (p != null)
        {
            cq.where(p);
        }

        Order o = cb.desc(root.get(NewsItem.ID));
        cq.orderBy(o);

        TypedQuery<NewsItem> tq = getSession().createQuery(cq);
        tq.setMaxResults(maxResult);

        return tq.getResultList();
    }

    @Override
    @Transactional
    public List<NewsItem> getNews()
    {
        CriteriaBuilder cb = getSession().getCriteriaBuilder();
        CriteriaQuery<NewsItem> cq = cb.createQuery(NewsItem.class);
        Root<NewsItem> root = cq.from(NewsItem.class);
        cq.select(root);
        TypedQuery<NewsItem> tq = getSession().createQuery(cq);
        return tq.getResultList();
    }

    @Override
    @Transactional
    public List<String> getNewsTitles()
    {
        CriteriaBuilder cb = getSession().getCriteriaBuilder();
        CriteriaQuery<String> cq = cb.createQuery(String.class);
        Root<NewsItem> root = cq.from(NewsItem.class);
        cq.multiselect(root.get(NewsItem.TITLE).as(String.class));
        TypedQuery<String> tq = getSession().createQuery(cq);
        return tq.getResultList();
    }

    @Override
    @Transactional
    public void save(NewsItem item)
    {
        truncateDescription(item);
        getSession().save(item);
    }

    private Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }

    private void truncateDescription(NewsItem item)
    {
        if (item.getDescription().length() > NewsItem.DEFAULT_TEXT_COLUMN_LENGTH)
        {
            item.setDescription(item.getDescription().substring(0, NewsItem.DEFAULT_TEXT_COLUMN_LENGTH - 4) + "...");
        }
    }
}
