import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Act extends JFrame {
    private Player player1 = new Player();
    private Player player2 = new Player();

    public Act() {
        JFrame.setDefaultLookAndFeelDecorated(true);//使窗口美观,创建窗口前使用
        final JFrame jFrame = new JFrame("扫雷");
        jFrame.setSize(338, 600);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setTitle("扫雷");
        jFrame.setIconImage(getToolkit().getImage("src/爆炸2.jpg"));

        ImageIcon imageIcon2 = new ImageIcon("src/背景4.jpg");
        imageIcon2.setImage(imageIcon2.getImage().getScaledInstance(338, 360, Image.SCALE_DEFAULT));
        JLabel jLabel2 = new JLabel(imageIcon2);

        jLabel2.setSize(imageIcon2.getIconWidth(), imageIcon2.getIconHeight());
        getLayeredPane().add(jLabel2, new Integer(Integer.MIN_VALUE));

        JPanel panel = (JPanel) jFrame.getContentPane();
        panel.setOpaque(false);
        panel.setLayout(new FlowLayout());

        ImageIcon imageIcon = new ImageIcon("src/输入名字.jpg");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(338, 240, Image.SCALE_DEFAULT));

        JButton jButton = new JButton(imageIcon);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(jFrame, "输入玩家1的名字：", "玩家1");
                String input2 = JOptionPane.showInputDialog(jFrame, "输入玩家2的名字", "玩家2");
                player1 = new Player(input);
                player2 = new Player(input2);
                jFrame.setVisible(true);
                jFrame.dispose();
                aa.main(player1, player2);
            }
        });

        Box box = Box.createVerticalBox();
        box.add(jButton);

        JPanel jPanel = new JPanel();
        jPanel.add(box);
        jPanel.add(jLabel2);

        jFrame.setContentPane(jPanel);
        jFrame.setVisible(true);
        dispose();
    }

    public static void main() throws Exception {
        new Act();
    }

}


