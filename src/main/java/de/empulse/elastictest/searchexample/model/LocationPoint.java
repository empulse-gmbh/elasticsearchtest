/**
 * 
 */
package de.empulse.elastictest.searchexample.model;

import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.NestedField;

/**
 * A {@link LocationPoint} describes a geographic point including latitude,
 * longitude and the address as text.
 * <p>
 * Additionally a {@link TimeRange} is given to mark the time period when this
 * {@link LocationPoint} is valid.
 * </p>
 * 
 * @author Christoph Guse
 *
 */
public class LocationPoint {

	private Double latitude;
	private Double longitude;
	private String address;

	@NestedField(type = FieldType.Object, index = FieldIndex.analyzed, store = true, dotSuffix = "timeRange")
	private TimeRange timeRange;

	/**
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
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
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the timeRange
	 */
	public TimeRange getTimeRange() {
		return timeRange;
	}

	/**
	 * @param timeRange
	 *            the timeRange to set
	 */
	public void setTimeRange(TimeRange timeRange) {
		this.timeRange = timeRange;
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
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result
				+ ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result
				+ ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result
				+ ((timeRange == null) ? 0 : timeRange.hashCode());
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
		LocationPoint other = (LocationPoint) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
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
		if (timeRange == null) {
			if (other.timeRange != null)
				return false;
		} else if (!timeRange.equals(other.timeRange))
			return false;
		return true;
	}

}
