//swing and action listener libraries

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Signup implements ActionListener {
    //creating frame window
    JFrame signup_frame = new JFrame();
    //getting window object to add UI using frame object
    Container signup_window = signup_frame.getContentPane();
    //text fields and button declaring from swing UI
    JTextField username_val = new JTextField();
    JTextField pin_val = new JTextField();

    JTextField usrlock_val = new JTextField();
    JButton signup_sbmt = new JButton("Signup");
    JButton locate_login = new JButton("Already have account? login");

    void signupScreen() {

        //setting dimensions
        signup_frame.setBounds(100, 50, 600, 300);

        //setting welcome text
        JLabel logo = new JLabel("Signup here!");
        logo.setBounds(300, 10, 150, 20);
        //adding on screen
        signup_window.add(logo);

        JLabel username = new JLabel("Username/email: ");
        username.setBounds(100, 50, 250, 20);
        signup_window.add(username);


        //setting positions of UI on screen
        username_val.setBounds(240, 50, 150, 20);
        signup_window.add(username_val);


        JLabel usrlock = new JLabel("Enter lock: ");
        usrlock.setBounds(100, 90, 250, 20);
        signup_window.add(usrlock);


        usrlock_val.setBounds(240, 90, 150, 20);
        signup_window.add(usrlock_val);

        JLabel pin = new JLabel("Enter pin: ");
        pin.setBounds(100, 130, 250, 20);
        signup_window.add(pin);


        pin_val.setBounds(240, 130, 150, 20);
        signup_window.add(pin_val);

        signup_sbmt.setBounds(200, 200, 90, 25);
        signup_window.add(signup_sbmt);
        //applying click event action listener
        signup_sbmt.addActionListener(this);

        locate_login.setBounds(300, 200, 220, 25);
        signup_window.add(locate_login);
        //applying click event action listener
        locate_login.addActionListener(this);

        //setting layout to null and making UI to Visible =  true
        signup_window.setLayout(null);
        signup_frame.setVisible(true);
        signup_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //here e is the reference of button clicked
        if (e.getSource() == signup_sbmt) {
            //getting value of text fields
            String username = username_val.getText().trim().toLowerCase();
            String password = usrlock_val.getText().trim();
            String pin = pin_val.getText().trim();
            boolean user_found = false;
//check on password
            if (password.length() != 8 || pin.length() != 4) {
                JOptionPane.showMessageDialog(signup_frame, "Password should have length equal to 8 and pin should have 4");

            } else {
                //create buffer and read file
                BufferedReader reader;

                try {
                    reader = new BufferedReader(new FileReader("data.txt"));
                    String line = reader.readLine();

                    //iterate through file if user exists fail signup
                    while (line != null) {
                        if (line.substring(14).equals(username)) {
                            JOptionPane.showMessageDialog(signup_frame, "User already exists");
                            user_found = true;
                            break;
                        }
                        // read next line
                        line = reader.readLine();
                    }

                    reader.close();
                } catch (IOException readException) {
                    JOptionPane.showMessageDialog(signup_frame, "Error while reading");
                }
                // if user do not exists pass signup
                if (!user_found) {
                    try {
                        FileWriter myWriter = new FileWriter("data.txt", true);
                        myWriter.write("\n" + password + " " + pin + " " + username);
                        myWriter.close();
                        JOptionPane.showMessageDialog(signup_frame, "user added success!");
                        signup_frame.dispose();
                        Login login_screen = new Login();
                        login_screen.masterScreen();

                    } catch (IOException exception) {
                        JOptionPane.showMessageDialog(signup_frame, "Error while writing data");

                    }
                }

            }
        } else if (e.getSource() == locate_login) {
            //destroying signup frame loading login frame
            signup_frame.dispose();
            Login login = new Login();
            login.masterScreen();
        }

    }
}
