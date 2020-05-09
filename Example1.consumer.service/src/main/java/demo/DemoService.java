package demo;

public interface DemoService {

	public Demo create(Demo demo);

	public Demo getDemoById(String id);

	public Demo updateDemo(String id, Demo update);

	public void deleteAllDemoes();

}
