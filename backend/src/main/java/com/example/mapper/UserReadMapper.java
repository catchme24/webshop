//package com.example.projectforlyozin.mapper;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import ru.catchme.spring.dto.CompanyReadDto;
//import ru.catchme.spring.dto.UserReadDto;
//import ru.catchme.spring.entity.User;
//
//import java.util.Map;
//import java.util.Optional;
//
//@Component
//@RequiredArgsConstructor
//public class UserReadMapper implements Mapper<User, UserReadDto> {
//
//    private final CompanyReadMapper companyReadMapper;
//
//    @Override
//    public UserReadDto map(User object) {
//        CompanyReadDto company = Optional.ofNullable(object.getCompany())
//                .map((companyReadMapper::map))
//                .orElse(null);
//        return new UserReadDto(
//            object.getId(),
//                object.getUsername(),
//                object.getBirthDate(),
//                object.getFirstname(),
//                object.getLastname(),
//                object.getRole(),
//                object.getImage(),
//                company);
//    }
//}
