package com.raspby.filemanager;


import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;

import com.raspby.filemanager.persistence.AccessPath;
import com.raspby.filemanager.persistence.Authority;
import com.raspby.filemanager.persistence.Users;
import com.raspby.filemanager.repository.AccessPathRepository;
import com.raspby.filemanager.repository.AuthorityRepository;
import com.raspby.filemanager.repository.UsersRepository;
import com.raspby.filemanager.service.FileService;

@SpringBootApplication
public class FilemanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilemanagerApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demoVetRepository(AccessPathRepository accessPathRepository, AuthorityRepository authorityRepository,UsersRepository usersRepository, FileService fileService, PasswordEncoder passwordEncoder) {
		return (args) -> {
//			System.err.println("imprime");
//			Users user= new Users();//usersRepository.findByUsername("fran");
//			user.setUsername("fran1");
//			user.setEnabled(true);
//			user.setPassword(passwordEncoder.encode("fran1"));
//			List<Authority> ls=new ArrayList<Authority>();
//			Authority auth=new Authority();
//			auth.setName("admin");
//			ls.add(authorityRepository.save(auth));
//			ls.add(authorityRepository.findOne((short) 2));
//			user.setAuthorities(ls);
//			user = usersRepository.save(user);
//			AccessPath ap =new AccessPath();
//			ap.setDevice("usb1");
//			ap.setPath("/");
//			ap.setPermissions((short) 0);
//			ap.setUser(user);
//			accessPathRepository.save(ap);
//			System.err.println(user.getPassword().length());
//			usersRepository.save(user);
//			for (CustomFile string : fileService.getRootFiles((short)1, "usb1")) {
//			for (CustomFile string : fileService.getRoots((short)1)) {
//			for (CustomFile string : fileService.getFiles((short)1, "usb1", "/usb/per/mi/nueva")) {
//				System.err.println(string.getName());
//				System.err.println(string.getPath());
//				System.err.println(string.getParentPath());
//				System.err.println(string.getDevice());
//				System.err.println(string.getLastModified());
//				System.err.println(string.isDirectory());
//				System.err.println(string.getSize());
//				System.err.println(string.getPermission());
//				System.err.println(string.isRootFile());
//				System.err.println("----------------------");
//			}
			//fileService.getFiles((short)1, new File("ejemplo/usb1/"));
		};
		
	}
	
}
