package guestbook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;

/**
 * 
 * A data access object (DAO) for the Entry entity
 * 
 * EntryJOADao uses JPA persistence technology to connect to the default 
 * database context (java:comp/env/jdbc/DefaultDB).
 * 
 * @author tendlich, pakunz
 *
 */
public class EntryJPADAO implements EntryDAO {
	
	// used for the database lookup in the context
	private final String lookupString = "java:comp/env/jdbc/DefaultDB";
	
	private EntityManagerFactory emf;
	
	/**
	 * Creates a DAO with the default data source
	 */
	public EntryJPADAO() throws RuntimeException, NullPointerException {

		DataSource ds = obtainDataSource();
		setup(ds);
	}
	
	/**
	 * Creates a DAO with the 
	 * @param DataSource The data source to connect to
	 */
	public EntryJPADAO(DataSource ds) throws NullPointerException {
		setup(ds);
	}
	
	private void setup(DataSource ds) throws NullPointerException
	{
		Map<String, DataSource> properties = new HashMap<String, DataSource>();
		properties.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, ds);
		emf = Persistence.createEntityManagerFactory("guestbookTeam3", properties);
		if(emf == null) {
			throw new NullPointerException("could not create Entity Manager Factory");
		}
	}
	
	@Override
	public List<Entry> getEntries() {
		EntityManager em = emf.createEntityManager();
		@SuppressWarnings("unchecked")
		List<Entry> returnList = (List<Entry>) em.createNamedQuery("getAllEntries").getResultList();
		em.close();
		return returnList;
	}

	@Override
	public void addEntry(Entry entry) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(entry);
		em.getTransaction().commit();
		em.close();
	}
	
	private DataSource obtainDataSource() throws RuntimeException {
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			return (DataSource) ctx.lookup(lookupString);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}

}
