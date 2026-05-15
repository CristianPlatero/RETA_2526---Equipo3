package InterfazNueva;

/**
 * =====================================================================
 *  ROL  —  Define los tipos de usuario del sistema
 * =====================================================================
 *  Usar un enum en lugar de Strings ("admin", "profesor") es más seguro:
 *  el compilador detecta errores de escritura y los switch/if son claros.
 *
 *  Para añadir más roles en el futuro solo añade una constante aquí,
 *  por ejemplo: TECNICO, DIRECTOR, INVITADO...
 * =====================================================================
 */
public enum Rol {

    /** Acceso total: CRUD completo + informes */
    ADMINISTRADOR,

    /** Acceso de solo lectura: listar, filtrar y generar informes */
    PROFESOR
}
