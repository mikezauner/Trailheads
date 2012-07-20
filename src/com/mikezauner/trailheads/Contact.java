package com.mikezauner.trailheads;

public class Contact {
// Column names...
	String _name;
	String _coords;
	int _difficulty;
	String _description;
	String _facilities;
	int _distance;
// Empty constructor.
	public Contact() {
		
	}
// Constructor
	public Contact(String name, String coords, int difficulty, String description, String facilities, int distance) {
		this._name = name;
		this._coords = coords;
		this._difficulty = difficulty;
		this._description = description;
		this._facilities = facilities;
		this._distance = distance;
	}
// getting the name of the trail.
	public String getName() {
		return this._name;
	}
// Setting the name.
	public void setName(String name) {
		this._name = name;
	}
// Getting the Coords to the trailhead.
	public String getCoords() {
		return this._coords;
	}
// Setting the coords.
	public void setCoords(String coords) {
		this._coords = coords;
	}
// Getting difficulty rating
	public int getDifficulty() {
		return this._difficulty;
	}
// Setting difficulty rating
	public void setDifficulty(int difficulty) {
		this._difficulty = difficulty;
	}
// Getting the description
	public String getDescription() {
		return this._description;
	}
// Setting the description
	public void setDescription(String description) {
		this._description = description;
	}
// Get Facilities info.
	public String getFacilities() {
		return this._facilities;
	}
// Set facilities info.
	public void setFacilities(String facilities) {
		this._facilities = facilities;
	}
// Get round trip distance
	public int getDistance() {
		return this._distance;
	}
// set round trip distance
	public void setDistance(int distance) {
		this._distance = distance;
	}
}
