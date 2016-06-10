package warehouse.fh_muenster.de.warehouse.Server;

import android.util.Log;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
import java.util.List;

import warehouse.fh_muenster.de.warehouse.Article;
import warehouse.fh_muenster.de.warehouse.Commission;
import warehouse.fh_muenster.de.warehouse.Employee;
import warehouse.fh_muenster.de.warehouse.Role;

/**
 * Created by Thomas on 08.06.2016.
 */
public class Server implements ServerMockInterface {
    /**
     * Namespace is the targetNamespace in the WSDL.
     */
    private static final String NAMESPACE = "http://onlinebanking.xbank.de/";

    /**
     * The WSDL URL.
     */
    private static final String URL = "http://10.0.2.2:8080/xbank/XbankOnlineIntegration";


    @Override
    public Employee login(int employeeNr, String password) {
        Employee result = null;
        String METHOD_NAME = "login";
        SoapObject response = null;
        try {
           response = executeSoapAction(METHOD_NAME, employeeNr, password);

            if (response != null) {
                int sessionId = Integer.parseInt(response.getPrimitivePropertySafelyAsString("sessionId"));
                int role = Integer.parseInt(response.getPrimitivePropertySafelyAsString("role"));
                if (sessionId != 0) {
                    result = new Employee(employeeNr,sessionId);
                    if (role == 0) {
                        result.setRole(Role.Kommissionierer);
                    } else if (role == 1) {
                        result.setRole(Role.Lagerist);
                    }
                    return result;
                } else {
                    return null;
                }
            }
        } catch (SoapFault e) {
            return null;
        }
        return null;
    }


    public void logout(int sessionId){
        String METHOD_NAME = "logout";
        SoapObject response = null;
        try {
            response = executeSoapAction(METHOD_NAME, sessionId);
        }
        catch (SoapFault e) {

        }
    }

    @Override
    public HashMap<Integer, Article> getPositionToCommission(int id) {
        HashMap<Integer, Article> result = new HashMap<>();
        String METHOD_NAME = "getPositionToCommission";
        try {
            SoapObject response = executeSoapAction(METHOD_NAME, id);
            for (int i=1; i<response.getPropertyCount(); i++) {
                SoapObject soapAccountEntry = (SoapObject) response.getProperty(i);
                SoapPrimitive soapAccountNr = (SoapPrimitive) soapAccountEntry.getProperty("id");
                SoapPrimitive soapBetrag = (SoapPrimitive) soapAccountEntry.getProperty("balance");
                Article article = new Article();
                result.put(Integer.valueOf(article.getCode()),article);
            }
            return result;
        }
        catch (SoapFault e) {
            //throw new NoSessionException(e.getMessage());
            return null;
        }
    }

    public void updateQuantityOnCommissionPosition(int commissionId, String articleCode, int quantity){
        String METHOD_NAME = "logout";
        SoapObject response = null;
        try {
            response = executeSoapAction(METHOD_NAME, commissionId, articleCode, quantity);
        }
        catch (SoapFault e) {

        }
    }

    @Override
    public HashMap<Integer, Commission> getFreeCommissions() {
        return null;
    }

    @Override
    public HashMap<Integer, Commission> getCommissions(Employee picker) {
        return null;
    }

    private SoapObject executeSoapAction(String methodName, Object... args) throws SoapFault {

        Object result = null;

	    /* Create a org.ksoap2.serialization.SoapObject object to build a SOAP request. Specify the namespace of the SOAP object and method
	     * name to be invoked in the SoapObject constructor.
	     */
        SoapObject request = new SoapObject(NAMESPACE, methodName);

	    /* The array of arguments is copied into properties of the SOAP request using the addProperty method. */
        for (int i=0; i<args.length; i++) {
            request.addProperty("arg" + i, args[i]);
        }
	    /* Next create a SOAP envelop. Use the SoapSerializationEnvelope class, which extends the SoapEnvelop class, with support for SOAP
	     * Serialization format, which represents the structure of a SOAP serialized message. The main advantage of SOAP serialization is portability.
	     * The constant SoapEnvelope.VER11 indicates SOAP Version 1.1, which is default for a JAX-WS webservice endpoint under JBoss.
	     */
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

	    /* Assign the SoapObject request object to the envelop as the outbound message for the SOAP method call. */
        envelope.setOutputSoapObject(request);

	    /* Create a org.ksoap2.transport.HttpTransportSE object that represents a J2SE based HttpTransport layer. HttpTransportSE extends
	     * the org.ksoap2.transport.Transport class, which encapsulates the serialization and deserialization of SOAP messages.
	     */
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
	        /* Make the soap call using the SOAP_ACTION and the soap envelop. */
            List<HeaderProperty> reqHeaders = null;

            @SuppressWarnings({"unused", "unchecked"})
            List<HeaderProperty> respHeaders = androidHttpTransport.call("", envelope, reqHeaders);

	        /* Get the web service response using the getResponse method of the SoapSerializationEnvelope object.
	         * The result has to be cast to SoapPrimitive, the class used to encapsulate primitive types, or to SoapObject.
	         */

            result = envelope.getResponse();

            if (result instanceof SoapFault) {
                throw (SoapFault) result;
            }
        }
        catch (SoapFault e) {
            Log.i("Server: " , e.getMessage());
            e.printStackTrace();
            throw e;
        }
        catch (Exception e) {
            Log.i("Server: " , e.getMessage());
            e.printStackTrace();
        }

        return (SoapObject) result;
    }


}
