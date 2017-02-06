import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.http.HTTPException;
import java.io.*;
import java.util.StringTokenizer;

public class DocumentsServlet extends HttpServlet {
	private Documents documents;
	
	@Override
	public void init() {
		documents = new Documents();
		documents.setServletContext(this.getServletContext());
	}
	
	// GET /storage/documents/{documentID}
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		String param = parseURL(request.getRequestURI());
		
		if(param == null) {
			throw new HTTPException(HttpServletResponse.SC_NOT_FOUND);			
		} else {
			// get the specific document
			Document d = documents.getMap().get(param);
			
			if(d == null) {
				throw new HTTPException(HttpServletResponse.SC_NOT_FOUND);
			} else {
				// print out the contents in the response
				sendResponse(response, d.getContents() + " \n");
			}
		}
	}
	
	// gets the path of the url and returns it as a string
	private String parseURL(String url) {
		StringTokenizer st = new StringTokenizer(url, "/");
		st.nextToken(); // DocumentStorage/..
		st.nextToken(); // storage/..
		st.nextToken(); // documents/..
		if(st.hasMoreTokens()) {
			return st.nextToken();
		} else {
			return null;
		}
	}
	
	// POST /storage/documents
	// HTTP body should contain the document contents as data
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		InputStream body = request.getInputStream();
		// is the data in the request present?
		if(body == null) {
			throw new HTTPException(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(body));
		String contents = br.readLine();
		
		// create new document with the contents
		Document d = new Document();
		d.setContents(contents);
		String id = documents.addDocument(d);
		sendResponse(response, id + "\n");
	}
	
	// DELETE /storage/documents/{documentID}	
	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response) {
		String param = parseURL(request.getRequestURI());
		if(param == null) {
			throw new HTTPException(HttpServletResponse.SC_BAD_REQUEST);
		}
		try {
			Document d = documents.getMap().get(param);
			// is there a document with the given id?
			if(d == null) {
				throw new HTTPException(HttpServletResponse.SC_NOT_FOUND);
			} else {
				documents.getMap().remove(param);
				response.setStatus(204);
			}
		} catch (Exception e) {
			throw new HTTPException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
	
	// PUT /source/documents/{documentID}
	// HTTP body should contain the contents as data
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String key = parseURL(request.getRequestURI());
		InputStream body = request.getInputStream();
		if(key == null || body == null) {
			throw new HTTPException(HttpServletResponse.SC_NOT_FOUND);
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(body));
		String contents = br.readLine();
		
		Document d = documents.getMap().get(key);
		if(d == null) {
			throw new HTTPException(HttpServletResponse.SC_NOT_FOUND);
		} else {
			d.setContents(contents);
			response.setStatus(204);
		}	
	}
	
	// helper method which sends takes in a response variable and a string 
	// writes the string in the http response
	private void sendResponse(HttpServletResponse response, String payload) {
		try{
			OutputStream out = response.getOutputStream();
			out.write(payload.getBytes());
			out.flush();
			
		} catch(Exception e) {
			throw new HTTPException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
