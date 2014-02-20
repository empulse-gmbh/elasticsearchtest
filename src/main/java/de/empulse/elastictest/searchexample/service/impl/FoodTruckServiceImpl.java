/**
 * 
 */
package de.empulse.elastictest.searchexample.service.impl;

import static org.elasticsearch.index.query.FilterBuilders.boolFilter;
import static org.elasticsearch.index.query.FilterBuilders.geoDistanceFilter;
import static org.elasticsearch.index.query.FilterBuilders.nestedFilter;
import static org.elasticsearch.index.query.FilterBuilders.queryFilter;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.fuzzyLikeThisFieldQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;

import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;

import de.empulse.elastictest.searchexample.model.FoodTruck;
import de.empulse.elastictest.searchexample.model.TimeRange;
import de.empulse.elastictest.searchexample.repository.FoodTruckRepository;
import de.empulse.elastictest.searchexample.service.FoodTruckSearch;
import de.empulse.elastictest.searchexample.service.FoodTruckService;

/**
 * Implementation using {@link FoodTruckRepository} for service implementation.
 * 
 * @author Christoph Guse
 *
 */
public class FoodTruckServiceImpl implements FoodTruckService {

	private FoodTruckRepository foodTruckRepository;

	/**
	 * @param foodTruckRepository
	 *            the foodTruckRepository to set
	 */
	public void setFoodTruckRepository(FoodTruckRepository foodTruckRepository) {
		this.foodTruckRepository = foodTruckRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FoodTruck saveFoodTruck(FoodTruck foodTruck) {

		if (foodTruck != null) {
			return foodTruckRepository.save(foodTruck);
		}

		return foodTruck;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FoodTruck> findFoodTrucksUsingQueryBuilder(
			FoodTruckSearch foodTruckSearch) {

		BoolFilterBuilder boolFilter = boolFilter();

		/*
		 * add search criteria by description
		 */
		if (StringUtils.isNotBlank(foodTruckSearch.getDescriptionLike())) {

			boolFilter.must(queryFilter(fuzzyLikeThisFieldQuery("description")
					.likeText(foodTruckSearch.getDescriptionLike())));

		}

		/*
		 * add search criteria by timerange. it is assumed in timerange from is
		 * always before to.
		 */
		TimeRange searchTimeRange = foodTruckSearch.getSearchTimeRange();
		if (searchTimeRange != null) {

			String startTimePath = "location.timeRange.from";
			String endTimePath = "location.timeRange.to";

			boolFilter.must(nestedFilter(
					"location.timeRange",
					boolQuery().should(
							rangeQuery(startTimePath).from(
									searchTimeRange.getFrom().getTime()).to(
									searchTimeRange.getTo().getTime())).should(
							rangeQuery(endTimePath).from(
									searchTimeRange.getFrom().getTime()).to(
									searchTimeRange.getTo().getTime()))));

		}

		/*
		 * add search criteria for radius search
		 */
		if (foodTruckSearch.getLatitude() != null
				&& foodTruckSearch.getLongitude() != null) {

			if (foodTruckSearch.getSearchRadiusInKilometers() == null) {
				foodTruckSearch.setSearchRadiusInKilometers(5);
			}

			boolFilter.must(nestedFilter(
					"location",
					geoDistanceFilter("point")
							.distance(
									foodTruckSearch
											.getSearchRadiusInKilometers(),
									DistanceUnit.KILOMETERS)
							.lat(foodTruckSearch.getLatitude())
							.lon(foodTruckSearch.getLongitude())));

		}

		return IteratorUtils.toList(foodTruckRepository.search(
				new NativeSearchQuery(matchAllQuery(), boolFilter)).iterator());

	}

}
