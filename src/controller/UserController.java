package controller;

import dao.UserDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.UserModel;
import view.Signup;
import view.Login;

public class UserController {
    private final UserDao userdao = new UserDao();   //we wrote this because jasma model set gareko cha so teslai import garnu paryo so that's why. In this line code move your cursor to the UserDao and click Ctrl and left click mouse to see if it immediately navigates to UserDao file where UserDao is! If it navigates then u are on the right path.
    private final Signup userView;                   //class name is Signup so i'm writing S captial here. (final keyword leh hamileh ekchoti value assign garepachi change garna mildaina. Yesma hamro privacy ko kura cha so we wrote private final. In this line code move your cursor to the Signup and click Ctrl and left click mouse to see if it immediately navigates to Signup file where AddUserListener is! If it navigates then u are on the right path.

    public UserController(Signup userView) {                     // constructor banauneh code line ho yo using file name UserController.  Note: We are writing Signup userView inside parenthesis because we are giving the controller access to the Signup form.
        this.userView = userView;
        
        userView.AddUserListener(new SignUpListener());
        userView.LoginButtonListener(new LoginListener());// In this line code move your cursor to the AddUserListener and click Ctrl and left click mouse to see if it immediately navigates to Signup file where AddUserListener is! If it navigates then u are on the right path. Note: We click this to verify (milirako cha ki chaina bhanerah we check).
    }

    public void open(){          // this is open method ahd it opens this.userView 
        this.userView.setVisible(true);   // this setVisible leh screen lai visible banaidincha hai! Yo setVisible leh multliple times open garaidincha
    }
    
    public void close(){           // this is close method and it closes the mothod that was open. 
        this.userView.dispose();   // yo dispose bhanneh method leh screen close garaidincha. Also yesleh multliple times close garaidincha, Yo dispose method inbuilt ho.
    }

    class ImageFieldClickHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(userView);
            
            if (result == JFileChooser.APPROVE_OPTION){
                File file = chooser.getSelectedFile();
                            //save in controller
                                                                     // show in text field
            }
        }
    }

    class LoginListener implements ActionListener {             // we removed private static because? search
        @Override
        public void actionPerformed(ActionEvent e){
            Login log = new Login();
            LoginController lc = new LoginController(log);
            lc.open();
        }
    }

    
    class SignUpListener implements ActionListener {            // we removed
        @Override                                                       // we are implementing something below so for that we have to write @Override.
        public void actionPerformed(ActionEvent e){             // import add garneh (actionPerform gara bhaneko cha . SignupListener lai thichyo bhaneh k chai actionPerform garcha tesko bhitra chai lekhnu paryo. (Aba yo public void actionPerformed bhitra lekhnu paryo)
            try {                                                
                String username = userView.getUsernameField().getText();   // ahileh maileh kah ko use garirako ho ? --> signup ko kata banako yo--> UserModel bhitra(constructor haina yo constructor bhitra) (main method ma lekhthiyo ni ho testai garera liyehko ho)
                String email = userView.getEmailField().getText();         // Note: in this case on controller we only use gettext not int even though int. Only in dao we write setString setInt etc.
                String password = userView.getPasswordField().getText();   // yeta j password ko field rakheko cha 
                UserModel usermodel = new UserModel(username, email, password);         //Note only through file name we can click Ctrl and click it to see if it is working or not (Verify)
                boolean check = userdao.check(usermodel);   //yesleh check method call garcha UserDao ma bhako. Jaba yo method call huncha kunchai method call huneh bhayo?--> check method in UserDao file!.
                if (check) {
                    JOptionPane.showMessageDialog(userView, "Duplicated user");  // yesleh same username cha bhanneh duplicated user message dekhauneh bhayo screen ma. 
                }else{
                    userdao.signUp(usermodel); 
                    JOptionPane.showMessageDialog(userView, "Successful");  
                    Login lc = new Login();
                    LoginController log = new LoginController(lc);
                    close();
                    log.open();
                   // yesleh new different username cha bhanneh Successful message dekhauneh bhayo screen ma.
                }
            }catch (Exception ext) {                            
                System.out.println(ext.getMessage());                         
        }
    }         
   }
}
