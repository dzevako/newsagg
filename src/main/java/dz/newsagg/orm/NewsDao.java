package dz.newsagg.orm;

import java.util.List;

/**
 * Доступ к данным типа Новость
 *
 * @author dzevako
 * @since Nov 6, 2017
 */
public interface NewsDao
{
    List<NewsItem> getLastNews(String text, int maxResult);

    List<NewsItem> getNews();

    List<String> getNewsTitles();

    void save(NewsItem item);
}
