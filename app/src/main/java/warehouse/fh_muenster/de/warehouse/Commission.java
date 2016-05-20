package warehouse.fh_muenster.de.warehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Thomas on 19.05.2016.
 */
public class Commission {

    private Employee picker;
    private Article articleArray[];
    private int id;

    private HashMap<Integer, Article> articleHashMap;

    public Commission() {
    }

    public Commission(Employee picker, Article[] articleArray) {
        this.picker = picker;
        this.articleArray = articleArray;
    }

    public Commission(int id, Article[] articleArray) {
        this.id = id;
        this.articleArray = articleArray;
        this.picker = null;
    }

    public Commission(int id, Article[] articleArray, Employee picker) {
        this.picker = picker;
        this.articleArray = articleArray;
        this.id = id;
    }

    public Commission(int id, HashMap<Integer, Article> articleHashMap, Employee picker) {
        this.id = id;
        this.articleHashMap = articleHashMap;
        this.picker = picker;
    }

    public HashMap<Integer, Article> getArticleHashMap() {
        return articleHashMap;
    }

    public void setArticleHashMap(HashMap<Integer, Article> articleHashMap) {
        this.articleHashMap = articleHashMap;
    }

    public Employee getPicker() {
        return picker;
    }

    public void setPicker(Employee picker) {
        this.picker = picker;
    }

    public Article[] getArticleArray() {
        return articleArray;
    }

    public void setArticleArray(Article[] articleArray) {
        this.articleArray = articleArray;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
