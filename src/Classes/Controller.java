package Classes;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lute_
 */

public class Controller {
    
    // Gets the current date and time of the system and formats it to hours, minutes and seconds respectively.
    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
    
    public void set(String url, String time) {
        // When the alarm time is the same as the system time
        if (time.equals(sdf.format(d))) {
            // Checks the OS before opening the browser for code compatibilities.
            String os = System.getProperty("os.name").toLowerCase();
            // For Windows
            if (os.indexOf("win") >= 0) {
                
                try {
                    Runtime rt = Runtime.getRuntime();
                    rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
                } catch (IOException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // For Mac
            else if (os.indexOf("mac") >= 0) {
                try {
                    Runtime rt = Runtime.getRuntime();
                    rt.exec("open " + url);
                } catch (IOException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // For Linux
            else if (os.indexOf("nix") >=0 || os.indexOf("nux") >=0) {
                try {
                    Runtime rt = Runtime.getRuntime();
                    String[] browsers = { "epiphany", "firefox", "mozilla", "konqueror",
                        "netscape", "opera", "links", "lynx" };
                    
                    StringBuffer cmd = new StringBuffer();
                    for (int i = 0; i < browsers.length; i++)
                        if(i == 0)
                            cmd.append(String.format(    "%s \"%s\"", browsers[i], url));
                        else
                            cmd.append(String.format(" || %s \"%s\"", browsers[i], url));
                    // If the first didn't work, try the next browser and so on
                    
                    rt.exec(new String[] { "sh", "-c", cmd.toString() });
                } catch (IOException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
