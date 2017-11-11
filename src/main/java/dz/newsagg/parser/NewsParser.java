package dz.newsagg.parser;

import java.util.List;

import dz.newsagg.common.HasEnabled;
import dz.newsagg.common.HasResource;
import dz.newsagg.orm.NewsItem;

/**
 * Интерфейс парсера новостей
 *
 * @author dzevako
 * @since Nov 4, 2017
 */
public interface NewsParser extends HasResource, HasEnabled
{
    public final static int DEFAULT_TIMEOUT = 5000;

    NewsParseRule getParseRule();

    List<NewsItem> parse();
}