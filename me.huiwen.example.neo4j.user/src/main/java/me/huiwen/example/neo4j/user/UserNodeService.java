package me.huiwen.example.neo4j.user;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import me.huiwen.example.neo4j.user.node.User;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;


import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.index.RelationshipIndex;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.helpers.collection.PagingIterator;

import org.neo4j.kernel.GraphDatabaseAPI;
import org.neo4j.kernel.Traversal;



public abstract class UserNodeService {
	private static final Logger LOG = Logger.getLogger(UserNodeService.class);

	protected GraphDatabaseService graphDb;
	protected GraphDatabaseAPI graphDbApi;

	protected Index<Node> userIndex;

	protected RelationshipIndex userRelIndex;




	public abstract void init() throws Exception ;

	public abstract void destroy() throws Exception ;

	public Node adduser(User user) throws Exception {
		Node node;
		IndexHits<Node> indexHits = userIndex.get("name",user.getName());
		if (indexHits == null || indexHits.size() < 1) {
			Transaction tx = graphDb.beginTx();
			try {
				node = graphDb.createNode();
				Long nodeId = userToNode(node, user);
				userIndex.add(node, "name", user.getName());
				tx.success();
			} catch (Exception e) {
				tx.failure();
				throw e;
			} finally {
				tx.finish();
			}
		} else {
			node = indexHits.getSingle();
		}

		return node;
	}


	public long updateuser(User user) throws Exception {
		Node node;
		Long nodeId = null ;
		IndexHits<Node> indexHits = userIndex.get("name",user.getName());

		if (indexHits != null && indexHits.size()> 1) {
			Transaction tx = graphDb.beginTx();
			try {
				node = indexHits.getSingle();
				nodeId = userToNode(node, user);
				userIndex.add(node, "name", user.getName());
				tx.success();
			} catch (Exception e) {
				tx.failure();
				throw e;
			} finally {
				tx.finish();
			}
		}
		return nodeId;
	}

	public void removeuser(User user) throws Exception {
		
		Node node;
		IndexHits<Node> indexHits = userIndex.get("name",user.getName());

		if (indexHits != null && indexHits.size()> 1) {
			Transaction tx = graphDb.beginTx();
			try {
				node = indexHits.getSingle();
				node.delete();
				tx.success();
			} catch (Exception e) {
				tx.failure();
				throw e;
			} finally {
				tx.finish();
			}
		}

	}

	public void addUserRelation(Node fromNode, Node toNode,
			RelationshipType relType) throws Exception {

		Transaction tx = graphDb.beginTx();
		try {
			String rel = fromNode.getId()+"_"+toNode.getId();
			IndexHits<Relationship> hits = userRelIndex.get("user_user_"+relType.name(), rel);
			if(hits == null || hits.size()<1)
			{
				Relationship relationship = fromNode.createRelationshipTo(toNode,
						relType);
				
				userRelIndex.add(relationship, "user_user_"+relType.name(),rel);
			}
			tx.success();
		} catch (Exception e) {
			tx.failure();
			throw e;
		} finally {
			tx.finish();
		}

	}

	

	public Node getUser(String profUid) throws Exception {
		Node node = null;
		IndexHits<Node> indexHits = userIndex.get("profUid",profUid);

		if (indexHits != null && indexHits.size() > 1)
		{
			node= indexHits.getSingle();
		}
		
		node = indexHits.getSingle();
		return node;
	}


	private Long userToNode(Node node, User emp) {

		Map<String, String> empBeanMap;
		try {
			empBeanMap = BeanUtils.describe(emp);
			for (String key : empBeanMap.keySet()) {
				if (empBeanMap.get(key) != null) {
					node.setProperty(key, empBeanMap.get(key));
				}

			}

		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long nodeId = node.getId();
		return nodeId;

	}

	
	
	
	public Set<String> selectAllUserId() throws Exception {
		Set<String> uids = new HashSet<String>();
		IndexHits<Node> hits = userIndex.query("*:*");
		try{
		    while(hits.hasNext()){
		        Node n = hits.next();
		        String uid = (String)n.getProperty("profUid");
		        uids.add(uid);
		    }
		}finally{
		    hits.close();
		}
		return uids;
	}
	private User fromNode(Node node) {

		User emp = new User();
		for (String key : node.getPropertyKeys()) {
			try {
				BeanUtils.setProperty(emp, key, node.getProperty(key));
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return emp;
	}
	
	public PagingIterator<Node> selectAllUserIdByPage() {
		IndexHits<Node> hits = userIndex.query("*:*");
		PagingIterator<Node> pageIt= new PagingIterator<Node>(hits, 1000);
		return pageIt;
	}
	
	
	public void selectAllUserIdBy() {
		
		RelationshipType relationshipType = DynamicRelationshipType.withName("HAS_VISITED");
		Direction direction = Direction.BOTH;
		TraversalDescription traversal =
		Traversal.description()
		.evaluator(Evaluators.atDepth(1));
		//traversal.traverse(node);
	}
	
	public abstract  Set<String> selectAllUserIdByCypher(int skip,int limit);
	
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
	
	public static void main(String argv[])
	{

	}
}
