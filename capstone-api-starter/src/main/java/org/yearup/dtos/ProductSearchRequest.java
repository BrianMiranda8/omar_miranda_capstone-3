package org.yearup.dtos;

public class ProductSearchRequest {
    private Integer cat;
    private Double minPrice;
    private Double maxPrice;
    private String subCategory;

    public ProductSearchRequest() {

    }

    public ProductSearchRequest(Integer cat, Double minPrice, Double maxPrice, String subCategory) {
        this.cat = cat;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.subCategory = subCategory;
    }

    public Integer getCat() {
        return cat;
    }

    public void setCat(Integer cat) {
        this.cat = cat;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }
}
