import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Gui extends JFrame  {
    private JTextArea display;
    private  JPanel content_pane = new JPanel();
    private JButton enter_btn;
    public Gui (){

        setSize( 900, 900 );  
        content_pane.setSize(900,900);     
        content_pane.setBackground(Color.DARK_GRAY);
        display = new JTextArea();
        display.setEnabled(false);
        display.setText("adskkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n fsgbd\nadskkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n fsgbd\nadskkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n fsgbd\n");
        JScrollPane displayscroll = new JScrollPane(display);
        displayscroll.setPreferredSize(new Dimension(800,700));
        content_pane.add(displayscroll);
        
        enter_btn = new JButton();
        enter_btn.setBounds(875, 100, 50, 50);
        content_pane.add(enter_btn);
        setContentPane(content_pane);
        

       
        

    }

}