package com.example.demo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SongsListsServiceCrud 
	extends ReactiveMongoRepository<SongsList, String>{
	
	

}
