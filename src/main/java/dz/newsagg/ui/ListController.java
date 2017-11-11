package dz.newsagg.ui;

import java.util.List;

import javax.inject.Inject;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import dz.newsagg.orm.NewsDao;
import dz.newsagg.orm.NewsItem;

/**
 * Контроллер для списка.
 * Подгружает новые элементы в реальном времени
 *
 * @author dzevako
 * @since Nov 9, 2017
 */
@Component
public class ListController implements ApplicationListener<NewsItemSaveEvent>
{
    private static final int MAX_CAPACITY = 20;

    @Inject
    private NewsDao dao;

    private DefaultListModel<NewsItem> model;
    private JTextField filter;

    public void filterList()
    {
        model.clear();
        List<NewsItem> items = dao.getLastNews(filter.getText().trim(), MAX_CAPACITY);
        items.forEach(item -> model.addElement(item));
    }

    @Override
    public void onApplicationEvent(NewsItemSaveEvent event)
    {
        if (model == null)
        {
            return;
        }
        filterList();
    }

    public void setFilter(JTextField filter)
    {
        this.filter = filter;
    }

    public void setList(DefaultListModel<NewsItem> model)
    {
        this.model = model;
    }
}
