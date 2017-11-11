package dz.newsagg;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import dz.newsagg.orm.NewsDao;
import dz.newsagg.orm.NewsItem;
import dz.newsagg.parser.NewsParser;
import dz.newsagg.ui.NewsItemSaveEvent;

/**
 * Аггрегатор новостей.
 * Парсит ресурсы, сохраняет в бд уникальные новости.
 *
 * @author dzevako
 * @since Nov 6, 2017
 */
@Component
public class NewsSearchProcessor
{
    private static final int DEFAULT_PROCESS_TIMEOUT = 60000;

    @Inject
    private NewsDao dao;
    @Inject
    private ApplicationEventPublisher publisher;

    public void process(List<NewsParser> parsers)
    {
        // Если парсеров слишком много, то увеличиваем шаг выполнения
        int minProcessTimeout = NewsParser.DEFAULT_TIMEOUT * parsers.size();
        int timeout = minProcessTimeout > DEFAULT_PROCESS_TIMEOUT ? minProcessTimeout : DEFAULT_PROCESS_TIMEOUT;

        new Timer().schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                processStep(parsers);
            }
        }, 0, timeout);
    }

    private void processStep(List<NewsParser> parsers)
    {
        List<String> titles = dao.getNewsTitles();
        List<NewsItem> savedItems = new ArrayList<>();
        parsers.forEach(parser -> {
            System.out.println(String.format("Discovering new items for URL='%s'...", parser.getResourceUrl()));

            List<NewsItem> newItems = null;
            try
            {
                newItems = parser.parse();
                System.out.println(String.format("%s items found.", String.valueOf(newItems.size())));
            }
            catch (Exception e)
            {
                System.err.println(e.getLocalizedMessage());
                return;
            }

            savedItems.clear();
            newItems.forEach(item -> {
                if (!titles.contains(item.getTitle()))
                {
                    dao.save(item);
                    System.out.println(String.format("New item '%s' saved succesfully.", item.getTitle()));
                    savedItems.add(item);
                }
            });

            if (!savedItems.isEmpty())
            {
                publisher.publishEvent(new NewsItemSaveEvent(savedItems));
            }
        });
    }
}
