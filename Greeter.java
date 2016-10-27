import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import javax.swing.border.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.*;
import javax.imageio.ImageIO;

public class Greeter {

    public static void main(String[] args) {
        Input = new input();
        Input.main(Input);
    }

    private static input Input;
}

/* Input dialog */
class input extends JFrame {

    public input() {
        /* Constructor */
        this.setSize(400, 50);
        this.setTitle("NQueens problem");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setAlwaysOnTop(true);
        this.requestFocus();
    }

    public void main(input Input) {
        /* Called from the main function */
        inputEngine = new InputEngine(Input);

        panel = new JPanel();
        label = new JLabel("Enter board size : ");
        panel.add(label);

        text = new JTextField(2);
        text.addActionListener(inputEngine);
        panel.add(text);

        Input.setContentPane(panel);
        this.setSize(400, 60);
        this.setVisible(true);
    }

    private InputEngine inputEngine;

    private JPanel panel;
    private JLabel label;
    private JTextField text;
}

class InputEngine implements ActionListener {
    input parent;

    InputEngine(input parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        Object eventSource = evt.getSource();
        JTextField text = (JTextField) eventSource;
        int size = 0;

        try {
            size = Integer.parseInt(text.getText());
        } catch(Exception e) {
            System.out.println("Bad input!");
            System.exit(0);
        }

        if (size < 1) {
            System.out.println("Irrelevant board size!");
            System.exit(0);
        }
        parent.setVisible(false);
        Display = new display(size);
        Display.main(Display);
    }

    private static display Display;
}

class display extends JFrame {

    display(int size) {
        this.size = size;
    }

    public void main(display Display) {

        solutions = new ArrayList<int []>();
        solutions = Queens.main(size);
        counts = solutions.size();
        if (solutions.size() == 0) {
            JOptionPane.showMessageDialog(this, "No solutions!");
            System.exit(0);
        }

        view = 0;

        this.setTitle("Solutions");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        label11 = new JLabel(" Solution ");
        label12 = new JLabel(Integer.toString(view + 1));
        label13 = new JLabel(" / " + Integer.toString(solutions.size()) + " ");

        JButton first = new JButton("<<");
        first.setName("first");
        JButton prev = new JButton("<");
        prev.setName("prev");
        JButton next = new JButton(">");
        next.setName("next");
        JButton last = new JButton(">>");
        last.setName("last");

        panel1.add(first);
        panel1.add(prev);
        panel1.add(label11);
        panel1.add(label12);
        panel1.add(label13);
        panel1.add(next);
        panel1.add(last);

        displayEngine = new DisplayEngine(Display);
        first.addActionListener(displayEngine);
        prev.addActionListener(displayEngine);
        next.addActionListener(displayEngine);
        last.addActionListener(displayEngine);

        GridLayout g1 = new GridLayout(size, size);
        panel2.setLayout(g1);

        buttons = new JButton[size][size];
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(12, 12));
                buttons[i][j].setBorder(new LineBorder(Color.BLACK));
                buttons[i][j].setBorderPainted(true);
                buttons[i][j].setName(i + " " + j);
                buttons[i][j].setText(i + " " + j);
                buttons[i][j].setEnabled(false);
                panel2.add(buttons[i][j]);
            }
        }

        setBoard(0);

        mainPanel.add(panel1);
        mainPanel.add(panel2);
        this.setSize(400, 400);
        Display.setContentPane(mainPanel);
        this.setVisible(true);
    }

    void clearBoard() {
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                buttons[i][j].setText("");
            }
        }
    }

    void setBoard(int view) {
        clearBoard();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (((i+j) & 1) != 0) {
                    buttons[i][j].setBackground(new Color(0, 0, 0));
                    buttons[i][j].setForeground(new Color(255, 255, 255));
                    buttons[i][j].setOpaque(true);
                }
                else {
                    buttons[i][j].setBackground(new Color(255, 255, 255));
                    buttons[i][j].setForeground(new Color(0, 0, 0));
                    buttons[i][j].setOpaque(true);
                }
                if (solutions.get(view)[i] == j)
                    buttons[i][j].setText("Q");
            }
        }
    }

    int getView() {
        return view;
    }

    void setView(int newView) {
        view = newView;
        label12.setText(Integer.toString(view + 1));
    }

    int view;
    int counts;
    final private int size;
    JButton[][] buttons;
    private DisplayEngine displayEngine;
    JLabel label11;
    JLabel label12;
    JLabel label13;
    ArrayList<int []> solutions;
}

class DisplayEngine implements ActionListener {
    display parent;

    DisplayEngine(display parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        Object eventSource = evt.getSource();
        JButton clickedButton = (JButton) eventSource;
        String name = clickedButton.getName();
        if (name.equals("next")) {
            parent.clearBoard();
            int view = parent.getView();
            if (view == parent.counts - 1)
                view = 0;
            else
                view = view + 1;
            parent.setView(view);
            parent.setBoard(view);
        }
        else if (name.equals("prev")) {
            parent.clearBoard();
            int view = parent.getView();
            if (view == 0)
                view = parent.counts - 1;
            else
                view = view - 1;
            parent.setView(view);
            parent.setBoard(view);
        }
        else if (name.equals("last")) {
            parent.clearBoard();
            parent.setView(parent.counts - 1);
            parent.setBoard(parent.counts - 1);
        }
        else if (name.equals("first")) {
            parent.clearBoard();
            parent.setView(0);
            parent.setBoard(0);
        }
    }

}
