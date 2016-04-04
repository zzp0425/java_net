package com.network.socket.client.whois;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;

/**
 * Created by 周振平
 * on 2016/4/4.
 * 图形化whois客户端界面
 */
public class WhoisGUI extends JFrame {
    private JTextField searchString = new JTextField(30);//搜索框
    private JTextArea names = new JTextArea(15, 80);//显示输出结果区域
    private JButton findButton = new JButton("查找");//查找按钮
    private ButtonGroup searchIn = new ButtonGroup();//搜索类别
    private ButtonGroup searchFor = new ButtonGroup();//搜索条目
    private JCheckBox exactMach = new JCheckBox("Exac Mach", true);//是否匹配
    private JTextField choseServer = new JTextField();//服务器

    private Whois server;

    public WhoisGUI(Whois whois) {
        super("Whois");
        this.server = whois;
        Container pane = this.getContentPane();

        Font font = new Font("Monospaced", Font.PLAIN, 12);
        names.setFont(font);
        names.setEditable(false);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout(2, 1));

        //设置显示查询结果区域
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 1, 10, 10));
        JScrollPane jsp = new JScrollPane(names);
        centerPanel.add(jsp);
        pane.add(centerPanel);

        //不希望南边和北边的按钮沾满整个区域
        //所以在那里添加Panel
        //并在Panel中使用FlowLayout
        JPanel northPanelTop = new JPanel();
        northPanelTop.setLayout(new FlowLayout(FlowLayout.LEFT));
        northPanelTop.add(new JLabel("whois : "));
        northPanelTop.add("North", searchString);
        northPanelTop.add(exactMach);
        northPanelTop.add(findButton);

        northPanel.add("North", northPanelTop);

        JPanel northBottom = new JPanel();
        northBottom.setLayout(new GridLayout(1, 3, 5, 5));
        northBottom.add(initRecordType());
        northBottom.add(initSearchFields());
        northBottom.add(initServerChose());
        northPanel.add(northBottom);

        northPanel.add("Center", northBottom);

        pane.add("North", northPanel);

        ActionListener al = new LookupNames();
        findButton.addActionListener(al);
        searchString.addActionListener(al);
    }

    //初始化搜索条目panel
    private JPanel initRecordType() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 5, 2));
        panel.add(new JLabel("Search for : "));
        panel.add(new JLabel(""));
        JRadioButton any = new JRadioButton(Whois.SearchFor.ANY.value, true);
        any.setActionCommand(Whois.SearchFor.ANY.value);
        searchFor.add(any);
        panel.add(any);

        panel.add(this.makeRadioButton(Whois.SearchFor.ASN.value));
        panel.add(this.makeRadioButton(Whois.SearchFor.DOMAIN.value));
        panel.add(this.makeRadioButton(Whois.SearchFor.GATEWAY.value));
        panel.add(this.makeRadioButton(Whois.SearchFor.GROUP.value));
        panel.add(this.makeRadioButton(Whois.SearchFor.HOST.value));
        panel.add(this.makeRadioButton(Whois.SearchFor.NETWORK.value));
        panel.add(this.makeRadioButton(Whois.SearchFor.ORGANIZATION.value));
        panel.add(this.makeRadioButton(Whois.SearchFor.PERSON.value));

        return panel;
    }

    /**
     * 设置查询条目radio
     * @param label
     * @return
     */
    private JRadioButton makeRadioButton(String label) {
        JRadioButton button = new JRadioButton(label, false);
        button.setActionCommand(label);
        searchFor.add(button);
        return button;
    }

    /**
     * 设置查询类型radio
     * @param label
     * @return
     */
    private JRadioButton makeSearchInRadioButton(String label) {
        JRadioButton button = new JRadioButton(label, false);
        button.setActionCommand(label);
        searchIn.add(button);
        return button;
    }

    //初始化搜索类型panel
    private JPanel initSearchFields() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 5, 2));
        panel.add(new JLabel("Search In : "));

        JRadioButton all = new JRadioButton(Whois.SearchIn.ALL.value, true);
        all.setActionCommand(Whois.SearchIn.ALL.value);
        searchIn.add(all);
        panel.add(all);

        panel.add(this.makeSearchInRadioButton(Whois.SearchIn.HANDLER.value));
        panel.add(this.makeSearchInRadioButton(Whois.SearchIn.MAILBOX.value));
        panel.add(this.makeSearchInRadioButton(Whois.SearchIn.NAME.value));

        return panel;
    }

    //初始化搜索服务器
    private JPanel initServerChose() {
        final JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 5, 2));
        panel.add(new JLabel("Search At : "));

        choseServer.setText(server.getHost().getHostName());
        panel.add(choseServer);

        choseServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    server = new Whois(choseServer.getText());
                } catch (UnknownHostException e1) {
                    JOptionPane.showMessageDialog(panel, e1.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return panel;
    }

    private class LookupNames implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            names.setText("");
            SwingWorker<String, Object> worker = new Lookup();
            worker.execute();
        }
    }

    private class Lookup extends SwingWorker<String, Object> {

        @Override
        protected String doInBackground() throws Exception {
            Whois.SearchIn group = Whois.SearchIn.ALL;
            Whois.SearchFor category = Whois.SearchFor.ANY;

            String searchForLabel = searchFor.getSelection().getActionCommand();
            String searchInLable = searchIn.getSelection().getActionCommand();

            if (searchInLable.equals(Whois.SearchIn.NAME.value)) {
                group = Whois.SearchIn.NAME;
            } else if (searchInLable.equals(Whois.SearchIn.HANDLER.value)) {
                group = Whois.SearchIn.HANDLER;
            } else if (searchInLable.equals(Whois.SearchIn.MAILBOX.value)) {
                group = Whois.SearchIn.MAILBOX;
            }

            if (searchForLabel.equals(Whois.SearchFor.ASN.value)) {
                category = Whois.SearchFor.ASN;
            } else if (searchForLabel.equals(Whois.SearchFor.PERSON.value)) {
                category = Whois.SearchFor.PERSON;
            } else if (searchForLabel.equals(Whois.SearchFor.ORGANIZATION.value)) {
                category = Whois.SearchFor.ORGANIZATION;
            } else if (searchForLabel.equals(Whois.SearchFor.NETWORK.value)) {
                category = Whois.SearchFor.NETWORK;
            } else if (searchForLabel.equals(Whois.SearchFor.HOST.value)) {
                category = Whois.SearchFor.HOST;
            } else if (searchForLabel.equals(Whois.SearchFor.DOMAIN.value)) {
                category = Whois.SearchFor.DOMAIN;
            } else if (searchForLabel.equals(Whois.SearchFor.GATEWAY.value)) {
                category = Whois.SearchFor.GATEWAY;
            } else if (searchForLabel.equals(Whois.SearchFor.GROUP.value)) {
                category = Whois.SearchFor.GROUP;
            }
            server.setHost(choseServer.getText());
            return server.lookUpNames(searchString.getText(), category, group, exactMach.isSelected());
        }

        @Override
        protected void done() {
            try {
                names.setText(get());//调用doInBackground返回的string
            } catch (Exception e) {
                JOptionPane.showMessageDialog(WhoisGUI.this, e.getMessage(), "Lookup Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static class FrameShower implements Runnable{

        private final Frame frame;

        private FrameShower(Frame frame) {
            this.frame = frame;
        }

        @Override
        public void run() {
            frame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        try {
            Whois server = new Whois();
            WhoisGUI gui = new WhoisGUI(server);
            gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            gui.pack();
            EventQueue.invokeLater(new FrameShower(gui));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
