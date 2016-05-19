package warehouse.fh_muenster.de.warehouse;

import android.app.Application;
import android.util.Log;

/**
 * Created by Marco on 10.05.16.
 */
public class WarehouseApplication extends Application {
    private Employee employee;
    private Commission openCommissions[];
    private Commission pickerCommissions[];

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

    public Commission getPickerCommissionById(int id){

        for(int i = 0; i< pickerCommissions.length; i++){
            if(id == pickerCommissions[i].getId()){
                return pickerCommissions[i];
            }
        }
        return null;
    }
}
