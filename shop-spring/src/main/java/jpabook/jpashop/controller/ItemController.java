package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.Movie;
import jpabook.jpashop.dto.BookDto;
import jpabook.jpashop.dto.ItemDto;
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
        if (form.getDtype().equals("BOOK")) {
            Book book = Book.createBook(form.getName(), form.getPrice(), form.getStockQuantity(), form.getAuthor(), form.getIsbn());
            itemService.save(book);
        }
        else if (form.getDtype().equals("ALBUM")) {
            Album album = Album.createAlbum(form.getName(), form.getPrice(), form.getStockQuantity(), form.getArtist(), form.getEtc());
            itemService.save(album);
        }
        else if (form.getDtype().equals("MOVIE")) {
            Movie movie = Movie.createMovie(form.getName(), form.getPrice(), form.getStockQuantity(), form.getActor(), form.getDirector());
            itemService.save(movie);
        }

        return "redirect:/items";
    }

    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        List<ItemDto> itemDtos = items.stream()
                .map(item -> {
                    ItemDto dto = new ItemDto();
                    dto.setId(item.getId());
                    dto.setName(item.getName());
                    dto.setPrice(item.getPrice());
                    dto.setStockQuantity(item.getStockQuantity());

                    if(item instanceof Book book){
                        dto.setDtype("BOOK");
                        dto.setAuthor(book.getAuthor());
                        dto.setIsbn(book.getIsbn());
                    }
                    else if(item instanceof Album album){
                        dto.setDtype("ALBUM");
                        dto.setArtist(album.getArtist());
                        dto.setEtc(album.getEtc());
                    }
                    else if(item instanceof Movie movie){
                        dto.setDtype("MOVIE");
                        dto.setDirector(movie.getDirector());
                        dto.setActor(movie.getActor());
                    }
                    return dto;
                })
                .toList();

        model.addAttribute("items",itemDtos);
        return "items/itemList";
    }
}
