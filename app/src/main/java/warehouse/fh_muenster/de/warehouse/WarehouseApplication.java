package warehouse.fh_muenster.de.warehouse;

import android.app.Application;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Marco on 10.05.16.
 */
public class WarehouseApplication extends Application {
    private Employee employee;
    private HashMap<Integer, Commission> openCommissionsMap;
    private HashMap<Integer, Commission> pickerCommissionsMap;
    private HashMap<String, Article> articleMap;

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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

    public Commission getPickerCommissionById(int id) {

        if (pickerCommissionsMap.containsKey(id)) {
            return pickerCommissionsMap.get(id);
        } else {
            return null;
        }
    }

    public void addCommissionToPicker(int id) {
        Commission commission = this.openCommissionsMap.get(id);
        this.pickerCommissionsMap.put(id, commission);
    }

    public void removeCommissionFromOpen(int id) {
        Log.i("CommissionId: ", String.valueOf(id));
        this.openCommissionsMap.remove(id);
    }

    public HashMap<String, Article> getArticleMap() {
        return articleMap;
    }

    public void setArticleMap(HashMap<String, Article> articleMap) {
        this.articleMap = articleMap;
    }
}
