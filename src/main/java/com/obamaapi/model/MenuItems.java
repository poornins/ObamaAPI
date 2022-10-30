package com.obamaapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.obamaapi.enums.MenuAvailability;
import com.obamaapi.enums.MenuType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MenuItems {

    @Id
    @GeneratedValue
    private long menuId;

    @Column(nullable = false)
    private String menuName;

    @Column (nullable = false)
    private String price;

    @Enumerated (EnumType.STRING)
    private MenuAvailability availability;

    @Enumerated (EnumType.STRING)
    private MenuType type;

    @Column (nullable = false)
    private String imageUrl;

    @OneToMany(targetEntity = OrderIncludesMenu.class, mappedBy = "menuItems", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<OrderIncludesMenu> orderIncludesMenus;
}
