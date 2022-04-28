package org.kociemba.twophase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainProgram extends javax.swing.JFrame {

    private static final int maxtime = 5;
    private static final int maxDepth = 20;

    private static final long serialVersionUID = 1L;
    private final JButton[][] facelet = new JButton[6][9];
    private final JButton[] colorSel = new JButton[6];
    private final int[] XOFF = {3, 6, 3, 3, 0, 9};
    private final int[] YOFF = {0, 3, 3, 6, 3, 3};
    private final Color[] COLORS = {Color.white, Color.red, Color.green, Color.yellow, Color.orange, Color.blue};

    private Color curCol = COLORS[0];

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainProgram inst = new MainProgram();
            inst.setLocationRelativeTo(null);
            inst.setVisible(true);
        });
    }

    public MainProgram() {
        super();
        initGUI();
    }

    private void initGUI() {

        getContentPane().setLayout(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("lol");

        JButton solve = new JButton("Solve Cube");
        solve.setText("Solve");
        getContentPane().add(solve);
        solve.setBounds(422, 64, 114, 48);
        solve.addActionListener(this::solveCube);

        {
            JButton buttonRandom = new JButton("Random Cube");
            getContentPane().add(buttonRandom);
            buttonRandom.setBounds(422, 17, 114, 22);
            buttonRandom.setText("Scramble");
            buttonRandom.addActionListener(evt -> {

                CubieCube cc = new CubieCube();
                Random gen = new Random();
                cc.setFlip((short) gen.nextInt(CoordCube.N_FLIP));
                cc.setTwist((short) gen.nextInt(CoordCube.N_TWIST));
                do {
                    cc.setURFtoDLB(gen.nextInt(CoordCube.N_URFtoDLB));
                    cc.setURtoBR(gen.nextInt(CoordCube.N_URtoBR));
                } while ((cc.edgeParity() ^ cc.cornerParity()) != 0);
                String r = cc.toFaceCube().to_String();

                for (int i = 0; i < 6; i++)
                    for (int j = 0; j < 9; j++) {
                        switch (r.charAt(9 * i + j)) {
                            case 'U':
                                facelet[i][j].setBackground(COLORS[0]);
                                break;
                            case 'R':
                                facelet[i][j].setBackground(COLORS[1]);
                                break;
                            case 'F':
                                facelet[i][j].setBackground(COLORS[2]);
                                break;
                            case 'D':
                                facelet[i][j].setBackground(COLORS[3]);
                                break;
                            case 'L':
                                facelet[i][j].setBackground(COLORS[4]);
                                break;
                            case 'B':
                                facelet[i][j].setBackground(COLORS[5]);
                                break;
                        }
                    }
            });
        }


        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 9; j++) {
                facelet[i][j] = new JButton();
                getContentPane().add(facelet[i][j]);
                facelet[i][j].setBackground(Color.gray);
                facelet[i][j].setRolloverEnabled(false);
                facelet[i][j].setOpaque(true);
                facelet[i][j].setBounds(45 * XOFF[i] + 45 * (j % 3), 45 * YOFF[i] + 45 * (j / 3), 45, 45);
                facelet[i][j].addActionListener(evt -> ((JButton) evt.getSource()).setBackground(curCol));
            }
        for (int i = 0; i < 6; i++) {
            colorSel[i] = new JButton();
            getContentPane().add(colorSel[i]);
            colorSel[i].setBackground(COLORS[i]);
            colorSel[i].setOpaque(true);
            colorSel[i].setBounds(45 * (XOFF[1] + 1) + 45 / 4 * 3 * i, 45 * (YOFF[3] + 1), 45 / 4 * 3, 45 / 4 * 3);
            colorSel[i].setName("" + i);
            colorSel[i].addActionListener(evt -> curCol = COLORS[Integer.parseInt(((JButton) evt.getSource()).getName())]);

        }
        pack();
        this.setSize(556, 460);
    }


    private void solveCube(ActionEvent evt) {
        StringBuilder s = new StringBuilder(54);

        for (int i = 0; i < 54; i++)
            s.insert(i, 'B');

        for (int i = 0; i < 6; i++)

            for (int j = 0; j < 9; j++) {
                if (facelet[i][j].getBackground() == facelet[0][4].getBackground())
                    s.setCharAt(9 * i + j, 'U');
                if (facelet[i][j].getBackground() == facelet[1][4].getBackground())
                    s.setCharAt(9 * i + j, 'R');
                if (facelet[i][j].getBackground() == facelet[2][4].getBackground())
                    s.setCharAt(9 * i + j, 'F');
                if (facelet[i][j].getBackground() == facelet[3][4].getBackground())
                    s.setCharAt(9 * i + j, 'D');
                if (facelet[i][j].getBackground() == facelet[4][4].getBackground())
                    s.setCharAt(9 * i + j, 'L');
                if (facelet[i][j].getBackground() == facelet[5][4].getBackground())
                    s.setCharAt(9 * i + j, 'B');
            }

        String cubeString = s.toString();
        System.out.println("--------------------------------");
        System.out.println(cubeString);
        String result = Search.solution(cubeString, maxDepth, maxtime);
        if (result.contains("Error")) {
            switch (result.charAt(result.length() - 1)) {
                case '1':
                    result = "There are not exactly nine facelets of each color!";
                    break;
                case '2':
                    result = "Not all 12 edges exist exactly once!";
                    break;
                case '3':
                    result = "Flip error: One edge has to be flipped!";
                    break;
                case '4':
                    result = "Not all 8 corners exist exactly once!";
                    break;
                case '5':
                    result = "Twist error: One corner has to be twisted!";
                    break;
                case '6':
                    result = "Parity error: Two corners or two edges have to be exchanged!";
                    break;
                case '7':
                    result = "No solution exists for the given maximum move number!";
                    break;
                case '8':
                    result = "Timeout, no solution found within given maximum time!";
                    break;
            }
            System.out.println(result);
        } else {
            String solution = result.substring(0, result.length() - 1);
            System.out.println(solution);
            System.out.println(solution.split(" ").length);
            System.out.println(reverse(solution));
            System.out.println("--------------------------------");
        }
    }

    public static String reverse(String str) {
        List<String> reverse = new ArrayList<>();
        for (String s : str.split(" ")) {
            reverse.add(0, s.endsWith("'") ? s.substring(0, s.length() - 1) : (s.endsWith("2") ? s : s + "'"));
        }
        String result = "";
        for (String s : reverse.toArray(new String[0])) {
            result += " " + s;
        }
        return result.substring(1);
    }
}
