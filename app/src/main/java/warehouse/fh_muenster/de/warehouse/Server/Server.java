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
import warehouse.fh_muenster.de.warehouse.StorageLocation;

/**
 * Created by Thomas on 08.06.2016.
 */
public class Server implements ServerInterface {
    /**
     * Namespace is the targetNamespace in the WSDL.
     */
    private static final String NAMESPACE = "http://integration.warehouse.de/";

    /**
     * The WSDL URL.
     */
    private static final String SESSION_URL = "http://10.70.28.97:8080/WarehouseService-ejb-1.0.0/SessionManagementIntegration";
    private static final String COMMISSION_URL = "http://10.70.28.97:8080/WarehouseService-ejb-1.0.0/CommissionServiceIntegration";
    private static final String ARTICLE_URL = "http://10.70.28.97:8080/WarehouseService-ejb-1.0.0/ArticleManagementIntegration";


    @Override
    public Employee login(int employeeNr, String password) {
        Employee result = null;
        String METHOD_NAME = "login";
        SoapObject response = null;
        try {
            LoginRequest request = new LoginRequest();
            request.setEmployeeNr(employeeNr);
            request.setPassword(password);

           response = executeSoapAction(SESSION_URL,METHOD_NAME, employeeNr, password);

            if (response != null) {
                int sessionId = Integer.parseInt(response.getPrimitivePropertySafelyAsString("sessionId"));
                int role = Integer.parseInt(response.getPrimitivePropertySafelyAsString("role"));
                if (sessionId != 0) {
                    result = new Employee(employeeNr,sessionId);
                    result.setRole(Role.fromInt(role));
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

    @Override
    public void logout(int sessionId){
        String METHOD_NAME = "logout";
        SoapObject response = null;
        try {
            response = executeSoapAction(SESSION_URL,METHOD_NAME, sessionId);
        }
        catch (SoapFault e) {

        }
    }

    @Override
    public HashMap<Integer, Article> getPositionToCommission(int id) {
        HashMap<Integer, Article> result = new HashMap<>();
        String METHOD_NAME = "getPendingCommissionPositionsByCommissionId";
        try {
            SoapObject response = executeSoapAction(COMMISSION_URL, METHOD_NAME, id);
            for (int i=1; i<response.getPropertyCount(); i++) {
                SoapObject soapAccountEntry = (SoapObject) response.getProperty(i);
                SoapPrimitive soapArticleCode = (SoapPrimitive) soapAccountEntry.getProperty("articleCode");
                SoapPrimitive soapArticleName = (SoapPrimitive) soapAccountEntry.getProperty("articleName");
                SoapPrimitive soapStorageLocation = (SoapPrimitive) soapAccountEntry.getProperty("storageLocation");
                SoapPrimitive soapQuantityToCommit = (SoapPrimitive) soapAccountEntry.getProperty("quantityToCommit");
                SoapPrimitive soapQuantityOnStock = (SoapPrimitive) soapAccountEntry.getProperty("quantityOnStock");
                SoapPrimitive soapCommissionPositionId = (SoapPrimitive) soapAccountEntry.getProperty("commissionPositionId");

                String articleCode = soapArticleCode.getValue().toString();
                String articleName = soapArticleName.getValue().toString();
                String storageLocation = soapStorageLocation.getValue().toString();
                int quantityToCommit = Integer.valueOf(soapQuantityToCommit.getValue().toString());
                int quantityOnStock = Integer.valueOf(soapQuantityOnStock.getValue().toString());
                int commissionPositionId = Integer.valueOf(soapCommissionPositionId.getValue().toString());

                Article article = new Article(articleCode,articleName,quantityOnStock,quantityToCommit, commissionPositionId);
                article.setStorageLocation(new StorageLocation(storageLocation));
                //article.setPositionCommissionId(commissionPositionId);
                result.put(commissionPositionId,article);
            }
        }
        catch (SoapFault e) {
            //throw new NoSessionException(e.getMessage());

        }
        return result;
    }

    @Override
    public void startCommission(int commissionId){
        String METHOD_NAME = "updateStartOfCommission";
        SoapObject response = null;
        try {
            response = executeSoapAction(COMMISSION_URL,METHOD_NAME, commissionId);
        }
        catch (SoapFault e) {

        }
    }

    @Override
    public void endCommission(int commissionId){
        String METHOD_NAME = "updateFinishOfCommission";
        SoapObject response = null;
        try {
            response = executeSoapAction(COMMISSION_URL,METHOD_NAME, commissionId);
        }
        catch (SoapFault e) {

        }
    }

    @Override
    public void updateQuantityOnCommissionPosition(int commissionPositionId, int quantity){
        String METHOD_NAME = "updatePickedQuantity";
        SoapObject response = null;
        try {
            response = executeSoapAction(COMMISSION_URL,METHOD_NAME, commissionPositionId, quantity);
        }
        catch (SoapFault e) {

        }
    }

    @Override
    public void commitCommissionMessage(int sessionId, int commissionPositionId, int differenceQuantity, String note){
        String METHOD_NAME = "commitCommissionMessage";
        SoapObject response = null;
        try {
            response = executeSoapAction(COMMISSION_URL,METHOD_NAME, sessionId, commissionPositionId, differenceQuantity, note);
        }
        catch (SoapFault e) {

        }
    }

    @Override
    public void allocateCommission(int commissionId, int employeeId){
        String METHOD_NAME = "allocateCommission";
        SoapObject response = null;
        try {
            response = executeSoapAction(COMMISSION_URL,METHOD_NAME, commissionId, employeeId);
        }
        catch (SoapFault e) {

        }
    }

    @Override
    public void commitStock(String artikelCode, int menge){
        String METHOD_NAME = "updateQuantityOnStockOfArticle";
        SoapObject response = null;
        try {
            response = executeSoapAction(ARTICLE_URL,METHOD_NAME, artikelCode, menge);
        }
        catch (SoapFault e) {

        }
    }

    @Override
    public HashMap<Integer, Commission> getFreeCommissions() {
        HashMap<Integer, Commission> result = new HashMap<>();
        String METHOD_NAME = "getPendingCommissionsWithoutPicker";
        try {
            SoapObject response = executeSoapAction(COMMISSION_URL,METHOD_NAME);
            for (int i=1; i<response.getPropertyCount(); i++) {
                SoapObject soapAccountEntry = (SoapObject) response.getProperty(i);
                SoapPrimitive soapCommissionId = (SoapPrimitive) soapAccountEntry.getProperty("commissionId");
                SoapPrimitive soapPositionCount = (SoapPrimitive) soapAccountEntry.getProperty("positionCount");

                int id = Integer.valueOf(soapCommissionId.getValue().toString());
                int count = Integer.valueOf(soapPositionCount.getValue().toString());
                Commission commission = new Commission(id, count);
                result.put(commission.getId(), commission);
            }
            //return result;
        }
        catch (SoapFault e) {
            //throw new NoSessionException(e.getMessage());
            //Log.i("SoapMessage:", e.getMessage());
            //Log.i("SoapMessage:", e.getStackTrace().toString());

        }
        return result;
    }

    @Override
    public HashMap<Integer, Commission> getCommissions(Employee picker) {
        HashMap<Integer, Commission> result = new HashMap<>();
        String METHOD_NAME = "getPendingCommissionsByPickerId";
        try {
            SoapObject response = executeSoapAction(COMMISSION_URL,METHOD_NAME, picker.getEmployeeNr());
            for (int i=1; i<response.getPropertyCount(); i++) {
                SoapObject soapAccountEntry = (SoapObject) response.getProperty(i);
                SoapPrimitive soapCommissionId = (SoapPrimitive) soapAccountEntry.getProperty("commissionId");
                SoapPrimitive soapPositionCount = (SoapPrimitive) soapAccountEntry.getProperty("positionCount");

                int id = Integer.valueOf(soapCommissionId.getValue().toString());
                int count = Integer.valueOf(soapPositionCount.getValue().toString());
                Commission commission = new Commission(id, count);
                result.put(commission.getId(), commission);
            }
            //return result;
        }
        catch (SoapFault e) {
            //throw new NoSessionException(e.getMessage());
            //Log.i("SoapMessage:", e.getMessage());
            //Log.i("SoapMessage:", e.getStackTrace().toString());

        }
        return result;
    }

    @Override
    public HashMap<String, Article> getArticles(int sessionId) {
        HashMap<String, Article> result = new HashMap<>();
        String METHOD_NAME = "getArticles";
        try {
            SoapObject response = executeSoapAction(ARTICLE_URL,METHOD_NAME, sessionId);
            for (int i=1; i<response.getPropertyCount(); i++) {
                SoapObject soapAccountEntry = (SoapObject) response.getProperty(i);
                SoapPrimitive soapCode = (SoapPrimitive) soapAccountEntry.getProperty("code");
                SoapPrimitive soapName = (SoapPrimitive) soapAccountEntry.getProperty("name");
                SoapPrimitive soapQuantityOnStock = (SoapPrimitive) soapAccountEntry.getProperty("quantityOnStock");
                SoapPrimitive soapStorageLocation = (SoapPrimitive) soapAccountEntry.getProperty("storageLocation");

                String code = soapCode.getValue().toString();
                String name = soapName.getValue().toString();
                int quantityOnStock = Integer.valueOf(soapQuantityOnStock.getValue().toString());
                String storageLocation = soapStorageLocation.getValue().toString();
                Article article = new Article(code, name);
                article.setQuantityOnStock(quantityOnStock);
                article.setStorageLocation(new StorageLocation(storageLocation));

                result.put(code, article);
            }
            //return result;
        }
        catch (SoapFault e) {
            //throw new NoSessionException(e.getMessage());
            //Log.i("SoapMessage:", e.getMessage());
            //Log.i("SoapMessage:", e.getStackTrace().toString());

        }
        return result;
    }


    private SoapObject executeSoapAction(String url, String methodName, Object... args) throws SoapFault {

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
        HttpTransportSE androidHttpTransport = new HttpTransportSE(url);

        try {
	        /* Make the soap call using the SOAP_ACTION and the soap envelop. */
            List<HeaderProperty> reqHeaders = null;


            List resp = androidHttpTransport.call("", envelope, reqHeaders);

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
