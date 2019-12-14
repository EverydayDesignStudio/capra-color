/* public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");

        // If you don't specify credentials when constructing the client, the
        // client library will look for credentials in the environment.

        Storage storage = StorageOptions.getDefaultInstance().getService();

        Page<Bucket> buckets = storage.list();
        for (Bucket bucket : buckets.iterateAll()) {
            // do something with the info
        }

    }
}
*/

// Imports the Google Cloud client library

/*
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.Dataset;
import com.google.cloud.bigquery.DatasetInfo;

public class Main {
    public static void main(String... args) throws Exception {
        // Instantiate a client. If you don't specify credentials when constructing a client, the
        // client library will look for credentials in the environment, such as the
        // GOOGLE_APPLICATION_CREDENTIALS environment variable.
        BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();

        // The name for the new dataset
        String datasetName = "my_new_dataset";

        // Prepares a new dataset
        Dataset dataset = null;
        DatasetInfo datasetInfo = DatasetInfo.newBuilder(datasetName).build();

        // Creates the dataset
        dataset = bigquery.create(datasetInfo);

        System.out.printf("Dataset %s created.%n", dataset.getDatasetId().getDataset());
    }

    public static void detectProperties(String filePath, PrintStream out) throws Exception,
            IOException {

    }
}
*/



// Imports the Google Cloud client library
import com.google.cloud.vision.v1.*;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.protobuf.ByteString;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.io.RandomAccessFile;


public class Main {
    private static int hikeNumber = 1;

    public static void main(String... args) throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter("hike" + hikeNumber + "-gcloud" + ".csv"));

        for (int i = 1; i < 241; i++) {
            // Instantiates a client
            try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {

                // The path to the image file to annotate
                String fileName = "src/main/resources/hike" + hikeNumber + "/" + i + ".jpg";
//                String fileName = "/Users/Jordan/hike1/" + i + ".jpg";

                // Reads the image file into memory
                Path path = Paths.get(fileName);
                byte[] data = Files.readAllBytes(path);
                ByteString imgBytes = ByteString.copyFrom(data);

                // Builds the image annotation request
                List<AnnotateImageRequest> requests = new ArrayList<>();
                Image img = Image.newBuilder().setContent(imgBytes).build();
                Feature feat = Feature.newBuilder().setType(Type.IMAGE_PROPERTIES).build();
                AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                        .addFeatures(feat)
                        .setImage(img)
                        .build();
                requests.add(request);

                // Performs label detection on the image file
                BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
                List<AnnotateImageResponse> responses = response.getResponsesList();

                for (AnnotateImageResponse res : responses) {
                    if (res.hasError()) {
                        System.out.printf("Error: %s\n", res.getError().getMessage());
                        return;
                    }

                    // Builder of the Stings
                    StringBuilder sb = new StringBuilder();
                    sb.append(i);
                    sb.append(",\"(");

                    DominantColorsAnnotation colors = res.getImagePropertiesAnnotation().getDominantColors();

                    for (int j = 0; j < 5; j++) {
                        ColorInfo color = colors.getColorsList().get(j);
                        if (j == 0) {
                            sb.append(color.getColor().getRed() + ", ");
                            sb.append(color.getColor().getGreen() + ", ");
                            sb.append(color.getColor().getBlue() + ")\",");
                            sb.append("\"[");
                            sb.append("(" + color.getPixelFraction() + ", (");
                            sb.append(color.getColor().getRed() + ", ");
                            sb.append(color.getColor().getGreen() + ", ");
                            sb.append(color.getColor().getBlue() + ")), ");
                        } else if (j == 4) {
                            sb.append("(" + color.getPixelFraction() + ", (");
                            sb.append(color.getColor().getRed() + ", ");
                            sb.append(color.getColor().getGreen() + ", ");
                            sb.append(color.getColor().getBlue() + "))]\"\n");
                            //                        (0.01, (98, 101, 122))]\"
                        } else {
                            sb.append("(" + color.getPixelFraction() + ", (");
                            sb.append(color.getColor().getRed() + ", ");
                            sb.append(color.getColor().getGreen() + ", ");
                            sb.append(color.getColor().getBlue() + ")), ");
                        }
                    }
//                    writer.write(sb.toString()); // TODO - not working for some reason
                    writer.append(sb.toString());
//                    System.out.print(sb.toString());
                }
            }
        }
        writer.close();
    }
}