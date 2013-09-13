package guestbook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class RestServiceJsonTest {
	
	@Test
	public void testPostEntry() throws Exception {
		
		final Entry[] posted = new Entry[1];
		
		final EntryDAO mock = new EntryDAO() {
			
			@Override
			public List<Entry> getEntries() {
				throw new AssertionError("unexpected call");
			}
			
			@Override
			public void addEntry(Entry entry) {
				posted[0] = entry;
			}
		};
		
		RestService classUnderTest = new RestService() {

			@Override
			EntryDAO getEntryDAO() {
				return mock;
			}
			
		};
		
		classUnderTest.postEntry("{ name: 'hugo', text:'huhu' }");
		
		Entry entry = posted[0];
		assertNotNull(entry);
		assertEquals("hugo", entry.getName());
		assertEquals("huhu", entry.getText());
		long postTimestamp = entry.getDate().getTime();
		long now = System.currentTimeMillis();
		
		assertTrue(now - 1000 < postTimestamp && postTimestamp < now + 1000);
		
	}

	@Test
	public void testGetEntries() throws Exception {
		
		final EntryDAO stub = new EntryDAO() {
			
			@Override
			public List<Entry> getEntries() {
				Entry entry = new Entry();
				entry.setName("hugo");
				entry.setText("huhu");
				entry.setDate(new Date(0));
				return Collections.singletonList(entry);
			}
			
			@Override
			public void addEntry(Entry entry) {
				throw new AssertionError("unexpected call");
			}
		};
		
		RestService classUnderTest = new RestService() {

			@Override
			EntryDAO getEntryDAO() {
				return stub;
			}
			
		};
		
		String json = classUnderTest.getEntries();
		assertEquals("[{\"name\":\"hugo\",\"text\":\"huhu\",\"date\":\"Jan 1, 1970 1:00:00 AM\"}]", json);
	}
	
	@Test
	public void testSort(){
final EntryDAO stub = new EntryDAO() {
			
			@Override
			public List<Entry> getEntries() {
				List<Entry> list = new ArrayList<Entry>();
				
				Entry entry = new Entry();
				entry.setName("hugo");
				entry.setText("huhu");
				entry.setDate(new Date(0));				
				list.add(entry);
				
				entry = new Entry();
				entry.setName("john");
				entry.setText("hello");
				entry.setDate(new Date(1000));				
				list.add(entry);
				return list;
			}
			
			@Override
			public void addEntry(Entry entry) {
				throw new AssertionError("unexpected call");
			}
		};
		
		RestService classUnderTest = new RestService() {

			@Override
			EntryDAO getEntryDAO() {
				return stub;
			}
			
		};
		
		String json = classUnderTest.getEntries();
		assertEquals("[{\"name\":\"john\",\"text\":\"hello\",\"date\":\"Jan 1, 1970 1:00:01 AM\"},{\"name\":\"hugo\",\"text\":\"huhu\",\"date\":\"Jan 1, 1970 1:00:00 AM\"}]", json);
	}
}
