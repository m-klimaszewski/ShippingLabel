import ij.ImagePlus;
import ij.io.Opener;
import ij.plugin.filter.GaussianBlur;

import java.awt.image.SampleModel;


public class Scanner_Lib {

    public static void showImg(String path) {
        Opener imagejOpener = new Opener();
        ImagePlus img = imagejOpener.openImage(path);
        img.setTitle("Etykieta nr." + img.getID());
        var ip =img.getProcessor();
        var gs = new GaussianBlur();
        gs.blurGaussian(ip,20,20,0.01);
        img.show();

    }
}
