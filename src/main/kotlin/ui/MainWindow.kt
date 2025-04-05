package org.example.ui

import javax.swing.JFrame
import javax.swing.WindowConstants

class MainWindow : JFrame() {
    init {
        title = "Graph GUI"
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        setSize(800, 600)
        add(GraphInputPanel())
    }
}