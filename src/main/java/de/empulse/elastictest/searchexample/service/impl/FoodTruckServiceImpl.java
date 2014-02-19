/**
 * 
 */
package de.empulse.elastictest.searchexample.service.impl;

import static org.elasticsearch.index.query.FilterBuilders.geoDistanceFilter;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.fuzzyLikeThisFieldQuery;
import static org.elasticsearch.index.query.QueryBuilders.nestedQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
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

	private ElasticsearchTemplate elasticSearchTemplate;

	/**
	 * @param foodTruckRepository
	 *            the foodTruckRepository to set
	 */
	public void setFoodTruckRepository(FoodTruckRepository foodTruckRepository) {
		this.foodTruckRepository = foodTruckRepository;
	}

	/**
	 * @param elasticSearchTemplate
	 *            the elasticSearchTemplate to set
	 */
	public void setElasticSearchTemplate(
			ElasticsearchTemplate elasticSearchTemplate) {
		this.elasticSearchTemplate = elasticSearchTemplate;
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
	@Override
	public List<FoodTruck> findFoodTrucksUsingCriteria(
			FoodTruckSearch foodTruckSearch) {

		if (foodTruckSearch != null) {
			Criteria searchCriteria = new Criteria();

			/*
			 * add search criteria by description
			 */
			if (StringUtils.isNotBlank(foodTruckSearch.getDescriptionLike())) {

				searchCriteria = searchCriteria.and(new Criteria("description")
						.fuzzy(foodTruckSearch.getDescriptionLike()));

			}

			/*
			 * add search criteria by timerange. it is assumed in timerange from
			 * is always before to.
			 */
			TimeRange searchTimeRange = foodTruckSearch.getSearchTimeRange();
			if (searchTimeRange != null) {

				String startTimePath = "locationPoint.timeRange.from";
				String endTimePath = "locationPoint.timeRange.to";

				searchCriteria = searchCriteria.and(
						new Criteria(startTimePath).between(searchTimeRange
								.getFrom().getTime(), searchTimeRange.getTo()
								.getTime())).or(
						new Criteria(endTimePath).between(searchTimeRange
								.getFrom().getTime(), searchTimeRange.getTo()
								.getTime()));

			}

			/*
			 * add search criteria for radius search
			 */
			if (foodTruckSearch.getLatitude() != null
					&& foodTruckSearch.getLongitude() != null) {

				if (foodTruckSearch.getSearchRadiusInKilometers() == null) {
					foodTruckSearch.setSearchRadiusInKilometers(5);
				}

				// what to do to match the custom LocationPoint to GeoPoint?
				searchCriteria = searchCriteria.and(new Criteria(
						"locationPoint").within(
						new GeoPoint(foodTruckSearch.getLatitude(),
								foodTruckSearch.getLongitude()),
						foodTruckSearch.getSearchRadiusInKilometers() + "km"));

			}

			return elasticSearchTemplate.queryForList(new CriteriaQuery(
					searchCriteria), FoodTruck.class);

		}

		return new ArrayList<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FoodTruck> findFoodTrucksUsingQueryBuilder(
			FoodTruckSearch foodTruckSearch) {

		BoolQueryBuilder searchQuery = boolQuery();

		/*
		 * add search criteria by description
		 */
		if (StringUtils.isNotBlank(foodTruckSearch.getDescriptionLike())) {

			searchQuery.must(fuzzyLikeThisFieldQuery("description").likeText(
					foodTruckSearch.getDescriptionLike()));

		}

		/*
		 * add search criteria by timerange. it is assumed in timerange from is
		 * always before to.
		 */
		TimeRange searchTimeRange = foodTruckSearch.getSearchTimeRange();
		if (searchTimeRange != null) {

			String startTimePath = "location.timeRange.from";
			String endTimePath = "location.timeRange.to";

			searchQuery.must(nestedQuery(
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
		FilterBuilder searchFilter = null;
		if (foodTruckSearch.getLatitude() != null
				&& foodTruckSearch.getLongitude() != null) {

			if (foodTruckSearch.getSearchRadiusInKilometers() == null) {
				foodTruckSearch.setSearchRadiusInKilometers(5);
			}
						
			searchFilter = geoDistanceFilter("location.point")
					.distance(
							foodTruckSearch.getSearchRadiusInKilometers()
									+ "km").lat(foodTruckSearch.getLatitude())
					.lon(foodTruckSearch.getLongitude());

		}

		if (searchFilter != null) {
			NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(
					searchQuery, searchFilter);
			return IteratorUtils.toList(foodTruckRepository.search(
					nativeSearchQuery).iterator());
		} else {
			return IteratorUtils.toList(foodTruckRepository.search(searchQuery)
					.iterator());
		}

	}

}
