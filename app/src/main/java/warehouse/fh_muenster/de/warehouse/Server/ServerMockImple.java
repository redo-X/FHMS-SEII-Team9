package warehouse.fh_muenster.de.warehouse.Server;

import android.util.Log;

import java.util.Random;

import warehouse.fh_muenster.de.warehouse.Article;
import warehouse.fh_muenster.de.warehouse.Commission;
import warehouse.fh_muenster.de.warehouse.Employee;

/**
 * Created by Thomas on 10.05.2016.
 */
public class ServerMockImple implements ServerMockInterface {
    @Override
    public Employee login(int employeeNr, String password) {
        if(employeeNr == 123 && password.equals("123")){
            Employee user = new Employee(123, "123");
            return user;
        }
        else{
            return null;
        }
    }

    @Override
    public void getAllCommissions() {

    }

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

    @Override
    public Commission[] getCommissions(Employee picker) {
        Commission commissionArray[] = new Commission[5];
        Article articleArray[] = new Article[3];
        Random rand = new Random();
        for(int j = 0; j < 5; j++){
            for(int i = 0; i < 3; i++){
                int articelCode = rand.nextInt(800000 - 10000) + 10000;
                Article article = new Article(String.valueOf(articelCode), "Tolle Beschreibung des Artikels");
                article.setQuantityOnStock(rand.nextInt(100 - 5) + 5);
                article.setQuantityOnCommit(rand.nextInt(10 - 1) + 1);
                articleArray[i] = article;
            }
            int kommissionCode = rand.nextInt(800000 - 10000) + 10000;
            Commission commission = new Commission(kommissionCode , articleArray, picker);
            commissionArray[j] = commission;
        }
        return commissionArray;
    }
}
