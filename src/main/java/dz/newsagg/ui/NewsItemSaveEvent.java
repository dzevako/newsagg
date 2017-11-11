package dz.newsagg.ui;

import java.util.List;

import org.springframework.context.ApplicationEvent;

import dz.newsagg.orm.NewsItem;

/**
 * Событие изменения данных
 *
 * @author dzevako
 * @since Nov 9, 2017
 */
public class NewsItemSaveEvent extends ApplicationEvent
{
    private static final long serialVersionUID = 1L;

    public NewsItemSaveEvent(List<NewsItem> items)
    {
        super(items);
    }
}
