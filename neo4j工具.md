import csv 命令
./neo4j-import   --multiline-fields=true --bad-tolerance=1000000 --into /home/ubuntu/neo4jdata/databases/graph.db --id-type string --nodes:声音 voice.csv --nodes:专辑 resource.csv  --relationships:包含 relation.csv 



unwind 写法(java)

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
}


创建索引
	CREATE INDEX ON :标签(id)
	
