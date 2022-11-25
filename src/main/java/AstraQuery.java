


import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import java.nio.file.Paths;

public class AstraQuery {

    static final String ASTRA_ZIP_FILE = "/path/to/secure-connect-mydb.zip";
    static final String ASTRA_USERNAME = "CLIENT_ID";
    static final String ASTRA_PASSWORD = "SECRET_KEY";
    static final String ASTRA_KEYSPACE = "myks";

    public static void main(String[] args) {
        // Logger logger = LoggerFactory.getLogger(MumbaiAstra.class);
        String city = "";
        String name = "";
        int id = 0;
        System.out.println("Initiating Connection to Astra Cluster");
        System.out.println(java.time.LocalDateTime.now());
        CqlSession cqlSession = CqlSession.builder()
                    .withCloudSecureConnectBundle(Paths.get(ASTRA_ZIP_FILE))
                    .withAuthCredentials(ASTRA_USERNAME, ASTRA_PASSWORD)
                    .withKeyspace(ASTRA_KEYSPACE)
                    .build();

        // logger.info("[OK] Welcome to ASTRA. Connected to Keyspace {}", cqlSession.getKeyspace().get());

        // logger.info("[OK] Success");
        // System.exit(0);

        System.out.println("Connected successfully to Astra Cluster");
        System.out.println(java.time.LocalDateTime.now());

        PreparedStatement ps1 = cqlSession.prepare("insert into myks.t1 (id,city,name) values (?,?,?)");
    //    PreparedStatement ps2 = cqlSession.prepare("insert into myks.t2 (id,city,name) values (?,?,?)");
    //    PreparedStatement ps3 = cqlSession.prepare("insert into myks.t3 (id,city,name) values (?,?,?)");

    //    PreparedStatement qs1 = cqlSession.prepare("select * from myks.t1 WHERE id = ? and city = ?");
        PreparedStatement qs1 = cqlSession.prepare("select * from myks.t1 WHERE id > ? and id < ? allow filtering");

        for(int i = 2100; i < 40000 ; i++ )
        {

      //      if((i%1000) == 0)
      //      {

                city = "this is city"+ i;
                System.out.println(java.time.LocalDateTime.now());
                System.out.println("Reading now");
       //         ResultSet rs = cqlSession.execute(qs1.bind(i,city));
                ResultSet rs = cqlSession.execute(qs1.bind(i,i+50));
                for (Row row : rs) {
                System.out.println(row.getFormattedContents());
                 }
           //     Row row = rs.one();
              //  System.out.println(rs.one());
         //   System.out.format("%s %d\n", rs.getString("city"), rs.getInt("id"));

      //      }

        }

    }

}
