package controllers;

import model.*;
import org.apache.commons.io.FileUtils;
import play.data.FormFactory;
import play.libs.Files;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import request.ProductImageRequest;
import request.ProductRequest;
import response.*;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static play.libs.Json.toJson;


public class ProductController extends Controller {
    private final FormFactory formFactory;
    private final HttpExecutionContext ec;

    private final GenericRepository<Product> productRepository;
    private final GenericRepository<ProductTag> productTagRepository;
    private final GenericRepository<ProductSize> productSizeRepository;
    private final GenericRepository<ProductColor> productColorRepository;
    private final GenericRepository<ProductImage> productImageRepository;

    @Inject
    public ProductController(FormFactory formFactory, HttpExecutionContext ec,
                             GenericRepository<Product> productRepository,
                             GenericRepository<ProductTag> productTagRepository,
                             GenericRepository<ProductSize> productSizeRepository,
                             GenericRepository<ProductColor> productColorRepository,
                             GenericRepository<ProductImage> productImageRepository) {
        this.formFactory = formFactory;
        this.ec = ec;
        this.productRepository = productRepository;
        this.productTagRepository = productTagRepository;
        this.productSizeRepository = productSizeRepository;
        this.productColorRepository = productColorRepository;
        this.productImageRepository = productImageRepository;
    }

    public CompletionStage<Result> index() {
        return CompletableFuture.supplyAsync(() -> ok(toJson(this.getProduct(null))));
    }

    public CompletionStage<Result> find(final String id) {
        return CompletableFuture.supplyAsync(() -> ok(toJson(this.getProduct(id))));
    }

    private List<ProductResponse> getProduct(String id){
        return Optional.ofNullable(id)
                .map(m-> this.productRepository.listByProductId(m,Product.class))
                .orElseGet(()-> this.productRepository.list(Product.class))
                .map(m -> {
                    ProductResponse pr = ProductResponse.builder()
                            .id(m.getId())
                            .code(m.getCode())
                            .name(m.getName())
                            .description(m.getDescription())
                            .stock(m.getStock())
                            .price(m.getPrice())
                            .discount(m.getDiscount())
                            .rating(m.getRating())
                            .salePrice(m.getSalePrice())
                            .category(m.getCategoryId())
                            .deleted(m.getDeleted())
                            .active(m.getActive())
                            .build();
                    pr.setTags(this.productTagRepository.listByProductId(m.getId(),ProductTag.class)
                            .map(t-> ProductTagResponse.builder()
                                    .id(t.getId())
                                    .category(t.getCategoryId())
                                    .build()
                            )
                            .collect(Collectors.toList())
                    );
                    pr.setSize(this.productSizeRepository.listByProductId(m.getId(),ProductSize.class)
                            .map(t-> ProductSizeResponse.builder()
                                    .id(t.getId())
                                    .size(t.getSizeId())
                                    .build()
                            )
                            .collect(Collectors.toList())
                    );
                    pr.setColors(this.productColorRepository.listByProductId(m.getId(),ProductColor.class)
                            .map(t-> ProductColorResponse.builder()
                                    .id(t.getId())
                                    .color(t.getColorId())
                                    .build()
                            )
                            .collect(Collectors.toList())
                    );
                    pr.setPictures(this.productImageRepository.listByProductId(m.getId(),ProductImage.class)
                            .map(t-> ProductImageResponse.builder()
                                    .id(t.getId())
                                    .fileName(t.getFileName())
                                    .imageType(t.getImageType())
                                    .imageBased64(t.getImageBased64())
                                    .build()
                            )
                            .collect(Collectors.toList())
                    );
                    return pr;
                })
                .collect(Collectors.toList());
    }


    public CompletionStage<Result> addProduct(final Http.Request request) {
        Http.MultipartFormData<Files.TemporaryFile> body = request.body().asMultipartFormData();
        ProductRequest productRequest = Arrays.stream(body.asFormUrlEncoded().get("reqData"))
                .findFirst()
                .map(m -> Json.fromJson(Json.parse(m), ProductRequest.class))
                .map(m -> {
                    m.setProductImage(this.processFiles(body.getFiles(), body));
                    return m;
                })
                .orElse(null);
        return CompletableFuture.supplyAsync(() -> this.productRepository.wrap(em -> this.addProduct(em, productRequest)), this.ec.current())
                .thenApply(m -> ok());
    }

    private List<ProductImageRequest> processFiles(List<Http.MultipartFormData.FilePart<Files.TemporaryFile>> files,
                                                   Http.MultipartFormData<Files.TemporaryFile> body) {
        return files.stream().map(m -> {
                    ProductImageRequest productImageRequest = null;
                    Http.MultipartFormData.FilePart<Files.TemporaryFile> picture
                            = body.getFile(m.getFilename());
                    if (picture != null) {
                        Files.TemporaryFile file = picture.getRef();
                        try {
                            byte[] fileContent = FileUtils.readFileToByteArray(file.path().toFile());
                            String encodedString = Base64.getEncoder().encodeToString(fileContent);
                            productImageRequest = ProductImageRequest.builder()
                                    .fileName(picture.getFilename())
                                    .imageType(picture.getContentType())
                                    .imageBased64(encodedString)
                                    .build();
                        } catch (Exception e) {
                            System.out.println("Error reading file");
                        }
                    }
                    return productImageRequest;
                })
                .filter(f -> Objects.nonNull(f))
                .collect(Collectors.toList());

    }

    private ProductRequest addProduct(EntityManager em, ProductRequest pr) {
        Product product = Product.builder()
                .code(Optional.ofNullable(pr.getCode()).orElse(pr.getName()))
                .name(pr.getName())
                .description(Optional.ofNullable(pr.getDescription()).orElse(pr.getName()))
                .stock(Optional.ofNullable(pr.getStock()).orElse(new Integer(0)))
                .price(pr.getPrice())
                .discount(Optional.ofNullable(pr.getDiscount()).orElse(new Double(0.0)))
                .rating(Optional.ofNullable(pr.getRating()).orElse(new Integer(0)))
                .salePrice(Optional.ofNullable(pr.getSalePrice()).orElse(new Double(0.0)))
                .categoryId(pr.getCategoryId())
                .deleted(Boolean.FALSE)
                .active(Boolean.TRUE)
                .build();
        this.productRepository.persist(em, product);
        System.out.println(product.getId());
        System.out.println(product.getCode());
        System.out.println(product.getName());

        if (pr.getProductTag() != null && pr.getProductTag().size() > 0) {
            pr.getProductTag().forEach(m -> {
                this.productTagRepository.persist(em, ProductTag.builder()
                        .productId(product.getId())
                        .categoryId(m.getCategoryId())
                        .build()
                );
            });
        }
        if (pr.getProductSize() != null && pr.getProductSize().size() > 0) {
            pr.getProductSize().forEach(m -> {
                this.productSizeRepository.persist(em, ProductSize.builder()
                        .productId(product.getId())
                        .sizeId(m.getSizeId())
                        .build()
                );
            });
        }
        if (pr.getProductColor() != null && pr.getProductColor().size() > 0) {
            pr.getProductColor().forEach(m -> {
                this.productColorRepository.persist(em, ProductColor.builder()
                        .productId(product.getId())
                        .colorId(m.getColorId())
                        .build()
                );
            });
        }

        if (pr.getProductImage() != null && pr.getProductImage().size() > 0) {
            pr.getProductImage().forEach(m -> {
                this.productImageRepository.persist(em, ProductImage.builder()
                        .productId(product.getId())
                        .fileName(m.getFileName())
                        .imageType(m.getImageType())
                        .imageBased64(m.getImageBased64())
                        .build()
                );
            });
        }
        return pr;
    }


}
