package com.ort.healthyfoods.fragments

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.ort.healthyfoods.entities.Tip

class ListTipsViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var consejos: MutableList<Tip> = arrayListOf()

    lateinit var consejoPrueba: Tip

    private val db = FirebaseFirestore.getInstance()

    fun initTipsList () {
        consejos.add(Tip(1,"TIP 1: Variedad, equilibrio y moderación en tu alimentación.","La importancia de una buena alimentación radica en estos tres pilares. Por eso, es interesante comer de todos los colores, de todos los grupos de alimentos sanos y en las cantidades saludables que se ajusten a tus necesidades. No te olvides de tomar pescados, carnes blancas, huevos, legumbres, frutas y verduras…","https://www.nutriban.com/wp-content/webp-express/webp-images/doc-root/wp-content/uploads/2020/03/DIETA-VARIADA-300x225.jpg.webp"))
        consejos.add(Tip(2, "TIP 2: Frutas y verduras, ¡tus aliadas!\n" +
                "\n","En este tip te proponemos que llegues a las famosas  5 raciones de frutas y verduras diarias. Además de su valor nutricional incuestionable, serán tus mejores aliadas como snacks saludables aportándote beneficios para tu salud.\n" +
                "\n","https://www.nutriban.com/wp-content/webp-express/webp-images/doc-root/wp-content/uploads/2020/03/VERDURAS-300x169.jpg.webp"))
        consejos.add(Tip(3,"TIP 3: Sí a los hidratos de carbono complejos.","Con complejos nos referimos a la patata, pasta, arroz, avena… Este tipo de alimentos te aportan saciedad controlando el hambre y los odiosos picoteos entre horas.\n" +
                "\n","https://www.nutriban.com/wp-content/webp-express/webp-images/doc-root/wp-content/uploads/2020/03/HIDRATOS-COMPLEJOS-300x204.jpg.webp"))
        consejos.add(Tip(4,"TIP 4: Cocinar tus propias comidas.","Sin duda, lo mejor que puedes hacer es ponerte el delantal y encender los fogones. Y si no tienes tiempo, te recomendamos que te hagas amig@ de las etiquetas nutricionales para que puedas realizar las mejores elecciones, ya que hay opciones saludables  y rápidas en el mercado.","https://www.nutriban.com/wp-content/webp-express/webp-images/doc-root/wp-content/uploads/2020/03/COCINAR-SANO.jpg.webp"))
        consejos.add(Tip(5,"TIP 5: Respetar los horarios de las comidas.","Seguir unas pautas en cuanto a horarios te ayudará a tener el hambre controlada y a evitar atracones.","https://www.nutriban.com/wp-content/webp-express/webp-images/doc-root/wp-content/uploads/2020/03/HORARIOS-DE-COMIDAS.jpg.webp"))
        consejos.add(Tip(6,"TIP 6: Elegir siempre alimentos frescos y de temporada.","No dudamos que lo ideal es ingerir materias primas lo más frescas posibles y aprovechar los productos que nos ofrece cada temporada del año. Ello te ayudará a comer variado y, sobre todo, ¡a no aburrirte comiendo!.","https://www.nutriban.com/wp-content/uploads/2020/03/ALIMENTOS-DE-TEMPORADA.png"))
        consejos.add(Tip(7,"TIP 7: Realizar 5  comidas al día.","De esta forma, tendrás sensación de saciedad evitando así caer en tentaciones innecesarias que sabotearán tu estilo de vida saludable.","https://www.nutriban.com/wp-content/webp-express/webp-images/doc-root/wp-content/uploads/2020/03/5-COMIDAS-AL-D%C3%8DAS.jpg.webp"))
        consejos.add(Tip(8,"TIP 8: Limitar el consumo de alcohol y bebidas azucaradas.","Está claro que las bebidas espirituosas no ayudan en nada a mantener un buen estado de salud ya que solo te aportan muchas calorías vacías y azúcar.","https://www.nutriban.com/wp-content/webp-express/webp-images/doc-root/wp-content/uploads/2020/03/no-alcohol-294x300.jpg.webp"))
        consejos.add(Tip(9,"TIP 9: Beber el agua que necesites.","Pese a lo que se cree, el agua no adelgaza, por eso no es necesario estar todo el día con la botellita en la mano. Así, que bebe lo que tu cuerpo te demande, ni más ni menos.","https://www.nutriban.com/wp-content/webp-express/webp-images/doc-root/wp-content/uploads/2020/03/AGUA-1.jpg.webp"))
        consejos.add(Tip(10,"TIP 10: Aceite de oliva","Producto estrella de nuestra dieta mediterránea. Sin duda, la mejor opción para usar en crudo como en tu cocina ya que tiene grasas saludables. Pero no por ello te debes de pasar tampoco ya que, recordemos, es grasa y es calórica.","https://www.nutriban.com/wp-content/webp-express/webp-images/doc-root/wp-content/uploads/2020/03/aceite-de-oliva.jpg.webp"))

    }

    fun cargarTips_Base() {
        for (elemento in consejos){
            consejoPrueba = elemento
            var newTip = hashMapOf(
                "idTip" to consejoPrueba.idTip,
                "titulo" to consejoPrueba.titulo,
                "descripcion" to consejoPrueba.descripcion
            )
            db.collection("TipsYConsejos")
                .add(newTip)
                .addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG,"DocumentSnapshot written with ID: ${documentReference.id}\"Se cargó en la BBDD\"")
                }
                .addOnFailureListener {
                        e -> Log.w(ContentValues.TAG, "ERROR writing document", e)
                }
        }
    }

    }