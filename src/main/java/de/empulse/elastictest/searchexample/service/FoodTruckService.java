/**
 * 
 */
package de.empulse.elastictest.searchexample.service;

import java.util.List;

import de.empulse.elastictest.searchexample.model.FoodTruck;

/**
 * Service for {@link FoodTruck}.
 * 
 * @author Christoph Guse
 *
 */
public interface FoodTruckService {

	/**
	 * Creates a {@link FoodTruck} object in the persistence.
	 * 
	 * @param foodTruck
	 * @return the persisted {@link FoodTruck}
	 */
	FoodTruck saveFoodTruck(FoodTruck foodTruck);

	/**
	 * Gets all {@link FoodTruck}s matching the given search criteria.
	 * 
	 * Criteria is only available for simple queries, not for nested queries.
	 * 
	 * @param foodTruckSearch
	 * @return
	 */
	List<FoodTruck> findFoodTrucksUsingCriteria(FoodTruckSearch foodTruckSearch);

	/**
	 * Gets all {@link FoodTruck}s matching the given search criteria.
	 * 
	 * @param foodTruckSearch
	 * @return
	 */
	List<FoodTruck> findFoodTrucksUsingQueryBuilder(
			FoodTruckSearch foodTruckSearch);
}
