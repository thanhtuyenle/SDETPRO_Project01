package utils.data;

import com.google.gson.Gson;
import testdata.purchasing.ComputerDataObject;
import testdata.user.UserDataObject;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CommonData {

    public static UserDataObject buildUserDataObject(String jsonDataFileLocation) {
        return buildDataObjectFrom(jsonDataFileLocation, UserDataObject.class);
    }

    private static <T> T buildDataObjectFrom(String jsonDataFileLocation, Class<T> dataType) {
        //ComputerDataObject[] cheapComputers = new ComputerDataObject[]{};
        // Read file content
        String currentProjectLocation = System.getProperty("user.dir");
        try {
            // create Gson instance
            Reader reader = Files.newBufferedReader(Paths.get(currentProjectLocation + jsonDataFileLocation));
            Gson gson = new Gson();
            // Convert to array of Computer instances
            return gson.fromJson(reader, dataType);

            //reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
