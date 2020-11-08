package com.ort.healthyfoods.fragments

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.ort.healthyfoods.entities.Food

class ListBreackfastViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var desayunos: MutableList<Food> = arrayListOf()

    lateinit var comidaPrueba: Food

    private val db = FirebaseFirestore.getInstance()

    fun initDesayYMeriendas () {
        desayunos.add(Food(50,"Plátano, fresas, almendras y yogurt","1 plátano mediano pelado, Entre 8 y 10 almendras, 50 gramos de fresa picadas en trozos, 1 vaso de yogur natural (sin azúcar).Se incorporan los ingredientes juntos en la batidora y se mezclan.",Food.Constants.tipoVegetariana,"https://cdn2.cocinadelirante.com/sites/default/files/styles/gallerie/public/images/2019/05/batido-de-fresa-con-avena.jpg",500))
        desayunos.add(Food(51,"Pan integral con pera y queso","Cortar la pera en rodajas, 2 rebanadas de pan integral de 45g cada una, queso cabra o requesón. Colocar las rodajas de pera en forma de abanico en los dos panes, y untar el queso.",Food.Constants.tipoVegana,"https://pbs.twimg.com/media/DLtSsQ_VAAIvuTp.jpg",550))
        desayunos.add(Food(52,"Muesli casero con queso quark y naranja"," Recolectar el jugo de una naranja  y mezclar con 30 g de queso quark descremado y ½ cucharadita de miel de abeja, Puedes agregar 1 cucharadita de pistachos y 50 g de avena.",Food.Constants.tipoVegetariana,"https://i.pinimg.com/originals/78/32/ed/7832edd71f355ae901f5d4a76af72e40.jpg",550))
        desayunos.add(Food(53,"Aguacate y huevos","Huevos duros combinado con medio aguacate. Agregar jugo de limón, sal y aceite de oliva.",Food.Constants.tipoVegetariana,"https://vod-hogarmania.atresmedia.com/cocinatis/images/images01/2019/07/05/5d1ef5381f4daa0001932669/1239x697.jpg",500))
        desayunos.add(Food(54,"Melón con yogurt y cereales","Corta un melón en pequeños pedacitos, mezclar con un vaso de yogur y 30 gramos de cereales, de preferencia integrales.",Food.Constants.tipoVegetariana,"https://c8.alamy.com/compes/b3237m/granola-cereales-con-yogur-natural-y-melon-b3237m.jpg",500))
        desayunos.add(Food(55,"Piña y semillas de lino","Agregar a la batidora unos trocitos de piña, un limón, semillas de lino, una infusión de té verde y jengibre para dar sabor. Todo esto se debe batir.",Food.Constants.tipoVegana,"https://mejorconsalud.com/wp-content/uploads/2016/06/batido-pina-chia.jpg",550))
        desayunos.add(Food(56," Huevos revueltos con verduras","Agrega 2 o 3 huevos, aceite de coco o mantequilla, sal, pimienta y verduras (espinacas, cebollas y pimientos). Freír los huevos y las verduras, en un sartén con aceite de coco o mantequilla.",Food.Constants.tipoVegetariana,"https://estaticos.miarevista.es/media/cache/760x570_thumb/uploads/images/recipe/5804f3217781e0aa09555f8d/huevos-revueltos-verduras.jpg",500))
        desayunos.add(Food(57,"Avena con huevos","Hervir la avena, añadir una pizca de sal y pimienta en lugar de azúcar. Posteriormente añade un huevo escalfado y espolvorea un poco de queso por encima",Food.Constants.tipoVegetariana,"https://i.blogs.es/227861/avena-cocida-huevo/1366_2000.jpg",500))
        desayunos.add(Food(58,"Aguacate con pan tostado","El pan tostado junto con el aguacate proporciona fibras. Se le puede añadir,  un huevo o dos y una pizca de levadura nutricional.",Food.Constants.tipoVegana,"https://cdn2.actitudfem.com/media/files/styles/big_img/public/images/2013/09/aguacate_y_pan_tostado.jpg",550))
    }

    fun cargarDes_Mer_Base() {
        for (elemento in desayunos){
                comidaPrueba = elemento
                var newFood = hashMapOf(
                    "idComida" to comidaPrueba.idComida,
                    "nombre" to comidaPrueba.nombre,
                    "tipoComida" to comidaPrueba.tipoComida,
                    "calorias" to comidaPrueba.calorias,
                    "descripcion" to comidaPrueba.descripcion,
                    "urlImagen" to comidaPrueba.urlImagen
                )
                db.collection("desayunosYmeriendas")
                    .add(newFood)
                    .addOnSuccessListener { documentReference ->
                        Log.d(ContentValues.TAG,"DocumentSnapshot written with ID: ${documentReference.id}\"La comida se cargó en la BBDD\"")
                    }
                    .addOnFailureListener {
                            e -> Log.w(ContentValues.TAG, "ERROR writing document", e)
                    }
        }
    }
}