package com.project.tapia.tienda.models.response;

import com.project.tapia.tienda.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse implements Serializable {
    private List<Product> prods;
}
