import org.carte.kottygraphics.core.*
import org.carte.kottygraphics.core.math.Vector
import org.carte.kottygraphics.util.ProgramUtils
import org.carte.kottygraphics.util.ShaderUtils
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.opengl.GL33.*;
import org.lwjgl.glfw.GLFW.*;

class SomeGlApp : GlApp() {
    private lateinit var input: KeyHandler
    private lateinit var controlPos: Uniform<Vector>
    private var playerX = 0.0;
    private var playerY = 0.0;
    private var points = 0;

    override fun onCreate() {


        input = KeyHandler(window);
        glClearColor(0f,0f,0f,0f);

        programRef = ProgramUtils.initProgram(
            vertexShaderCode = ShaderUtils.getShaderCode("/vert.glsl"),
            fragmentShaderCode = ShaderUtils.getShaderCode("/frag.glsl")
        )
        val vaoRef = glGenVertexArrays();
        glBindVertexArray(vaoRef);
        val radius = .2f;

        val vertices: FloatArray = calcCirclePoints(radius = radius, max = .5f);
        points = vertices.size/3;
        val posAttrib = Attribute(
            GlDataType.VEC3,
            vertices
        )
        posAttrib.associateVariable(programRef, "pos")

        controlPos = Uniform(
            GlDataType.VEC3,
            Vector(0.0,0.0,0.0)
        ).apply {
            locateVariable(programRef, "controlPos")
        }


    }
    companion object {
        const val playerSpeed = 3;
    }

    override fun render() {
        glClear(GL_COLOR_BUFFER_BIT .or (GL_DEPTH_BUFFER_BIT))
        glUseProgram(programRef);

        if (input.isKeyDown(GLFW_KEY_W))
            controlPos.data.values[1] += deltaTime.toDouble() * playerSpeed;
        if (input.isKeyDown(GLFW_KEY_S))
            controlPos.data.values[1] -= deltaTime.toDouble() * playerSpeed;
        if (input.isKeyDown(GLFW_KEY_A))
            controlPos.data.values[0] -= deltaTime.toDouble() * playerSpeed;
        if (input.isKeyDown(GLFW_KEY_D))
            controlPos.data.values[0] += deltaTime.toDouble() * playerSpeed;


        controlPos.uploadData();

        glDrawArrays(GL_TRIANGLE_FAN, 0, points);


    }

    override fun onKeyEvent(window: Long, key: Int, action: Int) {

        input.update(key, action);
    }

    override fun onWindowResized(width: Int, height: Int) {
        glViewport(0, 0, width, height)

    }


    override fun shutdown() {

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwSetErrorCallback(null)!!.free();
        glfwTerminate();
    }

}