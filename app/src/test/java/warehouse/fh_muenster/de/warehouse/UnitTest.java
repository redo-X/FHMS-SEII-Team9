package warehouse.fh_muenster.de.warehouse;

import org.junit.Test;

import java.util.HashMap;

import warehouse.fh_muenster.de.warehouse.Server.ServerMockImple;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class UnitTest {

    /**
     * Testet ob der Login als Kommissionierer erfolgreich ist
     */
    @Test
    public void testIfLastArticleFail(){
        boolean isLastArticle = Article.isLastArticle(5,4);
        assertFalse(isLastArticle);
    }
    @Test
    public void testIfLastArticle(){
        boolean isLastArticle = Article.isLastArticle(5,5);
        assertTrue(isLastArticle);
    }
    @Test
    public void testIfCommissionQuantityOkay(){
        Article article = new Article("Test", "TestName");
        article.setQuantityOnCommit(5);
        boolean isCommissionOkay = article.isCommissionQuantityOkay(5);
        assertTrue(isCommissionOkay);
    }
    @Test
    public void testIfCommissionQuantityOkayFail(){
        Article article = new Article("Test", "TestName");
        article.setQuantityOnCommit(5);
        boolean isCommissionOkay = article.isCommissionQuantityOkay(6);
        assertFalse(isCommissionOkay);
    }


}