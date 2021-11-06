package response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class ProductTagResponse implements IResponse{
    private String id;
    private String category;
}
