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

public class EntryJPADAO implements EntryDAO {

	private EntityManagerFactory emf;
	
	public EntryJPADAO() {
		try {
			DataSource ds = obtainDataSource();
			setup(ds);
		} catch(RuntimeException e) {
			// TO DO
		}
	}
	
	public EntryJPADAO(DataSource ds) {
		setup(ds);
	}
	
	private void setup(DataSource ds)
	{
		Map<String, DataSource> properties = new HashMap<String, DataSource>();
		properties.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, ds);
		emf = Persistence.createEntityManagerFactory("guestbookTeam3", properties);
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
			return (DataSource) ctx.lookup("java:comp/env/jdbc/DefaultDB");
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}

}
