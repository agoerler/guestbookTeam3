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
		createEmf(ds);
	}
	
	/**
	 * Creates a DAO with the 
	 * @param DataSource The data source to connect to
	 */
	public EntryJPADAO(DataSource ds) throws NullPointerException {
		createEmf(ds);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<Entry> getEntries() {
		EntityManager em = emf.createEntityManager();
		@SuppressWarnings("unchecked")
		List<Entry> returnList = (List<Entry>) em.createNamedQuery("getAllEntries").getResultList();
		em.close();
		return returnList;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void addEntry(Entry entry) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(entry);
		em.getTransaction().commit();
		em.close();
	}
	
	/**
	 * Internal helper method, executes the necessary steps to create an Entity Manager Factory
	 * 
	 * @param DataSource The data source to create persistence managers for
	 * @throws NullPointerException If EMF could not be created
	 */
	private void createEmf(DataSource ds) throws NullPointerException
	{
		Map<String, DataSource> properties = new HashMap<String, DataSource>();
		properties.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, ds);
		emf = Persistence.createEntityManagerFactory("guestbookTeam3", properties);
		if(emf == null) {
			throw new NullPointerException("could not create Entity Manager Factory");
		}
	}
	
	/**
	 * Internal method in order to create a DataSource object for the Entity Manager Factory
	 * 
	 * First, an InitialContext is created using the current environmental parameters.
	 * Second, the context is leveraged to perform a lookup using the default lookupString
	 * 
	 * @return The data source that can be found executing the default context lookup
	 * @throws RuntimeException If the context lookup fails
	 */
	private DataSource obtainDataSource() throws RuntimeException {
		InitialContext ctx;
		try {
			//Note: does not work locally, for local testing create an in-memory derby data base and use that data source
			ctx = new InitialContext();
			return (DataSource) ctx.lookup(lookupString);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}

}
