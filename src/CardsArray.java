import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class CardsArray{
    private ArrayList<Integer> indxList;
    private int in = 0, cardID = 0;
    private int indx = 99, indx2;
    private JFrame frame, frame2, startFrame, helpFrame;
    private JButton next;
    private JButton button[] = new JButton[52];
    private boolean test = false, test2 = false;
    private int incGuesses = 0, number = 0;
    private JTextField t1;
    private JLabel label;

    public static void main(String[] args){
        new CardsArray().startFrame();
    }

    private void startFrame(){
        startFrame = new JFrame("Number of cards");
        startFrame.setLocationRelativeTo(null);
        JLabel label = new JLabel("Type number of cards: ");
        Font bigFont = new Font ("serif", Font. BOLD, 18);
        label.setFont(bigFont);
        JPanel panel = new JPanel();
        t1 = new JTextField(7);
        bigFont = new Font ("serif", Font. BOLD, 15);
        JButton button = new JButton("Start game");
        button.addActionListener(new ButtonNumberListener());
        button.setFont(bigFont);
        panel.add(label);
        panel.add(t1);
        panel.add(button);

        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.getContentPane().add(BorderLayout.CENTER, panel);
        startFrame.setSize(400, 90);
        startFrame.setVisible(true);
    }
    private void frame(){
        frame = new JFrame("Card player");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        next = new JButton("Start");
        JButton back = new JButton("Back");

        next.addActionListener(new NextListener());
        back.addActionListener(new BackListener());
        frame.getContentPane().add(BorderLayout.CENTER, new MyDrawPanel());
        frame.getContentPane().add(BorderLayout.EAST, next);
        frame.getContentPane().add(BorderLayout.WEST, back);
        frame.setSize(365, 297);
        frame.setVisible(true);

        go();
    }
    private void go(){
        indxList = new ArrayList<Integer>();
        while(indxList.size() < number){
            rand();
        }
    }
    private void rand(){
        int r = (int) (Math.random() * 52);
        System.out.println(r);
        boolean check = false;
        if(in == 0){
            indxList.add(r);
            in++;
        }else{
            int n = in-1;
            while(n >= 0){
                if(indxList.get(n) == r){
                    check = true;
                    break;
                }
                n--;
            }
            if(!check){
                indxList.add(r);
                in++;
            }
        }
    }
    private void setFrame(){
        frame2 = new JFrame("Select Cards");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Color color = new Color(0,100,0);
        try{
            JPanel panel2 = new JPanel();
            for(int j=0; j<52; j++){
                BufferedImage buttonIcon = ImageIO.read(new File("Card_res" + j + ".png"));
                button[j] = new JButton(new ImageIcon(buttonIcon));
                button[j].setBorderPainted(false);
                button[j].setFocusable(false);
                button[j].setBackground(color);
                button[j].addActionListener(new ButtonCheckListener());
                button[j].setActionCommand("listener" + j);
                button[j].setEnabled(false);
                panel2.add(button[j]);
                setEnabled(j);
            }
            panel2.setBackground(color);
            Font bigFont = new Font ("serif", Font. BOLD, 28);
            label = new JLabel("1 card of the deck");
            label.setFont(bigFont);
            panel2.add(label);
            JButton helpButton = new JButton("Help me!");
            helpButton.addActionListener(new HelpListener());
            panel2.add(helpButton);
            frame2.getContentPane().add(panel2);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        frame2.setSize(1830,660);
        frame2.setVisible(true);
    }
    private void setEnabled(int test){
        for(int i=0; i<number; i++){
            if(test == indxList.get(i)){
                button[test].setEnabled(true);
            }
        }
    }
    private boolean checkIndx(int cardIndx){
        if(indxList.get(cardID) == cardIndx){
            button[cardIndx].setEnabled(false);
            cardID++;
            label.setText((cardID + 1) + " card of the deck ");
            return true;
        }else{
            return false;
        }
    }
    private boolean isDisabled(){
        int count = 0;
        for(int i=0; i<52; i++){
            if(button[i].isEnabled() == false){
                count++;
            }
        }
        if(count == 52){
            return true;
        }else{
            return false;
        }
    }
    private void gotMedal(int inGuess){
        JFrame frame3 = new JFrame();
        JPanel mainPanel = new JPanel();
        JButton pointButton;
        BufferedImage medal = null;
        Font bigFont = new Font ("serif", Font. BOLD, 12);
        JLabel info = new JLabel("You have made " + incGuesses + " incorrect guesses");
        info.setFont(bigFont);
        frame3.setSize(250,455);
        try{
            if(inGuess <= 5){
                frame3.setTitle("Awesome!");
                medal = ImageIO.read(new File("goldMedal.png"));
            }else if(inGuess <= 10){
                frame3.setTitle("Good!");
                medal = ImageIO.read(new File("silverMedal.png"));
            }else if(inGuess <= 15){
                frame3.setTitle("OK!");
                medal = ImageIO.read(new File("bronzeMedal.png"));
            }else{
                frame3.setTitle("Bad");
                medal = ImageIO.read(new File("sadFace.png"));
                frame3.setSize(450,500);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        pointButton = new JButton(new ImageIcon(medal));
        pointButton.setBorderPainted(false);
        pointButton.setFocusable(false);
        mainPanel.add(pointButton);
        mainPanel.add(info);
        frame3.setLocationRelativeTo(null);
        frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame3.getContentPane().add(mainPanel);
        frame3.setVisible(true);
    }
    private void setHelp(){
        helpFrame = new JFrame("Help");
        helpFrame.setLocationRelativeTo(null);
        MyDrawPanel myPanel = new MyDrawPanel();
        helpFrame.add(myPanel);
        helpFrame.setSize(227,300);
        indx = indxList.get(cardID);
        helpFrame.setVisible(true);
    }
    class NextListener implements ActionListener{
        public void actionPerformed(ActionEvent arg0) {
            test2 = true;
            if(test == false){
                next.setText("Next 1");
                indx = indxList.get(0);
                test = true;
                frame.repaint();
            }else{
                if(indx2 < (number-1)){
                    indx2++;
                    indx = indxList.get(indx2);
                    frame.repaint();
                    int temp = indx2 + 1;
                    next.setText("Next " + temp);
                    if(temp == number){
                        next.setText("Check");
                    }
                }else{
                    frame.setVisible(false);
                    setFrame();
                }
            }
        }
    }
    class BackListener implements ActionListener{
        public void actionPerformed(ActionEvent arg0) {
            if(test2 == true && indx2 > 0){
                indx2--;
                indx = indxList.get(indx2);
                int ind = indx2 + 1;
                next.setText("Next " + ind);
                test2 = false;
                frame.repaint();
            }else{
                if(indx2 > 0){
                    indx2--;
                    if(indx2 == 0){
                        next.setText("Next 1");
                    }else{
                        int temp = indx2 + 1;
                        next.setText("Next " + temp);
                    }
                    indx = indxList.get(indx2);
                    frame.repaint();
                }
            }
        }
    }
    class ButtonCheckListener implements ActionListener{
        public void actionPerformed(ActionEvent a) {
            for(int i=0; i<52; i++){
                if(a.getActionCommand().equals("listener" + i)){
                    if(checkIndx(i) == true){
                        break;
                    }else{
                        incGuesses++;
                        if(a.getSource() instanceof JButton) {
                            ((JButton)a.getSource()).setBackground(Color.RED);
                        }
                    }
                }
            }
            if(isDisabled() == true){
                gotMedal(incGuesses);
                frame2.setVisible(false);
            }
        }
    }
    class ButtonNumberListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            number = Integer.parseInt(t1.getText());
            if(number > 52){
                number = 52;
            }else if(number <= 0){
                number = 1;
            }
            startFrame.setVisible(false);
            frame();
        }
    }
    class HelpListener implements ActionListener{
        public void actionPerformed(ActionEvent arg0) {
            setHelp();
        }
    }
    class MyDrawPanel extends JPanel{
        public void paintComponent(Graphics g){
            Image img = new ImageIcon("Card" + indx + ".png").getImage();
            g.drawImage(img, 5, 0, this);
        }
    }
}