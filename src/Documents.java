

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletContext;

public class Documents {
	// storing all the documents in a hashmap
	private ConcurrentMap<String, Document> documents;
	private ServletContext sctx;
	
	public Documents() {
		documents = new ConcurrentHashMap<String, Document>();
	}
	
	public void setServletContext(ServletContext sctx) {
		this.sctx =sctx;
	}
	
	public ServletContext getServletContext() {
		return this.sctx;
	}
	
	public ConcurrentMap<String, Document> getMap() {
		if(getServletContext() == null) return null;
		return this.documents;
	}
	
	// adds the document in the hashmap
	public String addDocument(Document d) {
		String id = generateID();
		d.setId(id);
		documents.put(id, d);
		return id;
	}
	
	// helper method that generates an alpha-numeric unique id and returns it as a string
	private static String generateID() {
		char[] id = new char[20];
		String sampleAlphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		for(int i = 0; i < 20; i++) {
			id[i] = sampleAlphaNumeric.charAt(random.nextInt(sampleAlphaNumeric.length()));
		}
		return new String(id);
	}
	
}
