import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.boket.ui.camera.BarcodeScannerActivity;
import com.google.firebase.firestore.proto.TargetOuterClass;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.lang.reflect.*;

import static org.junit.Assert.assertEquals;

public class TestISBNValid {

    @Rule
    public ActivityScenarioRule<BarcodeScannerActivity> activityrule = new ActivityScenarioRule(BarcodeScannerActivity.class);

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"9789144115603", true}, {"9789ASDWFDS", false}, {"0", false}
        });
    }

    @Parameterized.Parameter(0)
    public String isbn;
    @Parameterized.Parameter(1)
    public boolean isValid;

    @Test(expected = IllegalAccessException.class)
    public void testISBNValid() {
        ActivityScenario scenario = activityrule.getScenario();
        BarcodeScannerActivity barcodeScannerActivity = new BarcodeScannerActivity();
        Class c = barcodeScannerActivity.getClass();
        try {
            Method reflectedMethod = c.getDeclaredMethod("isValidISBN13", String.class);

            Boolean result = (Boolean) reflectedMethod.invoke(null, isbn);
            assertEquals(result, isValid);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
