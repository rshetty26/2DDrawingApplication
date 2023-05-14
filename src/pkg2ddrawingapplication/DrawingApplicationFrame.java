package pkg2ddrawingapplication;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author rithv
 */
public class DrawingApplicationFrame extends JFrame {
    private final JFrame frame;
    private final JPanel main;
    private final JPanel top;
    private final JPanel bottom;
    
    private final JPanel drawPanel;
    private final GridLayout layout;
    private final JLabel shape;
    private final JComboBox<String> dropdown;
    public static final String[] names = {"Line", "Oval", "Rectangle"};
    private final JButton firstColor;
    private final JButton secondColor;
    private final JButton undo;
    private final JButton clear;
    
    private final JLabel options;
    private final JCheckBox filled;
    private final JCheckBox gradient;
    private final JCheckBox dashed;
    
    private final JLabel lineWidth;
    private final JSpinner lineWidthNum;
    private final JLabel dashLength;
    private final JSpinner dashLengthNum;
    private final JLabel status;
    
    private ArrayList<MyShapes> shapes = new ArrayList<>();
    private Color color1  = Color.black;
    private Color color2 = Color.black;
    private MyShapes currentShape;
  
    public DrawingApplicationFrame() {
        frame = new JFrame();
        main = new JPanel();
        top = new JPanel();
        bottom = new JPanel();
        drawPanel = new DrawPanel();
        layout = new GridLayout(2, 1);
        main.setLayout(layout);
        top.setBackground(new Color(179, 255, 255));
        bottom.setBackground(new Color(179, 255, 255));
                
        shape = new JLabel("Shape: ");
        top.add(shape);
        dropdown = new JComboBox<>(names);
        dropdown.setMaximumRowCount(3);
        
        top.add(dropdown);
        firstColor = new JButton("1st Color...");
        firstColor.addActionListener((ActionEvent event) -> {
            color1 = JColorChooser.showDialog(null, "Change Button Background", color1); });
        top.add(firstColor);
        secondColor = new JButton("2nd Color...");
        secondColor.addActionListener((ActionEvent event) -> {
            color2 = JColorChooser.showDialog(null, "Change Button Background", color2);});
        top.add(secondColor);
        undo = new JButton("Undo");
        undo.addActionListener((ActionEvent event) -> {
            if(!shapes.isEmpty()){
                shapes.remove(shapes.size() - 1);
                drawPanel.repaint();}});
        top.add(undo);
        clear = new JButton("Clear");
        clear.addActionListener((ActionEvent event) -> {
            if(!shapes.isEmpty()){
                shapes.clear();
                drawPanel.repaint();}});
        top.add(clear);
        
        options = new JLabel("Options: ");
        bottom.add(options);
        filled = new JCheckBox("Filled");
        bottom.add(filled);
        gradient = new JCheckBox("Use Gradient");
        bottom.add(gradient);
        dashed = new JCheckBox("Dashed");
        bottom.add(dashed);
        lineWidth = new JLabel("Line Width: ");
        bottom.add(lineWidth);
        lineWidthNum = new JSpinner();
        bottom.add(lineWidthNum);
        dashLength = new JLabel("Dash Length: ");
        bottom.add(dashLength);
        dashLengthNum = new JSpinner();
        bottom.add(dashLengthNum);
        
        main.add(top);
        main.add(bottom);
        
        status = new JLabel("( , )");
        status.setOpaque(true);
        status.setBackground(new Color(215, 215, 215));
        
        frame.add(main,  BorderLayout.NORTH);
        frame.add(drawPanel,  BorderLayout.CENTER);
        frame.add(status, BorderLayout.SOUTH);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Java 2D Drawings");
        frame.pack();
        frame.setSize(650,500);
        frame.setVisible(true);
    }

    private class DrawPanel extends JPanel {

        public DrawPanel() {
            MouseHandler handler = new MouseHandler();
            this.addMouseListener(handler);
            this.addMouseMotionListener(handler);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            for (int i = 0; i < shapes.size(); i++){
                shapes.get(i).draw(g2d);
            }
        }

        private class MouseHandler extends MouseAdapter implements MouseMotionListener {
            
            @Override
            public void mousePressed(MouseEvent event) { 
                Integer pressX = event.getX();
                Integer pressY = event.getY();
                Boolean fill = filled.isSelected(); 
                Paint paint; 
                Stroke stroke; 

                if(gradient.isSelected()){
                    if(color2 == null){
                        paint = new GradientPaint(0, 0, color1, 50, 50, color1, true);
                    } else {
                        paint = new GradientPaint(0, 0, color1, 50, 50, color2, true);
                    }
                } else {
                    paint = color1;
                }
                float lineWidthStroke = Float.valueOf(lineWidthNum.getValue().toString());
                float dashLengthStroke[] = {Float.valueOf(dashLengthNum.getValue().toString())};
                if(dashed.isSelected() && dashLengthStroke[0] != 0){
                    stroke = new BasicStroke(lineWidthStroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10, dashLengthStroke, 0);
                } else {
                    stroke  = new BasicStroke(lineWidthStroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
                }
                
                switch (dropdown.getSelectedItem().toString()) {
                    case "Line":
                        currentShape = new MyLine(new Point(pressX, pressY), new Point(pressX, pressY), paint, stroke);
                        break;
                    case "Oval":
                        if (fill){
                            currentShape = new MyOval(new Point(pressX, pressY), new Point(pressX, pressY), paint, stroke, true);
                        } else {
                            currentShape = new MyOval(new Point(pressX, pressY), new Point(pressX, pressY), paint, stroke, false);
                        }   break;
                    case "Rectangle":
                        if (fill){
                            currentShape = new MyRectangle(new Point(pressX, pressY), new Point(pressX, pressY), paint, stroke, true);
                        } else {
                            currentShape = new MyRectangle(new Point(pressX, pressY), new Point(pressX, pressY), paint, stroke, false);
                        }   break;
                }
                currentShape.setStartPoint(event.getPoint());
                currentShape.setEndPoint(event.getPoint());
                shapes.add(currentShape);
            }

            @Override
            public void mouseReleased(MouseEvent event) {
                status.setText("(" + event.getX() + ", " + event.getY() + ")");
            }

            @Override
            public void mouseDragged(MouseEvent event) {
                status.setText("(" + event.getX() + ", " + event.getY() + ")");
                shapes.get(shapes.size()-1).setEndPoint(event.getPoint());
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent event) { 
                status.setText("(" + event.getX() + ", " + event.getY() + ")");
            }
        }

    }   
}
