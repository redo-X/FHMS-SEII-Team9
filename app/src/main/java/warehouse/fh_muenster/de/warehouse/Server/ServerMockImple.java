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

/**
 * Created by Thomas on 10.05.2016.
 */
public class ServerMockImple implements ServerMockInterface {
    @Override
    public Employee login(int employeeNr, String password) {
        if(employeeNr == 123 && password.equals("123")){
            Employee user = new Employee(123, "123");
            user.setRole(Role.Kommissionierer);
            return user;
        }
        else{
            return null;
        }
    }

    @Override
    public void getAllCommissions() {

    }
/*
    @Override
    public Commission[] getFreeCommissions() {

        Commission commissionArray[] = new Commission[10];
        Article articleArray[] = new Article[5];
        Random rand = new Random();
        for(int j = 0; j < 10; j++){
            for(int i = 0; i < 5; i++){
                int articelCode = rand.nextInt(800000 - 10000) + 10000;
                Article article = new Article(String.valueOf(articelCode), "Tolle Beschreibung des Artikels");

                articleArray[i] = article;
            }
            int kommissionCode = rand.nextInt(800000 - 10000) + 10000;
            Commission commission = new Commission(kommissionCode , articleArray);
            commissionArray[j] = commission;
        }
        return commissionArray;
    }
  */

    public HashMap<Integer, Commission> getFreeCommissions() {
        HashMap<Integer,Commission> commissionMap = new HashMap<Integer, Commission>();

        Random rand = new Random();
        for(int i = 0; i< 10; i++){
            HashMap<Integer,Article> articleMap = new HashMap<Integer, Article>();
            for(int j = 0; j< rand.nextInt(10-1)+1; j++){

                int articelCode = 5000 + j + (i*1000);
                StorageLocation location = new StorageLocation(String.valueOf(rand.nextInt(70-1)+1));
                Article article = new Article(String.valueOf(articelCode), "Tolle Beschreibung des Artikels");
                articleMap.put(articelCode, article);
            }
            int kommissionCode = 10000 + i;
            Commission commission = new Commission(kommissionCode , articleMap);
            commissionMap.put(kommissionCode,commission);
        }
        return commissionMap;
    }


    /*
    @Override
    public Commission[] getCommissions(Employee picker) {
        Commission commissionArray[] = new Commission[5];
        Article articleArray[] = new Article[3];
        Random rand = new Random();
        for(int j = 0; j < 5; j++){
            for(int i = 0; i < 3; i++){
                int articelCode = rand.nextInt(800000 - 10000) + 10000;
                StorageLocation location = new StorageLocation(String.valueOf(rand.nextInt(70-1)+1));
                Article article = new Article(String.valueOf(articelCode), "Tolle Beschreibung des Artikels " + i);
                article.setQuantityOnStock(rand.nextInt(100 - 5) + 5);
                article.setQuantityOnCommit(rand.nextInt(10 - 1) + 1);
                article.setStorageLocation(location);
                articleArray[i] = article;
            }
            int kommissionCode = rand.nextInt(800000 - 10000) + 10000;
            Commission commission = new Commission(kommissionCode , articleArray, picker);
            commissionArray[j] = commission;
        }
        return commissionArray;
    }

*/
    public HashMap<Integer,Commission> getCommissions(Employee picker){
        HashMap<Integer,Commission> commissionMap = new HashMap<Integer, Commission>();

        Random rand = new Random();
        for(int i = 0; i< 6; i++){
            HashMap<Integer,Article> articleMap = new HashMap<Integer, Article>();
            for(int j = 0; j< 5; j++){

                int articelCode = 5000 + j + (i*1000);
                StorageLocation location = new StorageLocation(String.valueOf(rand.nextInt(70-1)+1));
                Article article = new Article(String.valueOf(articelCode), "Tolle Beschreibung des Artikels");
                article.setQuantityOnStock(rand.nextInt(100 - 5) + 5);
                article.setQuantityOnCommit(rand.nextInt(10 - 1) + 1);
                article.setStorageLocation(location);
                articleMap.put(articelCode, article);
            }
            int kommissionCode = 10000 + i;
            Commission commission = new Commission(kommissionCode , articleMap, picker);
            commissionMap.put(kommissionCode,commission);
        }
    return commissionMap;
    }

}
