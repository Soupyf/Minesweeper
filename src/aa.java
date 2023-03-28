import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Random;

public class aa {
    public static void main() {
        try {
            Act.main();
        } catch(Exception exception) {
            exception.printStackTrace();
        }
        Music music = new Music("src/Grasswalk.wav");
        music.start();
        int musicOpenLab = 1;
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame jFrame = new JFrame();
        GuiSl test = new GuiSl("扫雷", 600, 300, 600, 700);
    }

    public static void main(Player a, Player b) {
        try {
            Act.main();
        } catch(Exception exception) {
            exception.printStackTrace();
        }
        Music music = new Music("src/Grasswalk.wav");
        music.start();
        int musicOpenLab = 1;
        JFrame.setDefaultLookAndFeelDecorated(true);//使窗口美观,创建窗口前使用
        JFrame jFrame = new JFrame();
        GuiSl test = new GuiSl("扫雷", 600, 300, 600, 700, a, b);
    }
}

class GuiSl extends JFrame {
    JMenuBar jMenuBar;
    JMenu m1, m2;
    JMenuItem i1, i2, i4, i5, i6, i7, i8, i9;
    JButton[][] mineField;
    private int[][] chessboard;
    private final Random random = new Random();
    JPanel checkerboard;
    int[] mines;
    static int x = 9;
    static int y = 9;
    static int num = 10;//雷
    static int sum = 0;//已扫的雷
    boolean[][] book;//标记数组
    private static int aa = 2;
    private static int bb = 2;
    Player p1 = new Player("玩家1");
    Player p2 = new Player("玩家2");
    private Player onTurn;
    int turn = 1;
    int action = 1;

    Timer time;
    JLabel tu;//秒表图
    JLabel nu, nu2;//计分图
    JLabel mi, mi2;//失误图
    JLabel show, show2;
    JLabel tm0 = new JLabel();//计时
    JLabel nu0 = new JLabel();//计分
    JLabel nu02 = new JLabel();
    JLabel mi0 = new JLabel();//计失误数
    JLabel mi02 = new JLabel();

    JTextField re = new JTextField();//留下姓名文本框
    int max1 = 999999;//最大的耗时

    GuiSl() {
    }

    GuiSl(String s, int x, int y, int width, int height) {
        setBounds(x, y, width, height);//设置大小
        setLocationRelativeTo(null);//设置为放在屏幕中央
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭方式
        inits(s);
        setVisible(true);
    }

    GuiSl(String s, int x, int y, int width, int height, Player a, Player b) {
        p1 = a;
        p2 = b;
        setBounds(x, y, width, height);//设置大小
        setLocationRelativeTo(null);//设置为放在屏幕中央
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭方式
        inits(s);
        setVisible(true);
    }

    GuiSl getck() {
        return this;
    }

    void inits(String s) {
        init0(s);
        init1(p1, p2);
    }

    void init0(String s) {
        setTitle(s);
        setIconImage(getToolkit().getImage("src/爆炸2.jpg"));    //设置图标
        jMenuBar = new JMenuBar();
        //设置头标题
        m1 = new JMenu("游戏");
        m2 = new JMenu("道具");
        //设置头标题中的内容
        i1 = new JMenuItem("新游戏");
        i4 = new JMenuItem("重载");
        i5 = new JMenuItem("透视");
        i2 = new JMenuItem("设置");
        i6 = new JMenuItem("存档");
        i7 = new JMenuItem("读档");
        i8 = new JMenuItem(p1.getUserName() + "的时光倒流:" + aa);
        i9 = new JMenuItem(p2.getUserName() + "的时光倒流:" + bb);

        i6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                init3();
            }
        });

        i7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                init4();
            }
        });

        i1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ini();
            }
        });    //设置监视器

        i4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ini1();
            }
        });    //设置监视器

        i5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int w = checkerboard.getWidth() / y;
                int h = checkerboard.getHeight() / x;
                int n = 0;
                for(int ii = 0; ii < x; ii++) {    //透视
                    for(int kk = 0; kk < y; kk++) {
                        if(mineField[ii][kk].getIcon() != null) {
                            if(mineField[ii][kk].getIcon().toString() == new ImageIcon("src/地雷2.jpg").toString()) {
                                mineField[ii][kk].setIcon(new ImageIcon("src/草地4.jpg"));
                                n++;
                            }
                        }
                    }
                }
                if(n == 0) detonateMine(w, h);    //透视
            }
        });    //设置监视器

        i2.addActionListener(new Set0());

        i8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(aa > 0) {
                    if(turn % 2 == 1) {
                        aa--;
                        tm0.setText("30");
                        i8.setText(p1.getUserName() + "的时光倒流:" + aa);
                    }
                }
            }
        });

        i9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(bb > 0) {
                    if(turn % 2 == 0) {
                        bb--;
                        tm0.setText("30");
                        i9.setText(p2.getUserName() + "的时光倒流:" + bb);
                    }
                }
            }
        });

        m1.add(i1);
        m1.addSeparator();//添加菜单项分界线
        m1.add(i4);
        m1.addSeparator();
        m1.add(i5);
        m1.addSeparator();
        m1.add(i2);
        m1.addSeparator();
        m1.add(i6);
        m1.addSeparator();
        m1.add(i7);
        jMenuBar.add(m1);
        setJMenuBar(jMenuBar);
        m2.add(i8);
        m2.addSeparator();
        m2.add(i9);
        jMenuBar.add(m2);

        //设置计分器
        Box num1 = Box.createHorizontalBox();
        nu = new JLabel("分数:");
        nu.setIcon(new ImageIcon());
        nu.setIcon(setImageSize0("src/玩家二号.jpg", 50, 50));
        nu2 = new JLabel("分数:");
        nu2.setIcon(new ImageIcon());
        nu2.setIcon(setImageSize0("src/玩家三号.jpg", 50, 50));
        JLabel nu3 = new JLabel("       ");
        mi = new JLabel("失误数:");
        mi2 = new JLabel("失误数:");
        show = new JLabel("        回合:");
        show2 = new JLabel();

        onTurn = p1;
        show2.setText(String.valueOf(onTurn.getUserName()));

        nu0.setText(String.valueOf(p1.getScore()));
        nu02.setText(String.valueOf(p2.getScore()));
        mi0.setText(String.valueOf(p1.getMistake()));
        mi02.setText(String.valueOf(p2.getMistake()));
        num1.add(nu);
        num1.add(nu0);
        num1.add(mi);
        num1.add(mi0);
        num1.add(nu3);
        num1.add(nu2);
        num1.add(nu02);
        num1.add(mi2);
        num1.add(mi02);
        num1.add(show);
        num1.add(show2);
        add(num1, BorderLayout.NORTH);

        //设置计时器
        time = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int i = Integer.parseInt(tm0.getText());
                if(i <= 0) {
                    tm0.setText("30");
                    i = Integer.parseInt(tm0.getText());
                    next();
                    turn++;
                }
                tm0.setText(String.valueOf(i - 1));
            }
        });
    }

    //新游戏
    void init1() {
        setGrid(x, y);//设置网格
        setRand(num);//设置雷的位置
        int[][] l = new int[x][y];
        for(int ii = 0; ii < x; ii++) {
            for(int kk = 0; kk < y; kk++) {
                l[ii][kk] = 0;
            }
        }
        for(int ii = 0; ii < x; ii++) {
            for(int kk = 0; kk < y; kk++) {
                for(int mine : mines) {
                    if(ii * x + kk + 1 == mine) {//这就是9进制吗，爱了爱了
                        l[ii][kk] = -1;
                        break;
                    }
                }
            }
        }
        for(int ii = 1; ii < x - 1; ii++) {
            for(int kk = 1; kk < y - 1; kk++) {
                if(l[ii][kk] == -1) {
                    if(l[ii - 1][kk + 1] == -1 && l[ii - 1][kk] == -1 && l[ii - 1][kk + 1] == -1 && l[ii][kk + 1] == -1 && l[ii][kk - 1] == -1 && l[ii + 1][kk + 1] == -1 && l[ii + 1][kk] == -1 && l[ii + 1][kk - 1] == -1) {
                        dispose();
                        init1(p1, p2);
                    }
                }
            }
        }

        p1.setScore(0);
        p1.setMistake(0);
        p2.setScore(0);
        p2.setMistake(0);
        turn = 1;
        nu0.setText(String.valueOf(p1.getScore()));
        mi0.setText(String.valueOf(p1.getMistake()));
        nu02.setText(String.valueOf(p2.getScore()));
        mi02.setText(String.valueOf(p2.getMistake()));
        i8.setText(p1.getUserName() + "的时光倒流:" + 2);
        i9.setText(p2.getUserName() + "的时光倒流:" + 2);

        onTurn = p1;
        show2.setText(String.valueOf(onTurn.getUserName()));

        tm0.setText("30");
        Box tim = Box.createHorizontalBox();
        tu = new JLabel(":");
        tu.setIcon(new ImageIcon());
        tu.setIcon(setImageSize0("src/进度条.jpg", 200, 50));
        checkerboard = new JPanel();
        checkerboard.setFocusable(true);
        checkerboard.setLayout(new GridLayout(x, y));
        mineField = new JButton[x][y];
        book = new boolean[x][y];
        sum = 0;

        //设置位置
        tim.add(Box.createHorizontalStrut(10));
        tim.add(tu);
        tim.add(tm0);
        add(tim, BorderLayout.SOUTH);
        add(checkerboard, BorderLayout.CENTER);

        //上色
        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                mineField[i][j] = new JButton();
                mineField[i][j].setIcon(new ImageIcon("src/草地4.jpg"));
                mineField[i][j].addMouseListener(new BiuListener());
                checkerboard.add(mineField[i][j]);
                book[i][j] = false;
            }
        }

    }


    void init1(Player a, Player b) {
        p1 = a;
        p2 = b;
        setGrid(x, y);//设置网格
        setRand(num);//设置雷的位置
        int[][] l = new int[x][y];
        for(int ii = 0; ii < x; ii++) {
            for(int kk = 0; kk < y; kk++) {
                l[ii][kk] = 0;
            }
        }
        for(int ii = 0; ii < x; ii++) {
            for(int kk = 0; kk < y; kk++) {
                for(int mine : mines) {
                    if(ii * x + kk + 1 == mine) {//这就是9进制吗，爱了爱了
                        l[ii][kk] = -1;
                        break;
                    }
                }
            }
        }
        for(int ii = 1; ii < x - 1; ii++) {
            for(int kk = 1; kk < y - 1; kk++) {
                if(l[ii][kk] == -1) {
                    if(l[ii - 1][kk + 1] == -1 && l[ii - 1][kk] == -1 && l[ii - 1][kk + 1] == -1 && l[ii][kk + 1] == -1 && l[ii][kk - 1] == -1 && l[ii + 1][kk + 1] == -1 && l[ii + 1][kk] == -1 && l[ii + 1][kk - 1] == -1) {
                        dispose();
                        init1(p1, p2);
                    }
                }
            }
        }

        p1.setScore(0);
        p1.setMistake(0);
        p2.setScore(0);
        p2.setMistake(0);
        turn = 1;
        nu0.setText(String.valueOf(p1.getScore()));
        mi0.setText(String.valueOf(p1.getMistake()));
        nu02.setText(String.valueOf(p2.getScore()));
        mi02.setText(String.valueOf(p2.getMistake()));
        i8.setText(p1.getUserName() + "的时光倒流:" + 2);
        i9.setText(p2.getUserName() + "的时光倒流:" + 2);

        onTurn = p1;
        show2.setText(String.valueOf(onTurn.getUserName()));

        tm0.setText("30");
        Box tim = Box.createHorizontalBox();
        tu = new JLabel(":");
        tu.setIcon(new ImageIcon());
        tu.setIcon(setImageSize0("src/进度条.jpg", 200, 50));
        checkerboard = new JPanel();
        checkerboard.setFocusable(true);
        checkerboard.setLayout(new GridLayout(x, y));
        mineField = new JButton[x][y];
        book = new boolean[x][y];
        sum = 0;

        //设置位置
        tim.add(Box.createHorizontalStrut(10));
        tim.add(tu);
        tim.add(tm0);
        add(tim, BorderLayout.SOUTH);
        add(checkerboard, BorderLayout.CENTER);

        //上色
        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                mineField[i][j] = new JButton();
                mineField[i][j].setIcon(new ImageIcon("src/草地4.jpg"));
                mineField[i][j].addMouseListener(new BiuListener());
                checkerboard.add(mineField[i][j]);
                book[i][j] = false;
            }
        }


    }

    //重载
    void init2() {
        p1.setScore(0);
        p1.setMistake(0);
        p2.setScore(0);
        p2.setMistake(0);
        nu0.setText(String.valueOf(p1.getScore()));
        mi0.setText(String.valueOf(p1.getMistake()));
        nu02.setText(String.valueOf(p2.getScore()));
        mi02.setText(String.valueOf(p2.getMistake()));
        turn = 1;

        onTurn = p1;
        show2.setText(String.valueOf(onTurn.getUserName()));

        tm0.setText("30");
        Box tim = Box.createHorizontalBox();
        tu = new JLabel(":");
        tu.setIcon(new ImageIcon());
        tu.setIcon(setImageSize0("src/进度条.jpg", 200, 50));
        int w = checkerboard.getWidth() / y;
        int h = checkerboard.getHeight() / x;
        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                if(mineField[i][j].getIcon().toString() != new ImageIcon("src/草地4.jpg").toString())
                    mineField[i][j].setIcon(new ImageIcon("src/草地4.jpg"));
                book[i][j] = false;
            }
        }
        tim.add(Box.createHorizontalStrut(10));
        tim.add(tu);
        tim.add(tm0);
        add(tim, BorderLayout.SOUTH);
        add(checkerboard, BorderLayout.CENTER);
        sum = 0;
    }

    ImageIcon setImageSize0(String s, int width, int height) {
        ImageIcon im = new ImageIcon(s);//按如下方法设置任意宽高图片
        im.setImage(im.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        return im;
    }

    class BiuListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            if(tm0.getText().equals("30"))//点击第一个按钮时启动计时器
                time.start();
            int i, j, k;
            int w = checkerboard.getWidth() / y;
            int h = checkerboard.getHeight() / x;
            JButton button = (JButton) e.getSource();//获取事件源
            for(i = 0; i < x; i++) {
                for(k = 0; k < y; k++) {
                    if(mineField[i][k] == button) {//找到对应按钮
                        if(e.getModifiers() == InputEvent.BUTTON3_MASK) {//鼠标右键
                            tm0.setText("30");
                            time.restart();
                            if(mineField[i][k].getIcon().toString().equals(new ImageIcon("src/草地4.jpg").toString()) || mineField[i][k].getIcon().toString().equals(new ImageIcon("src/地雷2.jpg").toString())) {
                                for(j = 0; j < mines.length; j++) {
                                    if(mines[j] == i * x + k + 1) {
                                        if(turn == 1) {
                                            ini();
                                            break;
                                        }
                                        tm0.setText("30");
                                        time.restart();
                                        onTurn.addScore();
                                        nu0.setText(String.valueOf(p1.getScore()));
                                        nu02.setText(String.valueOf(p2.getScore()));
                                        mineField[i][k].setIcon(setImageSize0("src/旗子.jpg", w, h));
                                        File sound1 = new File("src/正确.wav");
                                        AudioClip sound_choose = null;
                                        try {
                                            sound_choose = Applet.newAudioClip(sound1.toURL());
                                        } catch(MalformedURLException opop) {
                                            opop.printStackTrace();
                                        }
                                        assert sound_choose != null;
                                        sound_choose.play();
                                        JOptionPane.showMessageDialog(null, "帅！！！");
                                        sum++;
                                        next();
                                        turn++;
                                        break;
                                    }
                                }
                                if(j == mines.length) {
                                    tm0.setText("30");
                                    time.restart();
                                    File sound1 = new File("src/插旗失败.wav");
                                    AudioClip sound_choose = null;
                                    try {
                                        sound_choose = Applet.newAudioClip(sound1.toURL());
                                    } catch(MalformedURLException qwq) {
                                        qwq.printStackTrace();
                                    }
                                    assert sound_choose != null;
                                    sound_choose.play();
                                    JOptionPane.showMessageDialog(null, "呆比~~~");
                                    searchMine(i, k, w, h);//查找周边雷并显示
                                    onTurn.addMistake();
                                    onTurn.costScore();
                                    nu0.setText(String.valueOf(p1.getScore()));
                                    nu02.setText(String.valueOf(p2.getScore()));
                                    mi0.setText(String.valueOf(p1.getMistake()));
                                    mi02.setText(String.valueOf(p2.getMistake()));
                                    next();
                                    turn++;
                                }
                            }
                            return;
                        } else {
                            for(j = 0; j < mines.length; j++) {
                                if(mines[j] == i * x + k + 1 && !mineField[i][k].getIcon().toString().equals(new ImageIcon("src/地雷.jpg").toString()) && !mineField[i][k].getIcon().toString().equals(new ImageIcon("src/旗子.jpg").toString())) {//扫到雷了
                                    if(turn == 1) {
                                        ini();
                                        break;
                                    }
                                    tm0.setText("30");
                                    time.restart();
                                    mineField[i][k].setIcon(setImageSize0("src/地雷.jpg", w, h));
                                    File sound1 = new File("src/爆炸.wav");
                                    AudioClip sound_choose = null;
                                    try {
                                        sound_choose = Applet.newAudioClip(sound1.toURL());
                                    } catch(MalformedURLException tut) {
                                        tut.printStackTrace();
                                    }
                                    assert sound_choose != null;
                                    sound_choose.play();
                                    JOptionPane.showMessageDialog(null, "唏噗！！");
                                    onTurn.costScore();
                                    nu0.setText(String.valueOf(p1.getScore()));
                                    nu02.setText(String.valueOf(p2.getScore()));
                                    sum++;
                                    next();
                                    turn++;
                                    break;
                                }
                            }
                            if(j == mines.length && mineField[i][k].getIcon().toString().equals(new ImageIcon("src/草地4.jpg").toString())) {//未扫到雷
                                tm0.setText("30");
                                time.restart();
                                searchMine(i, k, w, h);//查找周边雷并显示
                                File sound1 = new File("src/无雷.wav");
                                AudioClip sound_choose = null;
                                try {
                                    sound_choose = Applet.newAudioClip(sound1.toURL());
                                } catch(MalformedURLException QAQ) {
                                    QAQ.printStackTrace();
                                }
                                assert sound_choose != null;
                                sound_choose.play();
                                next();
                                turn++;
                            }
                            return;
                        }
                    }
                }
            }
        }
    }

    void init3() {
        JFrame JFi6 = new JFrame();
        JFi6.setLayout(new FlowLayout());
        JFi6.setBounds(200, 50, 500, 400);
        String input = JOptionPane.showInputDialog(JFi6, "请输入存档名：", "存档", JOptionPane.INFORMATION_MESSAGE);
        System.out.println(input);
        JFi6.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFi6.setVisible(true);

        int[][] smjb = new int[x][y];
        StringBuilder sb = new StringBuilder();
        sb.append(p1.getUserName()).append("\n");
        sb.append(p1.getScore()).append("\n");
        sb.append(p1.getMistake()).append("\n");
        sb.append(p2.getUserName()).append("\n");
        sb.append(p2.getScore()).append("\n");
        sb.append(p2.getMistake()).append("\n");
        sb.append(x).append("\n").append(y).append("\n").append(turn).append("\n").append(onTurn.getUserName()).append("\n").append(num).append("\n").append(sum).append("\n");
        for(int ii = 0; ii < x; ii++) {
            for(int jj = 0; jj < y; jj++) {
                smjb[ii][jj] = 10;
                if(mineField[ii][jj].getIcon().toString() == (new ImageIcon("src/一.jpg")).toString()) {
                    smjb[ii][jj] = 1;
                }
                if(mineField[ii][jj].getIcon().toString() == new ImageIcon("src/二.jpg").toString()) {
                    smjb[ii][jj] = 2;
                }
                if(mineField[ii][jj].getIcon().toString() == new ImageIcon("src/三.jpg").toString()) {
                    smjb[ii][jj] = 3;
                }
                if(mineField[ii][jj].getIcon().toString() == new ImageIcon("src/四.jpg").toString()) {
                    smjb[ii][jj] = 4;
                }
                if(mineField[ii][jj].getIcon().toString() == new ImageIcon("src/五.jpg").toString()) {
                    smjb[ii][jj] = 5;
                }
                if(mineField[ii][jj].getIcon().toString() == new ImageIcon("src/六.jpg").toString()) {
                    smjb[ii][jj] = 6;
                }
                if(mineField[ii][jj].getIcon().toString() == new ImageIcon("src/七.jpg").toString()) {
                    smjb[ii][jj] = 7;
                }
                if(mineField[ii][jj].getIcon().toString() == new ImageIcon("src/八.jpg").toString()) {
                    smjb[ii][jj] = 8;
                }
                if(mineField[ii][jj].getIcon().toString() == new ImageIcon("src/草地4.jpg").toString()) {
                    smjb[ii][jj] = 0;
                }
                for(int mine : mines) {
                    if(ii * x + jj + 1 == mine) {//这就是9进制吗，爱了爱了
                        smjb[ii][jj] = -1;
                        break;
                    }
                }
                if(mineField[ii][jj].getIcon().toString() == new ImageIcon("src/地雷.jpg").toString()) {
                    smjb[ii][jj] = -2;
                }
                if(mineField[ii][jj].getIcon().toString() == new ImageIcon("src/旗子.jpg").toString()) {
                    smjb[ii][jj] = -3;
                }
            }
        }
        for(int ii = 0; ii < x; ii++) {
            for(int jj = 0; jj < y; jj++) {
                sb.append(smjb[ii][jj]).append("\n");
            }
        }
        BufferedWriter wt = null;
        File file = new File("./" + input + ".txt");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException ee) {
                ee.printStackTrace();
            }
        }
        try {
            wt = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8"));
            wt.write(String.valueOf(sb));
        } catch(IOException ee) {
            ee.printStackTrace();
        } finally {
            try {
                if(wt != null) {
                    wt.close();
                }
            } catch(IOException ee) {
                ee.printStackTrace();
            }
        }
        JFi6.dispose();
    }

    void init4() {
        JFileChooser JFC = new JFileChooser("./");
        JFC.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        JFC.showDialog(new JLabel(), "读档");
        File file = JFC.getSelectedFile();
        int w = checkerboard.getWidth() / y;
        int h = checkerboard.getHeight() / x;
        try {
            ini();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String p1name = br.readLine();
            p1.setUserName(p1name);
            int p1score = Integer.parseInt(br.readLine());
            p1.setScore(p1score);
            int p1mistake = Integer.parseInt(br.readLine());
            p1.setMistake(p1mistake);
            String p2name = br.readLine();
            p2.setUserName(p2name);
            int p2score = Integer.parseInt(br.readLine());
            p2.setScore(p2score);
            int p2mistake = Integer.parseInt(br.readLine());
            p2.setMistake(p2mistake);
            int xvalue = Integer.parseInt(br.readLine());
            int yvalue = Integer.parseInt(br.readLine());
            int turnvalue = Integer.parseInt(br.readLine());
            String onturnvalue = br.readLine();
            int numvalue = Integer.parseInt(br.readLine());
            int sumvalue = Integer.parseInt(br.readLine());
            x = xvalue;
            y = yvalue;
            turn = turnvalue;
            num = numvalue;
            sum = sumvalue;
            if(onturnvalue.equals(p1.getUserName())) {
                onTurn = p1;
            } else if(onturnvalue.equals(p2.getUserName())) {
                onTurn = p2;
            }
            int[][] cnm;
            cnm = new int[xvalue][yvalue];
            for(int i = 0; i < xvalue; i++) {
                for(int j = 0; j < yvalue; j++) {
                    cnm[i][j] = Integer.parseInt(br.readLine());
                }
            }

            mines = new int[num];

            nu0.setText(String.valueOf(p1.getScore()));
            mi0.setText(String.valueOf(p1.getMistake()));
            nu02.setText(String.valueOf(p2.getScore()));
            mi02.setText(String.valueOf(p2.getMistake()));
            show2.setText(String.valueOf(onTurn.getUserName()));

            tm0.setText("30");
            Box tim = Box.createHorizontalBox();
            tu = new JLabel(":");
            tu.setIcon(new ImageIcon());
            tu.setIcon(setImageSize0("src/进度条.jpg", 200, 50));
            checkerboard = new JPanel();
            checkerboard.setFocusable(true);
            checkerboard.setLayout(new GridLayout(x, y));
            mineField = new JButton[x][y];
            book = new boolean[x][y];

            //  设置位置
            tim.add(Box.createHorizontalStrut(10));
            tim.add(tu);
            tim.add(tm0);
            add(tim, BorderLayout.SOUTH);
            add(checkerboard, BorderLayout.CENTER);
            int k = 0;
            //上色
            for(int i = 0; i < x; i++) {
                for(int j = 0; j < y; j++) {
                    mineField[i][j] = new JButton();
                    if(cnm[i][j] == 0) {
                        mineField[i][j].setIcon(setImageSize0("src/草地4.jpg", w, h));
                        book[i][j] = false;
                    }
                    if(cnm[i][j] == -1) {
                        mineField[i][j].setIcon(setImageSize0("src/草地4.jpg", w, h));
                        book[i][j] = false;
                        mines[k] = i * x + j + 1;
                        k++;
                    }
                    if(cnm[i][j] == -3) {
                        mineField[i][j].setIcon(setImageSize0("src/旗子.jpg", w, h));
                        book[i][j] = true;
                        mines[k] = i * x + j + 1;
                        k++;
                    }
                    if(cnm[i][j] == -2) {
                        mineField[i][j].setIcon(setImageSize0("src/地雷.jpg", w, h));
                        book[i][j] = true;
                        mines[k] = i * x + j + 1;
                        k++;
                    }
                    if(cnm[i][j] == 1) {
                        mineField[i][j].setIcon(setImageSize0("src/一.jpg", w, h));
                        book[i][j] = true;
                    }
                    if(cnm[i][j] == 2) {
                        mineField[i][j].setIcon(setImageSize0("src/二.jpg", w, h));
                        book[i][j] = true;
                    }
                    if(cnm[i][j] == 3) {
                        mineField[i][j].setIcon(setImageSize0("src/三.jpg", w, h));
                        book[i][j] = true;
                    }
                    if(cnm[i][j] == 4) {
                        mineField[i][j].setIcon(setImageSize0("src/四.jpg", w, h));
                        book[i][j] = true;
                    }
                    if(cnm[i][j] == 5) {
                        mineField[i][j].setIcon(setImageSize0("src/五.jpg", w, h));
                        book[i][j] = true;
                    }
                    if(cnm[i][j] == 6) {
                        mineField[i][j].setIcon(setImageSize0("src/六.jpg", w, h));
                        book[i][j] = true;
                    }
                    if(cnm[i][j] == 7) {
                        mineField[i][j].setIcon(setImageSize0("src/七.jpg", w, h));
                        book[i][j] = true;
                    }
                    if(cnm[i][j] == 8) {
                        mineField[i][j].setIcon(setImageSize0("src/八.jpg", w, h));
                        book[i][j] = true;
                    }
                    if(cnm[i][j] == 10) {
                        book[i][j] = true;
                    }
                    mineField[i][j].addMouseListener(new BiuListener());
                    checkerboard.add(mineField[i][j]);
                }
            }
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }
    }

    void popDialog0() {
        JDialog jCkou = new JDialog(getck(), "游戏结束", true);
        jCkou.setBounds(600, 300, 300, 200);
        JLabel hint;
        hint = new JLabel("酣畅淋漓。", JLabel.CENTER);
        JButton jButton1 = new JButton("江湖再会");
        JButton jButton2 = new JButton("再战一局");
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ini();
                jCkou.setVisible(false);
                remove(jCkou);
            }
        });
        jCkou.setLayout(new FlowLayout());
        Box b, c;
        b = Box.createVerticalBox();
        c = Box.createHorizontalBox();

        b.add(Box.createVerticalStrut(30));
        b.add(hint);
        b.add(Box.createVerticalStrut(60));
        c.add(jButton1);
        c.add(Box.createHorizontalStrut(80));
        c.add(jButton2);
        jCkou.add(b);
        jCkou.add(c);
        jCkou.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ini();
                jCkou.setVisible(false);
            }
        });
        jCkou.setVisible(true);
    }

    class Aa {
        int a, b;
    }

    Aa[] searchArray(int i, int k) {
        Aa[] a;
        a = new Aa[8];
        for(int ia = 0; ia < a.length; ia++) {
            a[ia] = new Aa();
            a[ia].a = -1;
            a[ia].b = -1;
        }
        if(i - 1 >= 0 && k - 1 >= 0 && !book[i - 1][k - 1]) {
            a[0].a = i - 1;
            a[0].b = k - 1;
        }
        if(i - 1 >= 0 && !book[i - 1][k]) {
            a[1].a = i - 1;
            a[1].b = k;
        }
        if(i - 1 >= 0 && k + 1 < y && !book[i - 1][k + 1]) {
            a[2].a = i - 1;
            a[2].b = k + 1;
        }
        if(k - 1 >= 0 && !book[i][k - 1]) {
            a[3].a = i;
            a[3].b = k - 1;
        }
        if(k + 1 < y && !book[i][k + 1]) {
            a[4].a = i;
            a[4].b = k + 1;
        }
        if(i + 1 < x && k - 1 >= 0 && !book[i + 1][k - 1]) {
            a[5].a = i + 1;
            a[5].b = k - 1;
        }
        if(i + 1 < x && !book[i + 1][k]) {
            a[6].a = i + 1;
            a[6].b = k;
        }
        if(i + 1 < x && k + 1 < y && !book[i + 1][k + 1]) {
            a[7].a = i + 1;
            a[7].b = k + 1;
        }
        return a;
    }

    void searchMine(int i, int k, int w, int h) {
        book[i][k] = true;    //标记当前位置
        Aa[] a = searchArray(i, k).clone();    //返回周边位置
        int fa = 0;    //周围雷的数量
        for(int jm = 0; jm < mines.length; jm++) {
            for(Aa ia : a) {
                if(ia.a == -1)
                    continue;
                if(mines[jm] == ia.a * x + ia.b + 1) {
                    fa++;
                    break;
                }
            }
        }
        //按照相应的雷数替换相应图片

        switch(fa) {
            case 1:
                if(mineField[i][k].getIcon().toString() == new ImageIcon("src/草地4.jpg").toString()) {
                    mineField[i][k].setIcon(setImageSize0("src/一.jpg", w, h));
                    break;
                }
            case 2:
                if(mineField[i][k].getIcon().toString() == new ImageIcon("src/草地4.jpg").toString()) {
                    mineField[i][k].setIcon(setImageSize0("src/二.jpg", w, h));
                    break;
                }
            case 3:
                if(mineField[i][k].getIcon().toString() == new ImageIcon("src/草地4.jpg").toString()) {
                    mineField[i][k].setIcon(setImageSize0("src/三.jpg", w, h));
                    break;
                }
            case 4:
                if(mineField[i][k].getIcon().toString() == new ImageIcon("src/草地4.jpg").toString()) {
                    mineField[i][k].setIcon(setImageSize0("src/四.jpg", w, h));
                    break;
                }
            case 5:
                if(mineField[i][k].getIcon().toString() == new ImageIcon("src/草地4.jpg").toString()) {
                    mineField[i][k].setIcon(setImageSize0("src/五.jpg", w, h));
                    break;
                }
            case 6:
                if(mineField[i][k].getIcon().toString() == new ImageIcon("src/草地4.jpg").toString()) {
                    mineField[i][k].setIcon(setImageSize0("src/六.jpg", w, h));
                    break;
                }
            case 7:
                if(mineField[i][k].getIcon().toString() == new ImageIcon("src/草地4.jpg").toString()) {
                    mineField[i][k].setIcon(setImageSize0("src/七.jpg", w, h));
                    break;
                }
            case 8:
                if(mineField[i][k].getIcon().toString() == new ImageIcon("src/草地4.jpg").toString()) {
                    mineField[i][k].setIcon(setImageSize0("src/八.jpg", w, h));
                    break;
                }
            case 0:
                if(mineField[i][k].getIcon().toString() == new ImageIcon("src/草地4.jpg").toString()) {
                    mineField[i][k].setIcon(new ImageIcon(""));
                    for(Aa ia : a) {
                        if(ia.a == -1)
                            continue;
                        searchMine(ia.a, ia.b, w, h);    //递归调用
                    }
                    break;
                }
        }    //switch
    }

    void detonateMine(int w, int h) {
        for(int ii = 0; ii < x; ii++) {    //透视
            for(int kk = 0; kk < y; kk++) {
                for(int jj = 0; jj < mines.length; jj++) {
                    if(ii * x + kk + 1 == mines[jj]) {
                        book[ii][kk] = false;
                        if(mineField[ii][kk].getIcon().toString() != new ImageIcon("src/地雷.jpg").toString() && mineField[ii][kk].getIcon().toString() != new ImageIcon("src/旗子.jpg").toString()) {

                            mineField[ii][kk].setIcon(setImageSize0("src/地雷2.jpg", w, h));
                        }
                    }
                }
            }
        }
    }

    void showall(int w, int h) {
        for(int ii = 0; ii < x; ii++) {    //显示未知雷
            for(int kk = 0; kk < y; kk++) {
                for(int jj = 0; jj < mines.length; jj++) {
                    if(ii * x + kk + 1 == mines[jj]) {
                        book[ii][kk] = false;
                        if(mineField[ii][kk].getIcon().toString() != new ImageIcon("src/地雷.jpg").toString() && mineField[ii][kk].getIcon().toString() != new ImageIcon("src/旗子.jpg").toString()) {

                            mineField[ii][kk].setIcon(setImageSize0("src/地雷.jpg", w, h));
                        }
                    }
                }
            }
        }
    }

    void next() {
        int w = checkerboard.getWidth() / y;
        int h = checkerboard.getHeight() / x;
        if(turn % action == 0) {
            if(onTurn == p1)
                onTurn = p2;
            else onTurn = p1;
        }
        show2.setText(String.valueOf(onTurn.getUserName()));
        System.out.println(num);
        System.out.println(sum);

        if(Math.abs(p1.getScore() - p2.getScore()) > num - sum) {//如果双方得分数差距大于游戏区中未揭晓得雷数，
            System.out.println((p1.getScore() > p2.getScore() ? p1.getUserName() : p2.getUserName()) + "获胜");
            JOptionPane.showMessageDialog(null, (p1.getScore() > p2.getScore() ? p1.getUserName() : p2.getUserName()) + "获胜", "游戏结束", JOptionPane.PLAIN_MESSAGE);
            showall(w, h);
            try {
                Thread.sleep(200);    //延时执行
            } catch(Exception ee) {
            }

            popDialog0();
        } else {//判断平局
            if(num == sum && p1.getScore() == p2.getScore()) {
                if(p1.getMistake() > p2.getMistake()) {
                    System.out.println(p2.getUserName() + "获得胜利");
                    JOptionPane.showMessageDialog(null, p2.getUserName() + "获得胜利", "游戏结束", JOptionPane.PLAIN_MESSAGE);
                    showall(w, h);
                    try {
                        Thread.sleep(200);    //延时执行
                    } catch(Exception ee) {
                    }
                    popDialog0();
                } else if(p1.getMistake() < p2.getMistake()) {
                    System.out.println(p1.getUserName() + "获得胜利");
                    JOptionPane.showMessageDialog(null, p1.getUserName() + "获得胜利", "游戏结束", JOptionPane.PLAIN_MESSAGE);
                    showall(w, h);
                    try {
                        Thread.sleep(200);    //延时执行
                    } catch(Exception ee) {
                    }
                    popDialog0();
                } else { //失误数相等则双放平局
                    JOptionPane.showMessageDialog(null, "平局", "游戏结束", JOptionPane.PLAIN_MESSAGE);
                    showall(w, h);
                    try {
                        Thread.sleep(200);    //延时执行
                    } catch(Exception ee) {
                    }
                    popDialog0();
                }
            }
        }
    }

    void setGrid(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void setRand(int q) {
        mines = new int[q];
        for(int i : mines) i = 0;
        mines[0] = -1;
        for(int i = 0; i < mines.length; i++) {
            int rand = 0, j;
            boolean flag = false;
            while(!flag) {
                rand = (int) (Math.random() * x * y);
                if(rand <= 0 || rand > x * y) continue;
                if(mines[i] == -1) break;
                for(j = 0; j < mines.length; j++) {
                    if(rand == mines[j])
                        break;
                }
                if(j == mines.length) flag = true;
            }
            mines[i] = rand;
        }
        Arrays.sort(mines);
    }

    void ini() {
        checkerboard.setVisible(false);    //隐藏当前容器
        remove(checkerboard);        //移除当前容器
        time.stop();    //停止计时
        turn = 1;
        init1();//重新初始化
    }

    void ini1() {
        time.stop();
        init2();
    }

    class Set0 implements ActionListener {
        JPanel setJ;
        JPanel SetJ1 = new JPanel();
        JLabel setJi;
        JButton j, j0;
        JRadioButton j1 = new JRadioButton("简单：9X9网格，10个雷");    //单选按钮
        JRadioButton j2 = new JRadioButton("中等：16X16网格，40个雷");
        JRadioButton j3 = new JRadioButton("困难：16X30网格，99个雷");
        JRadioButton j4 = new JRadioButton("自定义");
        Box b, b1;
        ButtonGroup bg = new ButtonGroup();    //按钮组
        JRadioButton jc = j1, ja = j1;    //存储所选选项

        class Se extends JFrame implements ItemListener {
            Se() {
            }

            Se(String s, int x, int y, int width, int height) {
                inits(s);
                setBounds(x, y, width, height);
                setLocationRelativeTo(null);
                setVisible(true);
                setDefaultCloseOperation(2);
            }

            void inits(String s) {
                setTitle(s);
                setIconImage(getToolkit().getImage("src/地雷.jpg"));

                setJ = new JPanel();


                b = Box.createVerticalBox();
                b1 = Box.createHorizontalBox();
                setJi = new JLabel("难度:", JLabel.CENTER);
                j = new JButton("确定");
                j0 = new JButton("取消");
                bg.add(j1);
                bg.add(j2);
                bg.add(j3);
                bg.add(j4);
                jc.setSelected(true);

                JLabel widthLabel = new JLabel(" 宽度 (2-24):");
                widthLabel.setBounds(70, 240, 80, 20);
                add(widthLabel);
                JTextField width = new JTextField();
                width.setBounds(170, 240, 40, 20);
                add(width);
                JLabel heightLabel = new JLabel("高度 (2-30):");
                heightLabel.setBounds(70, 260, 80, 20);
                add(heightLabel);
                JTextField height = new JTextField();
                height.setBounds(170, 260, 40, 20);
                add(height);
                JLabel mineLabel = new JLabel("雷数 (好自为之):");
                mineLabel.setBounds(70, 280, 160, 20);
                add(mineLabel);
                JTextField mines = new JTextField();
                mines.setBounds(180, 280, 30, 20);
                add(mines);
                JLabel actLabel = new JLabel("机动(1-5):");
                actLabel.setBounds(70, 300, 80, 20);
                add(actLabel);
                JTextField act = new JTextField();
                act.setBounds(170, 300, 40, 20);
                add(act);

                j.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jc = ja;
                        jc.setSelected(true);
                        act.setEditable(true);

                        if(act.getText().isEmpty()) {
                            if(jc == j1) {
                                x = y = 9;
                                num = 10;
                            } else if(jc == j2) {
                                x = y = 16;
                                num = 40;
                            } else if(jc == j3) {
                                x = 16;
                                y = 30;
                                num = 99;
                            } else if(jc == j4) {
                                width.setEditable(true);
                                height.setEditable(true);
                                mines.setEditable(true);
                                if(width.getText().isEmpty() || height.getText().isEmpty() || mines.getText().isEmpty())
                                    JOptionPane.showMessageDialog(null, "请输入数据!");
                                else if(Integer.parseInt(width.getText()) >= 2 && Integer.parseInt(width.getText()) <= 24 && Integer.parseInt(height.getText()) >= 2 && Integer.parseInt(height.getText()) <= 30) {
                                    if(Integer.parseInt(mines.getText()) <= Integer.parseInt(width.getText()) * Integer.parseInt(height.getText()) / 2) {
                                        x = Integer.parseInt(width.getText());
                                        y = Integer.parseInt(height.getText());
                                        num = Integer.parseInt(mines.getText());
                                    } else JOptionPane.showMessageDialog(null, "玩 你 🐎   !!!");
                                } else JOptionPane.showMessageDialog(null, "请输入合法数据!");
                            }
                            ini();
                            dispose();    //销毁窗口
                        } else if(Integer.parseInt(act.getText()) < 1 || Integer.parseInt(act.getText()) > 5) {
                            JOptionPane.showMessageDialog(null, "请输入合法数据!");
                            dispose();
                        } else {
                            if(jc == j1) {
                                x = y = 9;
                                num = 10;
                                action = Integer.parseInt(act.getText());
                            } else if(jc == j2) {
                                x = y = 16;
                                num = 40;
                                action = Integer.parseInt(act.getText());
                            } else if(jc == j3) {
                                x = 16;
                                y = 30;
                                num = 99;
                                action = Integer.parseInt(act.getText());
                            } else if(jc == j4) {
                                width.setEditable(true);
                                height.setEditable(true);
                                mines.setEditable(true);
                                if(width.getText().isEmpty() || height.getText().isEmpty() || mines.getText().isEmpty())
                                    JOptionPane.showMessageDialog(null, "请输入数据!");
                                else if(Integer.parseInt(width.getText()) >= 2 && Integer.parseInt(width.getText()) <= 24 && Integer.parseInt(height.getText()) >= 2 && Integer.parseInt(height.getText()) <= 30) {
                                    if(Integer.parseInt(mines.getText()) <= Integer.parseInt(width.getText()) * Integer.parseInt(height.getText()) / 2) {
                                        x = Integer.parseInt(width.getText());
                                        y = Integer.parseInt(height.getText());
                                        num = Integer.parseInt(mines.getText());
                                        action = Integer.parseInt(act.getText());
                                    } else JOptionPane.showMessageDialog(null, "玩 你 🐎   !!!");
                                } else JOptionPane.showMessageDialog(null, "请输入合法数据!");
                            }

                            ini();
                            dispose();    //销毁窗口
                        }
                    }
                });
                j0.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });
                j1.addItemListener(this);
                j2.addItemListener(this);
                j3.addItemListener(this);
                j4.addItemListener(this);
                b.add(Box.createVerticalStrut(10));
                b.add(setJi);
                b.add(Box.createVerticalStrut(10));
                b.add(j1);
                b.add(Box.createVerticalStrut(5));
                b.add(j2);
                b.add(Box.createVerticalStrut(5));
                b.add(j3);
                b.add(Box.createVerticalStrut(5));
                b.add(j4);
                b.add(Box.createVerticalStrut(30));
                b1.add(j);
                b1.add(Box.createHorizontalStrut(80));
                b1.add(j0);
                setJ.add(b);
                setJ.add(b1);
                add(setJ);
            }

            public void itemStateChanged(ItemEvent e) {
                ja = (JRadioButton) e.getSource();
            }

        }

        public void actionPerformed(ActionEvent e) {
            Se se = new Se("设置", 700, 400, 400, 520);
        }

    }

}