import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * FigureViewer
 *
 * Simple graphical application to display simple geometric figures
 *
 * Created by Pongnut Jittipanyakul (Prues)
 * ID 58070503419
 *
 * Modified for Exercise 5
 * - Implements MouseListener.
 * - Add redraw button.
 * - Check inShape for all shapes.
 * - Add draw to color the selected shape.
 *
 * 10 September 2017
 */

public class FigureViewer extends JFrame implements ActionListener, MouseListener
{
    /* UI objects */
    private DrawingCanvas drawCanvas = null;
    private JButton clearButton = null;
    private JButton redrawButton = null;
    private JButton exitButton = null;

    /**
     * Constructor creates the User Interface.
     */
    public FigureViewer()
    {
        super("Figure Viewer");
        buildUI();
    }

    /**
     * Create the visible part of the user interface.
     */
    private void buildUI()
    {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.setBorder(new EtchedBorder());

        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        controlPanel.add(clearButton);

        redrawButton = new JButton("Redraw");
        redrawButton.addActionListener(this);
        controlPanel.add(redrawButton);

        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        controlPanel.add(exitButton);
        mainPanel.add(controlPanel, BorderLayout.NORTH);

        drawCanvas = new DrawingCanvas(400, 400);
        drawCanvas.addMouseListener(this);
        drawCanvas.setBorder(new EtchedBorder());
        drawCanvas.setBackground(Color.white);
        mainPanel.add(drawCanvas, BorderLayout.CENTER);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * This is the method required for the ActionListener
     * interface. It handles the necessary actions when
     * buttons are pressed.
     */
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        if (source == exitButton)
        {
            System.exit(0);
        }
        else if (source == redrawButton)
        {
            drawCanvas.update(getViewerGraphics());
            AbstractShape.drawAll(getViewerGraphics());
        }
        else if (source == clearButton)
        {
            drawCanvas.clear();
        }
    }

    /**
     * Clear the drawing panel.
     */
    public void clear()
    {
        drawCanvas.clear();
    }

    /**
     * Return the graphics context associated with
     * the panel used for drawing.
     *
     * @return Graphics context
     */
    public Graphics2D getViewerGraphics()
    {
        return (Graphics2D) drawCanvas.getGraphics();
    }

    /**
     * MouseListener methods
     **/
    public void mouseEntered(MouseEvent mouseEv)
    {
    }
    public void mouseExited(MouseEvent mouseEv)
    {
    }
    public void mousePressed(MouseEvent mouseEv)
    {
    }
    public void mouseReleased(MouseEvent mouseEv)
    {
    }

    /**
     * If the user clicks the mouse in the drawing area,
     * get the point and pass it to inShape.
     */
    public void mouseClicked(MouseEvent mouseEv)
    {
        for (int i = 0; i < AbstractShape.allFigures.size(); i++)
        {
            if (AbstractShape.allFigures.get(i).inShape(mouseEv.getX(), mouseEv.getY()))
            {
                drawCanvas.update(getViewerGraphics());
                for (int j = 0; j < AbstractShape.allFigures.size(); j++)
                {
                    if (j == i)
                    {
                        AbstractShape.allFigures.get(j).draw(getViewerGraphics(), AbstractShape.allFigures.get(j).drawColor);
                    }
                    else
                    {
                        AbstractShape.allFigures.get(j).draw(getViewerGraphics());
                    }
                }
                break;
            }
        }
    }
}