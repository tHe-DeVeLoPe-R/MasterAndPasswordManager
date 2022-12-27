//swing and action listener libraries

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Login implements ActionListener {
    //creating object of JFrame class to use window frame methods
    JFrame login_frame = new JFrame();

    //getting access to the screen using Frame object
    Container login_window = login_frame.getContentPane();

    //text fields
    JTextField username_val = new JTextField();
    JTextField pin_val = new JTextField();

    JTextField usrlock_val = new JTextField();

    //buttons
    JButton master_sbmt = new JButton("unlock");
    JButton locate_signup = new JButton("No account? signup");

    void masterScreen() {

        //setting dimensions and position of frame on screen
        login_frame.setBounds(100, 50, 600, 300);

        //setting welcome text
        JLabel logo = new JLabel("Keep Safe!");
        logo.setBounds(300, 10, 150, 20);
        login_window.add(logo);

        //labels for the text fields
        JLabel username = new JLabel("Username/email: ");
        username.setBounds(100, 50, 250, 20);
        login_window.add(username);


        //setting positions of text fields and buttons + adding them to window
        username_val.setBounds(240, 50, 150, 20);
        login_window.add(username_val);


        JLabel pin = new JLabel("Enter pin: ");
        pin.setBounds(100, 90, 250, 20);
        login_window.add(pin);


        pin_val.setBounds(240, 90, 150, 20);
        login_window.add(pin_val);

        JLabel usrlock = new JLabel("Enter lock: ");
        usrlock.setBounds(100, 120, 250, 20);
        login_window.add(usrlock);


        usrlock_val.setBounds(240, 120, 150, 20);
        login_window.add(usrlock_val);


        master_sbmt.setBounds(200, 200, 90, 25);
        login_window.add(master_sbmt);
        //applying on click action listener on the button
        master_sbmt.addActionListener(this);


        locate_signup.setBounds(350, 200, 200, 25);
        login_window.add(locate_signup);
        //applying on click action listener on the button
        locate_signup.addActionListener(this);

        //set layout to manage it by custom
        login_window.setLayout(null);
        //setting the UI to become visible true
        login_frame.setVisible(true);
        //on closing window app should close in background
        login_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //here e is the reference of button clicked
        if (e.getSource() == locate_signup) {
            //destroying current screen and creating object of Signup class
            login_frame.dispose();
            Signup signup_screen = new Signup();
            signup_screen.signupScreen();
        } else if (e.getSource() == master_sbmt) {
            //getting data from text fields
            String username = username_val.getText().trim().toLowerCase();
            String password = usrlock_val.getText().trim();
            String pin = pin_val.getText().trim();
            boolean user_found = false;

            //applying check on password length
            if (password.length() != 8 || pin.length() != 4) {
                JOptionPane.showMessageDialog(login_frame, "Password should have length equal to 8 and pin should have 4");

            } else {
                //reading file to check user by creating a buffer that will read data
                BufferedReader reader;
                try {
                    reader = new BufferedReader(new FileReader("data.txt"));
                    String line = reader.readLine();

                    //iterating through the file
                    while (line != null) {

                        if (line.length() > 0)
                            if (line.substring(0, 8).equals(password) && line.substring(9, 13).equals(pin) && line.substring(14).equals(username)) {

                                user_found = true;
                                break;
                            }
                        // read next line
                        line = reader.readLine();
                    }

                    reader.close();
                } catch (IOException readException) {
                    JOptionPane.showMessageDialog(login_frame, "Error while reading");
                }
                if (user_found) {
                    //destroying current window and initiating Main screen on login
                    login_frame.dispose();
                    MainScreen mainScreen = new MainScreen();
                    mainScreen.loadMainScreen();
                } else {
                    JOptionPane.showMessageDialog(login_frame, "User not found");
                }
            }
        }

    }
}
