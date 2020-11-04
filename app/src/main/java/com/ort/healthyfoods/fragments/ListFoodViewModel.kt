package com.ort.healthyfoods.fragments

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.ort.healthyfoods.entities.Food

class ListFoodViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var comidas: MutableList<Food> = arrayListOf()
    var colaciones: MutableList<Food> = arrayListOf()

    lateinit var comidaPrueba: Food

    private val db = FirebaseFirestore.getInstance()

    fun initTestList () {
        comidas.add(Food(20,"Bowls de vegetales y verduras","Bowls de vegetales y verduras. Cuencos de preparación de comidas de verduras de invierno con col roja en escabeche, batata asada, brócoli, tofu ahumado y salsa mágica de tahini para hacer realidad todos tus sueños.","https://www.heynutritionlady.com/wp-content/uploads/2018/01/winter_vegetable_meal_prep_bowls_3-500x453.jpg",Food.Constants.tipoVegetariana,1200))
        comidas.add(Food(19,"Meal Prep Recipes For Weight Loss","This meal prep is designed so you spend 60-90 minutes cooking on Sunday or Monday, and you have at least 5 healthy meals done for the week. I say at least, because the breackfast meal prep recipes can last 2-3 days, especially the frittata.",Food.Constants.tipoVegetariana,"https://flavcity.com/wp-content/uploads/2018/05/healthy-meal-prep-recipes-500x500.jpg",1200))
        comidas.add(Food(10,"Friburger rellena","Una hamburguesa Meat Free que replica toda la experiencia de la carne desde su textura hasta el sabor y el aroma rellena de queso mozzarella para que disfrutes cuando quieras. ¡Probá nuestra nueva opción vegetariana!",Food.Constants.tipoConCarne,"https://www.frizata.com/files/products/5f736d930f835-96-1.jpg",1500))
        comidas.add(Food(11,"Friburger","a primer hamburguesa Argentina Meat-Free hecha, en su mayoría, de plantas con el sabor, el olor, el color, la textura y la experiencia de la carne; pero sin carne.",Food.Constants.tipoVegetariana,"https://www.frizata.com/files/products/5f904ceda3e05-112-1.jpg",1250))
        comidas.add(Food(12,"Wok de Vegetales","Seguimos sumando opciones para la huerta en tu freezer! Ya no tenes que pasar horas picando verduras para armar un wok porque nosotros ya lo hicimos. Zanahoria, chaucha, apio y hongos cortadas en tiritas para que hagas tus salteados mucho más prácticos y ricos. Sin conservantes ni aditivos.",Food.Constants.tipoVegana,"https://www.frizata.com/files/products/5f71e69ba5c46-102-1.jpg",150))
        comidas.add(Food(13,"Bites de carne y queso","Hechos 100% con carne vacuna al igual que nuestra deliciosa hamburguesa y obviamente relleno con corazón de queso mozzarella. ¡Bomba! Además de ser increíblemente ricas, estas bolitas son fuente de proteínas.",Food.Constants.tipoConCarne,"https://www.frizata.com/files/products/5e4c0afba0d89-74-1.jpg",1500))
        comidas.add(Food(14,"Bites de espinaca con jamon y queso","Si ya amabas nuestros bites de papa o de carne no te podés perder estos. Una bolita prefrita de puré de espinaca y papa bien casero, condimentado como lo hacían en tu casa, relleno de queso mozzarella y jamón cocido…",Food.Constants.tipoConCarne,"https://www.frizata.com/files/products/5ef4ab4226202-89-1.jpg",1500))
        comidas.add(Food(15,"Pan de carne con jamón y queso"," Hecho 100% de carne vacuna de la mejor calidad y relleno con la más rica mozzarella y el mejor jamón para que disfrutes de esta delicia, que sólo comías en lo de tus viejos, en tu casa y en 20-25 minutos.",Food.Constants.tipoConCarne,"https://www.frizata.com/files/products/5e970ee2e79cd-81-1.jpg",1500))
        comidas.add(Food(16,"Milanesas de Espinaca con Avena","Una forma diferente de comer el plato que amamos todos los argentinos. Salí de la rutina y anímate a probar esta versión de espinaca prefrita que además de ser deliciosa es súper liviana y saludable. ",Food.Constants.tipoVegana,"https://www.frizata.com/files/products/5d35d9eed4b4e-29-1.jpg",1500))
        comidas.add(Food(17,"Milanesas de Brócoli con Avena","Una versión liviana y súper rica del clásico de la cocina argentina. ¿Qué esperas para probar estas milas de brócoli prefritas? Te van a sorprender!",Food.Constants.tipoVegana,"https://www.frizata.com/files/products/5d35d53d3f2cc-30-1.jpg",1250))
        comidas.add(Food(18,"Milanesas de Soja Rellenas con Queso", "La nueva revolución en milanesas de soja rellenas con muchísimo queso. ¡Super sabrosas y saludables! Para que almuerce toda tu familia.",Food.Constants.tipoVegetariana,"https://www.frizata.com/files/products/5f4fdbc8b6f25-111-1.jpg",1250))
    }

    fun initColaciones () {
        comidas.add(Food(90,"","","","",150))
        comidas.add(Food(91,"","","","",150))
        comidas.add(Food(92,"","","","",150))
        comidas.add(Food(93,"","","","",150))
        comidas.add(Food(94,"","","","",150))
        comidas.add(Food(95,"","","","",150))
        comidas.add(Food(96,"","","","",150))
        comidas.add(Food(97,"","","","",150))
        comidas.add(Food(98,"","","","",150))
        comidas.add(Food(99,"","","","",150))
        comidas.add(Food(100,"","","","",150))

    }

     fun cargarAlm_Cen_Base() {
         for (elemento in comidas){
             comidaPrueba = elemento
             var newFood = hashMapOf(
                 "idComida" to comidaPrueba.idComida,
                 "nombre" to comidaPrueba.nombre,
                 "tipoComida" to comidaPrueba.tipoComida,
                 "calorias" to comidaPrueba.calorias,
                 "descripcion" to comidaPrueba.descripcion,
                 "urlImagen" to comidaPrueba.urlImagen
             )
            db.collection("almuerzosYcenas")
                .add(newFood)
                .addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG,"DocumentSnapshot written with ID: ${documentReference.id}\"La comida se cargó en la BBDD\"")
                }
                .addOnFailureListener {
                        e -> Log.w(TAG, "ERROR writing document", e)
                }
         }
    }

    fun cargar_Colaciones_Base() {
        for (elemento in comidas){
            comidaPrueba = elemento
            var newFood = hashMapOf(
                "idComida" to comidaPrueba.idComida,
                "nombre" to comidaPrueba.nombre,
                "tipoComida" to comidaPrueba.tipoComida,
                "calorias" to comidaPrueba.calorias,
                "descripcion" to comidaPrueba.descripcion,
                "urlImagen" to comidaPrueba.urlImagen
            )
            db.collection("colaciones")
                .add(newFood)
                .addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG,"DocumentSnapshot written with ID: ${documentReference.id}\"La comida se cargó en la BBDD\"")
                }
                .addOnFailureListener {
                        e -> Log.w(TAG, "ERROR writing document", e)
                }
        }
    }



}