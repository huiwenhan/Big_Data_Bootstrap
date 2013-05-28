package me.huiwen.example.neo4j.user;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import me.huiwen.example.neo4j.user.node.User;

import org.apache.log4j.Logger;
import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.kernel.GraphDatabaseAPI;
import org.neo4j.server.WrappingNeoServerBootstrapper;
import org.neo4j.server.configuration.ServerConfigurator;
import org.neo4j.shell.ShellSettings;


public class EmbedUserNodeService extends UserNodeService {
	private static final Logger LOG = Logger.getLogger(EmbedUserNodeService.class);
	private String graphDir;

	public void setGraphDir(String graphDir) {
		this.graphDir = graphDir;
	}

	@Override
	public void init() throws Exception {
		// this.graphDb = new GraphDatabaseFactory().
		// newEmbeddedDatabaseBuilder(graphDir).
		// loadPropertiesFromFile("src/main/resources/neo4j.properties" ).
		// newGraphDatabase();

		// GraphDatabaseAPI

		this.graphDb = new GraphDatabaseFactory()
				.newEmbeddedDatabaseBuilder(graphDir)
				.loadPropertiesFromFile("src/main/resources/neo4j.properties")
				.setConfig(ShellSettings.remote_shell_enabled, "true")
				.newGraphDatabase();
		graphDbApi = (GraphDatabaseAPI) graphDb;

		registerShutdownHook( graphDb );
		ServerConfigurator config;
		config = new ServerConfigurator(graphDbApi);
		 //let the server endpoint be on a custom port
		config.configuration()
				.setProperty(
						org.neo4j.server.configuration.Configurator.WEBSERVER_PORT_PROPERTY_KEY,
						7474);

		config.configuration()
				.setProperty(
						org.neo4j.server.configuration.Configurator.DEFAULT_MANAGEMENT_API_PATH,
						"/db/manage");

		config.configuration()
				.setProperty(
						org.neo4j.server.configuration.Configurator.DEFAULT_WEB_ADMIN_PATH,
						"/webadmin");

		IndexManager neoIndexMgr = graphDb.index();
		userIndex = neoIndexMgr.forNodes("user");

		userRelIndex = neoIndexMgr.forRelationships("userRel");
		WrappingNeoServerBootstrapper srv;

		srv = new WrappingNeoServerBootstrapper(graphDbApi, config);
		LOG.info("Neo4j will Start!");
		srv.start();
		System.out.println("Neo4j Started!");

	}

	@Override
	public void destroy() throws Exception {

		graphDb.shutdown();
	}

	
	
	private static void registerShutdownHook( final GraphDatabaseService graphDb )
	{
	    // Registers a shutdown hook for the Neo4j instance so that it
	    // shuts down nicely when the VM exits (even if you "Ctrl-C" the
	    // running example before it's completed)
	    Runtime.getRuntime().addShutdownHook( new Thread()
	    {
	        @Override
	        public void run()
	        {
	        	LOG.info("Neo4j Will shutdown!");
	            graphDb.shutdown();
	            LOG.info("Neo4j is shutdowned!");
	        }
	    } );
	}
	
	public Set<String> selectAllUserIdByCypher(int skip,int limit) {
		Set<String> uids = new HashSet<String>();
		ExecutionEngine engine = new ExecutionEngine(graphDb, null  );
		StringBuffer query = new StringBuffer().append(" start users =node:user(\"*:*\") ") 
				.append(" return users ")
				.append(" skip ").append(" {querySkip} ").append(" limit ").append(" {queryLimit} ");
				
		HashMap<String, Object> paraHash = new  HashMap<String, Object>();
		paraHash.put("querySkip",skip);
		paraHash.put("queryLimit",limit);
		
		ExecutionResult result = engine.execute( query.toString(),paraHash);
		scala.collection.Iterator<Node> it =result.columnAs("users");
		while (it.hasNext()){
			Node node = it.next();
			 String uid = (String)node.getProperty("profUid");
			 uids.add(uid);
		}
		return uids;
	}
	
	public static void main(String[] argv)
	{
		EmbedUserNodeService service = new EmbedUserNodeService();
		service.setGraphDir("./data/user");
		try {
			service.init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
		
		User zhouXun = new User("Zhou Xun");
		User douPeng =  new User("Dou Peng");
		User douWei = new User("Dou Wei");
		User wangFei = new User("Wang Fei");
		User songNing = new User("Song Ning");
		User gaoYuan =  new User("Gao Yuan");
		User liYaPeng = new User("Li Ya Peng");
		User piaoShu = new User("Piao Shu");
		User zhangYaDong = new User ("Zhang ya Dong");
		User douYing =new User("Dou Ying");
		User quYing = new User("Qu Ying");
		User wangShuo = new User("Wang shuo");
		User wangyan = new User("Wang yan");
		
		
		try {
			Node zhouXunNode = service.adduser(zhouXun);
			Node douPengNode = service.adduser(douPeng);
			Node douWeiNode = service.adduser(douWei);
			Node wangFeiNode = service.adduser(wangFei);
			Node songNingNode = service.adduser(songNing);
			Node gaoYuanNode = service.adduser(gaoYuan);
			Node liYaPengNode = service.adduser(liYaPeng);
			Node piaoShuNode = service.adduser(piaoShu);
			Node zhangYaDongNode = service.adduser(zhangYaDong);
			Node douYingNode = service.adduser(douYing);
			Node quYingNode = service.adduser(quYing);
			Node wangShuoNode = service.adduser(wangShuo);
			Node wangyanNode = service.adduser(wangyan);
			
			RelationshipType exGirlFriend = DynamicRelationshipType.withName("exF");
			RelationshipType exBoyFriend = DynamicRelationshipType.withName("exBF");
			
			RelationshipType girlFriend = DynamicRelationshipType.withName("GF");
			RelationshipType boyFriend = DynamicRelationshipType.withName("BF");
			
			RelationshipType exHusband = DynamicRelationshipType.withName("exHB");
			RelationshipType exWife = DynamicRelationshipType.withName("exWF");
			RelationshipType husband = DynamicRelationshipType.withName("HB");
			RelationshipType wife = DynamicRelationshipType.withName("WF");
			
			RelationshipType cousin= DynamicRelationshipType.withName("Cousin");

			
			RelationshipType brother= DynamicRelationshipType.withName("BR");
			RelationshipType sister= DynamicRelationshipType.withName("SS");
			
			RelationshipType filmProducer= DynamicRelationshipType.withName("FP");
			
			RelationshipType stepmother=DynamicRelationshipType.withName("SM");


			service.addUserRelation(douPengNode, zhouXunNode, exGirlFriend);
			service.addUserRelation(zhouXunNode, douPengNode, exBoyFriend);
			
			service.addUserRelation(douPengNode, douWeiNode, cousin);
			service.addUserRelation(douWeiNode, douPengNode, cousin);
			
			
			service.addUserRelation(wangFeiNode, douWeiNode, exHusband);
			service.addUserRelation(douWeiNode, wangFeiNode, exWife);
			
			
			service.addUserRelation(songNingNode, zhouXunNode, exGirlFriend);
			service.addUserRelation(zhouXunNode, songNingNode, exBoyFriend);
			
			
			service.addUserRelation(songNingNode, gaoYuanNode, cousin);
			service.addUserRelation(gaoYuanNode, songNingNode, cousin);
			
			service.addUserRelation(gaoYuanNode, douWeiNode, wife);
			service.addUserRelation(douWeiNode, gaoYuanNode, husband);
			
			
			service.addUserRelation(douWeiNode, wangFeiNode, exWife);
			service.addUserRelation(wangFeiNode, douWeiNode, exHusband);
			
			service.addUserRelation(wangFeiNode, liYaPengNode, husband);
			service.addUserRelation(liYaPengNode, wangFeiNode, wife);
			
			service.addUserRelation(zhouXunNode, liYaPengNode, exBoyFriend);
			service.addUserRelation(liYaPengNode, zhouXunNode, exGirlFriend);
			
			service.addUserRelation(zhouXunNode, piaoShuNode, exBoyFriend);
			service.addUserRelation(piaoShuNode, zhouXunNode, exGirlFriend);
			
			service.addUserRelation(piaoShuNode, zhangYaDongNode, filmProducer);
			
			service.addUserRelation(douYingNode, douWeiNode, brother);
			service.addUserRelation(douWeiNode, douYingNode, sister);
			
			
			service.addUserRelation(zhangYaDongNode, douYingNode, exWife);
			service.addUserRelation(wangFeiNode, douWeiNode, exHusband);
			
			service.addUserRelation(wangFeiNode, zhangYaDongNode, filmProducer);
			
			service.addUserRelation(quYingNode, liYaPengNode, exBoyFriend);
			service.addUserRelation(liYaPengNode, quYingNode, exGirlFriend);
			
			service.addUserRelation(zhangYaDongNode, quYingNode, girlFriend);
			service.addUserRelation(quYingNode, douWeiNode, boyFriend);
			
			service.addUserRelation(wangShuoNode, zhouXunNode, girlFriend);
			service.addUserRelation(zhouXunNode, wangShuoNode, boyFriend);
			
			service.addUserRelation(wangShuoNode, wangyanNode, stepmother);
			
			System.out.println("All data is strored!");
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
