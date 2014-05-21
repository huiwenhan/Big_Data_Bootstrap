package me.huiwen.example.hypergraphdb.minimalistic.sample1;

import org.hypergraphdb.HGHandle;
import org.hypergraphdb.HGEnvironment;
import org.hypergraphdb.HGValueLink;
import org.hypergraphdb.HyperGraph;

public class Book {

	private String title;
    private String author;
    private int yearPublished;
    public Book() {
    	
    }

  

	public Book(String title, String author) {
		super();
		this.title = title;
		this.author = author;
	}



	public String getTitle() {return title; }
    public void setTitle(String title) {this.title = title;}

    public String getAuthor() {return author;}
    public void setAuthor(String author) {this.author = author;}
    
	public int getYearPublished() {
		return yearPublished;
	}

	public void setYearPublished(int yearPublished) {
		this.yearPublished = yearPublished;
	}

	@Override
	public String toString() {
		return "Book [" + (title != null ? "title=" + title + ", " : "")
				+ (author != null ? "author=" + author + ", " : "")
				+ "yearPublished=" + yearPublished + "]";
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		 HyperGraph graph =  HGEnvironment.get("c:/tmp/hypergraphdb/book");
		  String x = "Hello World";
		  Book mybook = new Book("Critique of Pure Reason", "E. Kant");
		  
		  graph.add(x);
		  HGHandle bookHandle = graph.add(mybook);
		  
		  
		  Object original1= graph.get(bookHandle);
		  if(original1 == null)
		  {
			  System.out.println("originalBook is null ");
		  }
		  if(original1 instanceof Book)
		  {
			  Book originalBook1 = (Book)original1;
			  System.out.println(originalBook1);
		  }
		  
		  graph.add(new double [] {0.9, 0.1, 4.3434});
		// At a later point, update an existing object's value:
		  mybook.setYearPublished(1988);
		  graph.update(mybook);

		  Object original2= graph.get(bookHandle);
		  if(original2 == null)
		  {
			  System.out.println("originalBook is null ");
		  }
		  if(original2 instanceof Book)
		  {
			  Book originalBook2 = (Book)original2;
			  System.out.println(originalBook2);
		  }
		  
		  // At a later point, delete the object:
		  graph.remove(bookHandle);
		  //graph.freeze(bookHandle);
		  
		  Object original3= graph.get(bookHandle);
		  if(original3 == null)
		  {
			  System.out.println("originalBook is null ");
		  }
		  if(original3 instanceof Book)
		  {
			  Book originalBook = (Book)original3;
			  System.out.println(originalBook);
		  }
		  
		 
		  
		  graph.replace(bookHandle, new Book("BookA","AuthorA"));
		  
		  Object original4= graph.get(bookHandle);
		  if(original4 == null)
		  {
			  System.out.println("originalBook is null ");
		  }
		  if(original4 instanceof Book)
		  {
			  Book originalBook4 = (Book)original4;
			  System.out.println(originalBook4);
		  }
		  
		  HGHandle priceHandle = graph.add(9.95);
		  HGValueLink link = new HGValueLink("book_price", bookHandle, priceHandle);
		  graph.add(link);
	}

}
