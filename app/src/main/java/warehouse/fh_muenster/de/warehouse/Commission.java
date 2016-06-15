package warehouse.fh_muenster.de.warehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Thomas on 19.05.2016.
 */
public class Commission {

    private Employee                    picker;
    private int                         id,
                                        positionCount,
                                        orderDateAsUnixTimestamp,
                                        dueDateAsUnixTimestamp;
    private HashMap<String, Article>   articleHashMap;
    private double                      progress;


    public Commission() {
    }

    public Commission(int id, int positionCount) {
        this.id = id;
        this.positionCount = positionCount;
    }


    public Commission(int id, HashMap<String, Article> articleHashMap, Employee picker) {
        this.id = id;
        this.articleHashMap = articleHashMap;
        this.picker = picker;
    }

    public HashMap<String, Article> getArticleHashMap() {
        return articleHashMap;
    }

    public void setArticleHashMap(HashMap<String, Article> articleHashMap) {
        this.articleHashMap = articleHashMap;
    }

    public int getPositionCount() {
        return positionCount;
    }

    public void setPositionCount(int positionCount) {
        this.positionCount = positionCount;
    }

    public Employee getPicker() {
        return picker;
    }

    public void setPicker(Employee picker) {
        this.picker = picker;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public int getOrderDateAsUnixTimestamp() {
        return orderDateAsUnixTimestamp;
    }

    public void setOrderDateAsUnixTimestamp(int orderDateAsUnixTimestamp) {
        this.orderDateAsUnixTimestamp = orderDateAsUnixTimestamp;
    }

    public int getDueDateAsUnixTimestamp() {
        return dueDateAsUnixTimestamp;
    }

    public void setDueDateAsUnixTimestamp(int dueDateAsUnixTimestamp) {
        this.dueDateAsUnixTimestamp = dueDateAsUnixTimestamp;
    }
}
