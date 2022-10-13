package com.obamaapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column (nullable = false)
    private String availability;

    @Column (nullable = false)
    private String type;

    @Column (nullable = false)
    private String imageUrl;

    @OneToMany(targetEntity = OrderIncludesMenu.class, mappedBy = "menuItems", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<OrderIncludesMenu> orderIncludesMenus;
}
