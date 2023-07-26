import java.util.*
import kotlin.math.*

fun calcCirclePoints(radius: Float, max: Float): FloatArray {

    val stepRate: Int = (max/radius).toInt();

    var vertices = LinkedList<Float>();
    for (i in 0..360 step(stepRate)) {
        vertices.add(cos(Math.toRadians(i.toDouble())).toFloat()*radius);
        vertices.add(sin(Math.toRadians(i.toDouble())).toFloat()*radius);
        vertices.add(0f);

    }
    return vertices.toFloatArray()

}