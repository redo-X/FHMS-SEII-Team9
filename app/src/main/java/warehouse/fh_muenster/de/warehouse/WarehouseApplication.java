package warehouse.fh_muenster.de.warehouse;

import android.app.Application;
/**
 * Created by Marco on 10.05.16.
 */
public class WarehouseApplication extends Application {
    private Employee employee;

    public Employee getEmployee(){
        return this.employee;
    }

    public void setEmployee(Employee employee){
        this.employee = employee;
    }
}
