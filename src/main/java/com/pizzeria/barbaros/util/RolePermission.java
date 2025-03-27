package com.pizzeria.barbaros.util;

public enum RolePermission {

    // Permisos para Productos
    READ_ALL_PRODUCTS,
    READ_ONE_PRODUCT,
    CREATE_ONE_PRODUCT,
    UPDATE_ONE_PRODUCT,
    DELETE_ONE_PRODUCT,

    // Permisos para Categorías
    READ_ALL_CATEGORY,
    READ_ONE_CATEGORY,
    CREATE_ONE_CATEGORY,
    UPDATE_ONE_CATEGORY,
    DELETE_ONE_CATEGORY,

    // Permisos para Ingredientes
    READ_ALL_INGREDIENT,
    READ_ONE_INGREDIENT,
    CREATE_ONE_INGREDIENT,
    UPDATE_ONE_INGREDIENT,
    DELETE_ONE_INGREDIENT,

    // Permisos para Órdenes
    READ_ALL_ORDER,
    READ_ONE_ORDER,
    CREATE_ONE_ORDER,
    UPDATE_ONE_ORDER,
    DELETE_ONE_ORDER,

    // Permisos para Proveedores
    READ_ALL_PROVIDER,
    READ_ONE_PROVIDER,
    CREATE_ONE_PROVIDER,
    UPDATE_ONE_PROVIDER,
    DELETE_ONE_PROVIDER,

    // Permisos para Tickets
    READ_ALL_TICKETS,
    READ_ONE_TICKET,
    CREATE_ONE_TICKET,
    UPDATE_ONE_TICKET,
    DELETE_ONE_TICKET,

    // Permisos para Usuarios
    READ_ALL_USERS,
    READ_ONE_USER,
    CREATE_ONE_USER,
    UPDATE_ONE_USER,
    DELETE_ONE_USER,

    // Permisos para Combos
    READ_ALL_COMBOS,
    READ_ONE_COMBO,
    CREATE_ONE_COMBO,
    UPDATE_ONE_COMBO,
    DELETE_ONE_COMBO,

    // PERMISOS PARA REPORTES Y ANÁLISIS
    GENERATE_SALES_REPORT,      // Generar reportes de ventas
    VIEW_TOP_SELLING_PRODUCTS,  // Ver los productos más vendidos
    VIEW_REVENUE_REPORT,         // Ver reportes de ingresos
    VIEW_INGREDIENT_COSTS       // Ver costos de ingredientes por producto
}
