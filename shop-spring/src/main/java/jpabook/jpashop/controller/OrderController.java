package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model){
        List<Item> itemList = itemService.findItems();
        List<Member> memberList = memberService.findMembers();

        model.addAttribute("items", itemList);
        model.addAttribute("members", memberList);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String create(@RequestParam("memberId") Long memberId,
                         @RequestParam("itemId") Long itemId,
                         @RequestParam("count") int count){
        orderService.save(memberId, itemId, count);

        return "redirect:/";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch")OrderSearch orderSearch, Model model){
        List<Order> orders = orderService.findOrders(orderSearch);

        model.addAttribute("orders", orders);

        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancel(@PathVariable("orderId") Long orderId){
        orderService.cancel(orderId);

        return "redirect:/orders";
    }
}
