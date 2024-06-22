package it.unibo.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibo.controller.GameControllerImpl;
import it.unibo.model.utilities.ScaledImage;

/**
 * map loading with slider for scrolling.
 */
public class SelectMapGui extends JFrame {

    private final JLabel imageLabels;
    private int focusIndex;
    private final List<String> maps = new GameControllerImpl().getAvailableMaps();
    private final JPanel oldGui;
    private GuiGameStart guiGameStart;
    private JLabel leftButton;
    private JLabel rightButton;
    private Image left = null;
    private Image right = null;
    private static final int DIMENSION_BUTTONS = 100;
    private static final int SPACE_TEXT = 25;
    private final JLabel textLabel;

    /**
     * @param oldGui switching the gui panel of the old window
     */
    public SelectMapGui(final JPanel oldGui) {
        this.oldGui = oldGui;

        // Set the layout of the contentPane to BorderLayout
        oldGui.setLayout(new BorderLayout());

        focusIndex = 0; // Index of the central image

        // Button to scroll left
        this.leftButton = new JLabel();
        leftButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                // Update the focusIndex and redraw the images
                focusIndex = (focusIndex - 1 + maps.size()) % maps.size();
                updateImages();
            }
        });

        try {
            left = ImageIO.read(ClassLoader.getSystemResource("buttons/left.png"));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("error when retrieving " + "buttons/left.png");
        }

        leftButton.setPreferredSize(new Dimension(DIMENSION_BUTTONS, DIMENSION_BUTTONS));
        leftButton.setIcon(ScaledImage.getScaledImage(left, DIMENSION_BUTTONS, DIMENSION_BUTTONS));

        // Add the button to scroll left to the frame
        oldGui.add(leftButton, BorderLayout.WEST);

        // Button to scroll right
        this.rightButton = new JLabel();
        rightButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                // Update the focusIndex and redraw the images
                focusIndex = (focusIndex + 1) % maps.size();
                updateImages();
            }
        });

        try {
            right = ImageIO.read(ClassLoader.getSystemResource("buttons/right.png"));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("error when retrieving " + "buttons/right.png");
        }

        rightButton.setPreferredSize(new Dimension(DIMENSION_BUTTONS, DIMENSION_BUTTONS));
        rightButton.setIcon(ScaledImage.getScaledImage(right, DIMENSION_BUTTONS, DIMENSION_BUTTONS));

        // Add the button to scroll right to the frame
        oldGui.add(rightButton, BorderLayout.EAST);
        textLabel = new JLabel("Select the map for starting the game. This is " + maps.get(this.focusIndex));
        textLabel.setSize(textLabel.getWidth(), 25);
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        oldGui.add(textLabel, BorderLayout.NORTH);

        // Panel to contain the images
        JPanel imagePanel = new JPanel(new FlowLayout());

        // Initialize the array of JLabels
        this.imageLabels = new JLabel();
        imageLabels.setHorizontalAlignment(SwingConstants.CENTER);
        //stringAndImage.add(imageLabels,BorderLayout.CENTER);
        // Initialize the JLabels with the images
        oldGui.add(imagePanel, BorderLayout.CENTER);

        // Initialize the JLabels with the images
        imageLabels.setSize(this.oldGui.getWidth(), this.oldGui.getHeight());
        imageLabels.setIcon(new ImageIcon(ClassLoader.getSystemResource("map_preview/" + maps.get(this.focusIndex) + ".png")));
        imageLabels.setIcon(getScalated(maps.get(focusIndex)));
        imageLabels.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                // Change GUI for starting game
                changeGui(maps.get(focusIndex));
            }
        });

        ComponentAdapter resize = new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                imageLabels.setIcon(getScalated(maps.get(focusIndex)));
            }
        };
        imagePanel.addComponentListener(resize);

        imagePanel.add(imageLabels);

        // Request the container to update the GUI
        oldGui.revalidate();
        oldGui.repaint();

        // Request the container to update the GUI
        oldGui.revalidate();
        oldGui.repaint();
    }

    // Update the image when an arrow is clicked
    private void updateImages() {
        textLabel.setText("Select the map for starting the game. This is " + maps.get(this.focusIndex));
        // Remove previous mouse listeners
        for (MouseListener adapter : imageLabels.getMouseListeners()) {
            imageLabels.removeMouseListener(adapter);
        }
        imageLabels.setIcon(getScalated(maps.get(focusIndex)));
        imageLabels.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                // Change GUI for starting game
                changeGui(maps.get(focusIndex));
            }
        });

    }

    /**
     * Change gui with map selected and start game.
     *
     * @param mapSelected Selected map name
     */
    public void changeGui(final String mapSelected) {
        oldGui.removeAll();
        if (guiGameStart == null) {
            guiGameStart = new GuiGameStart((mapSelected), this.oldGui);
        } else {
            // Ensure SelectMapGui is visible if it's already instantiated
            guiGameStart.setVisible(true);
        }
    }

    private ImageIcon getScalated(final String image) {
        int widthWitchButton = this.oldGui.getWidth() - (DIMENSION_BUTTONS * 2);
        int heightWitchText = this.oldGui.getHeight() - SPACE_TEXT;
        BufferedImage icon;
        try {
            icon = ImageIO.read(ClassLoader.getSystemResource("map_preview/" + image + ".png"));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("error when retrieving " + "map_preview/" + image + ".png");
            return null;
        }

        if (heightWitchText <= 0 || widthWitchButton <= 0) {
            return ScaledImage.getScaledImage(icon, DIMENSION_BUTTONS, DIMENSION_BUTTONS);
        }

        // Calculates the proportions of the image
        double iconAspectRatio = (double) icon.getWidth() / icon.getHeight();
        double panelAspectRatio = (double) widthWitchButton / heightWitchText;

        // Check if the image is wider than the available space
        if (icon.getWidth() > widthWitchButton || icon.getHeight() > heightWitchText) {
            if (panelAspectRatio > iconAspectRatio) {
                // L'altezza è il fattore limitante
                int newHeight = heightWitchText;
                int newWidth = (int) (newHeight * iconAspectRatio);
                return ScaledImage.getScaledImage(icon, newWidth, newHeight);
            } else {
                // Width is the limiting factor
                int newWidth = widthWitchButton;
                int newHeight = (int) (newWidth / iconAspectRatio);
                return ScaledImage.getScaledImage(icon, newWidth, newHeight);
            }
        }
        return new ImageIcon(icon);
    }
}
