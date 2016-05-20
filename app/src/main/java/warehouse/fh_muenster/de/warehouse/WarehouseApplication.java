package warehouse.fh_muenster.de.warehouse;

import android.app.Application;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Marco on 10.05.16.
 */
public class WarehouseApplication extends Application {
    private Employee employee;
    private Commission openCommissions[];
    private Commission pickerCommissions[];

    private HashMap<Integer,Commission> openCommissionsMap;
    private HashMap<Integer,Commission> pickerCommissionsMap;

    public Employee getEmployee(){
        return this.employee;
    }

    public void setEmployee(Employee employee){
        this.employee = employee;
    }

    public Commission[] getOpenCommissions() {
        return openCommissions;
    }

    public void setOpenCommissions(Commission[] openCommissions) {
        this.openCommissions = openCommissions;
    }

    public Commission[] getPickerCommissions() {
        return pickerCommissions;
    }

    public void setPickerCommissions(Commission[] pickerCommissions) {
        this.pickerCommissions = pickerCommissions;
    }

    public HashMap<Integer, Commission> getOpenCommissionsMap() {
        return openCommissionsMap;
    }

    public void setOpenCommissionsMap(HashMap<Integer, Commission> openCommissionsMap) {
        this.openCommissionsMap = openCommissionsMap;
    }

    public HashMap<Integer, Commission> getPickerCommissionsMap() {
        return pickerCommissionsMap;
    }

    public void setPickerCommissionsMap(HashMap<Integer, Commission> pickerCommissionsMap) {
        this.pickerCommissionsMap = pickerCommissionsMap;
    }
    /*
    public Commission getPickerCommissionById(int id){

        for(int i = 0; i< pickerCommissions.length; i++){
            if(id == pickerCommissions[i].getId()){
                return pickerCommissions[i];
            }
        }
        return null;
    }
*/
    public Commission getPickerCommissionById(int id){

        if(pickerCommissionsMap.containsKey(id)){
            return pickerCommissionsMap.get(id);
        }
        else{
            return null;
        }
    }

}
