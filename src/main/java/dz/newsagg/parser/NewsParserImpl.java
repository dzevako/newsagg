package dz.newsagg.parser;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import dz.newsagg.orm.NewsItem;

/**
 * Реализация парсера новостей
 *
 * @author dzevako
 * @since Nov 4, 2017
 */
public class NewsParserImpl implements NewsParser
{
    private String url;
    private NewsParseRuleImpl rule;
    private boolean enabled;

    @Override
    public NewsParseRule getParseRule()
    {
        return rule;
    }

    @Override
    public String getResourceUrl()
    {
        return url;
    }

    @Override
    public boolean isEnabled()
    {
        return enabled;
    }

    @Override
    public List<NewsItem> parse()
    {
        Document doc = getDoc();

        Elements items = doc.select(rule.getBasePath());
        if (items.isEmpty())
        {
            return Collections.emptyList();
        }

        return items.stream().map(item -> {
            String title = extractText(item.select(rule.getTitlePath()));
            String description = extractText(item.select(rule.getDescriptionPath()));
            return new NewsItem(title, description);
        }).filter(ni -> ni.getTitle() != null && ni.getDescription() != null).collect(Collectors.toList());
    }

    @Override
    public String toString()
    {
        return String.format("NewsParser {\n\tresource : '%s',\n\trule : %s \n}", url, rule.toString());
    }

    private String extractText(Elements elements)
    {
        if (elements.isEmpty())
        {
            return null;
        }
        return elements.first().text();
    }

    private Document getDoc()
    {
        try
        {
            return Jsoup.connect(url).timeout(DEFAULT_TIMEOUT).get();
        }
        catch (IOException e)
        {
            throw new RuntimeException(String.format("Exception while parsing '%s': %s", url, e.getLocalizedMessage()));
        }
    }
}
