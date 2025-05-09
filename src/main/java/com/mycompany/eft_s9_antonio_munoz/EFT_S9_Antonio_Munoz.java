package com.mycompany.eft_s9_antonio_munoz;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class EFT_S9_Antonio_Munoz {
    
    //Constantes del algoritmo
    public static final int PrecioVip = 15000;
    public static final int PrecioPalco = 12500;
    public static final int PrecioPlateaAlta = 10000;
    public static final int PrecioPlateaBaja = 7500;
    public static final int PrecioGaleria = 5000;
    public static final int DescuentoMayor = 25;
    public static final int DescuentoMujer = 20;
    public static final int DescuentoEstudiante = 15;
    public static final int DescuentoInfantil = 10;

    //Variables a usar en todo el algoritmo
    public static Scanner scanner = new Scanner(System.in);
    public static List<String[]> entradasReservadas = new ArrayList<>();
    public static List<String[]> entradasCompradas = new ArrayList<>();
    public static List<String[]> reservasGlobales = new ArrayList<>();
    public static String[] datosUsuario = new String[5];
    public static String[][]vip = new String[4][6];
    public static String[][]palco = new String[4][6];
    public static String[][]plateaAlta = new String[4][6];
    public static String[][]plateaBaja = new String[4][6];
    public static String[][]galeria = new String[4][6];
    public static boolean salida = false; //Boolean del menu principal
    public static boolean usuarioNuevo = true;
    public static int descuentos = 0;
    
    //Metodo destinado a validar si el input del usuario es valido
    public static int esValido(int numeroOpciones){
        int opcion;
        while (true){
            if (scanner.hasNextInt()){
                opcion = scanner.nextInt();
                scanner.nextLine();
                if (opcion > 0 && opcion <= numeroOpciones) {
                    return opcion;
                }else{
                    System.out.println("Opción fuera de rango, intente nuevamente");
                }
            }else{
                System.out.println("Entrada no valida, intentelo nuevamente");
                scanner.nextLine();
            }
        }
    }
    //Generador de grafica de asientos
    public static String[][] generarAsientos(){
        String[][] datos = {
        {"   ", "|1|", "|2|", "|3|", "|4|", "|5|"},
        {" A ", "|_|", "|_|", "|_|", "|_|", "|_|"},
        {" B ", "|_|", "|_|", "|_|", "|_|", "|_|"},
        {" C ", "|_|", "|_|", "|_|", "|_|", "|_|"}
        };
        return datos;
    }
    //Imprime los asientos de una seccion
    public static void imprimirSeccion(String[][] seccion){
        for (int i = 0; i < seccion.length; i++) {
            for (int j = 0; j < seccion[i].length; j++) {
                System.out.print(seccion[i][j]);
            }
            System.out.println();
        }
    }
    //Metodo del menu principal
    public static void menu (){
        //Primero generamos los asientos para las distintas secciones
        vip = generarAsientos();
        palco = generarAsientos();
        plateaAlta = generarAsientos();
        plateaBaja = generarAsientos();
        galeria = generarAsientos();
        while (!salida){
            if (usuarioNuevo){
                agregarNuevoUsuario();
            }
            System.out.println("""
                               =========================================================
                               Menu Principal
                               1 - Reservar Entradas
                               2 - Mostrar Carrito de Reservas
                               3 - Eliminar Entradas del Carrito
                               4 - Realizar Compra
                               5 - Mostrar Entradas Compradas
                               6 - Imprimir Boleta y Finalizar Programa
                               Ingrese el número de la opción que desea elegir
                               ========================================================="""); 

            int opcionMenu = esValido(7);

            switch (opcionMenu){
                case 1:
                    reservarEntrada();
                    break;

                case 2:
                    mostrarCarrito();
                    break;

                case 3:
                    modificarEntrada();
                    break;

                case 4:
                    comprarEntrada();
                    break;

                case 5:
                    mostrarCompras();
                    break;
                    
                case 6:
                    imprimirBoleta();
                    break;
            }
        }
    }
    //Funcion que ingresa un nuevo usuario con todos sus datos correspondientes
    public static void agregarNuevoUsuario(){
        System.out.println("Bienvenido al sistema de ventas de entradas del Teatro Moro");
        System.out.println("Por favor ingresa tu nombre");
        String nombre = scanner.nextLine();
        System.out.println("Por favor ingresa tu edad");
        int edad = esValido(120);
        System.out.println("¿Cual es su genero?");
        System.out.println("1- Femenino  2- Masculino");
        int genero = esValido(2);
        System.out.println("Nuestro teatro cuenta con distintos descuentos para nuestros usuarios");
        System.out.println("¿Eres estudiante?");
        System.out.println("1- SI  2- NO");
        int respuestaEstudiante = esValido(2);
        int idUsuario = (int)(Math.random() * 900000) + 100000; //Usare un numero random para el id del usuario
        datosUsuario[0] = nombre;
        datosUsuario[1] = String.valueOf(edad);
        datosUsuario[2] = String.valueOf(genero);
        datosUsuario[3] = String.valueOf(respuestaEstudiante);
        datosUsuario[4] = String.valueOf(idUsuario);
        agregarDescuentos();
        usuarioNuevo = false;
    }
    //Funcion encargada de aplicar los descuentos segun los datos del usuario
    public static void agregarDescuentos(){
        if (Integer.parseInt(datosUsuario[1]) >= 60){
            System.out.println("Se aplicara un descuento de adulto mayor correspondiente al %" + DescuentoMayor);
            descuentos += DescuentoMayor;
        }else if (Integer.parseInt(datosUsuario[1]) < 18){
            System.out.println("Se aplicara un descuento infantil correspondiente al %" + DescuentoInfantil);
            descuentos += DescuentoInfantil;
        }
        if (Integer.parseInt(datosUsuario[2]) == 1){
            System.out.println("En teatro Moro apreciamos a las mujeres, por lo tanto tendras un descuento adicional de %" + DescuentoMujer);
            descuentos += DescuentoMujer;
        }
        if (Integer.parseInt(datosUsuario[3]) == 1){
            System.out.println("Por ser estudiante gozaras de un descuento adicional del %" + DescuentoEstudiante);
            descuentos += DescuentoEstudiante;
        }
    }
    //Funcion encargada de reservar la entrada
    public static void reservarEntrada(){
        System.out.println("Seleccione el tipo de entrada que quiere comprar");
        System.out.println("1- Vip $15000  2- Palco $7500  3- Platea Alta $5000  4- Platea Baja $7500  5- Galeria $5000");
        int seccion = esValido(5);
        System.out.println("A continuacion le mostraremos una grafica con los asientos del teatro");
        System.out.println("Donde |_| corresponde a los asientos vacios y |X| corresponde a los asientos en uso");
        switch (seccion){
            case 1:
                imprimirSeccion(vip);
                escogerAsiento(vip, "VIP", PrecioVip);
                break;
            case 2:
                imprimirSeccion(palco);
                escogerAsiento(palco, "Palco", PrecioPalco);
                break;
            case 3:
                imprimirSeccion(plateaAlta);
                escogerAsiento(plateaAlta, "Platea Alta", PrecioPlateaAlta);
                break;
            case 4:
                imprimirSeccion(plateaBaja);
                escogerAsiento(plateaBaja, "Platea Baja", PrecioPlateaBaja);
                break;
            case 5:
                imprimirSeccion(galeria);
                escogerAsiento(galeria, "Galeria", PrecioGaleria);
                break;
        } 
    }
    //Funcion encargada de reservar un asiento en especifico
    public static void escogerAsiento(String[][] seccion, String tipoEntrada, int precio){
        System.out.println("Por favor seleccione la fila que desea comprar");
        System.out.println("1- A   2- B   3- C");
        int fila = esValido(3);
        System.out.println("Ahora seleccione el número del asiento que desea comprar");
        int asiento = esValido(5);
        if ("|X|".equals(seccion[fila][asiento])){
            System.out.println("El asiento seleccionado ya esta ocupado, intentelo nuevamente");
        }else{
            System.out.println("Usted eligio el asiento " + asiento + " de la fila " + seccion[fila][0]);
            seccion[fila][asiento] = "|X|";
            int precioDescuento = aplicarDescuento(precio);
            String[] entrada = {tipoEntrada, seccion[fila][0], String.valueOf(asiento), String.valueOf(precio), String.valueOf(precioDescuento)};  
            entradasReservadas.add(entrada);
        } 
    }
    //Funcion que aplica el descuento a la entrada
    public static int aplicarDescuento(int precio){
        int total = (precio / 100) * (100 - descuentos);
        return total;
    }
    //Muestra las entradas en carrito de compras
    public static void mostrarCarrito(){
        if (entradasReservadas.isEmpty()){
            System.out.println("Usted aun no realiza la reserva de ninguna entrada");
        }else{
            imprimirEntradas(entradasReservadas);
        }
    }
    //Funcion de impresion de entradas
    public static void imprimirEntradas(List<String[]> reservas){
        for (int i = 0; i < reservas.size(); i++){
            System.out.println((i + 1) + "- " + "Sección: " + reservas.get(i)[0] + " Fila:" + reservas.get(i)[1] + " Asiento: " + reservas.get(i)[2] + " Precio $" + reservas.get(i)[3] + " Precio Final $" + reservas.get(i)[4]);
        }
    }
    //Funcion que muestra las compras realizadas por el usuario
    public static void mostrarCompras(){
        if (entradasCompradas.isEmpty()){
            System.out.println("Usted aun no realiza la compra de ninguna entrada");
        }else{
            imprimirEntradas(entradasCompradas);
        }  
    }
    //Funcion de compra de entradas
    public static void comprarEntrada(){
        if (entradasReservadas.isEmpty()){
            System.out.println("Usted aun no a relizado ninguna reserva");
        }else{
            System.out.println("Usted tiene en su carrito de reservas las siguientes entradas");
            imprimirEntradas(entradasReservadas);
            int precioTotal = calcularTotalDescuento(entradasReservadas);
            System.out.println("Por un valor total de $" + precioTotal);
            System.out.println("¿Desea realizar la compra de las entradas?");
            System.out.println("1- Realizar compra   2- Salir");
            int respuesta = esValido(2);
            switch (respuesta){
                case 1:
                    for (int i = 0; i < entradasReservadas.size(); i++){
                        entradasCompradas.add(entradasReservadas.get(i).clone());
                    }
                    entradasReservadas.clear();
                    System.out.println("Se a realizado la compra de las entradas");
                    break;
                      
                case 2:
                    System.out.println("Se a cancelado la operación, será  redirigido al menu principal");
                    break;
            }    
        }
    }
    //Funcion que calcula el precio total
    public static int calcularPrecioTotal(List<String[]> reservas){
        int total = 0;
        for (int i = 0; i < reservas.size(); i++){
            total += Integer.parseInt(reservas.get(i)[3]);
        }
        return total;
    }
    //Funcion que calcula el precio total con descuento
    public static int calcularTotalDescuento(List<String[]> reservas){
        int total = 0;
        for (int i = 0; i < reservas.size(); i++){
            total += Integer.parseInt(reservas.get(i)[4]);
        }
        return total;
    }
    //Modifica las entradas que estan en el carrito
    public static void modificarEntrada(){
        if (entradasReservadas.isEmpty()){
            System.out.println("Usted aun no a relizado ninguna reserva");
        }else{
            System.out.println("Usted a realizado la compra de las siguientes entradas");
            imprimirEntradas(entradasReservadas);
            System.out.println("¿Cual entrada desea eliminar?");
            System.out.println("Porfavor ingrese el numero correspondiente a la entrada que desea seleecionar");
            int respuesta = esValido(entradasReservadas.size());
            desmarcarAsiento(entradasReservadas.get(respuesta-1));
            entradasReservadas.remove((respuesta - 1));
            System.out.println("La entrada a sido eliminada de forma exitosa");
        }
    }
    //Desmarca un asiento usado y lo deja disponible
    public static void desmarcarAsiento(String[] entrada){
        int fila = 0;
        int asiento = Integer.parseInt(entrada[2]);
        if (" A ".equals(entrada[1])){
            fila = 1;
        }else if(" B ".equals(entrada[1])){
            fila = 2;
        }else if(" C ".equals(entrada[1])){
            fila = 3;
        }
        switch (entrada[0]){
            case "VIP":
                vip[fila][asiento] = "|_|";
                break;
            case "Platea Alta":
                plateaAlta[fila][asiento] = "|_|";
                break;
            case "Platea Baja":
                plateaBaja[fila][asiento] = "|_|";
                break;
            case "Palco":
                palco[fila][asiento] = "|_|";
                break;
            case "Galeria":
                galeria[fila][asiento] = "|_|";
                break;
        }
    }
    //Imprimir Boleta
    public static void imprimirBoleta(){
        int ancho = 60;
        int total = calcularPrecioTotal(entradasCompradas);
        int totalDescuento = calcularTotalDescuento(entradasCompradas);
        if (entradasCompradas.isEmpty()){
            System.out.println("Aun no a realizado sus compras"); 
        }else{
            if (!entradasReservadas.isEmpty()){
                System.out.println("No se puede finalizar la compra con entradas en su carrito de reserva");
            }else{
                imprimirBorde (ancho);
                imprimirVacio(ancho);
                imprimirCentro("Teatro Moro", ancho);
                imprimirCentro("Avenida Duoc 1697", ancho);
                imprimirCentro("RUT 77777777777", ancho);
                imprimirVacio(ancho);
                imprimirIzquierda(datosUsuario[0] + " ID Usuario: " + datosUsuario[4] , ancho);
                imprimirVacio(ancho);
                for (String[] compra : entradasCompradas) {
                    imprimirIzquierda("Sección: " + compra[0] + " Fila:" + compra[1] + "Asiento: " + compra[2], ancho);
                    imprimirIzquierda("Precio: $ " + compra[3] + " Precio final: $ " + compra[4] + " Descuento: %" + descuentos, ancho);
                    imprimirVacio(ancho);
                }
                imprimirVacio(ancho);
                imprimirIzquierda("Total......Original $ " + total, ancho);
                imprimirIzquierda("Total con Descuento $ " + totalDescuento, ancho);
                imprimirBorde (ancho);
                finalizarPrograma();  
            }
            
        }
            
    }
    //Imprime un texto en el borde izquierdo
    public static void imprimirIzquierda(String texto, int ancho) {
        System.out.print("== ");
        System.out.print(texto);
        for (int i = 0; i < (ancho - 4) - texto.length() - 1; i++) {
            System.out.print(" ");
        }
        System.out.println("==");
    }
    //Imprime un texto en el centro
    public static void imprimirCentro(String texto, int ancho) {
        int espacioDisponible = ancho - 4;
        int espaciosAntes = (espacioDisponible - texto.length()) / 2;
        int espaciosDespues = espacioDisponible - espaciosAntes - texto.length();
        System.out.print("==");
        for (int i = 0; i < espaciosAntes; i++) {
            System.out.print(" ");
        }
        System.out.print(texto);
        for (int i = 0; i < espaciosDespues; i++) {
            System.out.print(" ");
        }
        System.out.println("==");
    }
    //Imprime una linea vacia en la boleta
    public static void imprimirVacio(int ancho){
        System.out.print("==");
        for(int j = 0; j < (ancho - 4); j++){
            System.out.print(" ");
        }
        System.out.println("==");        
    }
    //Imprime el borde superior e inferior de la boleta 
    public static void imprimirBorde(int ancho) {
        for (int i = 0; i < ancho; i++) {
            System.out.print("=");
        }
        System.out.println();
    }
    public static void finalizarPrograma(){
        System.out.println("¿Que desea hacer?");
        System.out.println("1- Realizar compras con otro usuario   2- Cerrar programa");
        int respuesta = esValido(2);
        switch (respuesta){
            case 1:
                System.out.println("Muchas gracias por su visita, se reiniciara el programa para un nuevo usuario");
                usuarioNuevo = true;
                entradasCompradas.clear();
                break;
            case 2:
                System.out.println("Muchas gracias por su visita, que tenga un excelente día");
                salida = true;
                break;
        }
    }
    public static void main(String[] args) {
        menu();
    }
}