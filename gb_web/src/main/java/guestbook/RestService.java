package guestbook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;

import com.google.gson.Gson;

@Path("/guestbook")
public class RestService {

	@Context
	private ContextResolver<EntryDAO> entryDAOProvider;

	EntryDAO getEntryDAO() {
		return entryDAOProvider.getContext(null);
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/entries")
	/**
	 * 	 * @return JSON string representing list of entries sorted by <code>Date</code> field starting from the most recent.
	 */
	public String getEntries() {
		Gson gson = new Gson();
		List<Entry> entries = getEntryDAO().getEntries(); // Get entries from DAO
		entries = new ArrayList<Entry>(entries); // Need mutable list to sort
		Collections.sort(entries, new Comparator<Entry>(){
			@Override
			public int compare(Entry o1, Entry o2) {				
				return o1.getDate().before(o2.getDate()) ? 1 : -1;
			}			
		});
		String json = gson.toJson(entries);		
		return json;
	}
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Path("/entries")
	/**
	 * 	 Adds new entry to the list
	 */
	public void postEntry(String json) {
		Gson gson = new Gson();
		Entry entry = gson.fromJson(json, Entry.class);
		entry.setDate(new Date());
		getEntryDAO().addEntry(entry);
	}

}
