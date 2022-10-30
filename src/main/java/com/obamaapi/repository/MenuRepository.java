package com.obamaapi.repository;

import com.obamaapi.model.MenuItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<MenuItems,Long> {
    MenuItems findByMenuName(String menuName);
    MenuItems findByMenuId(long menuId);

}
