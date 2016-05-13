package warehouse.fh_muenster.de.warehouse;

import java.util.Map;

/**
 * Created by Thomas on 13.05.2016.
 */
public class StorageLocation {

    private String                  code;
    private Map<Integer, Article>   stockArticles;

    public StorageLocation(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<Integer, Article> getStockArticles() {
        return stockArticles;
    }

    public void setStockArticles(Map<Integer, Article> stockArticles) {
        this.stockArticles = stockArticles;
    }
}
