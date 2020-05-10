package me.ofnullable.jpa.item.controller;

import lombok.RequiredArgsConstructor;
import me.ofnullable.jpa.item.domain.Book;
import me.ofnullable.jpa.item.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String create(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {
        var book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStock(form.getStock());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String list(Model model) {
        model.addAttribute("items", itemService.findItems());
        return "/items/itemList";
    }

    @GetMapping("/items/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        var item = (Book) itemService.findOne(id);
        var form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStock(item.getStock());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("/items/{id}/edit")
    public String edit(BookForm form) {
        var book = new Book();
        book.setId(form.getId());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStock(form.getStock());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items";
    }

}
