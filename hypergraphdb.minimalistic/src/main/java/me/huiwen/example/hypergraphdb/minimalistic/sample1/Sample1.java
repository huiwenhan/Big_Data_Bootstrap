package me.huiwen.example.hypergraphdb.minimalistic.sample1;

import java.util.List;

import org.hypergraphdb.HGHandle;
import org.hypergraphdb.HGPlainLink;
import org.hypergraphdb.HGQuery.hg;
import org.hypergraphdb.HGTypeSystem;
import org.hypergraphdb.HyperGraph;
import org.hypergraphdb.atom.HGRel;
import org.hypergraphdb.atom.HGRelType;

public class Sample1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HyperGraph graph = new HyperGraph("c:/tmp/hypergraphdb/sample1");
		HGHandle handle1 = graph.add("Hello World");
		HGHandle handle2 = hg.assertAtom(graph, "Hello China");
		HGHandle handle3 = hg.assertAtom(graph, "Hello Beijing");
		String hello = graph.get(handle1);
		System.out.println(hello.toLowerCase());
		System.out.println("------------");
		System.out.println(hg.getOne(graph, hg.type(String.class)));
		System.out.println("------------");
		for (Object str : hg.getAll(graph, hg.type(String.class))) {
			System.out.println(str);
		}

		// ---Link
		System.out.println("------------");
		HGHandle handlehenry = graph.add("Henry Han");
		HGHandle handleJorbin = graph.add("Jorbin Zhu");
		HGHandle duplicateLink = graph.add(new HGPlainLink(handlehenry,
				handleJorbin));

		List<HGHandle> dupsList = hg.findAll(graph,
				hg.link(handlehenry, handleJorbin));
		System.out.println("querying for link returned that duplicate Link? :"
				+ dupsList.contains(duplicateLink));
		//
		HGTypeSystem hts = graph.getTypeSystem();
		hts.getTop();
		HGHandle duplicateRelType = graph.add(new HGRelType("duplicates!", hts
				.getTop(), hts.getTop()));
		HGHandle hgrelDuplicateLink = graph.add(new HGRel(handle1, handle2),
				duplicateRelType);
		System.out.println("does handle2 have a duplicate? : "
				+ hg.findAll(graph,
						hg.and(hg.link(handle2), hg.type(duplicateRelType)))
						.contains(hgrelDuplicateLink));

		//
		Book book1 = new Book();
		book1.setAuthor("author1");
		book1.setTitle("title1");

		Book book2 = new Book();
		book2.setAuthor("author2");
		book2.setTitle("title2");

		Book book3 = new Book();
		book3.setAuthor("author3");
		book3.setTitle("title3");

		graph.add(book1);
		graph.add(book2);
		graph.add(book3);

		List<Book> searchBook1 = hg.getAll(
				graph,
				hg.and(hg.type(Book.class),
						hg.or(hg.eq("title", "title1"),
								hg.eq("author", "author1"))));
		List<Book> searchNoBook1 = hg.getAll(
				graph,
				hg.and(hg.type(Book.class),
						hg.not(hg.or(hg.eq("title", "title1"),
								hg.eq("author", "author1")))));

		for (Book n : searchBook1)
			System.out.println("Book1: title:" + n.getTitle()
					+ " , author: " + n.getAuthor());

		for (Book n : searchNoBook1)
			System.out.println("Not a Book1: title:" + n.getTitle()
					+ " , author: " + n.getAuthor());

		graph.close();

	}

}
