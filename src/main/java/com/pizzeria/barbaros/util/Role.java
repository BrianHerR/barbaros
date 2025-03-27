package com.pizzeria.barbaros.util;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum Role {

    ROLE_ADMIN(Arrays.asList(
            // Permisos de productos
            RolePermission.READ_ALL_PRODUCTS,
            RolePermission.READ_ONE_PRODUCT,
            RolePermission.CREATE_ONE_PRODUCT,
            RolePermission.UPDATE_ONE_PRODUCT,
            RolePermission.DELETE_ONE_PRODUCT,

            // Permisos de categorías
            RolePermission.READ_ALL_CATEGORY,
            RolePermission.READ_ONE_CATEGORY,
            RolePermission.CREATE_ONE_CATEGORY,
            RolePermission.UPDATE_ONE_CATEGORY,
            RolePermission.DELETE_ONE_CATEGORY,

            // Permisos de ingredientes
            RolePermission.READ_ALL_INGREDIENT,
            RolePermission.READ_ONE_INGREDIENT,
            RolePermission.CREATE_ONE_INGREDIENT,
            RolePermission.UPDATE_ONE_INGREDIENT,
            RolePermission.DELETE_ONE_INGREDIENT,

            // Permisos de órdenes
            RolePermission.READ_ALL_ORDER,
            RolePermission.READ_ONE_ORDER,
            RolePermission.CREATE_ONE_ORDER,
            RolePermission.UPDATE_ONE_ORDER,
            RolePermission.DELETE_ONE_ORDER,

            // Permisos de proveedores
            RolePermission.READ_ALL_PROVIDER,
            RolePermission.READ_ONE_PROVIDER,
            RolePermission.CREATE_ONE_PROVIDER,
            RolePermission.UPDATE_ONE_PROVIDER,
            RolePermission.DELETE_ONE_PROVIDER,

            // Permisos de tickets
            RolePermission.READ_ALL_TICKETS,
            RolePermission.READ_ONE_TICKET,
            RolePermission.CREATE_ONE_TICKET,
            RolePermission.UPDATE_ONE_TICKET,
            RolePermission.DELETE_ONE_TICKET,

            // Permisos de usuarios
            RolePermission.READ_ALL_USERS,
            RolePermission.READ_ONE_USER,
            RolePermission.CREATE_ONE_USER,
            RolePermission.UPDATE_ONE_USER,
            RolePermission.DELETE_ONE_USER,

            // Permisos de combos
            RolePermission.READ_ALL_COMBOS,
            RolePermission.READ_ONE_COMBO,
            RolePermission.CREATE_ONE_COMBO,
            RolePermission.UPDATE_ONE_COMBO,
            RolePermission.DELETE_ONE_COMBO,

            // Permisos de reportes
            RolePermission.GENERATE_SALES_REPORT,
            RolePermission.VIEW_TOP_SELLING_PRODUCTS,
            RolePermission.VIEW_REVENUE_REPORT,
            RolePermission.VIEW_INGREDIENT_COSTS
    )),

    ROLE_EMPLOYEE(Arrays.asList(
            // Permisos limitados de productos
            RolePermission.READ_ALL_PRODUCTS,
            RolePermission.READ_ONE_PRODUCT,

            // Permisos limitados de categorías
            RolePermission.READ_ALL_CATEGORY,
            RolePermission.READ_ONE_CATEGORY,

            // Permisos limitados de ingredientes
            RolePermission.READ_ALL_INGREDIENT,
            RolePermission.READ_ONE_INGREDIENT,

            // Permisos limitados de órdenes
            RolePermission.READ_ALL_ORDER,
            RolePermission.READ_ONE_ORDER,
            RolePermission.CREATE_ONE_ORDER,
            RolePermission.UPDATE_ONE_ORDER,

            // Permisos de tickets (solo lectura y creación)
            RolePermission.READ_ALL_TICKETS,
            RolePermission.READ_ONE_TICKET,
            RolePermission.CREATE_ONE_TICKET,

            // Permisos de combos
            RolePermission.READ_ALL_COMBOS,
            RolePermission.READ_ONE_COMBO
    ));

    private final List<RolePermission> permissions;

    Role(List<RolePermission> permissions) {
        this.permissions = permissions;
    }


}
