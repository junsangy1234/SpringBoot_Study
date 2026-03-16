package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.dto.BookDto;
import jpabook.jpashop.form.ItemForm;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("form", new ItemForm());

        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(ItemForm form, BindingResult result){
        if (result.hasErrors()){
            return "items/createItemFrom";
        }
        Book book = Book.createBook(form.getName(), form.getPrice(), form.getStockQuantity(), form.getAuthor(), form.getIsbn());

        itemService.save(book);

        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        List<BookDto> bookDto = items.stream()
                .map(item -> {
                    Book book = (Book) item;
                    return new BookDto(
                            book.getId(),
                            book.getName(),
                            book.getPrice(),
                            book.getStockQuantity(),
                            book.getAuthor(),
                            book.getIsbn()
                    );
                })
                .toList();

        model.addAttribute("items", bookDto);

        return "items/itemList";
    }
}
