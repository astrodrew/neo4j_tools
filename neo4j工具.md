import csv ����
./neo4j-import   --multiline-fields=true --bad-tolerance=1000000 --into /home/ubuntu/neo4jdata/databases/graph.db --id-type string --nodes:���� voice.csv --nodes:ר�� resource.csv  --relationships:���� relation.csv 



unwind д��(java)

Map<String, Object> map = new HashMap<String, Object>();
map.put("ʵ����", matcher.group(1));
map.put("id", matcher.group(2));
map.put("������", matcher.group(3));
map.put("����ʱ��", matcher.group(4));
rows.add(map);
i++;
if(i % 100 == 0){
	Map<String,Object> paramMap = new HashMap<String, Object>();
	paramMap.put("rows",rows);
	System.out.println(rows);
	System.out.println(i);
	String query  =  String.format("UNWIND $rows As row\nMERGE(n:%s) SET n = row","����");
	session.run(query,paramMap);
	rows.clear();
}