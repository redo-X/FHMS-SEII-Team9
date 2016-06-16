package warehouse.fh_muenster.de.warehouse;

/**
 * Created by Thomas on 09.05.2016.
 */
public class Article {
    private String code,
            name;
    private int quantityOnStock,
            quantityOnCommit,
            quantitiyCommited,
            positionCommissionId;
    private StorageLocation storageLocation;


    public Article() {
    }

    public Article(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Article(String code, String name, int quantityOnStock, int quantityOnCommit, int positionCommissionId) {
        this.code = code;
        this.name = name;
        this.quantityOnStock = quantityOnStock;
        this.quantityOnCommit = quantityOnCommit;
        this.positionCommissionId = positionCommissionId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantityOnStock() {
        return quantityOnStock;
    }

    public void setQuantityOnStock(int quantityOnStock) {
        this.quantityOnStock = quantityOnStock;
    }

    public int getQuantityOnCommit() {
        return quantityOnCommit;
    }

    public int getQuantitiyCommited() {
        return quantitiyCommited;
    }

    public int getPositionCommissionId() {
        return positionCommissionId;
    }

    public void setPositionCommissionId(int positionCommissionId) {
        this.positionCommissionId = positionCommissionId;
    }

    public void setQuantitiyCommited(int quantitiyCommited) {
        this.quantitiyCommited = quantitiyCommited;
    }

    public void setQuantityOnCommit(int quantityOnCommit) {
        this.quantityOnCommit = quantityOnCommit;
    }

    public StorageLocation getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(StorageLocation storageLocation) {
        this.storageLocation = storageLocation;
    }

    public String toString() {
        return "Code: " + this.code + " Name: " + name + " positionId: " + positionCommissionId + " commit: " + quantityOnCommit;
    }
}
