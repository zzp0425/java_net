package com.network.uriAndUrl.authenticator;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * Created by 周振平
 * on 2016/4/2.
 * 一个GUI认证程序
 */
public class DialogAuthenticator extends Authenticator {

    private static Logger logger = Logger.getLogger(DialogAuthenticator.class);

    private JDialog passwordDialog;
    private JTextField userNameField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);
    private JButton submitButton = new JButton("确定");
    private JButton cancelButton = new JButton("取消");
    private JLabel mainLabel = new JLabel("请输入用户名、密码");

    public DialogAuthenticator () {
        this("", new JFrame());
    }

    public DialogAuthenticator (String username) {
        this(username, new JFrame());
    }

    public DialogAuthenticator (JFrame parent) {
        this("", parent);
    }

    public DialogAuthenticator (String username, JFrame parent) {
        this.passwordDialog = new JDialog(parent, true);
        Container pane = passwordDialog.getContentPane();
        pane.setLayout(new GridLayout(4, 1));

        pane.add(mainLabel);

        JLabel userLable = new JLabel("用户名 : ");
        JLabel passwordLable = new JLabel("密码 : ");

        JPanel usernamePanel = new JPanel();
        usernamePanel.add(userLable);
        usernamePanel.add(userNameField);
        userNameField.setText(username);
        pane.add(usernamePanel);

        JPanel passwordPanel = new JPanel();
        passwordPanel.add(passwordLable);
        passwordPanel.add(passwordField);
        pane.add(passwordPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        pane.add(buttonPanel);
        passwordDialog.pack();

        ActionListener al = new OKResponse();
        submitButton.addActionListener(al);
//        userNameField.setAction();
        cancelButton.addActionListener(new CancelResponse());
    }

    private void show() {
        String prompt = this.getRequestingPrompt();//提示字符串
        if (prompt == null){
            String site = this.getRequestingSite().getHostName();
            String protocol = this.getRequestingProtocol();
            int port = this.getRequestingPort();

            if (site != null & protocol != null) {
                prompt = protocol + "://" + site;
                if (port > 0) {
                    prompt += ":" + port;
                }
            } else {
                prompt = "";
            }
        }

        mainLabel.setText("请输入用户名、密码 for " + prompt + ": ");
        passwordDialog.pack();
        passwordDialog.setVisible(true);
    }

    PasswordAuthentication response = null;

    //确定键监听
    class OKResponse implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            passwordDialog.setVisible(false);
            //出于安全原因
            //口令作为char数组返回
            char[] password = passwordField.getPassword();
            String username = userNameField.getText();

            //清除口令，以防再次使用
            passwordField.setText("");
            response = new PasswordAuthentication(username, password);
        }
    }

    //取消键监听
    class CancelResponse implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            passwordDialog.setVisible(false);

            //清除口令
            passwordField.setText("");
            response = null;
        }
    }

    public PasswordAuthentication getPasswordAuthentication () {
        this.show();
        return this.response;
    }

    public static void main(String[] args) {
        new DialogAuthenticator();
    }

}
