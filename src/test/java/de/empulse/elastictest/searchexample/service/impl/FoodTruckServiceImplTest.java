/**
 * 
 */
package de.empulse.elastictest.searchexample.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import de.empulse.elastictest.searchexample.model.FoodTruck;
import de.empulse.elastictest.searchexample.model.Point;
import de.empulse.elastictest.searchexample.model.builder.FoodTruckBuilder;
import de.empulse.elastictest.searchexample.model.builder.LocationBuilder;
import de.empulse.elastictest.searchexample.model.builder.TimeRangeBuilder;
import de.empulse.elastictest.searchexample.repository.FoodTruckRepository;
import de.empulse.elastictest.searchexample.service.FoodTruckSearch;
import de.empulse.elastictest.searchexample.service.FoodTruckService;

/**
 * @author Christoph Guse
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { FoodTruckServiceImplTestConfiguration.class }, loader = AnnotationConfigContextLoader.class)
public class FoodTruckServiceImplTest {

	@Autowired
	private FoodTruckRepository foodTruckRepository;

	@Autowired
	private FoodTruckService foodTruckService;

	@Before
	public void setUp() {

		// clean up ElasticSearch every time before a Unit test
		foodTruckRepository.deleteAll();
		
	}

	@Test
	public void testSaveFoodTruck() {

		/*
		 * create truck
		 */
		FoodTruck truck = FoodTruckBuilder
				.foodTruck()
				.withDescription("A very nice truck")
				.withLocationPoint(
						LocationBuilder
								.location()
								.withAddress("Cologne City")
								.withPoint(new Point(50.9406645, 6.9599115))
								.withTimeRange(
										TimeRangeBuilder.timeRange()
												.withFrom(createTime(8, 30))
												.withTo(createTime(12, 30))
												.build()).build()).build();
		foodTruckService.saveFoodTruck(truck);

		/*
		 * do search
		 */
		Iterable<FoodTruck> allTrucks = foodTruckRepository.findAll();
		assertTrue("at least one entry expected", allTrucks.iterator()
				.hasNext());

	}
	
	@Test
	public void testFindByDescription(){
		/*
		 * create truck
		 */
		FoodTruck truck = FoodTruckBuilder
				.foodTruck()
				.withDescription("A very nice truck")
				.withLocationPoint(
						LocationBuilder
								.location()
								.withAddress("Cologne City").withPoint(new Point(50.9406645, 6.9599115))
								.withTimeRange(
										TimeRangeBuilder.timeRange()
												.withFrom(createTime(8, 30))
												.withTo(createTime(12, 30))
												.build()).build()).build();
		truck = foodTruckService.saveFoodTruck(truck);
		
		/*
		 * test search by description
		 */
		FoodTruckSearch search = new FoodTruckSearch();
		search.setDescriptionLike("nice");
		List<FoodTruck> trucks = foodTruckService.findFoodTrucksUsingQueryBuilder(search);
		assertEquals(1, trucks.size());
		assertEquals(truck.getDescription(), trucks.get(0).getDescription());
	}
	
	
	@Test
	public void testFindFoodTrucksByTimerange(){
		/*
		 * create truck
		 */
		FoodTruck truck = FoodTruckBuilder
				.foodTruck()
				.withDescription("A very nice truck")
				.withLocationPoint(
						LocationBuilder
								.location()
								.withAddress("Cologne City").withPoint(new Point(50.9406645, 6.9599115))
								.withTimeRange(
										TimeRangeBuilder.timeRange()
												.withFrom(createTime(8, 30))
												.withTo(createTime(12, 30))
												.build()).build()).build();
		truck = foodTruckService.saveFoodTruck(truck);
		
	
		
		/*
		 * create search criteria. search for all trucks which are present
		 * between 9:00 and 14:00
		 */
		FoodTruckSearch search = new FoodTruckSearch();
		search = new FoodTruckSearch();
		search.setSearchTimeRange(TimeRangeBuilder.timeRange().withFrom(createTime(9, 0)).withTo(createTime(14, 00)).build());
		
		List<FoodTruck> trucks = foodTruckService.findFoodTrucksUsingQueryBuilder(search);
		assertFalse("truck expected to be found searching by timerange", trucks.isEmpty());
		
		
	}
	
	@Test
	public void testFindFoodTruckByLocation(){
		/*
		 * create truck
		 */
		FoodTruck truck = FoodTruckBuilder
				.foodTruck()
				.withDescription("A very nice truck")
				.withLocationPoint(
						LocationBuilder
								.location()
								.withAddress("Cologne City").withPoint(new Point(50.9406645, 6.9599115))
								.withTimeRange(
										TimeRangeBuilder.timeRange()
												.withFrom(createTime(8, 30))
												.withTo(createTime(12, 30))
												.build()).build()).build();
		truck = foodTruckService.saveFoodTruck(truck);
		
		/*
		 * search for all trucks in 10 km radius of Cologne.
		 */
		FoodTruckSearch search = new FoodTruckSearch();
		search.setLatitude(50.9406645);
		search.setLongitude(6.9599115);
		search.setSearchRadiusInKilometers(10);
		
		List<FoodTruck> trucks = foodTruckService.findFoodTrucksUsingQueryBuilder(search);
		assertFalse("truck expected to be found searching by radius", trucks.isEmpty());
		assertEquals(truck, trucks.get(0));
		
		/*
		 * search for all trucks in 10 km radius of Berlin.
		 */
		search.setLatitude(52.544547);
		search.setLongitude(13.3831);
		
		trucks = foodTruckService.findFoodTrucksUsingQueryBuilder(search);
		assertTrue("no trucks expected in berlin", trucks.isEmpty());
		
	}

	/**
	 * Creates a Date filled with time only. All information except hour and
	 * minutes are set to 0, year is set to 2000.
	 * 
	 * @param hour
	 * @param minutes
	 * @return
	 */
	private Date createTime(int hour, int minutes) {

		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minutes);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.DATE, 0);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.YEAR, 0);

		return cal.getTime();

	}

}
