package components;

import java.io.File;
import javax.swing.filechooser.*;
public class ImageFilter extends FileFilter {
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = Utils.getExtension(f);
        if (extension != null) {
            return extension.equals(Utils.nmz);
        }

        return false;
    }


    public String getDescription() {
        return "Pliki notatnika .nmz";
    }
}