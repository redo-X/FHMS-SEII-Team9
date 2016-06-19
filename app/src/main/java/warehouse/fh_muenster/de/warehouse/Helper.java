package warehouse.fh_muenster.de.warehouse;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Thomas on 13.06.2016.
 */
public class Helper {

    /**
     * Zeigt einen Toast an
     * @param text Anzuzeigender Text
     * @param context
     */
    public static void showToast(String text, Context context){
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
