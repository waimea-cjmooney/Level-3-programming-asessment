/**
 * ==================================================================================
 * Programming Project for NCEA Level 3, Standard 91906
 * ----------------------------------------------------------------------------------
 * Project Name:   Aegis-9
 * Project Author: Corban Mooney
 * GitHub Repo:    https://github.com/waimea-cjmooney/Level-3-programming-assessment
 * ----------------------------------------------------------------------------------
 * Notes:
 * PROJECT NOTES HERE
 * ==================================================================================
 */



import com.formdev.flatlaf.FlatDarkLaf
import java.awt.*
import java.awt.event.*
import javax.swing.*
import kotlin.system.exitProcess


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
class App {
    // Constants defining any key values
    val locations = mutableListOf<Location>()
    val keys = mutableListOf(0)
    private val EXIT = 26

    // Extra variables
    var currentLocation = 0
    var lockedHere = false
    var win = false

    init {
        /**
        * Add Locations to the list
        * Order of connections: (west, north, east, south)
        */
        locations.add(Location("The Conveyor",           "A room sprawling with hundreds of inoperative experimental robots, and is the output for the factory.", mutableListOf(1, 4, 3, null)))           // 0
        locations[0].discovered = true

        locations.add(Location("Cleaning Corridor",      "A small corridor filled with cleaning carts and wet floor signs.", mutableListOf(null, 5, 0, 2)))             // 1
        locations.add(Location("The Broom Closet",       "A tiny closet filled with cleaning supplies, brooms, and mops.", mutableListOf(null, 1, null, null)))         // 2
        locations[2].items.add(Item("small key with the number 1 engraved hanging from a metal shelf.", "key", 1))

        locations.add(Location("Storage Room",           "A room filled with various testing equipment and gadgets.", mutableListOf(0, null, null, null)))              // 3
        locations[3].keyRequired = 1
        locations[3].items.add(Item("small key with the number 2 engraved in it lays on the concrete ground.", "key", 2))

        locations.add(Location("Right Corridor",          "A large corridor with a rank smell.", mutableListOf(5, null, 17, 0)))                                         // 4
        locations[4].keyRequired = 2

        locations.add(Location("Left Corridor",         "A large corridor with a small waste bin in the corner.", mutableListOf(null, 6, 4, 1)))                       // 5
        locations[5].keyRequired = 2

        locations.add(Location("Break Room 1",           "A room with five tables arranged in a circle covered in snacks.", mutableListOf(7, 22, null, 5)))             // 6
        locations[6].keyRequired = 3

        locations.add(Location("Break Room 2",           "A room with various board games scattered around 3 large tables.", mutableListOf(null, null, 6, 8)))          // 7
        locations.add(Location("Right Factory hallway",  null, mutableListOf(9, 7, null, null)))                                                                        // 8
        locations.add(Location("Left Factory hallway",   null, mutableListOf(null, 12, 8, 10)))                                                                         // 9
        locations.add(Location("Factory room",    "A room filled with large clanging machinery", mutableListOf(11, 9, null, null)))                                     // 10
        locations[10].items.add(Item("small key with the number 4 engraved in it", "key", 4))

        locations.add(Location("Back Office",    "A messy office room missing a name tag.", mutableListOf(null, 12, 10, 25)))                                           // 11
        locations[11].keyRequired = 4

        locations.add(Location("Side Office",    "A clean office room with a name tag on the desk, it reads \"Elizabeth.\"", mutableListOf(11, 13, null, 9)))              // 12
        locations[12].keyRequired = 4

        locations.add(Location("Fire Exit",    null, mutableListOf(null, null, 14, 12)))           // 13
        locations.add(Location("Tour Room 1",    null, mutableListOf(13, 24, 15, null)))             // 14
        locations.add(Location("Tour Room 2",    null, mutableListOf(14, 16, null, null)))           // 15
        locations.add(Location("Tour Room 3",    null, mutableListOf(24, null, 22, 15)))             // 16
        locations.add(Location("Product Office",    null, mutableListOf(4, 20, 18, null)))              // 17
        locations.add(Location("Programming",    "Neatly arranged computers, all glowing. one of them has a sticky note which reads \"Password: password\"", mutableListOf(17, 19, null, null)))           // 18
        locations[18].keyRequired = 3

        locations.add(Location("Complaints Office",    null, mutableListOf(20, 21, null, 18)))             // 19
        locations[19].keyRequired = 3

        locations.add(Location("Main Office",    null, mutableListOf(22, null, 19, 17)))             // 20
        locations[20].items.add(Item("small key with the number 3 engraved in it", "key", 3))

        locations.add(Location("Entrance Hallway",    "", mutableListOf(22, 23, null, 19)))             // 21
        locations[21].keyRequired = 5
        locations[21].items.add(Item("large key card with the number 6 printed on it", "key", 6))

        locations.add(Location("Main Room",    null, mutableListOf(6, 16, 21, 20)))                // 22
        locations[22].keyRequired = 5

        locations.add(Location("Main Entrance", null, mutableListOf(null, EXIT, null, 21)))           // 23
        locations[23].keyRequired = 6

        locations.add(Location("Tour Room 4",    null, mutableListOf(null, null, 16, 14)))           // 24
        locations[24].items.add(Item("small key with the number 5 engraved in it", "key", 5))

        locations.add(Location("Vine Room",     "How did you get here...?", mutableListOf(null, 11, null, null)))         // 25
        locations[25].keyRequired = 6

    }

    fun travel(dir: Int){
        if (locations[currentLocation].connections[dir] == EXIT){
            println("GAME WIN")
            win = true
        } else if (dirAvailable(dir)){
            currentLocation = locations[currentLocation].connections[dir]!!
            locations[currentLocation].discovered = true
            lockedHere = false

            println("User traveled to ${locations[currentLocation].name}")
        } else if (dirAvailable(dir, true)) {
            lockedHere = true
        }
    }

    fun dirAvailable(dir: Int, ignoreLocks: Boolean = false): Boolean{
        if (locations[currentLocation].connections[dir] == EXIT) return true
        return if (ignoreLocks) {
            // This direction exists (Could be Locked)
            locations[currentLocation].connections[dir] != null
        } else {
            if (whatAtDir(dir) != "Nothing") {
                // Direction exists, and return true if unlocked else false
                return keys.contains(locations[whatAtDir(dir, true).toInt()].keyRequired)
            } else {
                // Direction unavailable
                return false
            }
        }
    }

    fun whatAtDir(dir: Int, returnIndex: Boolean = false): String{
        // Return "Nothing" if there is no connection here
        return if (locations[currentLocation].connections[dir] == null) "Nothing"

        else if (!returnIndex){

            if (locations[currentLocation].connections[dir] == EXIT) "Exit"

            /**
            * Return name of a location if it has been discovered
            * This was so hard to bugfix :(
            */
            else if (locations[locations[currentLocation].connections[dir]!!].discovered) {
                locations[locations[currentLocation].connections[dir]!!].name
            } else {
                "???"
            }
        } else {
            if (locations[currentLocation].connections[dir] == EXIT) "Nothing"

            // Return the index as a string
            else locations[currentLocation].connections[dir]!!.toString()
        }
    }
}

class Location(val name: String, val desc: String? = null, val connections: MutableList<Int?>, val items: MutableList<Item?> = mutableListOf(), var keyRequired: Int = 0){
    // Locations can only have four connections, (west, north, east, south)
    var discovered: Boolean = false
}

class Item(val name: String, val type: String?, val data: Int? = null)

/**
 * Main UI window (view)
 * Defines the UI and responds to events
 * The app model should be passed as an argument
 */
class MainWindow(private val app: App) : JFrame(), ActionListener {

    // Fields to hold the UI elements
    private lateinit var locationLabel: JLabel
    private lateinit var upButton:      JButton
    private lateinit var downButton:    JButton
    private lateinit var leftButton:    JButton
    private lateinit var rightButton:   JButton
    private lateinit var aButton:       JButton
    private lateinit var bButton:       JButton
    private lateinit var xButton:       JButton
    private lateinit var yButton:       JButton
    private lateinit var gameFrame:     JLabel
    private lateinit var desc:          JLabel
    private lateinit var tutButton:     JButton

    // Dialogs
    private lateinit var tutorial: TutorialPopup
    private lateinit var win: WinPopup


    // Configure the UI and display it
    init {
        configureWindow()               // Configure the window
        addControls()                   // Build the UI

        setLocationRelativeTo(null)     // Centre the window
        isVisible = true                // Make it visible

        updateView()                    // Initialise the UI
    }


    // Configure the main window
    private fun configureWindow() {
        title = "Aegis-9"
        contentPane.preferredSize = Dimension(800, 400)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isResizable = false
        layout = null

        pack()
    }


    // Populate the UI with UI controls
    private fun addControls() {

        tutorial = TutorialPopup()
        win = WinPopup()

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

        desc = JLabel()
        desc.bounds = Rectangle(255, 70, 300, 300)
        locationLabel.alignmentY = Component.TOP_ALIGNMENT
        desc.font = Font(Font.SANS_SERIF, Font.PLAIN, 13)
        add(desc)

        tutButton = JButton("?")
        tutButton.bounds = Rectangle( 50, 275, 50, 50)
        tutButton.font = baseFont
        tutButton.addActionListener(this)     // Handle any clicks
        add(tutButton)
    }

    /**
     * Update the UI controls based on the current state
     * of the application model
     */
    private fun updateView() {
        // Disable buttons when not usable
        leftButton.isEnabled = app.dirAvailable(0, true)
        leftButton.text = if (!app.dirAvailable(0, false) && app.dirAvailable(0, true)) "\uD83D\uDD12" else "⇠"

        upButton.isEnabled = app.dirAvailable(1, true)
        upButton.text = if (!app.dirAvailable(1, false) && app.dirAvailable(1, true)) "\uD83D\uDD12" else "⇡"

        rightButton.isEnabled = app.dirAvailable(2, true)
        rightButton.text = if (!app.dirAvailable(2, false) && app.dirAvailable(2, true)) "\uD83D\uDD12" else "⇢"

        downButton.isEnabled = app.dirAvailable(3, true)
        downButton.text = if (!app.dirAvailable(3, false) && app.dirAvailable(3, true)) "\uD83D\uDD12" else "⇣"

        // Shows name of room
        locationLabel.text = app.locations[app.currentLocation].name

        desc.text = "<html>"
        // If the user tries a locked door
        desc.text += if (app.lockedHere) "The door doesn't budge${if (app.keys.lastIndex != 0) ", and none of your keys fit in the lock." else "."}<br/>" else ""

        // Room description
        desc.text += "${if (app.locations[app.currentLocation].desc != null) app.locations[app.currentLocation].desc else "" } <br/>"

        // Shows Items that are in the room
        desc.text += "${if (app.locations[app.currentLocation].items.isNotEmpty()) "A ${app.locations[app.currentLocation].items[0]!!.name} [X] Pick up" else "Nothing in here"}<br/><br/>"

        // Shows possible movement options as well as what those rooms are if the user has unlocked them
        desc.text += "To West: ${app.whatAtDir(0)}<br/>To North: ${app.whatAtDir(1)}<br/>To East: ${app.whatAtDir(2)}<br/>To South: ${app.whatAtDir(3)}<br/>"

        // Shows keys you own
        if (app.keys.size > 1) for (i in app.keys) desc.text += if (i != 0) app.keys[i].toString() + " " else "Keys: "
        desc.text += "</html>"

        if (app.win) {
            win.isVisible = true
            isVisible = false
            exitProcess(0)
        }
    }


    /**
     * Handle any UI events (e.g. button clicks)
     * Usually this involves updating the application model
     * then refreshing the UI view
     */
    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {
            leftButton  -> app.travel(0)
            upButton    -> app.travel(1)
            rightButton -> app.travel(2)
            downButton  -> app.travel(3)
            xButton     -> {
                if (app.locations[app.currentLocation].items.isNotEmpty()) {
                    // If the item is a key, add the key to the keys list
                    if (app.locations[app.currentLocation].items[0]!!.type == "key"){
                        app.keys.add(app.locations[app.currentLocation].items[0]!!.data!!)
                    }
                    app.locations[app.currentLocation].items.removeAt(0)
                }
            }
            tutButton   -> tutorial.isVisible = true
        }
        updateView()
    }

}

class TutorialPopup(): JDialog() {
    /**
     * Configure the UI
     */
    init {
        configureWindow()
        addControls()
        setLocationRelativeTo(null)     // Centre the window

        isVisible = true
    }

    /**
     * Set up the dialog window
     */
    private fun configureWindow() {
        title = "Tutorial"
        contentPane.preferredSize = Dimension(400, 200)
        isResizable = false
        isModal = true
        layout = null
        pack()
    }

    /**
     * Populate the window with controls
     */
    private fun addControls() {
        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 16)

        // Adding <html> to the label text allows it to wrap
        val message = JLabel("<html>TUTORIAL<br/>" +
                "The movement buttons are on the left of the main window, use them to, yknow, move?<br/>" +
                "The [X] Button is on the right of the main window, it is used to pickup Items" +
                "<br/><br/>" +
                "Close this window to dismiss.")
        message.bounds = Rectangle(25, 25, 350, 150)
        message.verticalAlignment = SwingConstants.TOP
        message.font = baseFont
        add(message)
    }

}

class WinPopup(): JDialog() {
    /**
     * Configure the UI
     */
    init {
        configureWindow()
        addControls()
        setLocationRelativeTo(null)     // Centre the window
    }

    /**
     * Set up the dialog window
     */
    private fun configureWindow() {
        title = "You win!"
        contentPane.preferredSize = Dimension(400, 200)
        isResizable = false
        isModal = true
        layout = null
        pack()
    }

    /**
     * Populate the window with controls
     */
    private fun addControls() {
        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 16)

        // Adding <html> to the label text allows it to wrap
        val message = JLabel("<html>You Escaped!")
        message.bounds = Rectangle(25, 25, 350, 150)
        message.verticalAlignment = SwingConstants.TOP
        message.font = baseFont
        add(message)
    }

}