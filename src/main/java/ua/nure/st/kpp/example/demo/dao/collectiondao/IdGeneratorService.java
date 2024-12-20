package ua.nure.st.kpp.example.demo.dao.collectiondao;

import java.util.concurrent.atomic.AtomicLong;

public class IdGeneratorService {
	final AtomicLong id;

	public IdGeneratorService() {
		super();
		this.id = new AtomicLong();
	}
	
	public Long nextId() {
		return id.incrementAndGet();
	}
}
