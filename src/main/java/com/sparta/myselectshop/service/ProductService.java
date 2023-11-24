package com.sparta.myselectshop.service;

import com.sparta.myselectshop.dto.ProductMypriceRequestDto;
import com.sparta.myselectshop.dto.ProductRequestDto;
import com.sparta.myselectshop.dto.ProductResponseDto;
import com.sparta.myselectshop.entity.*;
import com.sparta.myselectshop.naver.dto.ItemDto;
import com.sparta.myselectshop.repository.FolderRepository;
import com.sparta.myselectshop.repository.ProductFolderRepository;
import com.sparta.myselectshop.repository.ProductRepository;
import com.sparta.myselectshop.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final FolderRepository folderRepository;
    private final ProductFolderRepository productFolderRepository;

    public static final int MIN_MY_PRICE = 100;


    public ProductResponseDto createProduct(ProductRequestDto productRequestDto, User user) {
        Product product = productRepository.save(new Product(productRequestDto,user));
        return new ProductResponseDto(product);
    }

    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductMypriceRequestDto productMypriceRequestDto) {
        int myprice = productMypriceRequestDto.getMyprice();
        if (myprice < MIN_MY_PRICE) {
            throw new IllegalArgumentException("유효하지 않은 관심 가격입니다. " + MIN_MY_PRICE + "원 이상으로 설정 가능합니다.");
        }
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 상품을 찾을 수 없습니다.")
        );
        product.update(productMypriceRequestDto);
        return new ProductResponseDto(product);
    }

    @Transactional(readOnly = true) //List<Folder> -> 지연로딩 사용하기 위해 readOnlu -> 성능을 높이기 위해
    public Page<ProductResponseDto> getProduct(User user, int page, int size, String sortBy, boolean isAsc) {
//        return productRepository.findAllByUser(user)
//                .stream()
//                .map(ProductResponseDto::new).collect(Collectors.toList());
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction,sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);

        UserRoleEnum userRoleEnum = user.getRole();

        Page<Product> productList;

        if(userRoleEnum == UserRoleEnum.USER){
            productList = productRepository.findAllByUser(user, pageable);
        }else{
            productList = productRepository.findAll(pageable);
        }

        return productList.map(ProductResponseDto::new);
    }

    @Transactional
    public void updateBySearch(Long id, ItemDto itemDto) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 상품은 존재하지 않습니다.")
        );
        product.updateByItenDto(itemDto);
    }

    public void addFolder(Long productId, Long folderId, User user) { //상품 폴더 추가
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NullPointerException("해당 상품이 존재하지 않습니다.")
        );
        Folder folder = folderRepository.findById(folderId).orElseThrow(
                () -> new NullPointerException("해당 폴더가 존재하지 않습니다.")
        );
        if(!product.getUser().getId().equals(user.getId()) || !folder.getUser().getId().equals(user.getId())){
            throw new IllegalArgumentException("회원님의 관싱상품이 아니거나, 회원님의 폴더가 아닙니다.");
        }
        Optional<ProductFolder> overlapFolder = productFolderRepository.findByProductAndFolder(product, folder);
        if(overlapFolder.isPresent()){
            throw new IllegalArgumentException("중복된 폴더입니다.");
        }
        productFolderRepository.save(new ProductFolder(product,folder));
    }

    public Page<ProductResponseDto> getProductsInFolder(Long folderId, int page, int size, String sortBy, boolean isAsc, User user) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction,sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);

        //현재 로그인한 user가 등록한 특정 폴더에 속해있는 product_id를 기준으로 product를 가져옴
        Page<Product> productList = productRepository.findAllByUserAndProductFolders_FolderId(user, folderId, pageable);

        Page<ProductResponseDto> responseDtoList = productList.map(ProductResponseDto::new);
        return responseDtoList;
    }

//    public List<ProductResponseDto> getAllProduct() {
//        List<Product> productList = productRepository.findAll();
//        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
//        for(Product product : productList){
//            productResponseDtos.add(new ProductResponseDto(product));
//        }
//        return productResponseDtos;
//    }
}
