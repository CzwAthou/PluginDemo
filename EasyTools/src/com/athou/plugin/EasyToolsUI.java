package com.athou.plugin;

import com.intellij.openapi.util.text.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

/**
 * Created by cai on 2017/3/30.
 */
public class EasyToolsUI extends JDialog {
    private JTextField uuuidTextField;
    private JButton uuuidCreateButton;
    private JButton uuuidCopyButton;

    private JTextArea base64OriTextArea;
    private JButton base64OriCopyButton;
    private JButton base64ConvertButton;
    private JButton base64RecoveryButton;
    private JTextArea base64TextArea;
    private JButton base64CopyButton;

    private JFormattedTextField unixTimeTextField;
    private JButton unixTimeRefreshButton;
    private JButton unixTimeCopyButton;
    private JTextField unixFormatTextField;
    private JButton unixConverDateButton;
    private JButton dateConvertUnixButton;
    private JTextField dateTextField;
    private JButton dateRefreshButton;
    private JButton dateCopyButton;

    private JPanel panel1;

    public EasyToolsUI() {
//        setPreferredSize(new Dimension(540, 640));
        setContentPane(panel1);
        setTitle("EasyTools");
        setModal(false);
        setLocationRelativeTo(null);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int Swing1x = 510;
        int Swing1y = 490;
        setBounds(screensize.width / 2 - Swing1x / 2, screensize.height / 2 - Swing1y / 2, Swing1x, Swing1y);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        panel1.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        //UUID ================================================================
        uuuidCreateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String uuid = createUUUID();

                uuuidTextField.setText(null);
                uuuidTextField.setText(uuid);
            }
        });
        uuuidCopyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copy(uuuidTextField.getText());
            }
        });

        //base64 ================================================================
        base64ConvertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                base64TextArea.setText(encodeBase64(base64OriTextArea.getText()));
            }
        });
        base64RecoveryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                base64OriTextArea.setText(decodeBase64(base64TextArea.getText()));
            }
        });
        base64OriCopyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copy(base64OriTextArea.getText());
            }
        });
        base64CopyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copy(base64TextArea.getText());
            }
        });

        //时间戳 ===============================================================
        unixTimeRefreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unixTimeTextField.setText(String.valueOf(System.currentTimeMillis()));
            }
        });
        unixTimeCopyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copy(unixTimeTextField.getText());
            }
        });
        unixConverDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long time = System.currentTimeMillis();
                try {
                    time = Long.parseLong(unixTimeTextField.getText());
                } catch (Exception err) {
                    err.printStackTrace();
                }
                dateTextField.setText(getDateTime(time, getFormat()));
            }
        });
        dateConvertUnixButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unixTimeTextField.setText(String.valueOf(decodeDateStr2Time(dateTextField.getText(), getFormat())));
            }
        });
        dateRefreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dateTextField.setText(getDateTime(System.currentTimeMillis(), getFormat()));
            }
        });
        dateCopyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copy(dateTextField.getText());
            }
        });
    }

    private static String createUUUID() {
        long time = new Date().getTime();
        return String.format("%11x%2x", time, new Random().nextInt(255)).replace(" ", "0");
    }

    private static String encodeBase64(String source) {
        try {
            byte[] data = source.getBytes("utf-8");
            return new String(Base64.getEncoder().encode(data), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String decodeBase64(String source) {
        try {
            byte[] data = source.getBytes("utf-8");
            return new String(Base64.getDecoder().decode(data), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getDateTime(long millTime, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date(millTime);
        return dateFormat.format(date);
    }

    private static long decodeDateStr2Time(String timeStr, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            Date date = dateFormat.parse(timeStr);
            return date.getTime();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return 0;
    }

    private String getFormat() {
        String format = unixFormatTextField.getText();
        if (StringUtil.isEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        return format;
    }

    private void copy(String text) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        StringSelection selection = new StringSelection(text);
        clipboard.setContents(selection, new ClipboardOwner() {

            @Override
            public void lostOwnership(Clipboard arg0, Transferable arg1) {
                // TODO Auto-generated method stub
            }
        });
        JOptionPane.showMessageDialog(panel1, "复制成功！", "提示消息", JOptionPane.WARNING_MESSAGE);
    }

    private void onCancel() {
        dispose();
    }
}
