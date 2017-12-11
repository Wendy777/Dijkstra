import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.awt.*;

public class NumberInputer extends JTextField implements KeyListener, MouseWheelListener{
    double stepSize = 1;
    Toolkit toolkit = Toolkit.getDefaultToolkit();

    public NumberInputer(){
        Keymap keymap = getKeymap();
        keymap.addActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0),
                new AbstractAction(){
                    public void actionPerformed(ActionEvent e){
                        nextStep();
                    }
                });
        keymap.addActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0),
                new AbstractAction(){
                    public void actionPerformed(ActionEvent e){
                        previousStep();
                    }
                });
        addKeyListener(this);
        addMouseWheelListener(this);
        setHorizontalAlignment(CENTER);
    }

    public void paste(){
//Ctrl-V not allowed here to prevent illegal inputs.
    }

    public void keyTyped(KeyEvent e){
        char keyChar = e.getKeyChar();
        if((keyChar >= '0' && keyChar <= '9') //Digits
                || (keyChar == '-' && getCaretPosition() == 0 && getText().indexOf("-") == -1) //Sign
                || (keyChar == '.' && getText().indexOf(".") == -1) //Radix point
                || (keyChar == '\b') //Backspace
                ){
            return;
        }

        toolkit.beep();
        e.consume(); //Stop the illegal inputs from being added to the text field.
    }

    public void keyPressed(KeyEvent e){}

    public void keyReleased(KeyEvent e){}

    public void mouseWheelMoved(MouseWheelEvent e){
        if(e.getWheelRotation() < 0){
            nextStep();
        }else{
            previousStep();
        }
    }

    public double getStepSize(){
        return stepSize;
    }

    public void setStepSize(double s){
        stepSize = s;
    }

    public void nextStep(){
        try{
            double value = getDouble();
            String newText = String.valueOf(value + stepSize);
            setText(((int) value == value) ? newText.substring(0, newText.indexOf(".")) : newText);
        }catch(NumberFormatException e){}
    }

    public void previousStep(){
        try{
            double value = getDouble();
            String newText = String.valueOf(value - stepSize);
            setText(((int) value == value) ? newText.substring(0, newText.indexOf(".")) : newText);
        }catch(NumberFormatException e){}
    }

    public double getDouble() throws NumberFormatException{
        return Double.parseDouble(getText());
    }

    public float getFloat() throws NumberFormatException{
        return Float.parseFloat(getText());
    }

    public int getInt() throws NumberFormatException{
        return Integer.parseInt(getText());
    }

    public long getLong() throws NumberFormatException{
        return Long.parseLong(getText());
    }
}
