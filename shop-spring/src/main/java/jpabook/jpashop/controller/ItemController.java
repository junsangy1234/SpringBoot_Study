package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.Movie;
import jpabook.jpashop.dto.ItemDto;
import jpabook.jpashop.form.ItemForm;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String create(ItemForm form){
        switch (form.getDtype()){
            case "BOOK":
                Book book = Book.createBook(form.getName(), form.getPrice(), form.getStockQuantity(), form.getAuthor(), form.getIsbn());
                itemService.save(book);
                break;
            case "MOVIE":
                Movie movie = Movie.createMovie(form.getName(), form.getPrice(), form.getStockQuantity(), form.getDirector(), form.getActor());
                itemService.save(movie);
                break;
            case "ALBUM":
                Album album = Album.createAlbum(form.getName(), form.getPrice(), form.getStockQuantity(), form.getArtist(), form.getEtc());
                itemService.save(album);
                break;
        }

        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        List<ItemDto> itemDtos = items.stream()
                .map(i -> {
                    ItemDto itemDto = new ItemDto();
                    itemDto.setId(i.getId());
                    itemDto.setName(i.getName());
                    itemDto.setPrice(i.getPrice());
                    itemDto.setStockQuantity(i.getStockQuantity());

                    if(i instanceof Book book){
                        itemDto.setDtype("BOOK");
                        itemDto.setAuthor(book.getAuthor());
                        itemDto.setIsbn(book.getIsbn());
                    }else if(i instanceof Album album){
                        itemDto.setDtype("ALBUM");
                        itemDto.setArtist(album.getArtist());
                        itemDto.setEtc(album.getEtc());
                    }else if(i instanceof Movie movie){
                        itemDto.setDtype("MOVIE");
                        itemDto.setDirector(movie.getDirector());
                        itemDto.setActor(movie.getActor());
                    }
                    return itemDto;
                })
                .toList();
        model.addAttribute("items", itemDtos);

        return "items/itemList";
    }
}
