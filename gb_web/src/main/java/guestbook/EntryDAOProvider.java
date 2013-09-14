package guestbook;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class EntryDAOProvider implements ContextResolver<EntryDAO> {

	private EntryDAO instance;

	public EntryDAOProvider() {
	}

	public EntryDAOProvider(EntryDAO instance) {
		this.instance = instance;
	}

	public void setDefaultClass(String defaultClassName) 
	throws RuntimeException, NullPointerException, ClassNotFoundException,  InstantiationException, IllegalAccessException {
		Class<?> defaultClass = Class.forName(defaultClassName);
		instance = (EntryDAO) defaultClass.newInstance();
	}

	@Override
	public EntryDAO getContext(Class<?> type) {
		return instance;
	}

}
