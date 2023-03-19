import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

 public class LoginDialog extends JDialog {

        private JTextField usernameField;
        private JPasswordField passwordField;
        private JButton loginButton;
        private boolean loginSuccessful;

        public LoginDialog(Frame owner) {
            super(owner, "Login", true);

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.insets = new Insets(10, 10, 10, 10);
            panel.add(new JLabel("Username:"), constraints);

            constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridx = 1;
            constraints.gridy = 0;
            constraints.weightx = 1.0;
            constraints.insets = new Insets(10, 0, 10, 10);
            usernameField = new JTextField();
            usernameField.setPreferredSize(new Dimension(200, 30));
            panel.add(usernameField, constraints);

            constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.insets = new Insets(10, 10, 10, 10);
            panel.add(new JLabel("Password:"), constraints);

            constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridx = 1;
            constraints.gridy = 1;
            constraints.weightx = 1.0;
            constraints.insets = new Insets(10, 0, 10, 10);
            passwordField = new JPasswordField();
            passwordField.setPreferredSize(new Dimension(200, 30));
            panel.add(passwordField, constraints);

            constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = 2;
            constraints.gridwidth = 2;
            constraints.anchor = GridBagConstraints.CENTER;
            loginButton = new JButton("Login");
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String username = usernameField.getText();
                    char[] password = passwordField.getPassword();
                    if (authenticate(username, password)) {
                        loginSuccessful = true;
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(LoginDialog.this, "Invalid username or password", "Login failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            panel.add(loginButton, constraints);

            getContentPane().add(panel);
            pack();
            setLocationRelativeTo(owner);
        }

        public boolean isLoginSuccessful() {
            return loginSuccessful;
        }

        private boolean authenticate(String username, char[] password) {
            // Replace this with your own authentication logic
            return username.equals("ethiopia") && Arrays.equals(password, new char[]{'m', 'e', 'g', 'e', 'b'});
        }
    }



