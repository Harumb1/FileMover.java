import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class MyFrame extends JFrame {
    public MyFrame() {
        SwingUtilities.invokeLater(() -> {

            // Set up the frame
            setTitle("Movable!");
            setSize(400, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(null);

            JFileChooser from = new JFileChooser();
            JFileChooser to = new JFileChooser();
            from.setFileSelectionMode(JFileChooser.FILES_ONLY);
            from.setMultiSelectionEnabled(true);
            to.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            // Create text fields
            JTextField moveFrom = new JTextField(20);
            JTextField moveTo = new JTextField(20);

            // Create buttons
            JButton frombutton = new JButton("From");
            JButton tobutton = new JButton("To");
            JButton move = new JButton("Move");

            moveFrom.setBounds(70, 100, 190, 30);
            frombutton.setBounds(270, 100, 70, 30);
            moveTo.setBounds(70, 200, 190, 30);
            tobutton.setBounds(270, 200, 70, 30);
            move.setBounds(150, 270, 70, 30);

            // Add action listeners

            frombutton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int response = from.showOpenDialog(null);
                    if (response == JFileChooser.APPROVE_OPTION) {
                        File[] files = from.getSelectedFiles();
                        String[] filenames = new String[files.length];
                        for (int i = 0; i < files.length; i++) {
                            //filenames[0] = "a + b";
                            filenames[i] = files[i].getAbsolutePath();
                        }
                        String mftext = String.join("?", filenames);
                        moveFrom.setText(mftext);

                    }
                }
            });

            tobutton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int response = to.showSaveDialog(null);
                    if (response == JFileChooser.APPROVE_OPTION) {
                        File file = new File(to.getSelectedFile().getAbsolutePath());
                        moveTo.setText(String.valueOf(file));
                    }
                }
            });

            move.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String unSplit = moveFrom.getText();
                    String targetDir = moveTo.getText();
                    String[] filePaths = unSplit.split("\\?");
                    for (int i = 0; i < filePaths.length; i++) {
                        File f = new File(filePaths[i]);
                        String name = f.getName();

                        Path newFilePath = Paths.get(targetDir, name);
                        if (!Files.exists(newFilePath)) {
                            System.out.println("File moved successfully!");
                            try {
                                Files.move(f.getAbsoluteFile().toPath(), newFilePath, StandardCopyOption.REPLACE_EXISTING);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        } else {
                            System.out.println("This file already exists in this directory!");
                        }

                    }
                }
            });

            // Add components to the frame
            add(moveFrom);
            add(frombutton);
            add(moveTo);
            add(tobutton);
            add(move);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MyFrame frame = new MyFrame();
            frame.setVisible(true);
        });
    }
}