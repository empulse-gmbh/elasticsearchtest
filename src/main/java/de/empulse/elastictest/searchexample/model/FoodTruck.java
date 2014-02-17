/**
 * 
 */
package de.empulse.elastictest.searchexample.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.NestedField;

/**
 * FoodTruck is just an example of an model which is for a certain time at a
 * certain place.
 * 
 * <p>
 * <img src="doc-files/FoodTruck.png"/>
 * </p>
 * 
 * @author Christoph Guse
 *
 */
@Document(indexName = "searchexample", type = "foodtruck", indexStoreType = "memory", shards = 1, replicas = 0, refreshInterval = "-1")
public class FoodTruck {

	@Id
	private String id;

	private String description;

	@NestedField(type=FieldType.Object, index = FieldIndex.analyzed, store = true, dotSuffix="locationPoint")
	private LocationPoint locationPoint;

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the locationPoint
	 */
	public LocationPoint getLocationPoint() {
		return locationPoint;
	}

	/**
	 * @param locationPoint
	 *            the locationPoint to set
	 */
	public void setLocationPoint(LocationPoint locationPoint) {
		this.locationPoint = locationPoint;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((locationPoint == null) ? 0 : locationPoint.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FoodTruck other = (FoodTruck) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (locationPoint == null) {
			if (other.locationPoint != null)
				return false;
		} else if (!locationPoint.equals(other.locationPoint))
			return false;
		return true;
	}

}
