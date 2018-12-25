package quickneo4j;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Neo4jQuick {
    public static  void main(String args[]) throws IOException {
        File file = new File("voice.txt");
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(file));
        int i=0;
        String createCypher = null;
        Driver driver = GraphDatabase.driver( "bolt://HOST:PORT", AuthTokens.basic( "NAME", "PASSPORT"));
        Session session = driver.session();
        List<Map<String , Object>> rows = new ArrayList<Map<String, Object>>();
        while ((createCypher = reader.readLine()) != null) {
            //System.out.println(createCypher);
            String REGEX = "CREATE\\(m\\:声音\\{实体名\\:\"(.*)\"\\,id\\:\"(.*)\"\\,收听数\\:\"(.*)\"\\,发布时间\\:\"(.*)\"\\}\\)";
            Pattern pattern = Pattern.compile(REGEX);
            Matcher matcher = pattern.matcher(createCypher);
            if (matcher.find()) {
               Map<String, Object> map = new HashMap<String, Object>();
               map.put("实体名", matcher.group(1));
               map.put("id", matcher.group(2));
               map.put("收听数", matcher.group(3));
               map.put("发布时间", matcher.group(4));
               rows.add(map);
               i++;
                if(i % 100 == 0){
                    Map<String,Object> paramMap = new HashMap<String, Object>();
                    paramMap.put("rows",rows);
                    System.out.println(rows);
                    System.out.println(i);
                    String query  =  String.format("UNWIND $rows As row\nMERGE(n:%s) SET n = row","声音");
                    session.run(query,paramMap);
                    rows.clear();
                    // System.exit(0);
                    //System.out.println(rows);
                }
            }
        }
        session.close();
        driver.close();
    }
}
