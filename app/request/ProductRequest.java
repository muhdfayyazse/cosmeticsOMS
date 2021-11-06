package request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@SuperBuilder
@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class ProductRequest implements IRequest{
    private String id;
    private String code;
    private String name;
    private String description;
    private Integer stock;
    private Double price;
    private Double discount;
    private Integer rating;
    private Double salePrice;
    private String categoryId;
    private Boolean deleted;
    private Boolean active;

    private List<ProductTagRequest> productTag;
    private List<ProductSizeRequest> productSize;
    private List<ProductColorRequest> productColor;
    private List<ProductImageRequest> productImage;
}
