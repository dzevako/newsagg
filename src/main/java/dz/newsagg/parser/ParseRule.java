package dz.newsagg.parser;

/**
 * Общий интерфейс правил парсинга для получения 
 * каких либо кусков DOMa с какого либо ресурса
 * Интерфейс содержит описание абсолютного пути до объектов
 *
 * @author dzevako
 * @since Nov 4, 2017
 */
public interface ParseRule
{
    String getBasePath();
}
