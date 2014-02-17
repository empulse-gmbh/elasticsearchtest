/**
 * 
 */
package de.empulse.elastictest.searchexample.service;

import de.empulse.elastictest.searchexample.model.TimeRange;

/**
 * Search criteria object. All given search criteria is used as AND.
 * 
 * @author Christoph Guse
 *
 */
public class FoodTruckSearch {

	private String descriptionLike;
	
	private Double latitude;
	
	private Double longitude;
	
	private Integer searchRadiusInKilometers;
	
	private TimeRange searchTimeRange;

	/**
	 * @return the descriptionLike
	 */
	public String getDescriptionLike() {
		return descriptionLike;
	}

	/**
	 * @param descriptionLike the descriptionLike to set
	 */
	public void setDescriptionLike(String descriptionLike) {
		this.descriptionLike = descriptionLike;
	}

	/**
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the searchRadiusInKilometers
	 */
	public Integer getSearchRadiusInKilometers() {
		return searchRadiusInKilometers;
	}

	/**
	 * @param searchRadiusInKilometers the searchRadiusInKilometers to set
	 */
	public void setSearchRadiusInKilometers(Integer searchRadiusInKilometers) {
		this.searchRadiusInKilometers = searchRadiusInKilometers;
	}

	/**
	 * @return the searchTimeRange
	 */
	public TimeRange getSearchTimeRange() {
		return searchTimeRange;
	}

	/**
	 * @param searchTimeRange the searchTimeRange to set
	 */
	public void setSearchTimeRange(TimeRange searchTimeRange) {
		this.searchTimeRange = searchTimeRange;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descriptionLike == null) ? 0 : descriptionLike.hashCode());
		result = prime * result
				+ ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result
				+ ((longitude == null) ? 0 : longitude.hashCode());
		result = prime
				* result
				+ ((searchRadiusInKilometers == null) ? 0
						: searchRadiusInKilometers.hashCode());
		result = prime * result
				+ ((searchTimeRange == null) ? 0 : searchTimeRange.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		FoodTruckSearch other = (FoodTruckSearch) obj;
		if (descriptionLike == null) {
			if (other.descriptionLike != null)
				return false;
		} else if (!descriptionLike.equals(other.descriptionLike))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (searchRadiusInKilometers == null) {
			if (other.searchRadiusInKilometers != null)
				return false;
		} else if (!searchRadiusInKilometers
				.equals(other.searchRadiusInKilometers))
			return false;
		if (searchTimeRange == null) {
			if (other.searchTimeRange != null)
				return false;
		} else if (!searchTimeRange.equals(other.searchTimeRange))
			return false;
		return true;
	}
	
	
	
}
