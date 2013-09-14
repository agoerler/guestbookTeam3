package guestbook;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author tendlich, pakunz
 *
 */
public class EntryJPADAOTest {

	EntryDAOProvider provider;
	EntryJPADAO entryJPADAO;
	
	@Before
	public void setUp() throws Exception {
	
//		provider = new EntryDAOProvider();
//		provider.setDefaultClass("guestbook.EntryJPADAO");
//		entryJPADAO = (EntryJPADAO) provider.getContext(EntryJPADAO.class); 
		entryJPADAO = new EntryJPADAO(getInMemoryDataSource());
		assertNotNull(entryJPADAO);
	}

	/**
	 * 
	 * Returns an in memory derby data source for mocking purposes
	 * 
	 * @return DataSource The in-memory derby data source
	 */
	private DataSource getInMemoryDataSource() {
		
		BasicDataSource ds = new BasicDataSource();

		ds.setDriverClassName("org.apache.derby.jdbc.EmbeddedDriver");
		ds.setUrl("jdbc:derby:memory:test;create=true");
		ds.setUsername("test");
		ds.setPassword("test");
		
		return ds;
	}
	
	@Test
	public void testGetEntriesEmptyList() {
		// assert that by default is not null
		assertNotNull(entryJPADAO.getEntries());
	}
	
	@Test
	public void testGetEntriesAfterAdding() {
		// assert that an entry is contained
		Entry entry1 = new Entry();
		entry1.setName("Klaus Test");
		entry1.setText("Hello World :)");
		entry1.setDate(new Date());
		entryJPADAO.addEntry(entry1);
		assertTrue(entry1.equals(entryJPADAO.getEntries().get(0)));
	}

	@Test
	public void testGetEntriesAfterAddingTwoEntries() {

		Entry entry1 = new Entry();
		entry1.setName("Klaus Test");
		entry1.setText("Hello World :)");
		entry1.setDate(new Date());
		
		Entry entry2 = new Entry();
		entry2.setName("Peter Test");
		entry2.setText("Hello World!");
		entry2.setDate(new Date());
		
		List<Entry> testList = new ArrayList<Entry>();
		testList.add(entry1);
		testList.add(entry2);
		
		entryJPADAO.addEntry(entry1);
		entryJPADAO.addEntry(entry2);
		
		//Makes sure that every entry is in the result list
		for(Entry persistenceEntry : entryJPADAO.getEntries()) {
			boolean hasPair = false;
			
			testEntryLoop : for(Entry testEntry : testList) {
				if(persistenceEntry.equals(testEntry)) {
					hasPair = true;
					break testEntryLoop;
				}
			}
			
			assertTrue(hasPair);
		}
	}	
	
	
//
//	@Test
//	public void testAddEntry() {
//		fail("Not yet implemented");
//	}
//
}
