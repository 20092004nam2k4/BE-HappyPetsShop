package com.example.shoppet.controller;

import com.example.shoppet.model.Category;
import com.example.shoppet.model.Pet;
import com.example.shoppet.model.PetDetail;
import com.example.shoppet.repository.ICategoryPet;
import com.example.shoppet.repository.IPetDetailRepository;
import com.example.shoppet.repository.IPetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/Pet")
public class PetController {

    @Autowired
    private IPetRepository petRepository;

    @Autowired
    private ICategoryPet categoryRepository;

    @Autowired
    private IPetDetailRepository petDetailRepository;

    public static final String UPLOAD_DIRECTORY = "C:\\Users\\namca\\Documents\\BE-HappyPetsShop\\src\\main\\resources\\static\\image\\";


    @GetMapping("")
    public ModelAndView showView(@RequestParam(name = "page", defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 4, Sort.by("id").descending());
        Page<Pet> petPage = petRepository.findAll(pageable);

        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("pets", petPage.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", petPage.getTotalPages());

        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("create");
        List<Category> categories = categoryRepository.findAll();
        modelAndView.addObject("pet", new Pet());
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView createPet(@ModelAttribute Pet pet, @RequestParam("file") MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            // Lưu PetDetail trước
            PetDetail petDetail = pet.getPetDetail();
            petDetailRepository.save(petDetail);

            // Lưu tệp tin vào thư mục upload
            Path uploadPath = Paths.get(UPLOAD_DIRECTORY);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Xử lý tên tệp tin để tránh trùng lặp
            String fileName = file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            // Nếu tệp tin đã tồn tại, tạo tên mới cho tệp tin
            int count = 1;
            while (Files.exists(filePath)) {
                String newFileName = fileName + "(" + count + ")";
                filePath = uploadPath.resolve(newFileName);
                count++;
            }

            // Sao chép tệp tin vào thư mục upload
            Files.copy(file.getInputStream(), filePath);
            pet.setImage(filePath.getFileName().toString());

            // Lưu Pet
            pet.setPetDetail(petDetail);
            petRepository.save(pet);

            modelAndView.setViewName("redirect:/Pet"); // Chuyển hướng đến danh sách thú cưng sau khi lưu
            modelAndView.addObject("message", "Thú cưng đã được thêm thành công!");
        } catch (IOException e) {
            e.printStackTrace();
            modelAndView.setViewName("error"); // Hiển thị trang lỗi nếu có ngoại lệ
            modelAndView.addObject("message", "Lỗi khi lưu thú cưng: " + e.getMessage());
        }
        return modelAndView;
    }

    // Phương thức để xóa thú cưng
    @GetMapping("/delete/{id}")
    public ModelAndView deletePet(@PathVariable long id) {
        petRepository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/Pet");
        modelAndView.addObject("message", "Thú cưng đã được xóa thành công!");
        return modelAndView;
    }


    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("update");
        Pet pet = petRepository.findById(id).orElse(null);
        List<Category> categories = categoryRepository.findAll();

        if (pet != null) {
            modelAndView.addObject("pet", pet);
            modelAndView.addObject("categories", categories);
        } else {
            modelAndView.setViewName("redirect:/Pet");
            modelAndView.addObject("message", "Thú cưng không tồn tại!");
        }
        return modelAndView;
    }


    @PostMapping("/update")
    public ModelAndView updatePet(@ModelAttribute Pet pet, @RequestParam("file") MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Pet existingPet = petRepository.findById(pet.getId()).orElse(null);

            if (existingPet != null) {
                // Lưu PetDetail trước
                PetDetail petDetail = pet.getPetDetail();
                petDetailRepository.save(petDetail);

                // Xử lý tệp tin nếu có tệp tin mới
                if (!file.isEmpty()) {
                    // Lưu tệp tin vào thư mục upload
                    Path uploadPath = Paths.get(UPLOAD_DIRECTORY);
                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }

                    // Xử lý tên tệp tin để tránh trùng lặp
                    String fileName = file.getOriginalFilename();
                    Path filePath = uploadPath.resolve(fileName);

                    // Nếu tệp tin đã tồn tại, tạo tên mới cho tệp tin
                    int count = 1;
                    while (Files.exists(filePath)) {
                        String newFileName = fileName + "(" + count + ")";
                        filePath = uploadPath.resolve(newFileName);
                        count++;
                    }

                    // Sao chép tệp tin vào thư mục upload
                    Files.copy(file.getInputStream(), filePath);
                    pet.setImage(filePath.getFileName().toString());
                } else {
                    // Giữ lại hình ảnh cũ nếu không có tệp tin mới
                    pet.setImage(existingPet.getImage());
                }

                // Cập nhật thông tin Pet
                pet.setPetDetail(petDetail);
                petRepository.save(pet);

                modelAndView.setViewName("redirect:/Pet"); // Chuyển hướng đến danh sách thú cưng sau khi lưu
                modelAndView.addObject("message", "Thú cưng đã được cập nhật thành công!");
            } else {
                modelAndView.setViewName("redirect:/Pet");
                modelAndView.addObject("message", "Thú cưng không tồn tại!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            modelAndView.setViewName("error"); // Hiển thị trang lỗi nếu có ngoại lệ
            modelAndView.addObject("message", "Lỗi khi cập nhật thú cưng: " + e.getMessage());
        }
        return modelAndView;
    }

}
