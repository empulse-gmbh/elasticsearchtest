/**
 * 
 */
package de.empulse.elastictest.searchexample.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import de.empulse.elastictest.searchexample.model.FoodTruck;

/**
 * The spring-data-elasticsearch repository for {@link FoodTruck}.
 * 
 * @author Christoph Guse
 *
 */
public interface FoodTruckRepository extends ElasticsearchRepository<FoodTruck, String>{
	
}
