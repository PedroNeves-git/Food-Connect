package br.com.food_connect.Food_Connect.repository;

import br.com.food_connect.Food_Connect.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long>  {

    List<Address> findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
