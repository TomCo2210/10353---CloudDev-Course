package demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DemoServiceKeyValueStoreConsumer implements DemoService{
	private RestTemplate restTemplate;
	
	private String url;
	
	public DemoServiceKeyValueStoreConsumer() {
		this.restTemplate = new RestTemplate();
	}
	
	@Value("${storage.service.url}")
	public void setUrl(String url) {
		this.url = url;
		System.err.println("****** " + this.url);
	}
	
	@Override
	public Demo create(Demo demo) {
		demo.setId(null);
		// consuming other service method using POST
		KeyObjectPair keyObject = this.restTemplate
			.postForObject(
					this.url, 
					demo, 
					KeyObjectPair.class);
		Demo newObject = keyObject.getObject();
		newObject.setId(keyObject.getKey());
		updateDemo(keyObject.getKey(), newObject);
		return newObject;
	}

	@Override
	public Demo getDemoById(String key) {
		// consuming other service method using GET
		return this.restTemplate
			.getForObject(
					this.url + "/{id}", 
					Demo.class, 
					key);
	}

	@Override
	public Demo updateDemo(String id, Demo update) {
		// consuming other service method using PUT
		this.restTemplate
			.put(
				this.url + "/{id}", 
				update, 
				id);
		
		return getDemoById(id);
	}

	@Override
	public void deleteAllDemoes() {
		// consuming other service method using DELETE
		this.restTemplate
			.delete(this.url);
		
	}

}
