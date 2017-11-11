package dz.newsagg.parser;

import java.util.List;

/**
 * Объект конфигурации парсеров.
 *
 * @author dzevako
 * @since Nov 7, 2017
 */
public class ParsersConfig
{
    private List<NewsParser> parsers;

    public List<NewsParser> getParsers()
    {
        return parsers;
    }
}