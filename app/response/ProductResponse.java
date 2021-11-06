package response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import request.ProductColorRequest;
import request.ProductImageRequest;
import request.ProductSizeRequest;
import request.ProductTagRequest;

import java.util.List;

@NoArgsConstructor
@SuperBuilder
@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class ProductResponse implements IResponse{
    private String id;
    private String code;
    private String name;
    private String description;
    private Integer stock;
    private Double price;
    private Double discount;
    private Integer rating;
    private Double salePrice;
    private String category;
    private Boolean deleted;
    private Boolean active;

    private List<ProductTagResponse> tags;
    private List<ProductSizeResponse> size;
    private List<ProductColorResponse> colors;
    private List<ProductImageResponse> pictures;

}
