package bos.rick;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SecretUtility {
    public static String getSecret(String name) {
        String value = "";
        try {
            File file = new File("/run/secrets/"+ name);
            if ( file.exists()) {
                BufferedReader br = new BufferedReader( new FileReader(file));
                StringBuffer sb = new StringBuffer();
                String line = "";
                while ( ( line = br.readLine()) != null ) {
                    sb.append(line);

                }
                value = sb.toString();
            }
        }
        catch ( IOException ioe) {
            ioe.printStackTrace();
        }

        return value;

    }
}
