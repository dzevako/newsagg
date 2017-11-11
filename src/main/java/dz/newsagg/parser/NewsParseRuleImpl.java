package dz.newsagg.parser;

import dz.newsagg.orm.NewsItem;

/**
 * Объект правила парсинга для новстей {@link NewsItem}
 *
 * @author dzevako
 * @since Nov 4, 2017
 */
public class NewsParseRuleImpl extends ParseRuleBaseImpl implements NewsParseRule
{
    private String titlePath;
    private String descriptionPath;

    @Override
    public String getDescriptionPath()
    {
        return descriptionPath;
    }

    @Override
    public String getTitlePath()
    {
        return titlePath;
    }

    @Override
    public String toString()
    {
        return String.format("%s[basePath='%s', titlePath='%s', descriptionPath='%s']", this.getClass().getSimpleName(),
                getBasePath(), getTitlePath(), getDescriptionPath());
    }
}
