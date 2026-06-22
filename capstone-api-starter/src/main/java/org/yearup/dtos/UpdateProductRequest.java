package org.yearup.dtos;

public class UpdateProductRequest {
    private int quantity;

    public UpdateProductRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
