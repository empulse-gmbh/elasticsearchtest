/**
 * 
 */
package de.empulse.elastictest.searchexample.service.impl;

import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import de.empulse.elastictest.searchexample.repository.FoodTruckRepository;
import de.empulse.elastictest.searchexample.service.FoodTruckService;

/**
 * @author Christoph Guse
 *
 */
@Configuration
@EnableElasticsearchRepositories(basePackages="de.empulse.elastictest.searchexample.repository")
public class FoodTruckServiceImplTestConfiguration implements InitializingBean,
		DisposableBean {

	private Node elasticSearchNode;

	@Autowired
	private FoodTruckRepository foodTruckRepository;

	/**
	 * This configuration starts an embedded Elasticsearch instance when the
	 * configuration bean was initialized and closes the instance when this bean
	 * is destroyed.
	 * 
	 * @return
	 * @throws Exception 
	 */
	@Bean
	public ElasticsearchTemplate getElasticsearchTemplate() throws Exception {
		
		if(elasticSearchNode == null){
			afterPropertiesSet();
		}
		
		return new ElasticsearchTemplate(elasticSearchNode.client());
	}

	@Bean
	public ElasticsearchOperations elasticsearchTemplate() throws Exception {
		return getElasticsearchTemplate();
	}

	@Override
	public void destroy() throws Exception {

		// shutdown elasticsearch node
		elasticSearchNode.stop();
		elasticSearchNode.close();

	}

	@Override
	public void afterPropertiesSet() throws Exception {

		// start elasticsearch node
		elasticSearchNode = NodeBuilder.nodeBuilder().clusterName("foodtruck-test").local(true).build();
		elasticSearchNode.start();

	}

	/**
	 * Initializes the {@link FoodTruckService}.
	 * 
	 * @return
	 * @throws Exception 
	 */
	@Bean
	public FoodTruckService getFoodTruckService() throws Exception {

		FoodTruckServiceImpl service = new FoodTruckServiceImpl();
		service.setFoodTruckRepository(foodTruckRepository);

		return service;

	}
}
