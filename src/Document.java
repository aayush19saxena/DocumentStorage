

import java.io.Serializable;

public class Document {
	private String contents;
	private String id; 
	
	public Document() { }
	
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public String getContents() {
		return this.contents;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
}
