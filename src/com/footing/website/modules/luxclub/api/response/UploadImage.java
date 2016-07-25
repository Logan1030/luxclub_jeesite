package com.footing.website.modules.luxclub.api.response;

public class UploadImage {
    private String imageName;
    private String imageUrl;
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public UploadImage(String imageName, String imageUrl) {
		super();
		this.imageName = imageName;
		this.imageUrl = imageUrl;
	}
	public UploadImage() {
		super();
	}
    
}
