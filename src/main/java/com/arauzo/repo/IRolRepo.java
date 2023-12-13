package com.arauzo.repo;


import com.arauzo.model.Rol;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IRolRepo extends ReactiveMongoRepository<Rol, String>{

}
