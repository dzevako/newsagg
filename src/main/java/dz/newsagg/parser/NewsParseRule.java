package dz.newsagg.parser;

/**
 * Интерфейс правил парсинга для получения 
 * Новостей с названием и описанием.
 * Интерфейс содержит описание относительных путей(относительно пути Итема)
 *
 * @author dzevako
 * @since Nov 4, 2017
 */
public interface NewsParseRule extends ParseRule
{
    String getDescriptionPath();

    String getTitlePath();
}
