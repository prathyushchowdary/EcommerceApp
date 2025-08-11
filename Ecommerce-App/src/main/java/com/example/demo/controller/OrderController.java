package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.service.OrderService;
import com.example.demo.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order-home")
    public String showHomePage(Model model) {
        List<Order> ordersList = orderService.getAllOrders();
        model.addAttribute("orderList", ordersList);
        return "order-home";
    }

    @GetMapping("/add-order")
    public String showAddOrderPage(Model model) {
        model.addAttribute("order", new Order());
        return "add-order";
    }

    @PostMapping("/save-order")
    public String saveOrder(@ModelAttribute("order") Order order,
                            RedirectAttributes redirectAttributes) {
        orderService.saveOrder(order);
        redirectAttributes.addFlashAttribute("message", "Order Added Successfully");
        return "redirect:/order-home";
    }

    @GetMapping("/edit-order/{id}")
    public String showEditOrderPage(Model model, @PathVariable Long id) throws ResourceNotFoundException {
        model.addAttribute("order", orderService.getOrderById(id));
        return "edit-order";
    }

    @PostMapping("/update-order")
    public String updateOrder(@ModelAttribute("order") Order order) throws ResourceNotFoundException {
        Order existing = orderService.getOrderById(order.getId());
        existing.setOrderDate(order.getOrderDate());
        existing.setStatus(order.getStatus());
        existing.setTotalAmount(order.getTotalAmount());
        orderService.saveOrder(existing);
        return "redirect:/order-home";
    }

    @GetMapping("/delete-order/{id}")
    public String deleteOrder(@PathVariable Long id) throws ResourceNotFoundException {
        orderService.getOrderById(id); // validate exists
        orderService.deleteOrder(id);
        return "redirect:/order-home";
    }
}
