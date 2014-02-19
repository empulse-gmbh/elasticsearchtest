/**
 * 
 */
package de.empulse.elastictest.searchexample.model;

import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A {@link Location} describes a geographic point including latitude, longitude
 * and the address as text.
 * <p>
 * Additionally a {@link TimeRange} is given to mark the time period when this
 * {@link Location} is valid.
 * </p>
 * 
 * @author Christoph Guse
 *
 */
public class Location {

	private GeoPoint point;
	
	private String address;

	@Field(type = FieldType.Nested)
	private TimeRange timeRange;

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

	/**
	 * @return the point
	 */
	public GeoPoint getPoint() {
		return point;
	}

	/**
	 * @param point
	 *            the point to set
	 */
	public void setPoint(GeoPoint point) {
		this.point = point;
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
		result = prime * result + ((point == null) ? 0 : point.hashCode());
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
		Location other = (Location) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (point == null) {
			if (other.point != null)
				return false;
		} else if (!point.equals(other.point))
			return false;
		if (timeRange == null) {
			if (other.timeRange != null)
				return false;
		} else if (!timeRange.equals(other.timeRange))
			return false;
		return true;
	}

}
