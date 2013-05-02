package by.binfo.BusinessInform;


public class Company {
	private String id, name, region, district, city, street, building_no,
			eMail, site, keyWords;
	private String rubric = "", pic = "", direction = "", phone = "";
	private int zip, vip = 0;

	public Company() {

	}

	public Company(String id, String name, String region, String district,
			String city, String street, int zip, String building_no, String phone,
			String eMail, String site, String keyWords, String rubric,
			String pic, String direction) {
		this.id = id;
		this.name = name;
		this.region = region;
		this.district = district;
		this.city = city;
		this.street = street;
		this.zip = zip;
		this.building_no = building_no;
		this.phone = phone;
		this.eMail = eMail;
		this.site = site;
		this.keyWords = keyWords;
		this.rubric = rubric;
		this.pic = pic;
		this.direction = direction;
	}

	public Company(String id, String name, int zip, String region, String city,
			String rubric, String pic, int vip) {
		this.id = id;
		this.name = name;
		this.region = region;
		this.city = city;
		this.zip = zip;
		this.rubric = rubric;
		this.pic = pic;
		this.vip = vip;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		if (district.equals("")) {
			district = " ";
		}
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public String getBuilding_no() {
		return building_no;
	}

	public void setBuilding_no(String building_no) {
		this.building_no = building_no;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		if (phone != "") {
			this.phone= this.phone + phone + "\n";
		}
	}


	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getRubric() {
		return rubric;
	}

	public void setRubric(String rubric) {
		if (rubric != "") {
			this.rubric = this.rubric + rubric + " ";
		}
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		if (!this.direction.equals(direction + ", ") & direction != "") {
			this.direction = this.direction + direction + ", ";
		}
	}

	public String[] getPic() {
		String[] arr = pic.split(", ");
	    return arr;
	}
	
	public String getPicString(){
		return pic;
	}

	public void setPic(String pic) {
		if (pic != "") {
			this.pic = this.pic + pic + ", ";
		}
	}
	
	public int getVip() {
		return vip;
	}

	public void setVip(int vip) {
		this.vip = vip;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ",\n name=" + name + ", region=" + region
				+ ", zip=" + zip + ", city=" + city
				+ ", rubric=  " + rubric + "pics:" + pic + "]";
	}
	
	public String getAddress(){
		return region + " " + district + " " + city + " " + street + " " + building_no;
	}

}
