/**
 * =====================================================================
 * Programming Project for NCEA Level 3, Standard 91906
 * ---------------------------------------------------------------------
 * Project Name:   PROJECT NAME HERE
 * Project Author: Corban Mooney
 * GitHub Repo:    https://github.com/waimea-cjmooney/Level-3-programming-assessment
 * ---------------------------------------------------------------------
 * Notes:
 * PROJECT NOTES HERE
 * =====================================================================
 */



import com.formdev.flatlaf.FlatDarkLaf
import java.awt.*
import java.awt.event.*
import javax.swing.*


/**
 * Launch the application
 */
fun main() {
    FlatDarkLaf.setup()     // Flat, dark look-and-feel
    val app = App()         // Create the app model
    MainWindow(app)         // Create and show the UI, using the app model
}


/**
 * The application class (model)
 * This is the place where any application data should be
 * stored, plus any application logic functions
 */
class App() {
    // Constants defining any key values
    val locations = mutableListOf<Location>()
    var currentLocation: Int = 0

    init {
        // Add Locations to the list
        locations.add(Location("location0", null, mutableListOf(null, 1, null, null)))
        locations.add(Location("location1", null, mutableListOf(null, null, 2, 0)))
        locations.add(Location("location2", null, mutableListOf(1, null, null, null)))
    }

    fun travel(dir: Int){
        currentLocation = locations[currentLocation].connections[dir]!!
        println("User traveled to ${locations[currentLocation].name}")
    }
}

class Location(val name: String, val desc: String?, val connections: MutableList<Int?>){
    // Locations can only have four connections, (left, up, right, down)
}


/**
 * Main UI window (view)
 * Defines the UI and responds to events
 * The app model should be passed as an argument
 */
class MainWindow(val app: App) : JFrame(), ActionListener {

    // Fields to hold the UI elements
    private lateinit var locationLabel: JLabel
    private lateinit var upButton: JButton
    private lateinit var downButton: JButton
    private lateinit var leftButton: JButton
    private lateinit var rightButton: JButton
    private lateinit var aButton: JButton
    private lateinit var bButton: JButton
    private lateinit var xButton: JButton
    private lateinit var yButton: JButton
    private lateinit var gameFrame: JLabel


    /**
     * Configure the UI and display it
     */
    init {
        configureWindow()               // Configure the window
        addControls()                   // Build the UI

        setLocationRelativeTo(null)     // Centre the window
        isVisible = true                // Make it visible

        updateView()                    // Initialise the UI
    }

    /**
     * Configure the main window
     */
    private fun configureWindow() {
        title = "Kotlin Swing GUI Demo"
        contentPane.preferredSize = Dimension(800, 400)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isResizable = false
        layout = null

        pack()
    }

    /**
     * Populate the UI with UI controls
     */
    private fun addControls() {
        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 36)

        locationLabel = JLabel("Location")
        locationLabel.horizontalAlignment = SwingConstants.CENTER
        locationLabel.bounds = Rectangle(250, 50, 300, 50)
        locationLabel.alignmentX = Component.CENTER_ALIGNMENT
        locationLabel.font = baseFont
        add(locationLabel)

        gameFrame = JLabel()
        gameFrame.horizontalAlignment = SwingConstants.CENTER
        gameFrame.bounds = Rectangle(250, 50, 300, 300)
        gameFrame.border = BorderFactory.createLineBorder(Color.lightGray, 1, true)
        gameFrame.font = baseFont
        add(gameFrame)

        upButton = JButton("⇡")
        upButton.bounds = Rectangle(100,125,50,50)
        upButton.font = baseFont
        upButton.addActionListener(this)     // Handle any clicks
        add(upButton)

        leftButton = JButton("⇠")
        leftButton.bounds = Rectangle(50,175,50,50)
        leftButton.font = baseFont
        leftButton.addActionListener(this)     // Handle any clicks
        add(leftButton)

        rightButton = JButton("⇢")
        rightButton.bounds = Rectangle(150,175,50,50)
        rightButton.font = baseFont
        rightButton.addActionListener(this)     // Handle any clicks
        add(rightButton)

        downButton = JButton("⇣")
        downButton.bounds = Rectangle(100,225,50,50)
        downButton.font = baseFont
        downButton.addActionListener(this)     // Handle any clicks
        add(downButton)

        xButton = JButton("X")
        xButton.bounds = Rectangle(650,125,50,50)
        xButton.font = baseFont
        xButton.addActionListener(this)     // Handle any clicks
        add(xButton)

        yButton = JButton("Y")
        yButton.bounds = Rectangle(600,175,50,50)
        yButton.font = baseFont
        yButton.addActionListener(this)     // Handle any clicks
        add(yButton)

        aButton = JButton("A")
        aButton.bounds = Rectangle(700,175,50,50)
        aButton.font = baseFont
        aButton.addActionListener(this)     // Handle any clicks
        add(aButton)

        bButton = JButton("B")
        bButton.bounds = Rectangle(650,225,50,50)
        bButton.font = baseFont
        bButton.addActionListener(this)     // Handle any clicks
        add(bButton)
    }


    /**
     * Update the UI controls based on the current state
     * of the application model
     */
    private fun updateView() {
        // Disable buttons when not usable
        if (dirAvailable(0)) leftButton.isEnabled  = true else leftButton.isEnabled  = false
        if (dirAvailable(1)) upButton.isEnabled    = true else upButton.isEnabled    = false
        if (dirAvailable(2)) rightButton.isEnabled = true else rightButton.isEnabled = false
        if (dirAvailable(3)) downButton.isEnabled  = true else downButton.isEnabled  = false

        locationLabel.text = app.locations[app.currentLocation].name
    }

    private fun dirAvailable(dir: Int): Boolean{
        return app.locations[app.currentLocation].connections[dir] != null
    }

    /**
     * Handle any UI events (e.g. button clicks)
     * Usually this involves updating the application model
     * then refreshing the UI view
     */
    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {
            leftButton -> app.travel(0)
            upButton -> app.travel(1)
            rightButton -> app.travel(2)
            downButton -> app.travel(3)
        }
        updateView()
    }

}

