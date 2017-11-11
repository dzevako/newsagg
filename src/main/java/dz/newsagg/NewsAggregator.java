package dz.newsagg;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;

import dz.newsagg.parser.NewsParser;
import dz.newsagg.parser.NewsParserImpl;
import dz.newsagg.parser.ParsersConfig;
import dz.newsagg.ui.UIContainer;

/**
 * Агрегатор новостей
 *
 * @author dzevako
 * @since Nov 9, 2017
 */
@Component
public class NewsAggregator
{
    @Inject
    NewsSearchProcessor searchProcessor;
    @Inject
    UIContainer ui;

    public void run(String[] args) throws Exception
    {
        startUI();
        startSearchProcessor(args);
    }

    private List<NewsParser> getParsers(String path) throws Exception
    {
        try
        {
            XStream xstream = new XStream();
            xstream.alias("conf", ParsersConfig.class);
            xstream.alias("parser", NewsParserImpl.class);

            ParsersConfig conf = (ParsersConfig)xstream.fromXML(new File(path));
            return conf.getParsers();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Exception while parse config: \n", e);
        }
    }

    private void startSearchProcessor(String[] args) throws Exception
    {
        List<NewsParser> parsers = getParsers(args[1]);
        // Оставляем только активные парсеры
        parsers = parsers.stream().filter(p -> p.isEnabled()).collect(Collectors.toList());

        System.out.println("Active parsers:\n");
        parsers.forEach(parser -> System.out.println(parser.toString()));

        searchProcessor.process(parsers);
    }

    private void startUI()
    {
        ui.start();
    }
}
