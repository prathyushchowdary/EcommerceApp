package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import com.example.demo.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/product-home")
    public String showHomePage(Model model) {
        List<Product> productsList = productService.getAllProducts();
        model.addAttribute("productList", productsList);
        return "product-home";
    }

    @GetMapping("/add-product")
    public String showAddProductPage(Model model) {
        model.addAttribute("product", new Product());
        return "add-product";
    }

    @PostMapping("/save-product")
    public String saveProduct(@ModelAttribute("product") Product product,
                              RedirectAttributes redirectAttributes) {
        productService.saveProduct(product);
        redirectAttributes.addFlashAttribute("message", "Product Added Successfully");
        return "redirect:/product-home";
    }

    @GetMapping("/edit-product/{id}")
    public String showEditProductPage(Model model, @PathVariable Long id) throws ResourceNotFoundException {
        model.addAttribute("product", productService.getProductById(id));
        return "edit-product";
    }

    @PostMapping("/update-product")
    public String updateProduct(@ModelAttribute("product") Product product) throws ResourceNotFoundException {
        Product existing = productService.getProductById(product.getId());
        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        productService.saveProduct(existing);
        return "redirect:/product-home";
    }

    @GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable Long id) throws ResourceNotFoundException {
        productService.getProductById(id); // validate exists
        productService.deleteProduct(id);
        return "redirect:/product-home";
    }
}
