//package com.example.projectforlyozin.mapper;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//import ru.catchme.spring.database.repository.CompanyRepository;
//import ru.catchme.spring.dto.UserCreateEditDto;
//import ru.catchme.spring.entity.Company;
//import ru.catchme.spring.entity.User;
//
//import java.util.Optional;
//import java.util.function.Predicate;
//
//@Component
//@RequiredArgsConstructor
//public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {
//
//    private final CompanyRepository companyRepository;
//
//    @Override
//    public User map(UserCreateEditDto from, User to) {
//        copy(from, to);
//        return to;
//    }
//
//    @Override
//    public User map(UserCreateEditDto object) {
//        User user = new User();
//        copy(object, user);
//        return user;
//    }
//
//    private void copy(UserCreateEditDto object, User user) {
//        user.setFirstname(object.getFirstname());
//        user.setLastname(object.getLastname());
//        user.setUsername(object.getUsername());
//        user.setBirthDate(object.getBirthDate());
//        user.setRole(object.getRole());
//        user.setCompany(getCompany(object.getCompanyId()));
//
//        Optional.ofNullable(object.getImage())
//                .filter(img -> !img.isEmpty())
//                .ifPresent(image -> user.setImage(image.getOriginalFilename()));
//    }
//
//    private Company getCompany(Integer companyId) {
//        return Optional.ofNullable(companyId)
//                .flatMap(companyRepository::findById)
//                .orElse(null);
//    }
//}
