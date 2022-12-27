//swing and action listener libraries

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainScreen implements ActionListener {
    //creating frame window
    JFrame frame = new JFrame();
    //getting window object to add UI using frame object
    Container window = frame.getContentPane();
    //creating textArea and scroll pane to maki it scrollable
    JTextArea textArea = new JTextArea(10, 15);
    JScrollPane scrollPane;

    //declaring or creating required text fields or buttons
    JTextField service_val = new JTextField("service name");
    JTextField link_val = new JTextField("service URL");
    JTextField username_val = new JTextField("username");
    JTextField password_val = new JTextField("password");
    JButton add_service = new JButton("Add service");
    JButton edit_details = new JButton("Edit Details");
    JButton delete_details = new JButton("Delete Details");
    JButton generate_pass = new JButton("generate new password");
    JButton generate_theme = new JButton("Change " +
            "background theme with random color");
    JButton logout = new JButton("logout");
    JButton exit = new JButton("Exit");

    void loadMainScreen() {
        //setting main window dimensions
        frame.setBounds(100, 50, 1000, 660);


        JLabel logo = new JLabel("Main Screen");
        logo.setBounds(600, 30, 200, 30);
        window.add(logo);

        //adding scroll pane to screen and inside it adding Text area
        scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(20, 80, 400, 500);
        window.add(scrollPane);

        //create buffer to read file and show data in textArea
        BufferedReader reader;
        StringBuilder full_file_data = new StringBuilder();

        try {
            reader = new BufferedReader(new FileReader("services.txt"));
            String line = reader.readLine();

            //iterate and read data append each line to a variable full_file_data
            while (line != null) {
                full_file_data.append(line).append("\n");
                // read next line
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException readException) {
            JOptionPane.showMessageDialog(window, "Error while reading");
        }

        //make textArea un editable through frontend because data will come from file

        textArea.setEditable(false);
        textArea.setText(full_file_data.toString());

        //setting positions of UI on screen
        service_val.setBounds(600, 100, 200, 30);
        window.add(service_val);

        link_val.setBounds(600, 160, 200, 30);
        window.add(link_val);

        username_val.setBounds(600, 220, 200, 30);
        window.add(username_val);

        password_val.setBounds(600, 280, 200, 30);
        window.add(password_val);

        //adding buttons to the screen

        add_service.setBounds(520, 400, 130, 30);
        //adding action listener to button
        add_service.addActionListener(this);
        window.add(add_service);

        edit_details.setBounds(720, 400, 130, 30);
        //adding action listener to button
        edit_details.addActionListener(this);
        window.add(edit_details);

        delete_details.setBounds(520, 480, 130, 30);
        //adding action listener to button
        delete_details.addActionListener(this);
        window.add(delete_details);

        generate_pass.setBounds(700, 480, 200, 30);
        //adding action listener to button
        generate_pass.addActionListener(this);
        window.add(generate_pass);

        logout.setBounds(520, 530, 80, 20);
        //adding action listener to button
        logout.addActionListener(this);
        window.add(logout);

        exit.setBounds(720, 530, 80, 20);
        //adding action listener to button
        exit.addActionListener(this);
        window.add(exit);

        generate_theme.setBounds(500, 580, 300, 20);
        //adding action listener to button
        generate_theme.addActionListener(this);
        window.add(generate_theme);

        window.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String service = "", link = "", username = "", password = "";

        if (e.getSource() == add_service) {
            //get values from textFields trim the spaces and make them lowercase to improve accuracy
            service = service_val.getText().trim().toLowerCase();
            link = link_val.getText().trim();
            username = username_val.getText().trim().toLowerCase();
            password = password_val.getText().trim().toLowerCase();

            //check the password length
            if (password.length() != 8) {
                JOptionPane.showMessageDialog(window, "Password length should be 8");

            } else {
                //write to the file through FileWriter class object
                try {
                    FileWriter myWriter = new FileWriter("services.txt", true);
                    myWriter.write(service + " " + link + " " + username + " " + password + "\n");
                    myWriter.close();
                    JOptionPane.showMessageDialog(window, "service added success!");

                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(window, "Error while writing data");

                }

                //read data from file through Buffer Reader
                BufferedReader reader;
                StringBuilder full_file_data = new StringBuilder();

                try {
                    reader = new BufferedReader(new FileReader("services.txt"));
                    String line = reader.readLine();

                    //iterate file and append each line to a string
                    while (line != null) {
                        full_file_data.append(line).append("\n");
                        // read next line
                        line = reader.readLine();
                    }

                    reader.close();
                } catch (IOException readException) {
                    JOptionPane.showMessageDialog(window, "Error while reading");
                }

                //set text to the TextArea
                textArea.setEditable(false);
                //convert appended lines to string and add to textArea
                textArea.setText(full_file_data.toString());
            }
        } else if (e.getSource() == generate_pass) {
            //create built in random module
            Random random = new Random();

            //create arrays of characters to generate password
            char[] abc = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                    'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v'};
            char[] spec_char = {'@', '*', '%', '$', '#', '!'};
            char[] num = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
            char[] ABC = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                    'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

            //String builder builds a string by appending characters to it
            StringBuilder gen_password = new StringBuilder();

            //choose random index values from all the arrays
            for (int i = 0; i < 2; i++) {
                //appending to the original password string
                gen_password.append(abc[random.nextInt(abc.length - 1)]);
                gen_password.append(spec_char[random.nextInt(spec_char.length - 1)]);
                gen_password.append(num[random.nextInt(num.length - 1)]);
                gen_password.append(ABC[random.nextInt(ABC.length - 1)]);
            }

            JOptionPane.showMessageDialog(window, "You can use this new system " +
                    "generated password" + gen_password.toString());
        } else if (e.getSource() == delete_details) {
            //get data, trim it and make lowercase to improve accuracy
            service = service_val.getText().trim().toLowerCase();
            link = link_val.getText().trim();
            username = username_val.getText().trim().toLowerCase();
            password = password_val.getText().toLowerCase();
            List<String> lineList = new ArrayList<String>();

            //read file using buffered reader
            BufferedReader reader;

            try {
                reader = new BufferedReader(new FileReader("services.txt"));
                String line = reader.readLine();

                //iterating over file data
                while (line != null) {
                    //split each line on basis of spaces and return a array
                    String[] splitted_line = line.split(" ");
                    if (line.length() > 0)
                        //skipping the line to be deleted or updated
                        if (!splitted_line[0].equals(service) && !splitted_line[2].equals(username) && !splitted_line[3].equals(password)) {
                            lineList.add(line);
                        }
                    // read next line
                    line = reader.readLine();
                }


                reader.close();
            } catch (IOException readException) {
                JOptionPane.showMessageDialog(window, "Error while reading");
            }

            //delete the file to delete data

            File file = new File("services.txt");

            if (file.delete()) {
                try {
                    //re-write the data to the newly created file
                    FileWriter myWriter = new FileWriter("services.txt", true);
                    for (int i = 0; i < lineList.size(); i++) {
                        myWriter.write(lineList.get(i) + "\n");
                    }
                    myWriter.close();
                    JOptionPane.showMessageDialog(window, "service deleted success");

                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(window, "Error while writing data");

                }

                BufferedReader reader2;
                StringBuilder full_file_data = new StringBuilder();
          //again read data and append into a string
                try {
                    reader2 = new BufferedReader(new FileReader("services.txt"));
                    String line = reader2.readLine();

                    while (line != null) {
                        full_file_data.append(line).append("\n");
                        // read next line
                        line = reader2.readLine();
                    }

                    reader2.close();
                } catch (IOException readException) {
                    JOptionPane.showMessageDialog(window, "Error while reading");
                }

                //set data by getting it from new file now
                textArea.setEditable(false);
                textArea.setText(full_file_data.toString());
            }

            //this operation is same as delete operation
            //in delete operation we do not write the line that needs to be deleted
            //here we add new line after deleting old one hence updating it
        } else if (e.getSource() == edit_details) {

            //getting data to add new line rest of the thing is same as delete option
            service = service_val.getText().trim().toLowerCase();
            link = link_val.getText().trim();
            username = username_val.getText().trim().toLowerCase();
            password = password_val.getText().toLowerCase();
            List<String> lineList = new ArrayList<String>();
            BufferedReader reader;

            try {
                reader = new BufferedReader(new FileReader("services.txt"));
                String line = reader.readLine();

                while (line != null) {
                    String[] splitted_line = line.split(" ");

                    if (line.length() > 0)
                        if (!splitted_line[0].equals(service) && !splitted_line[2].equals(username) && !splitted_line[3].equals(password)) {
                            lineList.add(line);
                        } else {
                            JOptionPane.showMessageDialog(window, "user verified now you can add new details " +
                                    "for this enrty, close the dialogue box and start entering details.");

                        }
                    // read next line
                    line = reader.readLine();
                }


                reader.close();
            } catch (IOException readException) {
                JOptionPane.showMessageDialog(window, "Error while reading");
            }

            File file = new File("services.txt");

            if (file.delete()) {
                try {
                    FileWriter myWriter = new FileWriter("services.txt", true);
                    for (int i = 0; i < lineList.size(); i++) {
                        myWriter.write(lineList.get(i) + "\n");
                    }
                    myWriter.close();
                    //JOptionPane.showMessageDialog(window, "service updated success");

                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(window, "Error while writing data");

                }

                BufferedReader reader2;
                StringBuilder full_file_data = new StringBuilder();

                try {
                    reader2 = new BufferedReader(new FileReader("services.txt"));
                    String line = reader2.readLine();

                    while (line != null) {
                        full_file_data.append(line).append("\n");
                        // read next line
                        line = reader2.readLine();
                    }

                    reader2.close();
                } catch (IOException readException) {
                    JOptionPane.showMessageDialog(window, "Error while reading");
                }

                textArea.setEditable(false);
                textArea.setText(full_file_data.toString());
            }
        } else if (e.getSource() == exit) {
            //exiting by destroying the window
            frame.dispose();
        } else if (e.getSource() == logout) {
            //bringing to login oage
            frame.dispose();
            Login login = new Login();
            login.masterScreen();
        } else if (e.getSource() == generate_theme) {
            //create array of colors
            Color[] theme_colors = {Color.BLACK, Color.RED, Color.GREEN, Color.ORANGE, Color.blue, Color.white,};

            //choose random color and set it as background
            Random color_index = new Random();
            window.setBackground(theme_colors[color_index.nextInt(theme_colors.length - 1)]);
        }
    }
}
