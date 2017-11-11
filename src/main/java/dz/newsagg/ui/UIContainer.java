package dz.newsagg.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.springframework.stereotype.Component;

import dz.newsagg.orm.NewsItem;

@Component
public class UIContainer
{
    @Inject
    private ListController listController;

    private final int marginTop = 30;
    private final int marginLeft = 30;
    private final int width = 600;
    private final int height = 400;
    private final int searchHeight = 24;

    private JFrame frame;
    private JTextArea area;
    private JTextField filter;
    private JList<NewsItem> list;

    public void start()
    {
        frame = createFrame();

        area = createArea();
        frame.add(area);

        filter = createFilter();
        frame.add(filter);

        list = createList();
        frame.add(list);

        listController.filterList();
    }

    private JTextArea createArea()
    {
        JTextArea area = new JTextArea();
        area.setBounds(width + 2 * marginLeft, marginTop, width, height + searchHeight);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setVisible(true);

        return area;
    }

    private JTextField createFilter()
    {
        JTextField filter = new JTextField();
        listController.setFilter(filter);
        filter.setBounds(marginLeft, marginTop, width, searchHeight);
        filter.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                final String text = filter.getText();
                new Timer().schedule(new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        if (text.equals(filter.getText()))
                        {
                            listController.filterList();
                        }
                    }
                }, 1000);
            }
        });
        return filter;
    }

    private JFrame createFrame()
    {
        JFrame frame = new JFrame("Агрегатор новостей.");

        frame.setSize(2 * width + 3 * marginLeft, height + searchHeight + 4 * marginTop);
        frame.setLayout(null);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent windowEvent)
            {
                System.exit(0);
            }
        });
        return frame;
    }

    private JList<NewsItem> createList()
    {
        DefaultListModel<NewsItem> listModel = new DefaultListModel<>();
        listController.setList(listModel);

        JList<NewsItem> list = new JList<>(listModel);
        list.setBounds(marginLeft, searchHeight + marginTop, width, height);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        list.addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent event)
            {
                NewsItem item = list.getSelectedValue();
                if (item != null)
                {
                    area.setText(item.getTitle() + "\n\n" + item.getDescription());
                }
            }
        });

        return list;
    }
}
