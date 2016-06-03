package warehouse.fh_muenster.de.warehouse.Server;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import warehouse.fh_muenster.de.warehouse.Employee;

/**
 * Created by futur on 03.06.2016.
 */
public class WebService {
    //Namespace of the Webservice - can be found in WSDL
    private static String NAMESPACE = "";
    //Webservice URL - WSDL File location
    private static String URL = "";

    public static String LoginRequest(int employeeNr, String password) {

        String methodName = "LoginRequest";
        String soap_action = NAMESPACE + methodName;

        SoapObject request = new SoapObject(NAMESPACE, methodName);

        PropertyInfo piEmployeeNr = new PropertyInfo();
        piEmployeeNr.setName("employeeNr");
        piEmployeeNr.setValue(employeeNr);
        piEmployeeNr.setType(Integer.class);
        request.addProperty(piEmployeeNr);

        PropertyInfo piPassword = new PropertyInfo();
        piPassword.setName("password");
        piPassword.setValue(password);
        piPassword.setType(String.class);
        request.addProperty(piPassword);




        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        Log.i("InternetLog: ", request.toString());
        HttpTransportSE andoridHttpTransport = new HttpTransportSE(URL);
        try {
            andoridHttpTransport.call(soap_action, envelope);
            SoapPrimitive responce = (SoapPrimitive)envelope.getResponse();
            int elementCount = responce.getAttributeCount();
            if(elementCount > 0){
                int role = (Integer) responce.getAttribute(0);
                int sessionId = (Integer) responce.getAttribute(0);
                Log.i("LoginRequest", String.valueOf(role));
            }
        }
        catch (Exception e){
            Log.i("Internet ", "No Connecion");
        }

        return  "";
    }


}
