package br.com.jsilva.awesome.repository;

import br.com.jsilva.awesome.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User,Long>{
    User findByUserName(String userName);
}
