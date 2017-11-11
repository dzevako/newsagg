package dz.newsagg.parser;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Базовая реализация правила парсинга
 *
 * @author dzevako
 * @since Nov 4, 2017
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class ParseRuleBaseImpl implements ParseRule
{
    @XmlElement(name = "base-path")
    private String basePath;

    @Override
    public String getBasePath()
    {
        return basePath;
    }
}
