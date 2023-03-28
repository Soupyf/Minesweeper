import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JieMian extends JFrame {
    public JieMian() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame jFrame = new JFrame();

        ImageIcon imageIcon2 = new ImageIcon("src/背景2.jpg");
        imageIcon2.setImage(imageIcon2.getImage().getScaledInstance(600, 700, Image.SCALE_DEFAULT));
        JLabel jLabel2 = new JLabel(imageIcon2);

        jLabel2.setSize(imageIcon2.getIconWidth(), imageIcon2.getIconHeight());
        getLayeredPane().add(jLabel2, new Integer(Integer.MIN_VALUE));

        JPanel panel = (JPanel) jFrame.getContentPane();
        panel.setOpaque(false);
        panel.setLayout(new FlowLayout());

        ImageIcon imageIcon = new ImageIcon("src/开始游戏.jpg");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
        JLabel jLabel = new JLabel(imageIcon);

        JButton b1 = new JButton(imageIcon);

        jFrame.setTitle("扫雷");
        jFrame.setIconImage(getToolkit().getImage("src/爆炸2.jpg"));
        jFrame.setLayout(null);
        panel.add(b1);
        panel.add(jLabel2);
        b1.setBounds(225, 450, 150, 150);

        jFrame.setSize(600, 700);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jFrame.setVisible(false);
                try {
                    Act.main();
                } catch(Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        new JieMian();
    }

}