package warehouse.fh_muenster.de.warehouse.Server;

import java.util.HashMap;
import java.util.Random;

import warehouse.fh_muenster.de.warehouse.Article;
import warehouse.fh_muenster.de.warehouse.Commission;
import warehouse.fh_muenster.de.warehouse.Employee;
import warehouse.fh_muenster.de.warehouse.Role;
import warehouse.fh_muenster.de.warehouse.StorageLocation;

/**
 * Created by Thomas on 10.05.2016.
 */
public class ServerMockImple implements ServerInterface {
    @Override
    public Employee login(int employeeNr, String password) {
        if(employeeNr == 1 && password.equals("geheim")){
            Employee user = new Employee(1, "geheim");
            user.setRole(Role.Kommissionierer);

            return user;
        }
        else if(employeeNr == 2 && password.equals("geheim")){
            Employee user = new Employee(2, "geheim");
            user.setRole(Role.Lagerist);
            return user;
        }
        else if(employeeNr == 3 && password.equals("geheim")){
            Employee user = new Employee(3, "geheim");
            user.setRole(Role.Administrator);
            return user;
        }
        else{
            return null;
        }
    }

    @Override
    public void logout(int sessionId) {

    }

    @Override
    public HashMap<Integer, Commission> getFreeCommissions(int sessionId) {
        HashMap<Integer,Commission> commissionMap = new HashMap<Integer, Commission>();

        Random rand = new Random();
        for(int i = 0; i< 10; i++){
            int kommissionCode = 10000 + i;
            int positionCount = rand.nextInt(10-1)+1;
            Commission commission = new Commission(kommissionCode , positionCount);
            commissionMap.put(kommissionCode,commission);
        }

        Commission commission = new Commission(90001,1);
        commissionMap.put(commission.getId(),commission);
        return commissionMap;
    }

    @Override
    public HashMap<Integer,Commission> getCommissions(int sessionId, Employee picker){
        HashMap<Integer,Commission> commissionMap = new HashMap<Integer, Commission>();

        Random rand = new Random();
        for(int i = 0; i< 6; i++){
            //HashMap<Integer,Article> articleMap = new HashMap<Integer, Article>();
            //for(int j = 0; j< rand.nextInt(10-1)+1; j++){
              //  int articelCode = 5000 + j + (i*1000);
                //StorageLocation location = new StorageLocation(String.valueOf(rand.nextInt(70-1)+1));
                //Article article = new Article(String.valueOf(articelCode), "Tolle Beschreibung des Artikels");
                /*article.setQuantityOnStock(rand.nextInt(100 - 5) + 5);
                article.setQuantityOnCommit(rand.nextInt(10 - 1) + 1);
                article.setStorageLocation(location);
                articleMap.put(articelCode, article);*/
            //}
            int kommissionCode = 100 + i;
            int positionCount = rand.nextInt(10-1)+1;
            Commission commission = new Commission(kommissionCode , positionCount);
            commissionMap.put(kommissionCode,commission);
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Commission commission = new Commission(90000,1);
        commissionMap.put(commission.getId(),commission);
    return commissionMap;
    }


    @Override
    public HashMap<Integer, Article> getPositionToCommission(int sessionId, int id) {
        Commission commission = null;
        HashMap<Integer,Article> articleHashMap = new HashMap<>();
        //String code = "3662168005845";
        String[] codes = {"3662168005845","4035300705214","4052400035317"};
        Random rand = new Random();
        if(id != 90000){
            for(int j = 0; j< rand.nextInt(10-1)+1; j++){
                int articelCode = 5000 + j + (j*1000);
                int codeRand = rand.nextInt(3-0);
                String code = codes[codeRand];
                StorageLocation location = new StorageLocation(String.valueOf(rand.nextInt(70-1)+1));
                Article article = new Article(code/*String.valueOf(articelCode)*/, "Tolle Beschreibung des Artikels" + j);
                article.setQuantityOnStock(rand.nextInt(100 - 5) + 5);
                article.setQuantityOnCommit(rand.nextInt(10 - 1) + 1);
                article.setStorageLocation(location);
                articleHashMap.put(articelCode, article);
            }
        }
        else{
            Article article = new Article("T-A", "TestArticle");
            article.setQuantityOnStock(100);
            article.setQuantityOnCommit(20);
            article.setStorageLocation(new StorageLocation("Lager5"));
            articleHashMap.put(1, article);
        }
        //commission.setArticleHashMap(articleHashMap);
        return  articleHashMap;
    }

    @Override
    public void startCommission(int sessionId, int commissionId) {

    }

    @Override
    public void endCommission(int sessionId, int commissionId) {

    }

    @Override
    public void updateQuantityOnCommissionPosition(int sessionId, int commissionPositionId, int quantity) {

    }

    @Override
    public void commitCommissionMessage(int sessionId, int commissionPositionId, int differenceQuantity, String note) {

    }

    @Override
    public void allocateCommission(int sessionId, int commissionId, int employeeId) {

    }

    @Override
    public void commitStock(int sessionId, String artikelCode, int menge) {

    }
    @Override
    public HashMap<String, Article> getArticles(int sessionId) {
        HashMap<String, Article> aHashMap = new HashMap<>();
        Random rand = new Random();
        for(int j = 0; j< 20; j++){
            int articelCode = 5000 + j + (j*1000);
            StorageLocation location = new StorageLocation(String.valueOf(rand.nextInt(70-1)+1));
            Article article = new Article(String.valueOf(articelCode), "Tolle Beschreibung des Artikels");
            article.setQuantityOnStock(rand.nextInt(100 - 5) + 5);
            article.setStorageLocation(location);
            aHashMap.put(String.valueOf(articelCode), article);
        }
        Article article = new Article("90000", "Tolle Beschreibung des Artikels");
        article.setQuantityOnStock(100);
        article.setQuantityOnCommit(20);
        article.setStorageLocation(new StorageLocation("Lager5"));
        aHashMap.put(article.getCode(),article);
        return aHashMap;
    }

    @Override
    public boolean createArticle(int sessionId, String articleCode, String articleName, String lagerort) {
        return true;
    }
}
