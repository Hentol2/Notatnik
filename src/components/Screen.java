package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class Screen extends Component implements ActionListener {
    JTextArea output;
    JScrollPane scrollPane;
    JFileChooser fc;

    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;

        menuBar = new JMenuBar();

        menu = new JMenu("Plik");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        ImageIcon icon1 = createImageIcon("icons/new.png");
        ImageIcon icon2 = createImageIcon("icons/open.png");
        ImageIcon icon3 = createImageIcon("icons/save.png");
        ImageIcon icon4 = createImageIcon("icons/autor.png");

        menuItem = new JMenuItem("Nowy...", icon1);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, InputEvent.ALT_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Otworz", icon2);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_2, InputEvent.ALT_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Zapisz", icon3);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_3, InputEvent.ALT_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("Autor", icon4);
        menuItem.addActionListener(this);
        menu.add(menuItem);

        return menuBar;
    }

    public Container createContentPane() {
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setOpaque(true);

        output = new JTextArea(5, 30);
        output.setEditable(true);
        scrollPane = new JScrollPane(output);

        contentPane.add(scrollPane, BorderLayout.CENTER);

        return contentPane;
    }



    public void actionPerformed(ActionEvent e) {



        JMenuItem source = (JMenuItem)(e.getSource());


        if (source.getText().equals("Nowy...")) {
            if (output.getLineCount() >= 1) {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog (null, "Chcesz utworzyc nowy plik?","Uwaga!",dialogButton);
                if(dialogResult == JOptionPane.YES_OPTION){
                    output.selectAll();
                    output.replaceSelection(null);
                }
            }
        }



        if (source.getText().equals("Otworz")) {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Chcesz otworzyc plik?", "Uwaga!", dialogButton);
            if (dialogResult == JOptionPane.YES_OPTION) {
                fc = new JFileChooser();
                File file;
                fc.addChoosableFileFilter(new ImageFilter());
                fc.setAcceptAllFileFilterUsed(false);
                fc.showDialog(Screen.this, "Open");
                file = fc.getSelectedFile();
                    Path fileName = Path.of(String.valueOf(file));
                    String actual = null;
                    if (file != null) {
                        try {
                            actual = Files.readString(fileName);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }


                        assert actual != null;
                        StringBuilder actual2 = new StringBuilder(actual);
                        for (int i = 0; i < actual.length(); i++) {
                            actual2.setCharAt(i, (char) (actual.charAt(i) - 5));
                        }


                        output.selectAll();
                        output.replaceSelection(String.valueOf(actual2));
                    }
                }
            }



        if (source.getText().equals("Zapisz")) {
            fc = new JFileChooser();
            fc.addChoosableFileFilter(new ImageFilter());
            fc.setAcceptAllFileFilterUsed(false);
            fc.showSaveDialog(Screen.this);
            File file = fc.getSelectedFile();


            Path fileName = Path.of(String.valueOf(file));
            String fileName2 = String.valueOf(file);
            String content = output.getText();


            StringBuilder content2 = new StringBuilder(content);
            for (int i = 0; i < content.length(); i++) {
                content2.setCharAt(i, (char) (content.charAt(i) + 5));
            }

            String extension = "";

            int i = fileName2.lastIndexOf('.');
            if (i >= 0) {
                extension = fileName2.substring(i + 1);
            }

            System.out.println(extension);

            if (extension.equals("nmz")) {

                try {
                    Files.writeString(Path.of(String.valueOf(fileName)), String.valueOf(content2));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }else{
                try {
                    Files.writeString(Path.of(fileName + ".nmz"), String.valueOf(content2));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }





        if (source.getText().equals("Autor")) {
            JOptionPane.showMessageDialog(Screen.this, "Maksymilian Zeman", "Autor",
                    JOptionPane.INFORMATION_MESSAGE);
        }

    }


    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Screen.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Notatnik");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Screen demo = new Screen();
        frame.setJMenuBar(demo.createMenuBar());
        frame.setContentPane(demo.createContentPane());



        frame.setSize(1280, 720);
        frame.setIconImage(new ImageIcon("src/components/icons/icon.png").getImage());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(Screen::createAndShowGUI);
    }


}