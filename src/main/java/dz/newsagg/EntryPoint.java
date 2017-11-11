package dz.newsagg;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Точка входа
 *
 * @author dzevako
 * @since Nov 6, 2017
 */
public class EntryPoint
{

    public static void main(String[] args) throws Exception
    {
        if (args.length < 1)
        {
            System.out.println(String.format("Hallo, %s!", System.getenv().get("USER")));
            System.out.println("You must pass 1 parameter: path to file with your parsers xml configuration.");
            System.exit(1);
        }

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        NewsAggregator aggregator = context.getBean(NewsAggregator.class);
        aggregator.run(args);
    }
}
