import android.app.Instrumentation;
import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.boket.ui.camera.BarcodeScannerActivity;
import com.google.firebase.FirebaseApp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.lang.reflect.*;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestISBNValid {

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

    @Test
    public void testISBNValid() {
        /*
        Todo fixa detta på något sätt så metoden kan vara private o inte statisk (reflect method)
        try {
            BarcodeScannerActivity barcodeScannerActivity = new BarcodeScannerActivity();
            Class c = barcodeScannerActivity.getClass();
            Method reflectedMethod;
            reflectedMethod = c.getDeclaredMethod("isValidISBN13", String.class);
            Boolean result = (Boolean) reflectedMethod.invoke(null, isbn);
            assertEquals(result, isValid);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
         */

        assertEquals(isValid, BarcodeScannerActivity.isValidISBN13(isbn));
    }
}
