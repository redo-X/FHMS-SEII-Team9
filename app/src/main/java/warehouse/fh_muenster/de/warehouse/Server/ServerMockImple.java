package warehouse.fh_muenster.de.warehouse.Server;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import warehouse.fh_muenster.de.warehouse.Article;
import warehouse.fh_muenster.de.warehouse.Commission;
import warehouse.fh_muenster.de.warehouse.Employee;
import warehouse.fh_muenster.de.warehouse.Role;
import warehouse.fh_muenster.de.warehouse.StorageLocation;
import warehouse.fh_muenster.de.warehouse.WarehouseApplication;

/**
 * Created by Thomas on 10.05.2016.
 */
public class ServerMockImple implements ServerMockInterface {
    @Override
    public Employee login(int employeeNr, String password) {
        if(employeeNr == 123 && password.equals("123")){
            Employee user = new Employee(123, "123");
            user.setRole(Role.Kommissionierer);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return user;
        }
        else if(employeeNr == 234 && password.equals("234")){
            Employee user = new Employee(234, "234");
            user.setRole(Role.Lagerist);
            return user;
        }
        else{
            return null;
        }
    }


    public HashMap<Integer, Commission> getFreeCommissions() {
        HashMap<Integer,Commission> commissionMap = new HashMap<Integer, Commission>();

        Random rand = new Random();
        for(int i = 0; i< 10; i++){
            int kommissionCode = 10000 + i;
            int positionCount = rand.nextInt(10-1)+1;
            Commission commission = new Commission(kommissionCode , positionCount);
            commissionMap.put(kommissionCode,commission);
        }
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return commissionMap;
    }

    public HashMap<Integer,Commission> getCommissions(Employee picker){
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
    return commissionMap;
    }

    @Override
    public HashMap<Integer, Article> getPositionToCommission(int id) {
        Commission commission = null;
        HashMap<Integer,Article> articleHashMap = new HashMap<>();
        Random rand = new Random();
        for(int j = 0; j< rand.nextInt(10-1)+1; j++){
          int articelCode = 5000 + j + (j*1000);
            StorageLocation location = new StorageLocation(String.valueOf(rand.nextInt(70-1)+1));
            Article article = new Article(String.valueOf(articelCode), "Tolle Beschreibung des Artikels");
            article.setQuantityOnStock(rand.nextInt(100 - 5) + 5);
            article.setQuantityOnCommit(rand.nextInt(10 - 1) + 1);
            article.setStorageLocation(location);
            articleHashMap.put(articelCode, article);
        }
        //commission.setArticleHashMap(articleHashMap);
        return  articleHashMap;
    }

    public HashMap<Integer, Article> getAllArticle () {
        HashMap<Integer, Article> aHashMap = new HashMap<>();
        Random rand = new Random();
        for(int j = 0; j< 20; j++){
            int articelCode = 5000 + j + (j*1000);
            StorageLocation location = new StorageLocation(String.valueOf(rand.nextInt(70-1)+1));
            Article article = new Article(String.valueOf(articelCode), "Tolle Beschreibung des Artikels");
            article.setQuantityOnStock(rand.nextInt(100 - 5) + 5);
            article.setStorageLocation(location);
            aHashMap.put(articelCode, article);
        }
        return aHashMap;
    }
}
