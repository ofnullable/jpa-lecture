package me.ofnullable.jpa.order.controller;

import lombok.RequiredArgsConstructor;
import me.ofnullable.jpa.item.domain.Item;
import me.ofnullable.jpa.item.service.ItemService;
import me.ofnullable.jpa.member.domain.Member;
import me.ofnullable.jpa.member.service.MemberService;
import me.ofnullable.jpa.order.dto.OrderSearch;
import me.ofnullable.jpa.order.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/orders/new")
    public String createOrder(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);
        return "orders/createOrderForm";
    }

    @PostMapping("/orders/new")
    public String createOrder(Long memberId, Long itemId, int count) {
        orderService.placeOrder(memberId, itemId, count);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String list(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        model.addAttribute("orders", orderService.searchOrders(orderSearch));
        return "orders/orderList";
    }

    @PostMapping("/orders/{id}/cancel")
    public String cancel(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return "redirect:/orders";
    }

}
