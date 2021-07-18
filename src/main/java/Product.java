public class Product {

    private int categoryId;
    private boolean discontinued;
    private String productName;
    private String quantityPerUnit;
    private int reorderLevel;
    private int supplierId;
    private int unitPrice;
    private int unitsInStock;
    private int unitsOnOrder;

    public Product() {
    }

    public Product(int categoryId, boolean discontinued, String productName,
        String quantityPerUnit, int reorderLevel, int supplierId, int unitPrice, int unitsInStock,
        int unitsOnOrder) {
        this.categoryId = categoryId;
        this.discontinued = discontinued;
        this.productName = productName;
        this.quantityPerUnit = quantityPerUnit;
        this.reorderLevel = reorderLevel;
        this.supplierId = supplierId;
        this.unitPrice = unitPrice;
        this.unitsInStock = unitsInStock;
        this.unitsOnOrder = unitsOnOrder;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(boolean discontinued) {
        this.discontinued = discontinued;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantityPerUnit() {
        return quantityPerUnit;
    }

    public void setQuantityPerUnit(String quantityPerUnit) {
        this.quantityPerUnit = quantityPerUnit;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(int unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    public int getUnitsOnOrder() {
        return unitsOnOrder;
    }

    public void setUnitsOnOrder(int unitsOnOrder) {
        this.unitsOnOrder = unitsOnOrder;
    }
}
